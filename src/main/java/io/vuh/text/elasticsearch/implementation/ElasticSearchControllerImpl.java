package io.vuh.text.elasticsearch.implementation;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vuh.text.elasticsearch.ElasticSearchClient;
import io.vuh.text.elasticsearch.ElasticSearchController;
import io.vuh.text.model.ArticleManager;
import rx.Observable;
import rx.schedulers.Schedulers;

@Stateless
public class ElasticSearchControllerImpl implements ElasticSearchController {

	@Inject
	private Logger logger;

	@Inject
	private ArticleManager articleManager;

	@Inject
	private ElasticSearchClient client;

	@Override
	public void pushAllArticles() {
		Observable.from(articleManager.getAllArticles()).forEach((article)-> new Thread(()->client.postArticle(article)).start());
	}

	@Override
	public void pushArticlesByIDs(List<String> ids) {
		Observable
	    .from(ids)
	    .flatMap((id)->{
	    	return Observable.defer(()-> Observable.just(articleManager.getArticleById(id))).subscribeOn(Schedulers.newThread());
	    }).forEach((article)-> new Thread(()->client.postArticle(article)).start());
	}
}
