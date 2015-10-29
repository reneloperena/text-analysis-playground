package io.vuh.text.elasticsearch.implementation;

import java.lang.reflect.Field;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.vuh.text.elasticsearch.ElasticSearchClient;
import io.vuh.text.persistence.ArticleManager;

/**
 * Tests functionality for {@link ElasticSearchManagerImpl}
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@RunWith(EasyMockRunner.class)
public class ElasticSearchManagerImplTest {

	@TestSubject
	private ElasticSearchManagerImpl esManager = new ElasticSearchManagerImpl();

	@Mock
	private ArticleManager articleManager;

	@Mock
	private ElasticSearchClient client;

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
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// This should not fail unless the declared field name is changed.
		}
	}
}
