package io.vuh.text.elasticsearch;

import io.vuh.text.persistence.model.Article;

/**
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
public interface ElasticSearchClient {
	/**
	 * @param article
	 */
	void postArticle(Article article);
}
