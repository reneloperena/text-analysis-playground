package io.vuh.text.elasticsearch.implementation;

import java.util.List;

import javax.inject.Inject;

import io.vuh.text.elasticsearch.ArticleController;
import io.vuh.text.elasticsearch.ArticleResource;

public class ArticleResourceImpl implements ArticleResource{
	
	@Inject
	private ArticleController articleController;
	
	@Override
	public void loadArticlesById(List<String> ids) {
		articleController.retreiveArticlesByIDs(ids);
	}
	
	

}
