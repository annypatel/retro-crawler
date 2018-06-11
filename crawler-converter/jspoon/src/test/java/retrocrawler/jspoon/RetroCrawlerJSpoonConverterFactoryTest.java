package retrocrawler.jspoon;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import pl.droidsonroids.jspoon.annotation.Selector;
import retrocrawler.annotation.HtmlBaseUrl;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class RetroCrawlerJSpoonConverterFactoryTest {

    static final String BASE_URL = "http://example.com/";

    interface OpenGraphService {
        @GET("/")
        Call<OpenGraphData> parseHtml();

        @GET("/")
        @HtmlBaseUrl(BASE_URL)
        Call<OpenGraphData> parseHtmlWithBaseUrl();
    }

    static class OpenGraphData {
        @Selector(value = "meta[property^=og:title]", attr = "content")
        String title;

        @Selector(value = "meta[property^=og:url]", attr = "abs:content")
        String url;
    }

    @Rule
    public final MockWebServer server = new MockWebServer();
    private Retrofit retrofit;

    @Before
    public void setup() {
        retrofit = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(RetroCrawlerJSpoonConverterFactory.create())
                .build();
    }

    @Test
    public void givenMethodAnnotatedWithGet_returnsParsedData() throws IOException {
        server.enqueue(new MockResponse().setBody("<html>" +
                "<head>" +
                "<meta property=\"og:title\" content=\"The Rock\" />" +
                "<meta property=\"og:url\" content=\"/image.jpg\" />" +
                "</head>" +
                "<body></body>" +
                "</html>"
        ));

        OpenGraphData data = retrofit.create(OpenGraphService.class)
                .parseHtml()
                .execute()
                .body();

        assertNotNull(data);
        assertThat(data.title, is(equalTo("The Rock")));
        assertThat(data.url, is(equalTo(retrofit.baseUrl() + "image.jpg")));
    }

    @Test
    public void givenMethodAnnotatedWithHtmlBaseUrl_resolvesAbsoluteUrl() throws IOException {
        server.enqueue(new MockResponse().setBody("<html>" +
                "<head>" +
                "<meta property=\"og:title\" content=\"The Rock\" />" +
                "<meta property=\"og:url\" content=\"/image.jpg\" />" +
                "</head>" +
                "<body></body>" +
                "</html>"
        ));

        OpenGraphData data = retrofit.create(OpenGraphService.class)
                .parseHtmlWithBaseUrl()
                .execute()
                .body();

        assertNotNull(data);
        assertThat(data.title, is(equalTo("The Rock")));
        assertThat(data.url, is(equalTo(BASE_URL + "image.jpg")));
    }

    @Test
    public void responseBodyConverter_byDefault_returnsBufferingResponseConverter() {
        Converter.Factory factory = RetroCrawlerJSpoonConverterFactory.create();
        Annotation[] annotations = {};

        Converter<ResponseBody, ?> converter = factory.responseBodyConverter(
                OpenGraphData.class,
                annotations,
                retrofit
        );

        assertThat(converter, instanceOf(BufferingResponseConverter.class));
    }

    @Test
    public void responseBodyConverter_givenStreamingAnnotation_returnsStreamingResponseConverter() {
        Converter.Factory factory = RetroCrawlerJSpoonConverterFactory.create();
        Annotation[] annotations = {new Streaming() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        }};

        Converter<ResponseBody, ?> converter = factory.responseBodyConverter(
                OpenGraphData.class,
                annotations,
                retrofit
        );

        assertThat(converter, instanceOf(StreamingResponseConverter.class));
    }
}