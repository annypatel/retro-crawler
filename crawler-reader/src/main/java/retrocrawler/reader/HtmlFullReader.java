package retrocrawler.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import retrocrawler.HtmlReader;

/**
 * Reads entire html page including head and body.
 */
class HtmlFullReader implements HtmlReader {

    // 2K chars (4K bytes)
    private static final int BUFFER_SIZE = 2048;

    @Override
    public String read(Reader reader) throws IOException {
        final BufferedReader from = new BufferedReader(reader);
        final StringBuilder to = new StringBuilder();

        char[] buffer = new char[BUFFER_SIZE];
        int count;
        while ((count = from.read(buffer)) != -1) {
            to.append(buffer, 0, count);
        }
        return to.toString();
    }
}
