package com.vertaperic.crawler.reader;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HtmlHeadReaderTest {

    @Test
    public void read_givenFullHtmlPage_returnsHtmlHeadOnly() throws IOException {
        String fullHtmlPage = HtmlTestData.HTML;
        Reader reader = new InputStreamReader(
                new ByteArrayInputStream(fullHtmlPage.getBytes())
        );

        String actual = new HtmlHeadReader().read(reader);

        String expected = "<html>" + HtmlTestData.HEAD + "<body></body></html>";
        assertThat(actual, is(equalTo(expected)));
    }
}