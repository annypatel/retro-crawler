package retrocrawler.jsoup.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrocrawler.HtmlReader;
import retrocrawler.annotation.HtmlBaseUrl;
import retrocrawler.reader.HtmlReaderResolver;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Streaming;


/**
 * A {@linkplain Converter.Factory converter} which uses Jsoup Annotations for parsing Html.
 * <p>
 * If you are mixing Html deserialization with something else (such as json, protocol buffers),
 * you must {@linkplain Retrofit.Builder#addConverterFactory(Converter.Factory) add this instance}
 * last to allow the other converters a chance to see their types.
 */
public class RetroCrawlerJSoupAnnotationConverterFactory extends Converter.Factory {

    private final HtmlReader.Resolver resolver;

    private RetroCrawlerJSoupAnnotationConverterFactory(HtmlReader.Resolver resolver) {
        this.resolver = resolver;
    }

    /**
     * Create an instance using default {@link HtmlReader.Resolver} instance for conversion.
     * Decoding from Html (when no charset is specified by a header) will use UTF-8.
     */
    public static RetroCrawlerJSoupAnnotationConverterFactory create() {
        return create(new HtmlReaderResolver());
    }

    /**
     * Create an instance using given {@link HtmlReader.Resolver} instance for conversion. Decoding
     * from Html (when no charset is specified by a header) will use UTF-8.
     */
    public static RetroCrawlerJSoupAnnotationConverterFactory create(HtmlReader.Resolver resolver) {
        return new RetroCrawlerJSoupAnnotationConverterFactory(resolver);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        String baseUrl = extractBaseUrl(annotations, retrofit);
        boolean streaming = isAnnotationPresent(annotations, Streaming.class);

        if (streaming) {
            return new StreamingResponseConverter<>(baseUrl, (Class<?>) type);
        } else {
            HtmlReader reader = this.resolver.resolve(annotations);
            return new BufferingResponseConverter<>(reader, baseUrl, (Class<?>) type);
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
