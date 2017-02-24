package sinc.tests.storm.ovo.kafka;

import java.util.Arrays;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
public class TestKafka {

	public static void main(String[] args) {
		BrokerHosts bh = new ZkHosts("10.100.74.62:2181,10.100.74.61:2181,10.100.74.60:2181,10.100.74.59:2181,10.100.74.58:2181");
		SpoutConfig sc = new SpoutConfig(bh, "ovohostperformance", "/storm", "ovohostperformance");
		sc.zkServers = Arrays.asList(new String[] {"10.100.74.62","10.100.74.61","10.100.74.60","10.100.74.59","10.100.74.58"});
		sc.zkPort = 2181;
		sc.scheme = new SchemeAsMultiScheme(new StringScheme());
		sc.forceFromStart = false;

		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("kafka-spout", new KafkaSpout(sc));

		builder.setBolt("KafkaBlot", new KafkaBlot()).shuffleGrouping("kafka-spout");
		
		Config conf = new Config();
		try {
			StormSubmitter.submitTopology("ovohostperformance-warnmail", conf, builder.createTopology());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
