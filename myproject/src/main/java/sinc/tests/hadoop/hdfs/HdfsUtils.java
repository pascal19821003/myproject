package sinc.tests.hadoop.hdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HdfsUtils {

	// �������ļ�
	public static void createFile(String dst, byte[] contents) throws IOException {
		Configuration conf = new Configuration();

		FileSystem fs = FileSystem.get(conf);
		Path dstPath = new Path(dst); // Ŀ��·��
		// ��һ�������
		FSDataOutputStream outputStream = fs.create(dstPath);
		outputStream.write(contents);
		outputStream.close();
		fs.close();
		System.out.println("�ļ������ɹ���");
	}

	// ɾ���ļ�
	public static void delete(String filePath) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(filePath);
		boolean isok = fs.deleteOnExit(path);
		if (isok) {
			System.out.println("delete ok!");
		} else {
			System.out.println("delete failure");
		}
		fs.close();
	}

	// �ϴ������ļ�
	public static void uploadFile(String src, String dst) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path srcPath = new Path(src); // ԭ·��
		Path dstPath = new Path(dst); // Ŀ��·��
		// �����ļ�ϵͳ���ļ����ƺ���,ǰ�������ָ�Ƿ�ɾ��ԭ�ļ���trueΪɾ����Ĭ��Ϊfalse
		fs.copyFromLocalFile(false, srcPath, dstPath);
		fs.close();
	}

	// �ļ����ص�����
	public static void readFromHdfs(String src, String dst) throws IOException {

		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		FSDataInputStream hdfsInStream = fs.open(new Path(src));

		OutputStream out = new FileOutputStream(dst);
		byte[] ioBuffer = new byte[1024];
		int readLen = hdfsInStream.read(ioBuffer);

		while (-1 != readLen) {
			out.write(ioBuffer, 0, readLen);
			readLen = hdfsInStream.read(ioBuffer);
		}
		out.close();
		hdfsInStream.close();
		fs.close();
	}

	// ��ȡ�ļ�������
	public static void readFile(String filePath) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path srcPath = new Path(filePath);
		InputStream in = null;
		try {
			in = fs.open(srcPath);
			IOUtils.copyBytes(in, System.out, 4096, false); // ���Ƶ���׼�����
		} finally {
			IOUtils.closeStream(in);
		}
	}

	// ����Ŀ¼
	public static void mkdir(String path) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path srcPath = new Path(path);
		boolean isok = fs.mkdirs(srcPath);
		if (isok) {
			System.out.println("create dir ok!");
		} else {
			System.out.println("create dir failure");
		}
		fs.close();
	}

	public static void main(String[] args) {
		File file = new File("/usr/local/test/hadoop-2.7.2/splunk.data");
		InputStream is;
		try {
			is = new FileInputStream(file);
			byte[] buffer = new byte[4096];
			while (-1 != is.read(buffer)) {
				HdfsUtils.createFile("hdfs://Master.Hadoop:9000/tmp/20170105/" + System.currentTimeMillis(), buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
