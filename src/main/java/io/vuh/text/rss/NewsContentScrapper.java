package io.vuh.text.rss;

import java.io.IOException;
import java.net.MalformedURLException;

import rx.Observable;

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
    Observable<String> getNewsContent(String url) throws MalformedURLException, IOException;

}
