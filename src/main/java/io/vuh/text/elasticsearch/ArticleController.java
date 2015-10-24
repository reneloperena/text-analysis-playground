package io.vuh.text.elasticsearch;

import java.util.List;

public interface ArticleController {
	
	void pushAllArticles();
	
	void pushArticlesByIDs(List<String> ids);
	
}
