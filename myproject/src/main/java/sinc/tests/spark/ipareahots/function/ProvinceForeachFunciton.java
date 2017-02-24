package sinc.tests.spark.ipareahots.function;

import java.sql.PreparedStatement;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class ProvinceForeachFunciton implements VoidFunction<JavaPairRDD<String,Integer>> {
	private static final long serialVersionUID = 1L;
	private PreparedStatement statement;

	public ProvinceForeachFunciton(PreparedStatement statement) {
		super();
		this.statement = statement;
	}

	public void call(JavaPairRDD<String, Integer> t) throws Exception {
		t.foreach(new VoidFunction<Tuple2<String,Integer>>() {
			private static final long serialVersionUID = 1L;
			public void call(Tuple2<String, Integer> t) throws Exception {
				statement.setInt(1, t._2);
				statement.setString(0, t._1);
				statement.executeUpdate();
			}
		});
	}
}
