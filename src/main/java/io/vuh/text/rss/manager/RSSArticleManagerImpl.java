package io.vuh.text.rss.manager;

import java.net.MalformedURLException;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vuh.text.persistence.ArticleManager;
import io.vuh.text.persistence.model.Article;
import io.vuh.text.rss.RSSArticleReader;
import io.vuh.text.rss.RSSArticleReaderImpl;
import io.vuh.text.rss.resource.transport.LoadRSSResponse;
import rx.Observable;

public class RSSArticleManagerImpl implements RSSArticleManager {

    public static void main(final String args[]) {
	final RSSArticleManager manager = new RSSArticleManagerImpl();
	manager.loadRSSFeed("http://www.chicagotribune.com/bluesky/rss2.0.xml");
    }

    // @Inject
    private final RSSArticleReader rssArticleReader = new RSSArticleReaderImpl();

    @Inject
    private ArticleManager articleManager;

    @Inject
    private Logger logger;

    /*
     * (non-Javadoc)
     *
     * @see
     * io.vuh.text.rss.manager.RSSArticleManager#loadRSSFeed(java.lang.String)
     */
    @Override
    public LoadRSSResponse loadRSSFeed(final String url) {
	final LoadRSSResponse response = new LoadRSSResponse();

	try {
	    final long startTime = System.nanoTime();
	    final Observable<Article> results = rssArticleReader.loadArticles(url);

	    results.toBlocking().forEach(article -> {
		// articleManager.createArticle(article);
		System.out.println(article.getTitle());
		response.setLoadedArticles(response.getLoadedArticles() + 1);
	    });

	    final long endTime = System.nanoTime() - startTime;
	    final double timeElapsed = (double) endTime / 1000000000;
	    // logger.info("Load finished in " + timeElapsed + " seconds");
	    System.out.println("Load finished in " + timeElapsed + " seconds");
	    response.setTimeElapsed(timeElapsed);
	} catch (final MalformedURLException e) {
	    e.printStackTrace();
	}

	return response;

    }

}
