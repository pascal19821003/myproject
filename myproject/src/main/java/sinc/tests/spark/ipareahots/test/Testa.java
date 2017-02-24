package sinc.tests.spark.ipareahots.test;

import java.util.Date;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class Testa {

	public static void main(String[] args) {
		String inputFile = "file:///root/out/access.log";
		String outputFile = "file:///root/out/result/";
		SparkConf conf = new SparkConf().setMaster("local");
		conf.setAppName("Word count");
		@SuppressWarnings("resource")
		JavaSparkContext sc = new JavaSparkContext(conf);
		// TODO
		JavaRDD<String> lines = sc.textFile(inputFile);

		JavaPairRDD<String, String> counts = lines.mapToPair(new PairFunction<String, String, String>() {
			private static final long serialVersionUID = 1L;

			public Tuple2<String, String> call(String x) {
				return new Tuple2<String, String>(x.split(" ")[3].substring(1), x.split(" ")[0]);
			}
		});

		System.out.println("Before distinct: " + counts.count() + " items");
		JavaPairRDD<String, String> counts_distinct = counts.distinct();
		System.out.println("After distinct: " + counts_distinct.count() + " items");
		JavaPairRDD<String, Iterable<String>> counts_groupby = counts_distinct.groupByKey();
		System.out.println("After Groupby: " + counts_groupby.count() + " items");
		List<Tuple2<String, Iterable<String>>>  abc =counts_groupby.collect();
		
//		for (Tuple2<String, Iterable<String>> tu: abc) {
//			System.out.println("key:"+ tu._1.toString());
//			for (String a :tu._2){
//				System.out.println("value:"+ a);
//			} 
//		}
		
		Date da = new Date();
		outputFile = outputFile + da.getTime();
		counts.saveAsTextFile(outputFile);
	}
}
