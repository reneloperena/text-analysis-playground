package io.vuh.text.elasticsearch;

import java.util.List;

public interface ElasticSearchController {

	void pushAllArticles();

	void pushArticlesByIDs(List<String> ids);

}
