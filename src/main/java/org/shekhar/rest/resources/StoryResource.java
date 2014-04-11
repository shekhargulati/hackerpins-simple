package org.shekhar.rest.resources;

import org.hibernate.validator.constraints.URL;
import org.shekhar.business.bean_validation.StoryExists;
import org.shekhar.business.domain.Story;
import org.shekhar.business.services.StoryService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shekhargulati on 11/04/14.
 */
@Path("/stories")
public class StoryResource {

    @Inject
    private StoryService storyService;


    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response submitStory(@Valid @NotNull @StoryExists Story story) {
        Story submittedStory = storyService.create(story);
        return Response.status(Response.Status.CREATED).entity(submittedStory).build();
    }

    @GET
    @Produces("application/json")
    public
    @NotNull
    List<Story> storyTimeline() {
        return storyService.findAll();
    }

    @GET
    @Produces("application/json")
    @Path("/suggest")
    public Map<String, String> suggest(@QueryParam("url") @NotNull @URL String url) {
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://gooseextractor-t20.rhcloud.com/api/v1/extract").queryParam("url", url).request().get(String.class);
            JsonReader jsonReader = Json.createReader(new StringReader(response));
            JsonObject jsonObject = jsonReader.readObject();
            String image = jsonObject.getString("image");
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("text");
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("image", image);
            responseMap.put("title", title);
            responseMap.put("description", description);
            return responseMap;
        } catch (Exception e) {
            e.printStackTrace();;
            return Collections.EMPTY_MAP;
        }

    }
}

