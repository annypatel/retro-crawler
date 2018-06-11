package retrocrawler.reader;

import org.junit.Test;

import java.lang.annotation.Annotation;

import retrocrawler.HtmlReader;
import retrocrawler.annotation.HtmlBody;
import retrocrawler.annotation.HtmlHead;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HtmlReaderResolverTest {

    @Test
    public void resolve_givenHtmlHeadAnnotation_shouldReturnsHtmlHeadReader() {
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
    public void resolve_givenHtmlBodyAnnotation_shouldReturnsHtmlBodyReader() {
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
    public void resolve_givenEmptyArray_shouldReturnsHtmlFullReader() {
        Annotation[] annotations = {};

        HtmlReader htmlReader = new HtmlReaderResolver().resolve(annotations);

        assertThat(htmlReader, is(instanceOf(HtmlFullReader.class)));
    }
}