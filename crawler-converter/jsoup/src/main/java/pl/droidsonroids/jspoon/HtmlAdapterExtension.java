package pl.droidsonroids.jspoon;

import org.jsoup.nodes.Element;

/**
 * Extended {@link HtmlAdapter} allows to deserialize html into object directly from
 * {@link Element}.
 */
public class HtmlAdapterExtension {

    private HtmlAdapterExtension() {
    }

    public static <T> T fromElement(HtmlAdapter<T> adapter, Element pageRoot) {
        return adapter.loadFromNode(pageRoot);
    }
}