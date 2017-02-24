package sinc.tests.storm.ovo.kafka;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import sinc.tests.storm.ovo.bean.HostPerformance;
import sinc.tests.storm.ovo.bean.HostPerformanceSevenBase;
import sinc.tests.storm.ovo.elastic.ElasticClientFactory;
import sinc.tests.storm.ovo.util.MysqlUtil;

public class KafkaBlot implements IRichBolt {
	private static final long serialVersionUID = 1L;
	// private TopologyContext context;
	private OutputCollector collector;
	private static TransportClient client;
	private static ObjectMapper mapper;
	private static SimpleDateFormat sdf;
	// private static SimpleMailSender sms;
	private static ConcurrentMap<String, String> cache;

	public void prepare(@SuppressWarnings("rawtypes") Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		mapper = new ObjectMapper();
		client = ElasticClientFactory.getClient();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		MysqlUtil mysqlUtil = new MysqlUtil();
		cache = mysqlUtil.getSystemNames();
		mysqlUtil.deconn();
		// sms = new SimpleMailSender();
	}

	public void execute(Tuple input) {
		try {
			HostPerformance hf = new HostPerformance(input);

			QueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("hostname", hf.getHostname()))
					.must(QueryBuilders.termQuery("datestr", hf.getDatestr()))
					.must(QueryBuilders.termQuery("timestr", hf.getTimestr()));
			System.out.println("--------------------------------------------------------------------------");
			SearchResponse sr = client.prepareSearch("ovohostperformance_7base-" + hf.getDatestr())
					.setTypes("host7base").setQuery(qb).setSize(1).execute().actionGet();

			if (sr.getHits().totalHits() > 0) {
				HostPerformanceSevenBase hpsb = mapper.readValue(sr.getHits().getHits()[0].getSourceAsString(),
						HostPerformanceSevenBase.class);
				boolean cup_ok = hf.getCup() >= hpsb.getCpufloor() && hf.getCup() <= hpsb.getCpupeak();
				boolean memory_ok = hf.getMemory() >= hpsb.getMemoryfloor() && hf.getMemory() <= hpsb.getMemorypeak();
				boolean diskpeak_ok = hf.getDiskpeak() >= hpsb.getDiskpeakfloor()
						&& hf.getDiskpeak() <= hpsb.getDiskpeakpeak();
				String content = hf.getHostname() + "," + hf.getDatestr() + " " + hf.getTimestr() + ":";
				if (!cup_ok) {
					content = content + " CPU is abnormal,current:" + hf.getCup() + " floor:" + hpsb.getCpufloor()
							+ " peak:" + hpsb.getCpupeak() + "; ";
				}
				if (!memory_ok) {
					content = content + " Memory is abnormal,current:" + hf.getMemory() + " floor:"
							+ hpsb.getMemoryfloor() + " peak:" + hpsb.getMemorypeak() + "; ";
				}
				if (!diskpeak_ok) {
					content = content + " Diskpeak is abnormal,current:" + hf.getDiskpeak() + " floor:"
							+ hpsb.getDiskpeakfloor() + " peak:" + hpsb.getDiskpeakpeak();
				}
				if (!(cup_ok && memory_ok && diskpeak_ok)) {
					// String[] toAddresses = {"",""};
					// sms.sendTextMail(hf.getHostname()+"is abnormal!",
					// content, toAddresses);
					System.out.println(content);
					String systemname = cache.get(hf.getHostname().toUpperCase());

					IndexResponse response = client.prepareIndex("ovohostpermailwarn_" + hf.getDatestr(), "mailwarn")
							.setSource(XContentFactory.jsonBuilder().startObject().field("department", "上海")
									.field("systemname", systemname != null ? systemname : "UNKNOWN")
									.field("hostname", hf.getHostname())
									.field("timemillisstr",
											Long.toString(sdf.parse(hf.getDatestr() + " " + hf.getTimestr()).getTime()))
									.field("datestr", hf.getDatestr()).field("timestr", hf.getTimestr())
									.field("message", content).endObject())
							.execute().actionGet();
					if (response.isCreated()) {
						System.out.println("Success create document!");
					}

				} else {
					System.out.println("##########################" + hf.getHostname() + " "
							+ hf.getDatestr().concat(" " + hf.getTimestr()) + "###################Got and ok");
				}
			} else {
				System.out.println("##########################" + hf.getHostname() + " "
						+ hf.getDatestr().concat(" " + hf.getTimestr()) + "###################not found");
			}
			collector.ack(input);
		} catch (Exception e) {
			System.out.println("------------------------------EXCEPTION------------------------------");
			System.out.println(input.getString(0));
			e.printStackTrace();
		}
	}

	public void cleanup() {
		System.out.println("---------------------cleanupisrunning---------------------------------------");
		client.close();
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
