package io.vuh.text.elasticsearch.implementation;

import java.lang.reflect.Field;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.elasticsearch.client.Client;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests functionality for {@link ElasticSearchClientImpl}
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@RunWith(EasyMockRunner.class)
public class ElasticSearchClientImplTest {

	@TestSubject
	private ElasticSearchClientImpl esClient = new ElasticSearchClientImpl();

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private Client client;

	/**
	 * Injects Mocks using Reflection
	 */
	@Before
	public void setUp() {
		Field field;
		try {
			field = ElasticSearchClientImpl.class.getDeclaredField("objectMapper");
			field.setAccessible(true);
			field.set(esClient, objectMapper);
			field.setAccessible(false);
			field = ElasticSearchClientImpl.class.getDeclaredField("client");
			field.setAccessible(true);
			field.set(esClient, client);
			field.setAccessible(false);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// This should not fail unless the declared field name is changed.
		}
	}

	/**
	 * Sets the mocks on Replay mode
	 */
	private void replayMocks() {
		EasyMock.replay(objectMapper);
		EasyMock.replay(client);
	}

	/**
	 * Verifies calls on the mocks
	 */
	private void verifyMocks() {
		EasyMock.verify(objectMapper);
		EasyMock.verify(client);
	}

}
