package sinc.tests.storm.ovo.elastic;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ElasticClientFactory {

	private static TransportClient client;

	public static TransportClient getClient(){
		if(client == null){
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch-cluster").build();
		client = new TransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress("10.100.74.58", 9300))
				.addTransportAddress(new InetSocketTransportAddress("10.100.74.59", 9300))
				.addTransportAddress(new InetSocketTransportAddress("10.100.74.60", 9300))
				.addTransportAddress(new InetSocketTransportAddress("10.100.74.61", 9300));
		return client;
		}else{
			return client;
		}
	}
	public static void closeClient(){
		if(client !=null){
			client.close();
		}
	}
}
