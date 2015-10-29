package io.vuh.text.elasticsearch;

import io.vuh.text.persistence.model.Article;

/**
 * Interface to communicate with the ElasticSearch server.
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
public interface ElasticSearchClient {
	/**
	 * Will post a new {@link Article} to ElasticSearch
	 * 
	 * @param article
	 */
	void postArticle(Article article);
}
