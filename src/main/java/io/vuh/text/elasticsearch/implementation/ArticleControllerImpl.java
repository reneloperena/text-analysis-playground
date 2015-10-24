package io.vuh.text.elasticsearch.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vuh.text.elasticsearch.ArticleController;
import io.vuh.text.model.Article;
import io.vuh.text.model.ArticleManager;
import rx.Observable;

@Stateless
public class ArticleControllerImpl implements ArticleController{
	
	@Inject
	private Logger logger;
	
	@Inject 
	private ArticleManager articleManager;
	
	@Inject ElasticSearchLoaderImpl loader;
	
	@Override
	public void pushAllArticles(){		
		loader.loadArticles(articleManager.getAllArticles());
		
	}

	@Override
	public void pushArticlesByIDs(List<String> ids) {
		List<Article> articles = new ArrayList<>();
		Observable.from(ids).map((id)-> {return articleManager.getArticleById(id);})
		.subscribe((article)->{articles.add(article);},(err)->{logger.error(err);},()->{loader.loadArticles(articles);});
		
		
	}
}
