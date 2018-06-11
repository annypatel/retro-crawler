package retrocrawler.reader;

import java.lang.annotation.Annotation;

import retrocrawler.HtmlReader;
import retrocrawler.annotation.HtmlBody;
import retrocrawler.annotation.HtmlHead;

/**
 * Resolves {@link HtmlReader} based on the given annotations. Returns {@link HtmlHeadReader} and
 * {@link HtmlBodyReader} for {@link HtmlHead} and {@link HtmlBody} annotation respectively,
 * otherwise returns {@link HtmlFullReader}.
 */
public class HtmlReaderResolver implements HtmlReader.Resolver {

    private final HtmlReader headReader;
    private final HtmlReader bodyReader;
    private final HtmlReader fullReader;

    public HtmlReaderResolver() {
        this.headReader = new HtmlHeadReader();
        this.bodyReader = new HtmlBodyReader();
        this.fullReader = new HtmlFullReader();
    }

    @Override
    public HtmlReader resolve(Annotation[] annotations) {
        if (isAnnotationPresent(annotations, HtmlHead.class)) {
            return this.headReader;
        }
        if (isAnnotationPresent(annotations, HtmlBody.class)) {
            return this.bodyReader;
        }

        return this.fullReader;
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
}
