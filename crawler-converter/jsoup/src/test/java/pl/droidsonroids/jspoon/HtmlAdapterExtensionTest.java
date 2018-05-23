package pl.droidsonroids.jspoon;

import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static pl.droidsonroids.jspoon.HtmlAdapterExtension.fromElement;

public class HtmlAdapterExtensionTest {

    @Test
    public void fromElement_givenHtmlAdapter_callsLoadFromNode() {
        HtmlAdapter<?> mockedAdapter = mock(HtmlAdapter.class);
        Element mockedElement = mock(Element.class);

        //noinspection ResultOfMethodCallIgnored
        fromElement(mockedAdapter, mockedElement);

        verify(mockedAdapter).loadFromNode(mockedElement);
    }
}