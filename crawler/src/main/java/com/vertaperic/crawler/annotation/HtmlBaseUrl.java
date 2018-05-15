package com.vertaperic.crawler.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Use this annotation on the service method when you want to override the base url for all the
 * relative urls in the html. By default relative urls will be resolved against the
 * {@code Retrofit.baseUrl()}.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface HtmlBaseUrl {

    String value();
}

