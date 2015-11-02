package io.vuh.text.elasticsearch.implementation;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vuh.text.persistence.model.Article;

/**
 * Tests functionality for {@link ElasticSearchClientImpl}
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@RunWith(EasyMockRunner.class)
public class ElasticSearchClientImplTest {

	private static final String TEST_ID = "123";

	@TestSubject
	private ElasticSearchClientImpl esClient = new ElasticSearchClientImpl();

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private Client client;

	@Mock
	private Logger logger;

	@Mock
	private IndexRequestBuilder mockRequest;

	@Mock
	private ListenableActionFuture<IndexResponse> mockListeneableFuture;

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
			field = ElasticSearchClientImpl.class.getDeclaredField("client");
			field.setAccessible(true);
			field.set(esClient, client);
			field = ElasticSearchClientImpl.class.getDeclaredField("logger");
			field.setAccessible(true);
			field.set(esClient, logger);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// This should not fail unless the declared field name is changed.
		}
	}

	/**
	 * Given that I have an {@link Article} object When I call
	 * {@link #testPostValidArticle()} I will push the object into the
	 * ElasticSearch server
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testPostValidArticle() throws JsonProcessingException {
		Article article = new Article();
		article.setId(TEST_ID);
		byte[] articleJson = "{\"id\":\"123\"".getBytes();

		expect(objectMapper.writeValueAsBytes(article)).andReturn(articleJson);
		expect(client.prepareIndex("articles", "article", article.getId())).andReturn(mockRequest);
		expect(mockRequest.setSource(articleJson)).andReturn(mockRequest);
		expect(mockRequest.execute()).andReturn(mockListeneableFuture);
		expect(mockListeneableFuture.actionGet()).andReturn(null);

		replayMocks();
		esClient.postArticle(article);
		verifyMocks();
	}

	/**
	 * Given that I have an {@link Article} object which cannot be parsed
	 * correctly by my JSON parser. When I call {@link #testPostValidArticle()}
	 * and the parser throws an exception Then I will stop the flow and log the
	 * exception
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testJsonParserExceptionOnPostArticle() throws JsonProcessingException {
		Article article = new Article();
		JsonProcessingException ex = EasyMock.mock(JsonProcessingException.class);

		expect(objectMapper.writeValueAsBytes(article)).andThrow(ex);
		logger.error(ex);
		expectLastCall();

		replayMocks();
		esClient.postArticle(article);
		verifyMocks();
	}

	/**
	 * Sets the mocks on Replay mode
	 */
	private void replayMocks() {
		EasyMock.replay(objectMapper);
		EasyMock.replay(client);
		EasyMock.replay(mockRequest);
		EasyMock.replay(mockListeneableFuture);
		EasyMock.replay(logger);
	}

	/**
	 * Verifies calls on the mocks
	 */
	private void verifyMocks() {
		EasyMock.verify(objectMapper);
		EasyMock.verify(client);
		EasyMock.verify(mockRequest);
		EasyMock.verify(mockListeneableFuture);
		EasyMock.verify(logger);
	}

}
