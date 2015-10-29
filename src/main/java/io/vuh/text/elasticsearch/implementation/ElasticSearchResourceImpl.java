package io.vuh.text.elasticsearch.implementation;

import javax.inject.Inject;

import io.vuh.text.elasticsearch.ElasticSearchManager;
import io.vuh.text.elasticsearch.ElasticSearchResource;

/**
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
public class ElasticSearchResourceImpl implements ElasticSearchResource {

	@Inject
	private ElasticSearchManager elasticSearchController;

	/* (non-Javadoc)
	 * @see io.vuh.text.elasticsearch.ElasticSearchResource#pushArticleById(java.lang.String)
	 */
	@Override
	public void pushArticleById(String id) {
		elasticSearchController.pushArticleById(id);
	}
}
