package sinc.tests.tcp;

import java.io.*;

import java.net.*;

public class MyServer {

	public static void main(String[] args) throws IOException {
		int count = 0;
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(5678);
		while (true) {
			Socket client = server.accept();
			MultiClientsThread mc = new MultiClientsThread(client);
			mc.start();
			count++;// 统计客户端的数量
			System.out.println("客户端的数量：" + count);
			InetAddress address = client.getInetAddress();
			System.out.println("当前客户端的IP：" + address.getHostAddress());
		}
	}

}
