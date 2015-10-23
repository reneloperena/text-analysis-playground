package io.vuh.text.elasticsearch.implementation;

import java.util.List;

import io.vuh.text.elasticsearch.ElasticSearchClient;
import io.vuh.text.elasticsearch.ElasticSearchLoader;
import io.vuh.text.model.Article;
import rx.Observable;
import rx.schedulers.Schedulers;

public class ElasticSearchLoaderImpl implements ElasticSearchLoader{
	ElasticSearchClient client;

	@Override
	public void loadArticles(List<Article> articles) {
		Observable.from(articles).subscribeOn(Schedulers.io()).subscribe((article)-> {
			client.postArticle(article);
		});
	}

	public void setClient(ElasticSearchClient client) {
		this.client = client;
	}
	
	
}
