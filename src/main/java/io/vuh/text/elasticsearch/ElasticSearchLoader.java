package io.vuh.text.elasticsearch;

import java.util.List;

import io.vuh.text.model.Article;

public interface ElasticSearchLoader {
	void loadArticles(List<Article> articles);
}
