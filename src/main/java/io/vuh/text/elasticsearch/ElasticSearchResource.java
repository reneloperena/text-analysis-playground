package io.vuh.text.elasticsearch;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import io.vuh.text.persistence.model.Article;

/**
 * REST endpoint interface to interact with the system
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@Path("/v1/")
@Consumes({ "application/xml", "application/json" })
@Produces({ "application/xml", "application/json" })
public interface ElasticSearchResource {

	/**
	 * Pushes a specific {@link Article} from the persistence layer to
	 * ElasticSearch.
	 * 
	 * @param id
	 *            of the article to be pushed
	 */
	@POST
	@Path("articles")
	public void pushArticleById(String id);
}
