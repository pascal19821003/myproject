package sinc.tests.hadoop.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class WCPartitioner extends Partitioner<Text, LongWritable> {
	
	@Override
	public int getPartition(Text key, LongWritable value, int numPartitions) {
		return 0;
	}

}
