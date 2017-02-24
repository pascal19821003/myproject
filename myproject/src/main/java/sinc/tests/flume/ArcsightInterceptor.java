package sinc.tests.flume;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;


public class ArcsightInterceptor implements Interceptor {
	private static ConcurrentMap<String, String> cache;

	public ArcsightInterceptor() {
		super();
	}

	public void initialize() {
		MysqlUtil mysqlUtil = new MysqlUtil();
		cache = mysqlUtil.getSystemNames();
	}

	public Event intercept(Event event) {
		event.getHeaders().clear();
		event.getHeaders().put("department", "上海");
		
		
		String eventBody = new String(event.getBody());
		try {
			String[] tmp = eventBody.split("\\|");
			
			
			if(eventBody.contains("DIRECT")&&eventBody.contains("GET ")){
				event.getHeaders().put("logType", "ARCSIGHTEBANK");
			}else{
				event.getHeaders().put("logType", "ARCSIGHTLOG");
			}
			
			event.getHeaders().put("topType", tmp[1]);
			event.getHeaders().put("secType", tmp[2]);
			
			int start1 = eventBody.indexOf("rawEvent=");
			if (start1 == -1) {
				// System.out.println("DEBUG-----EventBody : " + eventBody);
				throw new Exception("Invalid format,no rawEvent found");
			}
			Pattern p = Pattern.compile(" \\w+[^\\\\]=");
			String str = eventBody.substring(start1 + 9);
			Matcher m = p.matcher(str);
			String rawEvent = null;
			if (m.find()) {
				rawEvent = str.substring(0, m.start());
			} else {
				// System.out.println("DEBUG-----EventBody : " + eventBody);
				throw new Exception("Invalid format,rawEvent:no matches");
			}

			rawEvent = rawEvent.replace("\\r\\n", "").replace("\\n", "").replaceAll("</?[^>]+>", "").replace("\\=", "=").trim();
			event.setBody(rawEvent.getBytes());

			Pattern p2 = Pattern.compile("( art=)([^ ]+)( .*)");
			Matcher m2 = p2.matcher(eventBody);
			String art = null;
			if (m2.find()) {
				art = m2.group(2);
			} else {
				// System.out.println("DEBUG-----EventBody : " + eventBody);
				throw new Exception("Invalid format,art:no matches");
			}

			event.getHeaders().put("art", art);

			Pattern p3 = Pattern.compile("( dvchost=)([^ ]+)( .*)");
			Matcher m3 = p3.matcher(eventBody);
			String dvchost = null;
			if (m3.find()) {
				dvchost = m3.group(2);
			} else {
				// System.out.println("DEBUG-----EventBody : " + eventBody);
				throw new Exception("Invalid format,dvchost: no matches");
			}
			event.getHeaders().put("dvchost", dvchost);

			String sysname = null;
			if (dvchost.contains(".")) {
				String[] pre = dvchost.split("\\.");
				sysname = cache.get(pre[0].toUpperCase());

			} else {
				sysname = cache.get(dvchost.toUpperCase());
			}
			if (sysname == null) {
				sysname = "UNKNOWN";
			}
			event.getHeaders().put("systemname", sysname);

			Pattern p4 = Pattern.compile("( dvc=)([^ ]+)( .*)");
			Matcher m4 = p4.matcher(eventBody);
			String dvc = null;
			if (m4.find()) {
				dvc = m4.group(2);
			} else {
				// System.out.println("DEBUG-----EventBody : " + eventBody);
				throw new Exception("Invalid format,dvc: no matches");
			}
			event.getHeaders().put("dvc", dvc);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

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
			return new ArcsightInterceptor();
		}

		public void configure(Context context) {
		}
	}

}
