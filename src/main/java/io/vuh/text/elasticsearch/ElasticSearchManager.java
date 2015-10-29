package io.vuh.text.elasticsearch;

/**
 * 
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
public interface ElasticSearchManager {
	/**
	 * Will push all the available articles in the the persistence layer to the ElasticSearch server.
	 */
	void pushAllArticles();
	/**
	 * Will push a specific article in the persistence layer to the ElasticSearch server.
	 * @param id of the article
	 */
	void pushArticleById(String id);
}
