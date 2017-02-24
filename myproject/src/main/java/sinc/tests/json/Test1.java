package sinc.tests.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;


public class Test1 {
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("D:/jsonstring.txt");
		OvoWarn ow = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				ow = mapper.readValue(tempString, OvoWarn.class);
				ow.setTimemillisstr(new Date().getTime());
			}
			reader.close();
			
			System.out.println(ow.getTimemillisstr());
			String Json =  mapper.writeValueAsString(ow); 
			System.out.println(Json);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		try {
			Date date = sdf.parse("2016-07-21 8:30:15");
			System.out.println(date.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
