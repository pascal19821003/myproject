package sinc.tests.hadoop.mr;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class WordCount {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		if (args.length < 2) {
			System.err.println("Input math and out path");
			System.exit(-1);
		}
		
		Job job = Job.getInstance();
		
		//重要：main方法所在类
		job.setJarByClass(WordCount.class);
		
		//设置mapper相关属性
		job.setMapperClass(WCMapper1.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		//设置reducer相关属性
		job.setReducerClass(WCReducer1.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);
	}

}
