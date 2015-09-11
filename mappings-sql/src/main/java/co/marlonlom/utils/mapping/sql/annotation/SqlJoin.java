package co.marlonlom.utils.mapping.sql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for describing sql joins for a table column
 * 
 * @author marlonlom
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SqlJoin {
	/**
	 * Indicates origin class for joined reference
	 * 
	 * @return class reference
	 */
	@SuppressWarnings("rawtypes")
	Class from() default Object.class;
}
