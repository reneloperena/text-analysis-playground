package io.vuh.text.elasticsearch;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/v1/")
@Consumes({ "application/xml", "application/json" })
@Produces({ "application/xml", "application/json" })
public interface ElasticSearchResource {

	@POST
	@Path("articles")
	public void pushArticlesById(List<String> ids);
}
