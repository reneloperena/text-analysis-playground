package io.vuh.text.rss;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import io.vuh.text.model.Article;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author nobuji.saito
 *
 */
public class RSSArticleReaderImpl implements RSSArticleReader {

    @Inject
    private NewsContentScrapper newsContentScrapper;

    @Inject
    private Logger logger;

    private Article createArticle(final SyndEntry s, final String source) {
	final Article result = new Article();
	result.setDate(s.getPublishedDate());
	result.setId(Integer.toString(s.getUri().hashCode()));
	result.setSource(source);
	try {
	    result.setText(newsContentScrapper.getNewsContent(s.getLink()));
	} catch (final IOException e) {
	    result.setText("Unable to scrape News Content");
	}
	result.setTitle(s.getTitle());
	result.setUrl(s.getLink());
	return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see io.vuh.text.rss.RSSArticleReader#loadArticles()
     */
    @Override
    public List<Article> loadArticles(final String rssFeedUrl) throws MalformedURLException {
	logger.debug("Called loadArticles with " + rssFeedUrl);

	final List<Article> results = new ArrayList<>();

	final URL url = new URL(rssFeedUrl);

	final SyndFeedInput input = new SyndFeedInput();
	try {
	    final SyndFeed feed = input.build(new XmlReader(url));

	    Observable.from(feed.getEntries()).flatMap(entry -> {
		return Observable.defer(() -> Observable.just(createArticle(entry, feed.getDescription())))
			.subscribeOn(Schedulers.newThread());
	    }).toBlocking().forEach(article -> results.add(article));

	} catch (IllegalArgumentException | FeedException | IOException e) {
	    e.printStackTrace();
	}
	return results;
    }

}
