package sinc.tests.flume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;


public class HostPerformanceInterceptor implements Interceptor {
	private Map<String, String> headers;
	private static ConcurrentMap<String, String> cache;

	public void initialize() {
		headers = new HashMap<String, String>();
		MysqlUtil mysqlUtil = new MysqlUtil();
		cache = mysqlUtil.getSystemNames();
	}

	public Event intercept(Event event) {
		headers.clear();
		event.getHeaders().clear();
		String eventBody = new String(event.getBody());
		String[] ebs = eventBody.trim().split(",");
		headers.put("department", "上海");

		String sysname = ebs[0];
		if (sysname.contains(".")) {
			String[] pre = sysname.split("\\.");
			sysname = cache.get(pre[0].toUpperCase());

		} else {
			sysname = cache.get(sysname.toUpperCase());
		}
		if (sysname != null) {
			headers.put("systemname", sysname);
		} else {
			headers.put("systemname", "UNKNOWN");
		}
		event.setHeaders(headers);
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

	public static class Builder implements Interceptor.Builder {
		public Interceptor build() {
			return new HostPerformanceInterceptor();
		}

		public void configure(Context context) {
		}
	}
}
