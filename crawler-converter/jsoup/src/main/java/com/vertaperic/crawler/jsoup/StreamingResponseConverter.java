package com.vertaperic.crawler.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import pl.droidsonroids.jspoon.HtmlAdapter;
import pl.droidsonroids.jspoon.HtmlAdapterExtension;
import retrofit2.Converter;

/**
 * Retrofit converter that stream Html response(handled by Jsoup) and then parsed it into Java
 * objects.
 */
class StreamingResponseConverter<T> implements Converter<ResponseBody, T> {

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final HtmlAdapter<T> adapter;
    private final String baseUrl;

    StreamingResponseConverter(HtmlAdapter<T> adapter, String baseUrl) {
        this.adapter = adapter;
        this.baseUrl = baseUrl;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String charset = charset(value.contentType()).toString();
        InputStream is = value.byteStream();

        Document document = Jsoup.parse(is, charset, this.baseUrl);
        return HtmlAdapterExtension.fromElement(this.adapter, document);
    }

    private Charset charset(MediaType mediaType) {
        return mediaType != null ?
                mediaType.charset(UTF_8) : UTF_8;
    }
}
