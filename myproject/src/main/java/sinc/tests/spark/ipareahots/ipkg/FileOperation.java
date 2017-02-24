package sinc.tests.spark.ipareahots.ipkg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class FileOperation {
	public static void writerText(String filePath, String filename, String content) {
        try {
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(filePath + filename, true));
            System.out.println(filePath+filename);
            bw1.write(content);
            bw1.flush();
            bw1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
