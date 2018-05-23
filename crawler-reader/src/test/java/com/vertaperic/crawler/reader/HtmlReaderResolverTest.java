package com.vertaperic.crawler.reader;

import com.vertaperic.crawler.HtmlReader;
import com.vertaperic.crawler.annotation.HtmlBody;
import com.vertaperic.crawler.annotation.HtmlHead;

import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HtmlReaderResolverTest {

    @Test
    public void read_givenHtmlHeadAnnotation_shouldReturnsHtmlHeadReader() {
        Annotation[] annotations = {new HtmlHead() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        }};

        HtmlReader htmlReader = new HtmlReaderResolver().resolve(annotations);

        assertThat(htmlReader, is(instanceOf(HtmlHeadReader.class)));
    }

    @Test
    public void read_givenHtmlBodyAnnotation_shouldReturnsHtmlBodyReader() {
        Annotation[] annotations = {new HtmlBody() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        }};

        HtmlReader htmlReader = new HtmlReaderResolver().resolve(annotations);

        assertThat(htmlReader, is(instanceOf(HtmlBodyReader.class)));
    }

    @Test
    public void read_givenEmptyArray_shouldReturnsHtmlFullReader() {
        Annotation[] annotations = {};

        HtmlReader htmlReader = new HtmlReaderResolver().resolve(annotations);

        assertThat(htmlReader, is(instanceOf(HtmlFullReader.class)));
    }
}