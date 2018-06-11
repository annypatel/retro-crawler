package retrocrawler.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Use this annotation on the service method when you only want to read {@code body} tag of the
 * html and ignore the {@code head}.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface HtmlBody {
}
