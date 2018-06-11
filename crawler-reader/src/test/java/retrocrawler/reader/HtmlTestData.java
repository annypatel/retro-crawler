package retrocrawler.reader;

/**
 * Defines static constants for test data.
 */
class HtmlTestData {

    static final String HEAD;
    static final String BODY;
    static final String HTML;

    static {

        String content = "Lorem ipsum dolor sit amet, consectetur elit, sed do eiusmod " +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo " +
                "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse " +
                "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat " +
                "non proident, sunt in culpa qui officia deserunt mollit anim id est laborum." +
                "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium " +
                "doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore " +
                "veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim " +
                "ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia " +
                "consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque " +
                "porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, " +
                "adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et " +
                "dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis " +
                "nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex " +
                "ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea " +
                "voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem " +
                "eum fugiat quo voluptas nulla pariatur?";

        HEAD = "<head>" +
                "<title>Page Title</title>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"description\" content=\"Free Web tutorials\">" +
                "<meta name=\"keywords\" content=\"HTML,CSS,XML,JavaScript\">" +
                "<meta name=\"author\" content=\"John Doe\">" +
                "<link rel=\"stylesheet\" href=\"mystyle.css\">" +
                "<style>" +
                "body {background-color: powderblue;}" +
                "h1   {color: blue;}" +
                "p    {color: red;}" +
                "</style>" +
                "<script>" +
                "function myFunction {" +
                "    document.getElementById(\"demo\").innerHTML = \"Hello JavaScript!\";" +
                "}" +
                "</script>" +
                "</head>";

        BODY = "<body>" +
                "<h1>This is Heading 1</h1>" +
                "<h2>This is Heading 2</h2>" +
                "<h3>This is Heading 3</h3>" +
                "<h4>This is Heading 4</h4>" +
                "<h5>This is Heading 5</h5>" +
                "<h6>This is Heading 6</h6>" +
                "<p>" +
                content +
                content +
                content +
                content +
                "</p>" +
                "<ul>" +
                "<li>Coffee</li>" +
                "<li>Tea</li>" +
                "<li>Milk</li>" +
                "</ul>" +
                "<a href=\"https://www.example.com\">This is a link</a>" +
                "</body>";

        HTML = "<html>" +
                HEAD +
                BODY +
                "</html>";
    }
}
