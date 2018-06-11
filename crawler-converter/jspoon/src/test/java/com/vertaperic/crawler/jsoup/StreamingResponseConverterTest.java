package com.vertaperic.crawler.jsoup;

import org.junit.Test;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import pl.droidsonroids.jspoon.HtmlAdapter;
import pl.droidsonroids.jspoon.Jspoon;
import pl.droidsonroids.jspoon.annotation.Selector;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StreamingResponseConverterTest {

    @Test
    public void convert_givenHtmlResponse_returnsParsedData() throws IOException {
        HtmlAdapter<OpenGraphData> adapter = Jspoon.create().adapter(OpenGraphData.class);
        String baseUrl = "http://example.com";
        String html = "<html>" +
                "<head>" +
                "<meta property=\"og:title\" content=\"The Rock\" />" +
                "<meta property=\"og:url\" content=\"/image.jpg\" />" +
                "</head>" +
                "<body></body>" +
                "</html>";
        ResponseBody response = ResponseBody.create(
                MediaType.parse("text/html; charset=utf-8"), html);

        StreamingResponseConverter<OpenGraphData> converter = new StreamingResponseConverter<>(
                adapter, baseUrl);
        OpenGraphData data = converter.convert(response);

        assertThat(data.title, is(equalTo("The Rock")));
        assertThat(data.url, is(equalTo(baseUrl + "/image.jpg")));
    }

    private static class OpenGraphData {

        @Selector(value = "meta[property^=og:title]", attr = "content")
        String title;

        @Selector(value = "meta[property^=og:url]", attr = "abs:content")
        String url;
    }
}