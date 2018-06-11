package retrocrawler.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import retrocrawler.HtmlReader;

/**
 * Reads only head tag of html page, ignores content inside body tag and appends empty body.
 */
class HtmlHeadReader implements HtmlReader {

    private static final String HEAD_END = "</head>";
    private static final String BODY = "<body></body>";
    private static final String HTML_END = "</html>";

    @Override
    public String read(Reader reader) throws IOException {
        final BufferedReader from = new BufferedReader(reader);
        final StringBuilder to = new StringBuilder();

        String line;
        int index;
        while ((line = from.readLine()) != null) {

            index = line.indexOf(HEAD_END);
            if (index > -1) {

                line = line.substring(0, index);
                to.append(line)
                        .append(HEAD_END)
                        .append(BODY)
                        .append(HTML_END);
                break;
            } else {
                to.append(line);
            }
        }

        return to.toString();
    }
}
