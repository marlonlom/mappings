package co.marlonlom.utils.mapping.sql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for describing sql columns for a table
 *
 * @author marlonlom
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SqlColumn {
    /**
     * Describes sql column name
     *
     * @return
     */
    String name() default "";

    /**
     * Describe column type
     *
     * @return
     */
    @SuppressWarnings("rawtypes") Class type() default Object.class;
}
