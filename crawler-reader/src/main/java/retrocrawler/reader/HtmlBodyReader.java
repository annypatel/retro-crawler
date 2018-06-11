package retrocrawler.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import retrocrawler.HtmlReader;

/**
 * Reads only body tag of html page, ignores content inside head tag and appends empty head.
 */
class HtmlBodyReader implements HtmlReader {

    private static final String HTML_START = "<html>";
    private static final String HEAD = "<head></head>";
    private static final String BODY_START = "<body>";

    @Override
    public String read(Reader reader) throws IOException {
        final BufferedReader from = new BufferedReader(reader);
        final StringBuilder to = new StringBuilder();

        String line;
        int index;
        while ((line = from.readLine()) != null) {

            index = line.indexOf(BODY_START);
            if (index > -1) {

                line = line.substring(index, line.length());
                to.append(HTML_START)
                        .append(HEAD)
                        .append(line);
                break;
            }
        }

        while ((line = from.readLine()) != null) {
            to.append(line);
        }

        return to.toString();
    }
}
