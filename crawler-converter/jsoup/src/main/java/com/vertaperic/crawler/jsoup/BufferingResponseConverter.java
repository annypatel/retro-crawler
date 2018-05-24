package com.vertaperic.crawler.jsoup;

import com.vertaperic.crawler.HtmlReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.ResponseBody;
import pl.droidsonroids.jspoon.HtmlAdapter;
import pl.droidsonroids.jspoon.HtmlAdapterExtension;
import retrofit2.Converter;

/**
 * Retrofit converter that reads Html response into a string directly(no streaming) and then parsed
 * it into Java objects.
 */
class BufferingResponseConverter<T> implements Converter<ResponseBody, T> {

    private final HtmlAdapter<T> adapter;
    private final HtmlReader reader;
    private final String baseUrl;

    BufferingResponseConverter(HtmlAdapter<T> adapter, HtmlReader reader, String baseUrl) {
        this.adapter = adapter;
        this.reader = reader;
        this.baseUrl = baseUrl;
    }

    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String response = this.reader.read(responseBody.charStream());

        Document document = Jsoup.parse(response, this.baseUrl);
        return HtmlAdapterExtension.fromElement(this.adapter, document);
    }
}
