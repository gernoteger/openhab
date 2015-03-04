package org.openhab.action.hue.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Group extends AbstractHueResource {

	private List<String> lights=new ArrayList<String>();

	/**
	 * create group w/p lights
	 * @param name
	 */
	public Group(String name) {
		this.name=name;
	}

	public List<String> getLights() {
		return lights;
	}

	public void setLights(List<String> lights) {
		this.lights = lights;
	}

	/**
	 * create Rule from json description
	 * @param json
	 * @return
	 * @throws IOException 
	 */
	@JsonIgnore
	public static AbstractHueResource create(String json) throws IOException{		
		return (AbstractHueResource) create(json,Group.class);		
	}
}
