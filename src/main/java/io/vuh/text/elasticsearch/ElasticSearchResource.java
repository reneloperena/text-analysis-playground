package io.vuh.text.elasticsearch;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * REST Endpoint Interface
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@Path("/v1/")
@Consumes({ "application/xml", "application/json" })
@Produces({ "application/xml", "application/json" })
public interface ElasticSearchResource {

	/**
	 * Will push a specific article from the persistence layer to ElasticSearch.
	 * @param id
	 */
	@POST
	@Path("articles")
	public void pushArticleById(String id);
}
