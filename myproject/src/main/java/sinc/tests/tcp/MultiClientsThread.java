package sinc.tests.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiClientsThread extends Thread {

	private Socket client;

	public MultiClientsThread(Socket client) {
		super();
		this.client = client;
	}

	public void run() {
		BufferedReader in;
		PrintWriter out;
		try {
			out = new PrintWriter(client.getOutputStream());
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while (true) {

				String str = in.readLine();

				System.out.println(str);

				out.println("has receive.");

				out.flush();

				if (str.equals("end"))

					break;
			}
			client.close();
			System.out.println("Client disconnect.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
