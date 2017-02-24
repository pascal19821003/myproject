package sinc.tests.hadoop.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class WCMapper1 extends Mapper<LongWritable, Text, Text, LongWritable> {
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		FileSplit fs =(FileSplit) context.getInputSplit();
		String file  = fs.getPath().getName();
		String line = value.toString();
		String[] words = line.split(" ");
		for (String w : words) {
			context.write(new Text(w+"->"+file), new LongWritable(1));
		}
	}

}
