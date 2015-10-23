package io.vuh.text.elasticsearch;

import io.vuh.text.model.Article;

public interface ElasticSearchClient {
	void postArticle(Article article);
}
