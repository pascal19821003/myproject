package sinc.tests.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

public class Test2 {
	private static  String hello="你好";
	public static void main(String[] args){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		File file = new File("D:/jsonstring.txt");
		NetcoolWarn ow = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				ow = mapper.readValue(tempString, NetcoolWarn.class);
				ow.setTimemillisstr(new Date().getTime());
			}
			reader.close();
			
			System.out.println(ow.getTimemillisstr());
			String Json =  mapper.writeValueAsString(ow); 
			System.out.println(Json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(hello);
	}
}
