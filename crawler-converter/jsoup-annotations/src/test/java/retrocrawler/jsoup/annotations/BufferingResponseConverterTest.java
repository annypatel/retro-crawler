package retrocrawler.jsoup.annotations;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;

import org.junit.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrocrawler.HtmlReader;
import retrocrawler.reader.HtmlReaderResolver;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BufferingResponseConverterTest {

    public static class OpenGraphData {
        @Attr(query = "meta[property^=og:title]", attr = "content")
        String title;

        @Attr(query = "meta[property^=og:url]", attr = "abs:content")
        String url;
    }

    @Test
    public void convert_givenHtmlResponse_returnsParsedData() throws IOException {
        HtmlReader reader = new HtmlReaderResolver().resolve(new Annotation[]{});
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

        BufferingResponseConverter<OpenGraphData> converter = new BufferingResponseConverter<>(
                reader, baseUrl, OpenGraphData.class);
        OpenGraphData data = converter.convert(response);

        assertThat(data.title, is(equalTo("The Rock")));
        assertThat(data.url, is(equalTo(baseUrl + "/image.jpg")));
    }
}