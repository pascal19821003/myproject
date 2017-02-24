package sinc.tests.flume;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class MysqlUtil {
	private Connection conn = null;  
    PreparedStatement statement = null;  
  
    // connect to MySQL  
    public void conn() {  
        String url = "jdbc:mysql://10.100.74.63:3306/spdbops?characterEncoding=UTF-8";  
        String username = "root";  
        String password = "123456"; 
        try {   
            Class.forName("com.mysql.jdbc.Driver" );   
            conn = DriverManager.getConnection( url,username, password );   
            }  
         catch ( ClassNotFoundException cnfex ) {  
             cnfex.printStackTrace();   
         }   
         catch ( SQLException sqlex ) {  
             sqlex.printStackTrace();   
         }  
    }  
  
    // disconnect to MySQL  
    public void deconn() {  
        try {  
            if (conn != null)  
                conn.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    // execute selection language  
    public ResultSet selectSQL(String sql) {  
        ResultSet rs = null;  
        try {  
            statement = conn.prepareStatement(sql);  
            rs = statement.executeQuery(sql);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return rs;  
    }  
  
    // execute insertion language  
    boolean insertSQL(String sql) {  
        try {  
            statement = conn.prepareStatement(sql);  
            statement.executeUpdate();  
            return true;  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return false;  
    }  
    //execute delete language  
    boolean deleteSQL(String sql) {  
        try {  
            statement = conn.prepareStatement(sql);  
            statement.executeUpdate();  
            return true;  
        } catch (SQLException e) {
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return false;  
    }  
    //execute update language  
    boolean updateSQL(String sql) {  
        try {  
            statement = conn.prepareStatement(sql);  
            statement.executeUpdate();  
            return true;  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return false;  
 
    }
    
    public ConcurrentHashMap<String,String> getSystemNames(){
    	ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<String, String>();
		conn();
		ResultSet rs = selectSQL(
				"select DISTINCT UPPER(d.device_name) as dn,a.APP_NAME as an from DEVICE_BASE_INFO d LEFT JOIN APP_PK_DEVICE apd on d.DEVICE_ID=apd.DEVICE_ID left join APPINFO a on apd.APP_ID=a.ID");
		try {
			while (rs.next()) {
				String dn = rs.getString("dn");
				String an = rs.getString("an");
				if (an != null && dn != null) {
					dn = dn.trim();
					an = an.trim();
					if (cache.get(dn) !=null) {
						String tmp = cache.get(dn);
						cache.put(dn, tmp + ";" + an);
					} else {
						cache.put(dn, an);
					}
				}
			}
			rs.close();
			for (Entry<String, String> entry : cache.entrySet()) {  
			    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		deconn();
		return cache;
    }
}
