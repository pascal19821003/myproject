package sinc.tests.anonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Table {
	public String tableName() default "className";
}
