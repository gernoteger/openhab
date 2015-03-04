package org.openhab.action.hue.internal;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openhab.binding.hue.internal.common.HueContext;
import org.openhab.binding.hue.internal.hardware.HueBridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public abstract class AbstractHueResource {
    protected static final Logger logger = LoggerFactory
	    .getLogger(AbstractHueResource.class);
    /**
     * the Id; will be set when reading!
     */
    @JsonIgnore
    private String id = null;

    @JsonProperty
    protected String name;

    @JsonIgnore
    public String getName() {
	return name;
    }

    @JsonIgnore
    public void setName(String name) {
	this.name = name;
    }

    @JsonIgnore
    public void setId(String id) {
	this.id = id;
    }

    @JsonIgnore
    public String getId() {
	return id;
    }

    /**
     * return Json for update
     * 
     * @return
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @JsonIgnore
    public String toJson() {

	ObjectMapper mapper = new ObjectMapper();

	try {
	    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
		    this);
	} catch (IOException e) {
	    throw new RuntimeException("mapping problem", e);
	}
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @JsonIgnore
    public static AbstractHueResource create(String json, Class clazz)
	    throws IOException {
	ObjectMapper mapper = new ObjectMapper();

	return mapper.readValue(json, clazz);
    }

    enum HttpMethod {
	GET, PUT, POST, DELETE
    };

    /**
     * get resource
     * 
     * @param path
     *            WITHOUT leading /
     * @return
     */
    @JsonIgnore
    protected static String getResource(String path) {
	return resourceRequest(HttpMethod.GET, path, (String) null);
    }

    /**
     * delete a resource
     * 
     * @param path
     * @return
     */
    @JsonIgnore
    protected static HueResponse deleteResource(String path) {
	String responseString = resourceRequest(HttpMethod.DELETE, path,
		(String) null);
	return new HueResponse(responseString);
    }

    /**
     * 
     * @param path
     * @param body
     * @return
     */
    @JsonIgnore
    protected static HueResponse putResource(String path, String body) {
	String responseString = resourceRequest(HttpMethod.PUT, path, body);
	return new HueResponse(responseString);
    }

    /**
     * 
     * @param path
     * @param body
     * @return
     */
    @JsonIgnore
    protected static HueResponse postResource(String path, String body) {
	String responseString = resourceRequest(HttpMethod.POST, path, body);
	return new HueResponse(responseString);
    }

    /**
     * 
     * @param method
     * @param inside
     *            bridge w/o api+secret part, e.g. /lights/1
     * @param resource
     * @return
     */
    @JsonIgnore
    protected static String resourceRequest(HttpMethod method, String path,
	    AbstractHueResource resource) {
	String body = null;
	if (resource != null) {
	    try {
		body = resource.toJson();
	    } catch (Exception e) {
		logger.warn("failed to serialize to json for path '" + path
			+ "'", e);

	    }
	}
	return resourceRequest(method, path, body);
    }

    /**
     * request resource
     * 
     * @param method
     * @param path
     *            inside bridge w/o api+secret part, e.g. /lights/1
     * @param body
     * @return
     */
    @JsonIgnore
    protected static String resourceRequest(HttpMethod method, String path,
	    String body) {

	logger.debug("resourceRequest {} start...", method);

	HueBridge bridge = HueContext.getInstance().getBridge();
	Client client = bridge.getClient();

	if (path.startsWith("/")) { // remove leading /
	    path = path.substring(1);
	}
	String url = bridge.getUrl() + path;

	WebResource webResource = client.resource(url);

	try {
	    ClientResponse response = null;
	    switch (method) {
	    case GET:
		response = webResource.accept("application/json").get(
			ClientResponse.class);
		break;
	    case PUT:
		response = webResource.accept("application/json").put(
			ClientResponse.class, body);
		break;
	    case POST:
		response = webResource.accept("application/json").post(
			ClientResponse.class, body);
		break;
	    case DELETE:
		response = webResource.accept("application/json").delete(
			ClientResponse.class);
		break;

	    }

	    String responseString = response.getEntity(String.class);

	    if (response.getStatus() != 200) {
		logger.warn("Failed to connect to Hue bridge at '" + url
			+ "': HTTP error code: " + response.getStatus());
		return null;
	    }

	    // check if ok
	    ObjectMapper mapper = new ObjectMapper();
	    try {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> responses = mapper.readValue(
			responseString, List.class);
		for (Map<String, Object> item : responses) {
		    if (item.containsKey("error")) {
			logger.warn("failed send command, url={}", url);
			logger.warn("failed send command, body={}", body);
			logger.warn("failed send command, response={}",
				responseString);
		    }
		}
	    } catch (IOException e) {
		logger.warn("failed to parse response '{}'", responseString);
	    }

	    return responseString;
	} catch (ClientHandlerException e) {
	    logger.warn("Failed to connect to Hue bridge at '" + url
		    + "': HTTP request timed out.");
	    return null;
	} finally {
	    logger.debug("resourceRequest done.");
	}

    }

}
