package io.vuh.text.elasticsearch.implementation;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vuh.text.elasticsearch.ElasticSearchClient;
import io.vuh.text.elasticsearch.ElasticSearchManager;
import io.vuh.text.persistence.ArticleManager;
import io.vuh.text.persistence.model.Article;
import rx.Observable;

/**
 * Tests functionality for {@link ElasticSearchManagerImpl}
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@RunWith(EasyMockRunner.class)
public class ElasticSearchManagerImplTest {

	private static final String TEST_ID = "123";

	@TestSubject
	private ElasticSearchManagerImpl esManager = new ElasticSearchManagerImpl();

	@Mock
	private ArticleManager articleManager;

	@Mock
	private ElasticSearchClient client;

	@Mock
	private Logger logger;

	/**
	 * Injects Mocks using Reflection
	 */
	@Before
	public void setUp() {
		Field field;
		try {
			field = ElasticSearchManagerImpl.class.getDeclaredField("articleManager");
			field.setAccessible(true);
			field.set(esManager, articleManager);
			field.setAccessible(false);
			field = ElasticSearchManagerImpl.class.getDeclaredField("client");
			field.setAccessible(true);
			field.set(esManager, client);
			field.setAccessible(false);
			field = ElasticSearchManagerImpl.class.getDeclaredField("logger");
			field.setAccessible(true);
			field.set(esManager, logger);
			field.setAccessible(false);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// This should not fail unless the declared field name is changed.
		}
	}

	/**
	 * Given that I have a set of articles in my DB, When I call
	 * {@link ElasticSearchManager#pushAllArticles()}, Then I will retrieve all
	 * articles from the persistence layer and post them into ElasticSearch
	 * 
	 * <br>
	 * TODO: Change the {@link Thread#sleep(long)} for something better like a
	 * {@link CountDownLatch}. <br>
	 * TODO: Investigate if the latter is the reason for the test failing
	 * sometimes, or is there another reason.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testPushAllArticles() throws InterruptedException {
		Article article = new Article();
		Observable<Article> articles = Observable.just(article, article);
		
		expect(articleManager.getAllArticles()).andReturn(articles);
		client.postArticle(article);
		expectLastCall().times(2);

		replayMocks();
		esManager.pushAllArticles();
		// Should change this for CountDownLatch or a similar solution, left
		// like this just for testing
		Thread.sleep(250);
		verifyMocks();

	}

	/**
	 * Given that I have a persistence layer is not working as expected, When I call
	 * {@link ElasticSearchManager#pushAllArticles()},
	 * Then I will log the error
	 */
	@Test
	public void testRuntimeErrorOccursOnPersistenceWhilePushingAllArticles() {
		IllegalArgumentException ex = new IllegalArgumentException();
		
		expect(articleManager.getAllArticles()).andThrow(ex);
		logger.error("Error on ArticleManager when loading all articles.",ex);
		expectLastCall();
		
		replayMocks();
		esManager.pushAllArticles();
		verifyMocks();
	}

	/**
	 * Given that I have an article in the persistence layer, When I call
	 * {@link ElasticSearchManager#pushArticleById(String} and pass in its id,
	 * Then I will retrieve it from the persistence layer and post it into
	 * ElasticSearch
	 */
	@Test
	public void testPushArticleByValidId() {
		Article article = new Article();
		Observable<Article> articleObservable = Observable.just(article);
		
		expect(articleManager.getArticleById(TEST_ID)).andReturn(articleObservable);
		client.postArticle(article);
		expectLastCall();

		replayMocks();
		esManager.pushArticleById(TEST_ID);
		verifyMocks();
	}

	/**
	 * Given that I have a persistence layer, When I call
	 * {@link ElasticSearchManager#pushArticleById(String} and pass in an invalid id,
	 * Then It wont post anything to ElasticSearch
	 * ElasticSearch
	 */
	@Test
	public void testPushArticleByInvalidId() {
		Article article = null;
		Observable<Article> articleObservable = Observable.just(article).filter((val) -> val != null);
		
		expect(articleManager.getArticleById(null)).andReturn(articleObservable);

		replayMocks();
		esManager.pushArticleById(null);
		verifyMocks();
	}

	/**
	 * Given that I have a persistence layer is not working as expected, When I call
	 * {@link ElasticSearchManager#pushArticleById(String},
	 * Then I will log the error
	 */
	@Test
	public void testRuntimeErrorOccursOnPersistenceWhilePushingPushArticleById() {
		IllegalArgumentException ex = new IllegalArgumentException();
		
		expect(articleManager.getArticleById(TEST_ID)).andThrow(ex);
		logger.error("Error on ArticleManager when loading "+TEST_ID+" article.",ex);
		expectLastCall();
		
		replayMocks();
		esManager.pushArticleById(TEST_ID);
		verifyMocks();
	}

	/**
	 * Sets the mocks on Replay mode
	 */
	private void replayMocks() {
		EasyMock.replay(articleManager);
		EasyMock.replay(client);
	}

	/**
	 * Verifies calls on the mocks
	 */
	private void verifyMocks() {
		EasyMock.verify(articleManager);
		EasyMock.verify(client);
	}
}
