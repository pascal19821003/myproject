package sinc.tests.spark.ipareahots.test;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class CountWords {

	public static void main(String[] args) {
		String inputFile = "file:///root/out/test.txt";
		String outputFile = "file:///root/out/result/";
		SparkConf conf = new SparkConf();
		conf.setAppName("Word count");
		@SuppressWarnings("resource")
		JavaSparkContext sc = new JavaSparkContext(conf);

		JavaRDD<String> input = sc.textFile(inputFile);

		JavaRDD<String> words = input.flatMap(new FlatMapFunction<String, String>() {
			private static final long serialVersionUID = 1L;

			public Iterator<String> call(String x) {
				return Arrays.asList(x.split(" ")).iterator();
			}
		});
		words.count();

		JavaPairRDD<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
			private static final long serialVersionUID = 1L;

			public Tuple2<String, Integer> call(String x) {
				return new Tuple2<String, Integer>(x, 1);
			}
		}).reduceByKey(new Function2<Integer, Integer, Integer>() {
			private static final long serialVersionUID = 1L;

			public Integer call(Integer x, Integer y) {
				return x + y;
			}
		});
		counts.saveAsTextFile(outputFile);
	}

}
