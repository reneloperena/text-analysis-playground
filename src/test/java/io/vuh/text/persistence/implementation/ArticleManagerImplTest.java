/**
 * 
 */
package io.vuh.text.persistence.implementation;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vuh.text.persistence.ArticleManager;
import io.vuh.text.persistence.model.Article;

/**
 * Tests functionality for {@link ArticleManagerImpl}
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@RunWith(EasyMockRunner.class)
public class ArticleManagerImplTest {

	private static final String TEST_ID = "123";

	@TestSubject
	private ArticleManagerImpl articleManagerImpl = new ArticleManagerImpl();

	@Mock
	private EntityManager entityManager;

	/**
	 * Injects Mocks using Reflection
	 */
	@Before
	public void setUp() {
		Field field;
		try {
			field = ArticleManagerImpl.class.getDeclaredField("entityManager");
			field.setAccessible(true);
			field.set(articleManagerImpl, entityManager);
			field.setAccessible(false);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// This should not fail unless the declared field name is changed.
		}
	}

	/**
	 * Given a valid Article When I call
	 * {@link ArticleManager#createArticle(Article)} Then it will persist the
	 * article on the DB
	 */
	@Test
	public void testSuccessfulyCreateArticleWhenValidIsPassed() {
		Article article = new Article();
		entityManager.persist(article);
		expectLastCall();

		replayMocks();
		articleManagerImpl.createArticle(article);
		verifyMocks();
	}

	/**
	 * Given an invalid Article When I call
	 * {@link ArticleManager#createArticle(Article)} Then it throw an exception
	 * 
	 * @TODO: Create custom exception for this, so it can be caught later
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testExceptWhenInvalidEntityIsPassed() {
		entityManager.persist(null);
		expectLastCall().andThrow(new IllegalArgumentException(""));

		replayMocks();
		articleManagerImpl.createArticle(null);
	}

	/**
	 * Given that the given an id that exists in the db, When I call
	 * {@link ArticleManager#getArticleById(String)}, Then it will return an
	 * observable with the article.
	 * 
	 */
	@Test
	public void testGetAnArticleUsingAValidId() {
		;
		Article article = new Article();
		expect(entityManager.find(Article.class, TEST_ID)).andReturn(article);
		replayMocks();
		Article response = articleManagerImpl.getArticleById(TEST_ID).toBlocking().first();

		assertEquals("Must return same article as the DB", article, response);

	}

	/**
	 * Given that the given id does not exist in the db, When I call
	 * {@link ArticleManager#getArticleById(String)}, Then it will return an
	 * empty observable.
	 * 
	 */
	@Test(expected = NoSuchElementException.class)
	public void testReturnEmptyObservableWhenArticleDoesntExistInDB() {
		expect(entityManager.find(Article.class, TEST_ID)).andReturn(null);
		replayMocks();
		// If the observable is empty, first() will throw NoSuchElementException
		articleManagerImpl.getArticleById(TEST_ID).toBlocking().first();
	}

	/**
	 * Given an invalid id is passed, When I call
	 * {@link ArticleManager#getArticleById(String)}, Then it throw an exception
	 * 
	 * @TODO: Create custom exception for this, so it can be caught later
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testExceptWhenAnInvalidIdIsPassedWhileGettingArticle() {
		expect(entityManager.find(Article.class, null)).andThrow(new IllegalArgumentException(""));
		replayMocks();
		articleManagerImpl.getArticleById(null);

	}

	/**
	 * Given that I have Articles in my DB, When I call
	 * {@link ArticleManager#getAllArticles()}, Then it will return an
	 * observable with all the articles in the db.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllArticles() {
		Article article = new Article();
		List<Article> list = new ArrayList<>();
		list.add(article);
		TypedQuery<Article> typedQuery = EasyMock.mock(TypedQuery.class);
		expect(typedQuery.getResultList()).andReturn(list);
		expect(entityManager.createNamedQuery("Article.getAllArticles", Article.class)).andReturn(typedQuery);

		replayMocks();
		EasyMock.replay(typedQuery);
		Article result = articleManagerImpl.getAllArticles().toBlocking().last();
		verifyMocks();

		assertEquals("The item returned on the obserbable must be the same as the returned inside the list", result,
				article);
	}

	/**
	 * Sets the mocks on Replay mode
	 */
	private void replayMocks() {
		EasyMock.replay(entityManager);
	}

	/**
	 * Verifies calls on the mocks
	 */
	private void verifyMocks() {
		EasyMock.verify(entityManager);
	}

}
