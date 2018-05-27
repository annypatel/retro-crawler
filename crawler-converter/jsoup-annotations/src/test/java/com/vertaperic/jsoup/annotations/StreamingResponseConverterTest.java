package com.vertaperic.jsoup.annotations;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;

import org.junit.Test;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StreamingResponseConverterTest {

    public static class OpenGraphData {
        @Attr(query = "meta[property^=og:title]", attr = "content")
        String title;

        @Attr(query = "meta[property^=og:url]", attr = "abs:content")
        String url;
    }

    @Test
    public void convert_givenHtmlResponse_returnsParsedData() throws IOException {
        String baseUrl = "http://www.vertaperic.com";
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
                baseUrl, OpenGraphData.class);
        OpenGraphData data = converter.convert(response);

        assertThat(data.title, is(equalTo("The Rock")));
        assertThat(data.url, is(equalTo(baseUrl + "/image.jpg")));
    }
}