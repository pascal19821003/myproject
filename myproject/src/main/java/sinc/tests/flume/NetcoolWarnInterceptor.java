package sinc.tests.flume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;


public class NetcoolWarnInterceptor implements Interceptor {
	private Map<String, String> headers;
	private static ConcurrentHashMap<String, String> cache;
	public NetcoolWarnInterceptor() {
		super();
	}
	public void initialize() {
		headers = new HashMap<String, String>();
		MysqlUtil mysqlUtil = new MysqlUtil();
		cache = mysqlUtil.getSystemNames();
	}
	public Event intercept(Event event) {
		System.out.println("----------------------------------------------------------------------");
		System.out.println(new String(event.getBody()));
//		try {
//			event.getHeaders().clear();
//			headers.put("logType", "OVOWARN");
//			headers.put("department", "上海");
//			String body = new String(event.getBody());
//			int find = body.indexOf("\"node_alias\":");
//			String hostname = null;
//			if (find != -1) {
//				String tmp1 = body.substring(find + 13, body.length());
//				hostname = tmp1.substring(1, tmp1.indexOf("\","));
//			} else {
//				throw new Exception("No node_alias found!");
//			}
//			String sysname = null;
//			if (hostname.contains(".")) {
//				String[] pre = hostname.split("\\.");
//				sysname = cache.get(pre[0].toUpperCase());
//			} else {
//				sysname = cache.get(hostname.toUpperCase());
//			}
//			if (sysname != null) {
//				headers.put("systemname", sysname);
//			} else {
//				headers.put("systemname", "UNKNOWN");
//			}
//			for (String a : headers.keySet()) {
//				System.out.print("#############key: " + a + " value: " + headers.get(a));
//			}
//			event.setHeaders(headers);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
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
			return new NetcoolWarnInterceptor();
		}

		public void configure(Context context) {
		}
	}
}
