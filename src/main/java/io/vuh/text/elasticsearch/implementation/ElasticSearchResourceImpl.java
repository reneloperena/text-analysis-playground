package io.vuh.text.elasticsearch.implementation;

import java.util.List;

import javax.inject.Inject;

import io.vuh.text.elasticsearch.ElasticSearchController;
import io.vuh.text.elasticsearch.ElasticSearchResource;

public class ElasticSearchResourceImpl implements ElasticSearchResource {

	@Inject
	private ElasticSearchController elasticSearchController;

	@Override
	public void pushArticlesById(List<String> ids) {
		elasticSearchController.pushArticlesByIDs(ids);
	}
}
