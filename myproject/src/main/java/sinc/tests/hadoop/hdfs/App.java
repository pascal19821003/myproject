package sinc.tests.hadoop.hdfs;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Hello world!
 *
 */
public class App {
	public void init() {

	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		long a = 1000;
		long b=2000;
		long c = a-b;
		System.out.println(c
				);
//		FileSystem fs = FileSystem.get(new URI("hdfs://10.102.34.248:9000"), new Configuration());
//		
//		InputStream in = fs.open(new Path("/spark-libs/spark-assembly-1.6.2-hadoop2.6.0.jar"));
//		BufferedInputStream bi = new BufferedInputStream(in);
//		FileOutputStream out = new FileOutputStream("d://abc.jar");
//		byte[] b = new byte[4096];
//		while (bi.read(b) != -1)
//		{
//			out.write(b);
//		}
//		out.close();
//		in.close();
//		fs.close();
	}
}
