package sinc.tests.elastic;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

public class SearchUsingscrolls {
	public static void main(String[] args) {
		Client client = ElasticClientFactory.getClient();
		QueryBuilder qb = QueryBuilders.matchAllQuery();
		SearchResponse scrollResp = client.prepareSearch("ovowarn-2017-02-09").setSearchType(SearchType.SCAN)
				.setScroll(new TimeValue(5000)).setQuery(qb).setSize(10).execute().actionGet();
		while (true) {
			for (SearchHit hit : scrollResp.getHits().getHits()) {
				System.out.println(hit.getSourceAsString());
			}
			scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(5000)).execute()
					.actionGet();
			if (scrollResp.getHits().getHits().length == 0) {
				break;
			}
		}
	}
}
