package io.vuh.text.elasticsearch;

import java.util.List;

import io.vuh.text.model.Article;

public interface ArticleController {
	
	void retreiveAllArticles();
	
	void retreiveArticlesByIDs(List<String> ids);
	
}
