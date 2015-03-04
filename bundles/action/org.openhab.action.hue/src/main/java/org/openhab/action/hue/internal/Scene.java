package org.openhab.action.hue.internal;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * represents a Scene
 * @author gernot
 *
 */
public class Scene extends AbstractHueResource {
	@JsonProperty
	List<String> lights;

	public Scene(String name, List<String> lights) {
		super();
		this.name = name;
		this.lights = lights;
	}
	
}
