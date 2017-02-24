package sinc.tests.elastic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

// hostname epayap05 timemillisstr 1486534080000
public class SearchUsintMatchQuery {
private static ActionFuture<GetIndexResponse> prepareGetIndex;

//	SimpleDateFormat sf = new SimpleDateFormat("", Locale.US)
	public static void main(String[] args) throws ParseException {
		long lt = new Long("1487807700000");
		Date date = new Date(lt);
		
//		Client client = ElasticClientFactory.getClient();
//		QueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("hostname", "mbankap04"))
//				.must(QueryBuilders.termQuery("datestr", "2017-02-13"))
//				.must(QueryBuilders.termQuery("timestr", "13:10:00"));
//
//		SearchResponse sr = client.prepareSearch(".kibana").setTypes("host7base").setQuery(qb).setSize(1)
//				.execute().actionGet();
//		System.out.println(sr.getHits().totalHits());
//		for (SearchHit hit : sr.getHits()) {
//			System.out.println(hit.getSourceAsString());
//		}
//		
//		GetIndexRequest gir = new GetIndexRequest();
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
//		Date date1 = sdf.parse("1487807700000");
		
		
		
		System.out.println(date);
//		
		QueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("timemillisstr", sdf.format(date)));
		Client client = ElasticClientFactory.getClient();
		SearchResponse sr = client.prepareSearch("ovohostperformance-2017-02-23").setTypes("hostinfo").setQuery(qb).execute().actionGet();
		
		
		for(SearchHit hit: sr.getHits()){
			System.out.println(hit.getSourceAsString());
		}
		
		
		
		ElasticClientFactory.closeClient();
	}
}
