package sinc.ztestonly;

public class Test5 {

	public static void main(String[] args) {
		
		String txt="abc.spdb.com,2017/01/01,10:35:00,10,10,10,";
		String ti[] = txt.split(",");
		// TODO Auto-generated method stub
		String reg = "^[0-9]*[1-9][0-9]*$";
		String hostname = ti[0].trim();
		int end = ti[0].indexOf(".");
		if (end != -1) {
			if (!(ti[0].substring(end + 1, end + 2).matches(reg))) {
				hostname = ti[0].substring(0, end);
			}
		}
		System.out.println(hostname);
	}

}
