package io.vuh.text.elasticsearch.implementation;

import static org.easymock.EasyMock.expectLastCall;

import java.lang.reflect.Field;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vuh.text.elasticsearch.ElasticSearchResource;

/**
 * Tests functionality for {@link ElasticSearchResourceImpl}
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@RunWith(EasyMockRunner.class)
public class ElasticSearchResourceImplTest {

	private static final String TEST_ID = "123";

	@TestSubject
	private ElasticSearchResourceImpl esResource = new ElasticSearchResourceImpl();

	@Mock
	private ElasticSearchManagerImpl elasticSearchManager;

	/**
	 * Injects Mocks using Reflection
	 */
	@Before
	public void setUp() {
		Field field;
		try {
			field = ElasticSearchResourceImpl.class.getDeclaredField("elasticSearchManager");
			field.setAccessible(true);
			field.set(esResource, elasticSearchManager);
			field.setAccessible(false);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// This should not fail unless the declared field name is changed.
		}
	}

	/**
	 * Given that I have a set of articles in the persistence layer, When I call
	 * the {@link ElasticSearchResource#pushArticleById(String)} with a given
	 * id, Then it will call the manager to send it to ElasticCloud if it
	 * exists.
	 */
	@Test
	public void testPushArticleByIdResource() {
		elasticSearchManager.pushArticleById(TEST_ID);
		expectLastCall();

		replayMocks();
		esResource.pushArticleById(TEST_ID);
		verifyMocks();
	}

	/**
	 * Sets the mocks on Replay mode
	 */
	private void replayMocks() {
		EasyMock.replay(elasticSearchManager);
	}

	/**
	 * Verifies calls on the mocks
	 */
	private void verifyMocks() {
		EasyMock.verify(elasticSearchManager);
	}
}
