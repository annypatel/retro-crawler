package com.vertaperic.crawler;

import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;

/**
 * Reads data from {@code Reader} into a {@code String}.
 */
public interface HtmlReader {

    String read(Reader reader) throws IOException;

    /**
     * Resolves the {@link HtmlReader} based on the passed annotations.
     */
    interface Resolver {

        HtmlReader resolve(Annotation[] annotations);
    }
}
