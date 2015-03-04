package org.openhab.action.hue.internal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;

/**
 * represents a Sensor
 * 
 * @author gernot
 * 
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GeneralSensor extends AbstractHueResource {

    @JsonProperty
    String modelid = null;
    @JsonProperty
    String swversion = null;
    @JsonProperty
    String type = null;
    @JsonProperty
    String uniqueid = null;
    @JsonProperty
    String manufacturername = null;
    @JsonProperty
    Map<String, Object> state = new HashMap<String, Object>();
    @JsonProperty
    Map<String, Object> config = new HashMap<String, Object>();

    public GeneralSensor() {

    }

    public GeneralSensor(String name) {
	super();
	this.name = name;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public void setName(String name) {
	this.name = name;
    }

    public String getModelid() {
	return modelid;
    }

    public void setModelid(String modelid) {
	this.modelid = modelid;
    }

    public String getSwversion() {
	return swversion;
    }

    public void setSwversion(String swversion) {
	this.swversion = swversion;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getUniqueid() {
	return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
	this.uniqueid = uniqueid;
    }

    public String getManufacturername() {
	return manufacturername;
    }

    public void setManufacturername(String manufacturername) {
	this.manufacturername = manufacturername;
    }

    public Map<String, Object> getState() {
	return state;
    }

    public void setState(Map<String, Object> state) {
	this.state = state;
    }

    public Map<String, Object> getConfig() {
	return config;
    }

    public void setConfig(Map<String, Object> config) {
	this.config = config;
    }

    @JsonIgnore
    public String create() {
	String json = toJson();
	HueResponse result = postResource("/sensors", json);
	String id = result.getId();

	setId(id);

	return id;
    }

    @JsonIgnore
    public String delete() {
	throw new RuntimeException("not yet implemented on bridge.");
    }

    /**
     * update this resource on bridge. if id is not set, it will throw an error
     * the same
     * 
     * @return
     */
    @JsonIgnore
    public HueResponse update() {
	// TODO: test!!
	String id = getId();
	if (id == null) {
	    new HueResourceRequestException("no id defined for sensor "
		    + getName());
	}
	String json = toJson();
	return putResource("/sensors/" + id, json);
    }

    /**
     * retrieve all sensors from bridge
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @SuppressWarnings("unchecked")
    @JsonIgnore
    public static Map<String, GeneralSensor> sensors() throws IOException {
	String sensorsJson = getResource("/sensors");

	ObjectMapper mapper = new ObjectMapper();

	Map<String, GeneralSensor> sensorMap = new HashMap<String, GeneralSensor>();

	sensorMap = mapper.readValue(sensorsJson,
		new TypeReference<Map<String, GeneralSensor>>() {
		});

	/**
	 * set id's
	 */
	for (String id : sensorMap.keySet()) {
	    Object o = sensorMap.get(id);
	    GeneralSensor sensor = sensorMap.get(id);
	    sensor.setId(id);
	}
	return sensorMap;
    }
}
