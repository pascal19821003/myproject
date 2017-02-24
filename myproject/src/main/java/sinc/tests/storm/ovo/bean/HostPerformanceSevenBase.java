package sinc.tests.storm.ovo.bean;

public class HostPerformanceSevenBase {
	private String department;
	private String hostname;
	private long timemillisstr;
	private String datestr;
	private String timestr;
	private double cpuavg;
	private double cpupeak;
	private double cpufloor;
	private double memoryavg;
	private double memorypeak;
	private double memoryfloor;
	private double diskpeakavg;
	private double diskpeakpeak;
	private double diskpeakfloor;

	public HostPerformanceSevenBase() {
		super();
	}

	public HostPerformanceSevenBase(String department, String hostname, long timemillisstr, String datestr,
			String timestr, double cpuavg, double cpupeak, double cpufloor, double memoryavg, double memorypeak,
			double memoryfloor, double diskpeakavg, double diskpeakpeak, double diskpeakfloor) {
		this.department = department;
		this.hostname = hostname;
		this.timemillisstr = timemillisstr;
		this.datestr = datestr;
		this.timestr = timestr;
		this.cpuavg = cpuavg;
		this.cpupeak = cpupeak;
		this.cpufloor = cpufloor;
		this.memoryavg = memoryavg;
		this.memorypeak = memorypeak;
		this.memoryfloor = memoryfloor;
		this.diskpeakavg = diskpeakavg;
		this.diskpeakpeak = diskpeakpeak;
		this.diskpeakfloor = diskpeakfloor;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public long getTimemillisstr() {
		return timemillisstr;
	}

	public void setTimemillisstr(long timemillisstr) {
		this.timemillisstr = timemillisstr;
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

	public double getCpuavg() {
		return cpuavg;
	}

	public void setCpuavg(double cpuavg) {
		this.cpuavg = cpuavg;
	}

	public double getCpupeak() {
		return cpupeak;
	}

	public void setCpupeak(double cpupeak) {
		this.cpupeak = cpupeak;
	}

	public double getCpufloor() {
		return cpufloor;
	}

	public void setCpufloor(double cpufloor) {
		this.cpufloor = cpufloor;
	}

	public double getMemoryavg() {
		return memoryavg;
	}

	public void setMemoryavg(double memoryavg) {
		this.memoryavg = memoryavg;
	}

	public double getMemorypeak() {
		return memorypeak;
	}

	public void setMemorypeak(double memorypeak) {
		this.memorypeak = memorypeak;
	}

	public double getMemoryfloor() {
		return memoryfloor;
	}

	public void setMemoryfloor(double memoryfloor) {
		this.memoryfloor = memoryfloor;
	}

	public double getDiskpeakavg() {
		return diskpeakavg;
	}

	public void setDiskpeakavg(double diskpeakavg) {
		this.diskpeakavg = diskpeakavg;
	}

	public double getDiskpeakpeak() {
		return diskpeakpeak;
	}

	public void setDiskpeakpeak(double diskpeakpeak) {
		this.diskpeakpeak = diskpeakpeak;
	}

	public double getDiskpeakfloor() {
		return diskpeakfloor;
	}

	public void setDiskpeakfloor(double diskpeakfloor) {
		this.diskpeakfloor = diskpeakfloor;
	}

}
