package sinc.tests.flume;

import java.util.List;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class TestInterceptors implements Interceptor {
	private static Logger logger = LoggerFactory.getLogger(TestInterceptors.class);
	private String sysCode;
	private String hostAddress;
	private String serviceName;

	public TestInterceptors(String sysCode, String hostAddress, String serviceName) {
		this.sysCode = sysCode;
		this.hostAddress = hostAddress;
		this.serviceName = serviceName;
	}

	public void initialize() {
	}

	public Event intercept(Event event) {
		event.getHeaders().put("sysCode", this.sysCode.toLowerCase());

		event.getHeaders().put("hostaddress", this.hostAddress);

		if (!"".equals(this.serviceName)) {
			event.getHeaders().put("serviceName", this.serviceName);
		}

		event.getHeaders().put("estimestamp", new DateTime().toString());
		return event;
	}

	public List<Event> intercept(List<Event> events) {
		List out = Lists.newArrayList();
		for (Event event : events) {
			Event outEvent = intercept(event);
			if (outEvent != null) {
				out.add(outEvent);
			}
		}
		return out;
	}

	public void close() {
	}

	public static class Builder implements Interceptor.Builder {
		private String sysCode;
		private String hostAddress;
		private String serviceName;

		public void configure(Context context) {
			this.sysCode = context.getString("sysCode");

			this.hostAddress = context.getString("hostAddress");

			this.serviceName = context.getString("serviceName", "");

			Preconditions.checkNotNull(this.sysCode);

			Preconditions.checkNotNull(this.hostAddress);
		}

		public Interceptor build() {
			TestInterceptors.logger
					.info("--------------------------- ClientInterceptor start... -----------------------------");
			return new TestInterceptors(this.sysCode, this.hostAddress, this.serviceName);
		}
	}

	private static class Constants {
	}
}