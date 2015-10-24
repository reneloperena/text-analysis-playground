package io.vuh.text.rss;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author nobuji.saito
 *
 */
public interface NewsContentScrapper {

    /**
     * @param url
     * @return {@link String}
     * @throws MalformedURLException
     * @throws IOException
     */
    String getNewsContent(String url) throws MalformedURLException, IOException;

}
