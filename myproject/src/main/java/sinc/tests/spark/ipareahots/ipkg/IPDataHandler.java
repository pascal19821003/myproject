package sinc.tests.spark.ipareahots.ipkg;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import scala.Serializable;

public class IPDataHandler implements Serializable {
	private static final long serialVersionUID = 1L;
	private String IP_DATA_PATH = "/root/Documents/ipprocess/17monipdb.dat";
	private long fileLength = -1;
	private int dataLength = -1;
	private Map<String, String> cacheMap = null;
	private byte[] allData = null;

	public IPDataHandler() throws IOException {
		File file = new File(IP_DATA_PATH);
		DataInputStream inputStream = null;
		try {
			inputStream = new DataInputStream(new FileInputStream(file));
			fileLength = file.length();
			cacheMap = new HashMap<String, String>();
			if (fileLength > Integer.MAX_VALUE) {
				throw new Exception("the filelength over 2GB");
			}
			dataLength = (int) fileLength;
			allData = new byte[dataLength];
			inputStream.read(allData, 0, dataLength);
			dataLength = (int) getbytesTolong(allData, 0, 4, ByteOrder.BIG_ENDIAN);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}

	}

	private long getbytesTolong(byte[] bytes, int offerSet, int size, ByteOrder byteOrder) {
		if ((offerSet + size) > bytes.length || size <= 0) {
			return -1;
		}
		byte[] b = new byte[size];
		for (int i = 0; i < b.length; i++) {
			b[i] = bytes[offerSet + i];
		}
		ByteBuffer byteBuffer = ByteBuffer.wrap(b);
		byteBuffer.order(byteOrder);
		long temp = -1;
		if (byteBuffer.hasRemaining()) {
			temp = byteBuffer.getInt();
		}
		return temp;
	}

	private long ip2long(String ip) throws UnknownHostException {
		InetAddress address = InetAddress.getByName(ip);
		byte[] bytes = address.getAddress();
		long reslut = getbytesTolong(bytes, 0, 4, ByteOrder.BIG_ENDIAN);
		return reslut;
	}

	private int getIntByBytes(byte[] b, int offSet) {
		if (b == null || (b.length < (offSet + 3))) {
			return -1;
		}
		byte[] bytes = Arrays.copyOfRange(allData, offSet, offSet + 3);
		byte[] bs = new byte[4];
		bs[3] = 0;
		for (int i = 0; i < 3; i++) {
			bs[i] = bytes[i];
		}
		return (int) getbytesTolong(bs, 0, 4, ByteOrder.LITTLE_ENDIAN);
	}

	public String findGeography(String address) {
		if (address == null) {
			return "illegal address";
		}
		if (dataLength < 4 || allData == null) {
			return "illegal ip data";
		}
		String ip = "127.0.0.1";
		try {
			ip = Inet4Address.getByName(address).getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String[] ipArray = ip.split("\\.");
		int ipHeadValue = Integer.parseInt(ipArray[0]);
		if (ipArray.length != 4 || ipHeadValue < 0 || ipHeadValue > 255) {
			return "illegal ip";
		}

		if (cacheMap.containsKey(ip)) {
			return cacheMap.get(ip);
		}

		long numIp = 1;
		try {
			numIp = ip2long(address);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		int tempOffSet = ipHeadValue * 4 + 4;
		long start = getbytesTolong(allData, tempOffSet, 4, ByteOrder.LITTLE_ENDIAN);
		int max_len = dataLength - 1028;
		long resultOffSet = 0;
		int resultSize = 0;

		for (start = start * 8 + 1024; start < max_len; start += 8) {
			if (getbytesTolong(allData, (int) start + 4, 4, ByteOrder.BIG_ENDIAN) >= numIp) {
				resultOffSet = getIntByBytes(allData, (int) (start + 4 + 4));
				resultSize = (char) allData[(int) start + 7 + 4];
				break;
			}
		}

		if (resultOffSet <= 0) {
			return "resultOffSet too small";
		}

		byte[] add = Arrays.copyOfRange(allData, (int) (dataLength + resultOffSet - 1024),
				(int) (dataLength + resultOffSet - 1024 + resultSize));
		try {
			if (add == null) {
				cacheMap.put(ip, new String("no data found!!"));
			} else {
				cacheMap.put(ip, new String(add, "UTF-8"));
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return cacheMap.get(ip);
	}

	public static void main(String[] args) throws IOException {
		IPDataHandler obj = new IPDataHandler();
		Calendar c = Calendar.getInstance();
		String filePath = "/root/Documents/ipprocess/ipdata/";
		String year = c.get(Calendar.YEAR) + "";
		String month = c.get(Calendar.MONTH) + "";
		String date = c.get(Calendar.DATE) + "";
		String hour = c.get(Calendar.HOUR_OF_DAY) + "";
		String minute = c.get(Calendar.MINUTE) + "";
		String second = c.get(Calendar.SECOND) + "";
		String filename = year + month + date + hour + minute + second + ".txt";
		int i = 0;
		int ip_num = 10000;
		String[] s;
		s = new String[ip_num];
		String content = "";
		String[] str = new String[3];
		str[0] = "eservice";
		str[1] = "cmnet";
		str[2] = "nbper";

		long startTime = System.currentTimeMillis();
		for (i = 0; i < ip_num; i++) {
			int random = (int) (Math.random() * 2);
			// s[i] = IpGenerate.getRandomIp();
			// content += (obj.findGeography(s[i]).replaceAll("\\s*",
			// "")).substring(2).replaceAll("中国", "") + "\t";
			s[i] = "20161207" + " " + IpGenerate.getRandomIp() + " " + str[random];
			content += s[i] + "\n\r";
		}
		
		try {  
            //1.建立一个服务器Socket(ServerSocket)绑定指定端口  
            ServerSocket serverSocket=new ServerSocket(7777);  
            //2.使用accept()方法阻止等待监听，获得新连接  
            Socket socket=serverSocket.accept();  
            //3.获得输入流  
            InputStream is=socket.getInputStream();  
            BufferedReader br=new BufferedReader(new InputStreamReader(is));  
            //获得输出流  
            OutputStream os=socket.getOutputStream();  
            PrintWriter pw=new PrintWriter(os);  
            //4.读取用户输入信息  
            String info=null;  
            while(!((info=br.readLine())==null)){  
                System.out.println("我是服务器，用户信息为："+info);  
            }  
            //给客户一个响应  
            String reply="welcome";  
            pw.write(content);  
            pw.flush();  
            //5.关闭资源  
            pw.close();  
            os.close();  
            br.close();  
            is.close();  
            socket.close();  
            serverSocket.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }      
		
		
		
		
		
//		File f = null;
//		boolean bool = false;
//
//		try {
//			// create new file
//			f = new File(filePath + filename);
//			// tries to create new file in the system
//			bool = f.createNewFile();
//			// prints
//			System.out.println("File created: " + bool);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		FileOperation.writerText(filePath, filename, content);
//		// WordCount.wordCount(content);
//
//		long endTime = System.currentTimeMillis();
//		System.out.println("程序运行时间 " + (endTime - startTime) + "ms");

	}
}
