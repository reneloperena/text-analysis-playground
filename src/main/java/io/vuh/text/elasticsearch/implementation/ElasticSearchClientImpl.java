package io.vuh.text.elasticsearch.implementation;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vuh.text.elasticsearch.ElasticSearchClient;
import io.vuh.text.model.Article;

public class ElasticSearchClientImpl implements ElasticSearchClient {

	@Inject
	private Logger logger;

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	private Client client;

	@Override
	public void postArticle(Article article) {
		try {
			byte[] json = objectMapper.writeValueAsBytes(article);
			client.prepareIndex("articles", "article", article.getId()).setSource(json).execute().actionGet();
		} catch (JsonProcessingException e) {
			logger.error(e);
		}

	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
