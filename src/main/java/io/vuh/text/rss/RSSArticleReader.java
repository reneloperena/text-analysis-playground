package io.vuh.text.rss;

import java.net.MalformedURLException;

import io.vuh.text.model.Article;
import rx.Observable;

/**
 * @author nobuji.saito
 *
 */
public interface RSSArticleReader {

    /**
     * @return List<Article>
     * @throws MalformedURLException
     */
    Observable<Article> loadArticles(String url) throws MalformedURLException;

}