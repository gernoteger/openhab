package org.openhab.action.hue.internal;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * a response from the bridge
 * 
 * @author gernot
 * 
 */
public class HueResponse {

    private final String rawResponse;
    private final List<Map<String, Map<String, Object>>> responses;

    public List<Map<String, Map<String, Object>>> getResponses() {
	return responses;
    }

    /**
     * get raw response string as returned from bridge
     * 
     * @return
     */
    public String getRawResponse() {
	return rawResponse;
    }

    /**
     * create response from string
     * 
     * @param responseString
     */
    @SuppressWarnings("unchecked")
    public HueResponse(String responseString) {
	// scan responses

	rawResponse = responseString;

	ObjectMapper mapper = new ObjectMapper();
	List<Map<String, Map<String, Object>>> resp = null;
	try {
	    resp = mapper.readValue(responseString, List.class);
	} catch (IOException e) {
	    // couldn't even parse!
	    resp = null;
	}
	responses = resp;
    }

    /**
     * get all success items
     * 
     * @return List<Map<String, Object>> wit all success items
     */
    public List<Map<String, Object>> getSuccess() {
	List<Map<String, Object>> success = new LinkedList<Map<String, Object>>();
	for (Map<String, Map<String, Object>> entry : responses) {
	    Map<String, Object> s = entry.get("success");
	    if (s != null) {
		success.add(s);
	    }
	}
	return success;
    }

    /**
     * assumes only one resultreturn id or null if not existing
     * 
     * @return
     */
    public String getId() {
	Object result = getSingleResult("id");
	if (result == null)
	    return null;

	return (String) result;
    }

    public Object getSingleResult(String itemKey) {
	Map<String, Object> singleResult = getSuccess().get(0);
	if (singleResult == null)
	    return null;

	return singleResult.get(itemKey);
    }

    /**
     * true if results contain an error
     * 
     * @return
     */
    public boolean hasError() {
	for (Map<String, Map<String, Object>> item : responses) {
	    if (item.containsKey("error")) {
		return true;
	    }
	}
	return false;
    }
}
