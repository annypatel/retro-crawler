package com.vertaperic.jsoup.annotations;

import com.fcannizzaro.jsoup.annotations.JsoupProcessor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Retrofit converter that stream Html response(handled by Jsoup) and then parsed it into Java
 * objects.
 */
class StreamingResponseConverter<T> implements Converter<ResponseBody, T> {

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final String baseUrl;
    private final Class<T> type;

    StreamingResponseConverter(String baseUrl, Class<T> type) {
        this.baseUrl = baseUrl;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String charset = charset(value.contentType()).toString();
        InputStream is = value.byteStream();

        Document document = Jsoup.parse(is, charset, this.baseUrl);
        return JsoupProcessor.from(document, this.type);
    }

    private Charset charset(MediaType mediaType) {
        return mediaType != null ?
                mediaType.charset(UTF_8) : UTF_8;
    }
}
