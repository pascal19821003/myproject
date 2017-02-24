package sinc.ztestonly;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Test4 {
	private static SimpleDateFormat fm2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);

	public static void main(String[] args) throws ParseException {
		// Date date = fm2.parse(Long.parseLong(1486552380000));
//		long lt = new Long("1486552380000");
//		Date date = new Date(lt);
//		System.out.println(fm2.format(date));
//		
//		String dates="02/05/2016".replace("/", "-");
//		if(dates.indexOf("-")==2){
//			String datetmp[]=dates.split("-");
//			dates=datetmp[2]+"-"+datetmp[0]+"-"+datetmp[1];
//		}
//		
//		System.out.println(dates);

	String timestr = "2017-02-15 12:45:00";
	System.out.println(fm2.parse(timestr).getTime());
	//1487090700000

	}
}
