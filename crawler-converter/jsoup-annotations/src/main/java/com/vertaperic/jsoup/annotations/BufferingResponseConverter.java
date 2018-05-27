package com.vertaperic.jsoup.annotations;

import com.fcannizzaro.jsoup.annotations.JsoupProcessor;
import com.vertaperic.crawler.HtmlReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Retrofit converter that reads Html response into a string directly(no streaming) and then parsed
 * it into Java objects.
 */
class BufferingResponseConverter<T> implements Converter<ResponseBody, T> {

    private final HtmlReader reader;
    private final String baseUrl;
    private final Class<T> type;

    BufferingResponseConverter(HtmlReader reader, String baseUrl, Class<T> type) {
        this.reader = reader;
        this.baseUrl = baseUrl;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String response = this.reader.read(responseBody.charStream());

        Document document = Jsoup.parse(response, this.baseUrl);
        return JsoupProcessor.from(document, this.type);
    }
}
