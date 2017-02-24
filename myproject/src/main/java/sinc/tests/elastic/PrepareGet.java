package sinc.tests.elastic;

import java.util.Date;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;

public class PrepareGet {
	public static void main(String[] args) {
		long lt = new Long("1486552380000");
		Date date = new Date(lt);

		Client client = ElasticClientFactory.getClient();
		GetResponse response = client.prepareGet(".kibana", "index-pattern", "*test").execute().actionGet();
		System.out.println(response.getSourceAsString());
		ElasticClientFactory.closeClient();
	}

}
