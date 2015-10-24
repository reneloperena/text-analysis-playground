package io.vuh.text.rss.manager;

import java.net.MalformedURLException;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vuh.text.model.Article;
import io.vuh.text.model.ArticleManager;
import io.vuh.text.rss.RSSArticleReader;
import io.vuh.text.rss.resource.transport.LoadRSSResponse;
import rx.Observable;
import rx.schedulers.Schedulers;

public class RSSArticleManagerImpl implements RSSArticleManager {

    @Inject
    private RSSArticleReader rssArticleReader;

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
	    final List<Article> results = rssArticleReader.loadArticles(url);
	    response.setLoadedArticles(results.size());

	    Observable.from(results).flatMap(entry -> {
		return Observable.defer(() -> Observable.just(entry)).subscribeOn(Schedulers.newThread());
	    }).toBlocking().forEach(article -> articleManager.createArticle(article));

	    final long endTime = System.nanoTime() - startTime;
	    final double timeElapsed = (double) endTime / 1000000000;
	    logger.info("Load finished in " + timeElapsed + " seconds");
	    response.setTimeElapsed(timeElapsed);
	} catch (final MalformedURLException e) {
	    e.printStackTrace();
	}

	return response;

    }

}
