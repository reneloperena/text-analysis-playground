package io.vuh.text.rss;

import java.net.MalformedURLException;
import java.util.List;

import io.vuh.text.model.Article;

/**
 * @author nobuji.saito
 *
 */
public interface RSSArticleReader {

    /**
     * @return List<Article>
     * @throws MalformedURLException
     */
    List<Article> loadArticles(String url) throws MalformedURLException;

}