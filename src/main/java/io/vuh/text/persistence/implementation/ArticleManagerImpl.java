/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.vuh.text.persistence.implementation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.vuh.text.persistence.ArticleManager;
import io.vuh.text.persistence.model.Article;
import rx.Observable;

/**
 * Implementation of {@link ArticleManager}
 * 
 * Uses JPA {@link EntityManager} for container-based RDBMS persistence.
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@Stateless
public class ArticleManagerImpl implements ArticleManager {

	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.vuh.text.model.ArticleManager#createArticle(io.vuh.text.model.Article)
	 */
	@Override
	public void createArticle(Article article) {
		entityManager.persist(article);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.vuh.text.model.ArticleManager#getArticleById(java.lang.String)
	 */
	@Override
	public Observable<Article> getArticleById(String id) {
		// It will return a RxJava Observable, if the entityManager returns
		// null, it will be filtered by the filter predicate
		return Observable.just(entityManager.find(Article.class, id)).filter(article -> article != null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.vuh.text.model.ArticleManager#getAllArticles()
	 */
	@Override
	public Observable<Article> getAllArticles() {
		return Observable.from(entityManager.createNamedQuery("Article.getAllArticles", Article.class).getResultList());
	}

}
