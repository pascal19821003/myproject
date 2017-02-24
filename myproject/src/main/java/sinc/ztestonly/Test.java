package sinc.ztestonly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) throws Exception {
		File[] files = listfiles();
		for (File file : files) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				
				while ((tempString = reader.readLine()) != null) {
					Map map = new HashMap();
					
					String eventBody = tempString;
						
						String[] tmp = tempString.split("\\|");
						System.out.println(tmp[1] + " " + tmp[2]) ;
						
						
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

						rawEvent = rawEvent.replace("\n", "").replaceAll("</?[^>]+>", "").replace("\\=", "=").trim();
						System.out.println(rawEvent);
//						Pattern p2 = Pattern.compile("( art=)([^ ]+)( .*)");
//						Matcher m2 = p2.matcher(eventBody);
//						String art = null;
//						if (m2.find()) {
//							art = m2.group(2);
//						} else {
//							// System.out.println("DEBUG-----EventBody : " + eventBody);
//							throw new Exception("Invalid format,art:no matches");
//						}
//
//						map.put("art", art);
//
//						Pattern p3 = Pattern.compile("( dvchost=)([^ ]+)( .*)");
//						Matcher m3 = p3.matcher(eventBody);
//						String dvchost = null;
//						if (m3.find()) {
//							dvchost = m3.group(2);
//						} else {
//							// System.out.println("DEBUG-----EventBody : " + eventBody);
//							throw new Exception("Invalid format,dvchost: no matches");
//						}
//						map.put("dvchost", dvchost);
//
//						String sysname = null;
//						if (sysname == null) {
//							sysname = "UNKNOWN";
//						}
//						map.put("systemname", sysname);
//
//						Pattern p4 = Pattern.compile("( dvc=)([^ ]+)( .*)");
//						Matcher m4 = p4.matcher(eventBody);
//						String dvc = null;
//						if (m4.find()) {
//							dvc = m4.group(2);
//						} else {
//							// System.out.println("DEBUG-----EventBody : " + eventBody);
//							throw new Exception("Invalid format,dvc: no matches");
//						}
//						map.put("dvc", dvc);

				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static File[] listfiles() {
		File file = new File("D:/arcsightlogs/");
		return file.listFiles();
	}

}
