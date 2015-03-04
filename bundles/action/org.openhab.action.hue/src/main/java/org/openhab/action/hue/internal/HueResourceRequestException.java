package org.openhab.action.hue.internal;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A Request to the hue Bridge failed; error Messages are shown here
 * 
 * @author gernoteger
 * 
 */
public class HueResourceRequestException extends IOException {

    /**
     * 
     */
    private static final long serialVersionUID = -4767437135637440831L;

    private List<Map<String, Object>> responses = null;

    public List<Map<String, Object>> getResponses() {
	return responses;
    }

    public HueResourceRequestException() {
    }

    public HueResourceRequestException(List<Map<String, Object>> responses) {
	this.responses = responses;
    }

    public HueResourceRequestException(String message) {
	super(message);
    }

    public HueResourceRequestException(String message, Exception e) {
	super(message, e);
    }

    public HueResourceRequestException(String message,
	    List<Map<String, Object>> responses) {
	super(message);
	responses = new LinkedList<Map<String, Object>>();
    }

}
