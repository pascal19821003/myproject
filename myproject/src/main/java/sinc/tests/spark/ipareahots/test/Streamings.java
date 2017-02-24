package sinc.tests.spark.ipareahots.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;
import sinc.tests.spark.ipareahots.function.ProvinceConvert;
import sinc.tests.spark.ipareahots.ipkg.IPDataHandler;


public class Streamings {
	public static void main(String[] args)
			throws InterruptedException, ClassNotFoundException, SQLException, IOException {
		SparkConf conf = new SparkConf();
		JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));
		JavaDStream<String> lines = jssc.socketTextStream("10.102.34.248", 7777);
		// 去重复
		JavaDStream<String> distinctLines = lines.transform(new Function<JavaRDD<String>, JavaRDD<String>>() {
			private static final long serialVersionUID = 1L;

			public JavaRDD<String> call(JavaRDD<String> rdd) throws Exception {
				// System.out.println("-----lines.tranform inner-----------");
				// System.out.println(StringUtils.join(rdd.collect(), ","));
				// System.out.println("-----lines.tranform inner after
				// distinct-----------");
				// System.out.println(StringUtils.join(rdd.distinct().collect(),
				// ","));
				// System.out.println(StringUtils.join(rdd.distinct().collect(),
				// ","));
				// System.out.println("rdd size"+ rdd.count());
				return rdd.distinct();
			}
		});

		IPDataHandler idh = new IPDataHandler();
		JavaDStream<String> records = distinctLines.map(new ProvinceConvert(idh));

		records.cache();

		JavaPairDStream<String, Integer> provinceSumRdd = records
				.mapToPair(new PairFunction<String, String, Integer>() {
					private static final long serialVersionUID = 1L;

					public Tuple2<String, Integer> call(String x) {
						return new Tuple2<String, Integer>(x.split(" ")[0], 1);
//						return new Tuple2<String, Integer>(x, 1);
					}
				}).reduceByKey(new Function2<Integer, Integer, Integer>() {
					private static final long serialVersionUID = 1L;

					public Integer call(Integer x, Integer y) {
						return x + y;
					}
				});

		provinceSumRdd.print();

		JavaPairDStream<String, Integer> operatorSumRdd = records
				.mapToPair(new PairFunction<String, String, Integer>() {
					private static final long serialVersionUID = 1L;

					public Tuple2<String, Integer> call(String x) {
						return new Tuple2<String, Integer>(x.split(" ")[1], 1);
					}
				}).reduceByKey(new Function2<Integer, Integer, Integer>() {
					private static final long serialVersionUID = 1L;

					public Integer call(Integer x, Integer y) {
						return x + y;
					}
				});

		operatorSumRdd.print();

		//
		// JavaPairDStream<String, Integer> loginSumRdd = records.
		// mapToPair(new PairFunction<String, String, Integer>() {
		// private static final long serialVersionUID = 1L;
		// public Tuple2<String, Integer> call(String x) {
		// return new Tuple2<String, Integer>(x.split(" ")[2], 1);
		// }
		// }).reduceByKey(new Function2<Integer, Integer, Integer>() {
		// private static final long serialVersionUID = 1L;
		// public Integer call(Integer x, Integer y) {
		// return x + y;
		// }
		// });
		//
		// JavaPairDStream<String, Integer> provinceOperatorSumRdd = records.
		// mapToPair(new PairFunction<String, String, Integer>() {
		// private static final long serialVersionUID = 1L;
		// public Tuple2<String, Integer> call(String x) {
		// return new Tuple2<String, Integer>(x.split(" ")[0]+x.split(" ")[1],
		// 1);
		// }
		// }).reduceByKey(new Function2<Integer, Integer, Integer>() {
		// private static final long serialVersionUID = 1L;
		// public Integer call(Integer x, Integer y) {
		// return x + y;
		// }
		// });
		//
		// JavaPairDStream<String, Integer> provinceLoginSumRdd = records.
		// mapToPair(new PairFunction<String, String, Integer>() {
		// private static final long serialVersionUID = 1L;
		// public Tuple2<String, Integer> call(String x) {
		// return new Tuple2<String, Integer>(x.split(" ")[0]+x.split(" ")[2],
		// 1);
		// }
		// }).reduceByKey(new Function2<Integer, Integer, Integer>() {
		// private static final long serialVersionUID = 1L;
		// public Integer call(Integer x, Integer y) {
		// return x + y;
		// }
		// });
		// JavaPairDStream<String, Integer> operatorLoginSumRdd = records.
		// mapToPair(new PairFunction<String, String, Integer>() {
		// private static final long serialVersionUID = 1L;
		// public Tuple2<String, Integer> call(String x) {
		// return new Tuple2<String, Integer>(x.split(" ")[1]+x.split(" ")[2],
		// 1);
		// }
		// }).reduceByKey(new Function2<Integer, Integer, Integer>() {
		// private static final long serialVersionUID = 1L;
		// public Integer call(Integer x, Integer y) {
		// return x + y;
		// }
		// });
		// JavaPairDStream<String, Integer> provinceOperatorsLoginSumRdd =
		// records.
		// mapToPair(new PairFunction<String, String, Integer>() {
		// private static final long serialVersionUID = 1L;
		// public Tuple2<String, Integer> call(String x) {
		// return new Tuple2<String, Integer>(x.split(" ")[0]+x.split(" ")[1],
		// 1);
		// }
		// }).reduceByKey(new Function2<Integer, Integer, Integer>() {
		// private static final long serialVersionUID = 1L;
		// public Integer call(Integer x, Integer y) {
		// return x + y;
		// }
		// });
		//

		provinceSumRdd.foreachRDD(new VoidFunction<JavaPairRDD<String, Integer>>() {
			private static final long serialVersionUID = -5625397226747767432L;
			public void call(JavaPairRDD<String, Integer> t) throws Exception {
				t.foreach(new VoidFunction<Tuple2<String, Integer>>() {
					private static final long serialVersionUID = -8144570121875937438L;
					public void call(Tuple2<String, Integer> t) throws Exception {
						// TODO Auto-generated method stub
						String url = "jdbc:mysql://10.102.34.247:3306/test?characterEncoding=UTF-8";
						String sql = "update t_test_map set connect_num=? where province=?";
						String username = "root";
						String password = "123456";
						Class.forName("com.mysql.jdbc.Driver");
						Connection conn = DriverManager.getConnection(url, username, password);
						PreparedStatement statement = conn.prepareStatement(sql);
						statement.setInt(1, t._2);
						statement.setString(2, t._1);
						System.out.println("update t_test_map set connect_num=" + t._2 + " where province=" + t._1);
						statement.executeUpdate();
						conn.close();
					}
				});
			}
		});
		jssc.start();
		jssc.awaitTermination();
	}
}
