package sinc.tests.spark.ipareahots.function;

import org.apache.spark.api.java.function.Function;

import sinc.tests.spark.ipareahots.ipkg.IPDataHandler;



public class ProvinceConvert implements Function<String, String> {
	private static final long serialVersionUID = 1L;
	private IPDataHandler idh;
	 private String [] str;
	 
	public ProvinceConvert(IPDataHandler idh) {
		this.idh = idh;
		str = new String[3];
		str[0]="电信";
		str[1]="移动";
		str[2]="联通";
	}
	
	public String call(String line) throws Exception {
		int random = (int) ( Math.random () * 3 );
		String[] items=line.split(" ");
		String type = null;
//		System.out.println("######################################################################");
//		System.out.println(StringUtils.join(items, ",")+ "length : "+items.length);
//		if(items.length<3){
//			return "20161227 河南 电信";
//		}
		if(items[2].equals("eservice")){
			type="个人";
		}else if(items[2].equals("cmnet")){
			type="企业";
		}else if(items[2].equals("nbper")){
			type="手机";
		}
		return idh.findGeography(items[1]).split("\t")[1] + " " + str[random] + " " + type;
	}

}
