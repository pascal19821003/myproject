package sinc.tests.storm.ovo.bean;

import backtype.storm.tuple.Tuple;

public class HostPerformance {
	private String hostname;
	private String datestr;
	private String timestr;
	private double cup;
	private double memory;
	private double diskpeak;

	public HostPerformance() {
		super();
	}

	public HostPerformance(Tuple input) {
		String[] ti = input.getString(0).split(",");

		String reg = "^[0-9]*[1-9][0-9]*$";
		hostname = ti[0].trim();
		int end = ti[0].indexOf(".");
		if (end != -1) {
			if (!(ti[0].substring(end + 1, end + 2).matches(reg))) {
				hostname = ti[0].substring(0, end);
			}
		}
		this.datestr = ti[1].trim().replace("/", "-");

		if (this.datestr.indexOf("-") == 2) {
			String datetmp[] = this.datestr.split("-");
			this.datestr = datetmp[2] + "-" + datetmp[0] + "-" + datetmp[1];
		}

		this.timestr = ti[2].trim();
		this.cup = Double.parseDouble(ti[3].trim());
		this.memory = Double.parseDouble(ti[4].trim());
		this.diskpeak = Double.parseDouble(ti[5].trim());
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getDatestr() {
		return datestr;
	}

	public void setDatestr(String datestr) {
		this.datestr = datestr;
	}

	public String getTimestr() {
		return timestr;
	}

	public void setTimestr(String timestr) {
		this.timestr = timestr;
	}

	public double getCup() {
		return cup;
	}

	public void setCup(double cup) {
		this.cup = cup;
	}

	public double getMemory() {
		return memory;
	}

	public void setMemory(double memory) {
		this.memory = memory;
	}

	public double getDiskpeak() {
		return diskpeak;
	}

	public void setDiskpeak(double diskpeak) {
		this.diskpeak = diskpeak;
	}
}
