package sinc.tests.flume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

public class TestInterceptor implements Interceptor {
	public TestInterceptor() {
		super();
	}
	public void initialize() {
	}

	public Event intercept(Event event) {
		Map<String,String> header = event.getHeaders();
		for(String a: header.keySet()){
			System.out.print("key: "+ a + " value: " + header.get(a));
		}
		String body = new String(event.getBody());
		System.out.println("body:" + body);
		return event;
	}

	public List<Event> intercept(List<Event> events) {
		List<Event> returnEvents = new ArrayList<Event>();
		for (Event event : events) {
			returnEvents.add(intercept(event));
		}
		return returnEvents;
	}

	public void close() {
	}

	public String parseArcsight(String eventBody) {
		String returnStr = null;
		
		return returnStr;
	}

	public static class Builder implements Interceptor.Builder {
		public Interceptor build() {
			return new TestInterceptor();
		}
		public void configure(Context context) {
		}
	}

}
