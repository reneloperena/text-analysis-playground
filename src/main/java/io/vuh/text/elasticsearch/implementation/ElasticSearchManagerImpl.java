package io.vuh.text.elasticsearch.implementation;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vuh.text.elasticsearch.ElasticSearchClient;
import io.vuh.text.elasticsearch.ElasticSearchManager;
import io.vuh.text.persistence.ArticleManager;
import io.vuh.text.persistence.model.Article;
import rx.Observable;

/**
 * Implements {@link ElasticSearchManager}
 * 
 * Uses {@link ArticleManager} to communicate with the persistence layer and
 * {@link ElasticSearchClient} to make the requests to ElasticSearch.
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@Stateless
public class ElasticSearchManagerImpl implements ElasticSearchManager {

	@Inject
	private Logger logger;

	@Inject
	private ArticleManager articleManager;

	@Inject
	private ElasticSearchClient client;

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.vuh.text.elasticsearch.ElasticSearchController#pushAllArticles()
	 */
	@Override
	public void pushAllArticles() {
		try{
			articleManager.getAllArticles().subscribe(article -> new Thread(() -> client.postArticle(article)).start());
		}catch(Exception e){
			logger.error("Error on ArticleManager when loading all articles.",e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.vuh.text.elasticsearch.ElasticSearchController#pushArticleById(java.
	 * lang.String)
	 */
	@Override
	public void pushArticleById(String id) {
		try{
			articleManager.getArticleById(id).subscribe(client::postArticle);
		}catch(Exception e){
			logger.error("Error on ArticleManager when loading "+id+" article.",e);
		}
	}

}
