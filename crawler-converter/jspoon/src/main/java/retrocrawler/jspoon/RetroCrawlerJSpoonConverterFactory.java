package retrocrawler.jspoon;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import pl.droidsonroids.jspoon.HtmlAdapter;
import pl.droidsonroids.jspoon.Jspoon;
import retrocrawler.HtmlReader;
import retrocrawler.annotation.HtmlBaseUrl;
import retrocrawler.reader.HtmlReaderResolver;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Streaming;

/**
 * A {@linkplain Converter.Factory converter} which uses Jspoon for parsing Html.
 * <p>
 * If you are mixing Html deserialization with something else (such as json, protocol buffers),
 * you must {@linkplain Retrofit.Builder#addConverterFactory(Converter.Factory) add this instance}
 * last to allow the other converters a chance to see their types.
 */
public class RetroCrawlerJSpoonConverterFactory extends Converter.Factory {

    private final Jspoon jspoon;
    private final HtmlReader.Resolver resolver;

    private RetroCrawlerJSpoonConverterFactory(Jspoon jspoon, HtmlReader.Resolver resolver) {
        this.jspoon = jspoon;
        this.resolver = resolver;
    }

    /**
     * Create an instance using a default {@link Jspoon} and {@link HtmlReader.Resolver} instance
     * for conversion. Decoding from Html (when no charset is specified by a header) will use UTF-8.
     */
    public static RetroCrawlerJSpoonConverterFactory create() {
        return create(Jspoon.create(), new HtmlReaderResolver());
    }

    /**
     * Create an instance using a given {@link Jspoon} and default {@link HtmlReader.Resolver}
     * instance for conversion. Decoding from Html (when no charset is specified by a header) will
     * use UTF-8.
     */
    public static RetroCrawlerJSpoonConverterFactory create(Jspoon jspoon) {
        return create(jspoon, new HtmlReaderResolver());
    }

    /**
     * Create an instance using a default {@link Jspoon} and given {@link HtmlReader.Resolver}
     * instance for conversion. Decoding from Html (when no charset is specified by a header) will
     * use UTF-8.
     */
    public static RetroCrawlerJSpoonConverterFactory create(HtmlReader.Resolver resolver) {
        return create(Jspoon.create(), resolver);
    }

    /**
     * Create an instance using a {@link Jspoon} and {@link HtmlReader.Resolver} instance for
     * conversion. Decoding from Html (when no charset is specified by a header) will use UTF-8.
     */
    public static RetroCrawlerJSpoonConverterFactory create(Jspoon jspoon, HtmlReader.Resolver resolver) {
        return new RetroCrawlerJSpoonConverterFactory(jspoon, resolver);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        HtmlAdapter<?> adapter = this.jspoon.adapter((Class<?>) type);
        String baseUrl = extractBaseUrl(annotations, retrofit);
        boolean streaming = isAnnotationPresent(annotations, Streaming.class);

        if (streaming) {
            return new StreamingResponseConverter<>(adapter, baseUrl);
        } else {
            HtmlReader reader = this.resolver.resolve(annotations);
            return new BufferingResponseConverter<>(adapter, reader, baseUrl);
        }
    }

    private String extractBaseUrl(Annotation[] annotations, Retrofit retrofit) {
        HtmlBaseUrl htmlBaseUrl = findAnnotation(annotations, HtmlBaseUrl.class);
        if (htmlBaseUrl != null) {
            return htmlBaseUrl.value();
        } else {
            return retrofit.baseUrl().toString();
        }
    }

    /**
     * Checks if given annotation is presents in array or not.
     */
    private boolean isAnnotationPresent(Annotation[] annotations,
                                        Class<? extends Annotation> cls) {
        for (Annotation annotation : annotations) {
            if (cls.isInstance(annotation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds annotation from array by class type.
     */
    private <T extends Annotation> T findAnnotation(Annotation[] annotations, Class<T> cls) {
        for (Annotation annotation : annotations) {
            if (cls.isInstance(annotation)) {
                //noinspection unchecked
                return (T) annotation;
            }
        }
        return null;
    }
}