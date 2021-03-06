/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.hue.internal.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import org.codehaus.jackson.map.ObjectMapper;
import org.openhab.binding.hue.internal.HueSettingsParseException;
import org.openhab.binding.hue.internal.hardware.HueTapState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains all information that is provided by the Hue bridge. There are
 * different information that can be requested about the connected bulbs.
 * <ul>
 * <li>Is a bulb switched on?</li>
 * <li>How is a bulb's color temperature?</li>
 * <li>How is the current brightness of a blub?</li>
 * <li>How is the hue of a given bulb?</li>
 * <li>How is the saturation of a given bulb?</li>
 * </ul>
 * 
 * @author Roman Hartmann
 * @author Jos Schering
 * @since 1.2.0
 */
public class HueSettings {

	static final Logger logger = LoggerFactory.getLogger(HueSettings.class);

	private SettingsTree settingsData = null;

	/**
	 * Constructor of HueSettings. It takes the settings of the Hue bridge to
	 * enable the HueSettings to determine the needed information about the
	 * bulbs.
	 * 
	 * @param settings
	 *            This is the settings string in Json format returned by the Hue
	 *            bridge.
	 */
	@SuppressWarnings("unchecked")
	public HueSettings(String settings) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			settingsData = new SettingsTree(mapper.readValue(settings,
					Map.class));
		} catch (Exception e) {
			logger.error("Could not read Settings-Json from Hue Bridge.");
		}
	}

	/**
	 * Determines whether the given bulb is turned on.
	 * 
	 * @param deviceNumber
	 *            The bulb number the bridge has filed the bulb under.
	 * @return true if the bulb is turned on, false otherwise.
	 */
	public boolean isBulbOn(int deviceNumber) {
		if (settingsData == null) {
			logger.error("Hue bridge settings not initialized correctly.");
			return false;
		}
		return (Boolean) settingsData.node("lights")
				.node(Integer.toString(deviceNumber)).node("state").value("on");
	}
	
	
	/**
	 * Determines whether the given bulb is reachable.
	 * Once a bulb is physical disconnected it becomes unreachable after around 10 seconds. 
	 * The hearbeat service of the Hue hub device is responsible for this behavior.  
	 * 
	 * @param deviceNumber
	 *            The bulb number the bridge has filed the bulb under.
	 * @return true if the bulb is reachable, false otherwise.
	 */
	public boolean isReachable(int deviceNumber) {
		if (settingsData == null) {
			logger.error("Hue bridge settings not initialized correctly.");
			return false;
		}
		return (Boolean) settingsData.node("lights")
				.node(Integer.toString(deviceNumber)).node("state").value("reachable");
	}
	
	
	/**
	 * Determine amount of lights connected to Hue hub
	 * 
	 * @return amount of lights connected to Hue hub
	 */
	public int getLightsCount() {
		if (settingsData == null) {
			logger.error("Hue bridge settings not initialized correctly.");
			return -1;
		}
		return settingsData.node("lights").count();
	}

	/**
	 * Get the rules in thsi settings file
	 * 
	 * @return number of sensors connected to Hue hub
	 */
	public SettingsTree getRules() {
		if (settingsData == null) {
			logger.error("No settings data.");
			return null;
		}
		
		return settingsData.node("rules");
	}
	
	/**
	 * find the rule id for a rule name
	 * @param name
	 * @return
	 */
	public String getRule(String name) {
		if (settingsData == null) {
			logger.error("No settings data.");
			return null;
		}
		
		SettingsTree rules=getRules();
		for(String id:rules.nodes()){
			SettingsTree rule=rules.node(id);
			if(rule.value("name").equals(name)){
				return  id;
			}
		}
		return null;
	}
	
	/**
	 * Determine number of sensors connected to Hue hub. This is not necessarily the number of tap devices!
	 * 
	 * @return number of sensors connected to Hue hub
	 */
	public int getSensorsCount() {
		if (settingsData == null) {
			logger.error("No settings data.");
			return -1;
		}
		return settingsData.node("sensors").count();
	}
	
	/**
	 * retrieve a Map of all tap States. Key is the Tap Sensor id
	 * @return
	 */
	public Map<Integer,HueTapState> getTapStates() throws HueSettingsParseException{
		Map<Integer,HueTapState> states=new HashMap<Integer,HueTapState>();
		SettingsTree sensors=settingsData.node("sensors");
			
		for(String deviceID:sensors.nodes()){
			SettingsTree tn=sensors.node(deviceID);
			if(tn.value("type").equals("ZGPSwitch")){
				SettingsTree stateNode=tn.node("state");
				HueTapState state=new HueTapState((Integer)stateNode.value("buttonevent"),(String)stateNode.value("lastupdated"));				
				states.put(Integer.parseInt(deviceID),state);
			}
		}
		return states;
	}
	
	/**
	 * Determines the color temperature of the given bulb.
	 * 
	 * @param deviceNumber
	 *            The bulb number the bridge has filed the bulb under.
	 * @return The color temperature as a value from 154 - 500
	 */
	public int getColorTemperature(int deviceNumber) {
		if (settingsData == null) {
			logger.error("Hue bridge settings not initialized correctly.");
			return 154;
		}
		Object ct = settingsData.node("lights").node(Integer.toString(deviceNumber)).node("state").value("ct");
		if(ct instanceof Integer) {
			return (Integer) ct;
		} else {
			return 154;
		}
	}

	/**
	 * Determines the brightness of the given bulb.
	 * 
	 * @param deviceNumber
	 *            The bulb number the bridge has filed the bulb under.
	 * @return The brightness as a value from 0 - 255
	 */
	public int getBrightness(int deviceNumber) {
		if (settingsData == null) {
			logger.error("Hue bridge settings not initialized correctly.");
			return 0;
		}
		return (Integer) settingsData.node("lights")
				.node(Integer.toString(deviceNumber)).node("state")
				.value("bri");
	}

	/**
	 * Determines the hue of the given bulb.
	 * 
	 * @param deviceNumber
	 *            The bulb number the bridge has filed the bulb under.
	 * @return The hue as a value from 0 - 65535
	 */
	public int getHue(int deviceNumber) {
		if (settingsData == null) {
			logger.error("Hue bridge settings not initialized correctly.");
			return 0;
		}

		Object hue = settingsData.node("lights")
				.node(Integer.toString(deviceNumber)).node("state")
				.value("hue");
		if(hue instanceof Integer) {
			return (Integer) hue;
		} else {
			return 0;
		}
	}

	/**
	 * Determines the saturation of the given bulb.
	 * 
	 * @param deviceNumber
	 *            The bulb number the bridge has filed the bulb under.
	 * @return The saturation as a value from 0 - 254
	 */
	public int getSaturation(int deviceNumber) {
		if (settingsData == null) {
			logger.error("Hue bridge settings not initialized correctly.");
			return 0;
		}

		Object sat = settingsData.node("lights")
				.node(Integer.toString(deviceNumber)).node("state")
				.value("sat");
		if(sat instanceof Integer) {
			return (Integer) sat;
		} else {
			return 0;
		}
	}

	/**
	 * The SettingsTree represents the settings Json as a tree with some
	 * convenience methods to get subtrees and the values of interest easily.
	 */
	public class SettingsTree {

		private Map<String, Object> dataMap;

		/**
		 * Constructor of the SettingsTree.
		 * 
		 * @param treeData
		 *            The Json data as it has been returned by the Json object
		 *            mapper.
		 */
		public SettingsTree(Map<String, Object> treeData) {
			this.dataMap = treeData;
		}

		/**
		 * @param nodeName
		 *            The name of the child node.
		 * @return The child node named like nodeName. This will be a sub tree.
		 */
		@SuppressWarnings("unchecked")
		public SettingsTree node(String nodeName) {
			return new SettingsTree((Map<String, Object>) dataMap.get(nodeName));
		}

		/**
		 * @return the amount of lights connected to Hue hub
		 */
		public int count() {
			return dataMap.size();
		}

		/**
		 * @param valueName
		 *            The name of the child node.
		 * @return The child node named like nodeName. This will be an object.
		 */
		public Object value(String valueName) {
			return dataMap.get(valueName);
		}

		public Set<String> nodes(){
			return dataMap.keySet();
		}
	}

}
