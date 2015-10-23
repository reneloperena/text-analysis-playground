package io.vuh.text.elasticsearch.implementation;

import org.easymock.EasyMock;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vuh.text.model.Article;

public class ElasticSearchClientImplTest {
	private ElasticSearchClientImpl elasticSearchClientImpl;
	private ObjectMapper objectMapper;
	private Client client;
	
	@Before
	public void setUp() throws Exception {
		objectMapper = EasyMock.createMock(ObjectMapper.class);
		client = EasyMock.createMock(Client.class);
		elasticSearchClientImpl = new ElasticSearchClientImpl();
		elasticSearchClientImpl.setClient(client);
		elasticSearchClientImpl.setObjectMapper(objectMapper);
	}
	
	/**
	 * Given a valid Article
	 * When testPostArticle is called
	 * Then the article will get parsed to JSON
	 * And sent to ElasticSearch via the Java API.
	 * @throws JsonProcessingException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testPostArticle() throws JsonProcessingException{
		IndexRequestBuilder indexRequestBuilder = EasyMock.mock(IndexRequestBuilder.class);
		ListenableActionFuture<IndexResponse> listenableActionFuture = EasyMock.mock(ListenableActionFuture.class);
		String id = "testId";
		Article article = new Article();
		article.setId(id);
		byte[] json = "{}".getBytes();
		EasyMock.expect(objectMapper.writeValueAsBytes(id)).andReturn(json);
		EasyMock.expect(indexRequestBuilder.setSource(json)).andReturn(indexRequestBuilder);
		EasyMock.expect(indexRequestBuilder.execute()).andReturn(listenableActionFuture);
		EasyMock.expect(listenableActionFuture.actionGet());
		EasyMock.expect(client.prepareIndex("articles","article",article.getId())).andReturn(indexRequestBuilder);
		
		elasticSearchClientImpl.postArticle(article);
	}

}
