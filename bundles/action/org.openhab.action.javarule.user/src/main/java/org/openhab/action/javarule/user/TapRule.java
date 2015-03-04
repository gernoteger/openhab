package org.openhab.action.javarule.user;

import static org.openhab.action.hue.internal.HueActions.*;
import static org.openhab.action.javarule.user.Items.*;

import java.io.IOException;
import java.util.Map;

import org.openhab.action.hue.internal.GeneralSensor;
import org.openhab.action.hue.internal.Rule;
import org.openhab.action.javarule.internal.GenericRule;
import org.openhab.core.items.Item;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * implement my hue actions
 * 
 * @author gernot
 * 
 */
public class TapRule extends GenericRule {
    protected static final Logger logger = LoggerFactory
	    .getLogger(TapRule.class);

    // example configs for initial scene settings
    static final String bodyOn2 = "{\"on\":true,\"ct\":153,\"bri\":255}";
    static final String bodyOn3 = "{\"on\":true,\"bri\":240,\"hue\":15331,\"sat\":121,\"transitiontime\":0}";
    static final String bodyOn4 = "{\"on\":true,\"ct\":500,\"bri\":60}";
    static final String bodyOff = "{\"on\":false,\"transitiontime\":0}";

    // my scenes for tap configuration
    static final String offSceneId = "tap-2-4-off";
    static final String scene2Id = "tap-2-2-on";
    static final String scene3Id = "tap-2-3-on";
    static final String scene4Id = "tap-2-4-on";

    // lichter im Wohnzimmer
    static final String lights = "1,2,3,5";

    static final String tapDevice = "2";
    static final String lightsFlagSensor = "4";

    static final String allLampsGroup = "0";

    String onTapScene = null;
    Boolean sceneOn;

    @Override
    public void receiveCommand(Item item, Command command) throws Exception {
	super.receiveCommand(item, command);

	if (Tap_1_2.isEqual(item)) {
	    logger.debug("pressed {}", item);
	    setOnScene(scene2Id);
	}
	if (Tap_1_3.isEqual(item)) {
	    logger.debug("pressed {}", item);
	    setOnScene(scene3Id);
	}
	if (Tap_1_4.isEqual(item)) {
	    logger.debug("pressed {}", item);
	    setOnScene(scene4Id);
	} else if (setTapRules.isEqual(item)) {
	    initializeTapRules();
	} else if (testTapRule.isEqual(item)) {
	    testTapRule();
	    testTapSensors();
	}

    }

    // @Override
    // public void stateUpdated(Item item, State state) throws Exception {
    // super.stateUpdated(item, state);
    // }

    /**
     * set scene for "On" state on button 1
     * 
     * @param scene
     */
    private void setOnScene(String scene) {
	Rule r = new Rule("tap 2-1-off");
	// conditions
	r.addTapButtonEqualsCondition(tapDevice, 1);
	r.addTapDeviceChangedCondition(tapDevice);
	r.addFlagSensorCondition(lightsFlagSensor, false);
	r.addGroupAction(allLampsGroup, "scene", scene);
	r.addSetFlagAction(lightsFlagSensor, true); // toggle the flag
	r.writeToBridge();

    }

    private void testTapSensors() throws IOException {
	Map<String, GeneralSensor> sensorMap = GeneralSensor.sensors();

	// GeneralSensor s1 = new GeneralSensor("IP Camera living room");
	// String name = s1.getName();

	GeneralSensor s1 = sensorMap.get("4");
	s1.getState().put("flag", false);

	GeneralSensor s4 = new GeneralSensor("LichterWohnzimmerAn");

	s4.setId(s1.getId());
	s4.getState().put("flag", false);
	// s3.setType("CLIPGenericFlag");
	s4.getConfig().put("url", "");
	s4.update();

	// s1.getState().put("presence", false);
	// s1.getConfig().put("url",
	// "http://192.168.168.1/api/sensors/presence");
	//
	// s1.setModelid("IPSENSOR");
	// s1.setSwversion("1.0");
	// s1.setType("CLIPGenericFlag"); // CLIPGenericFlag
	// s1.setUniqueid("12345678");
	// s1.setManufacturername("GernotManufacturer");
	//
	// String response = s1.create();
	// logger.info("response=''", response);

	sensorMap = GeneralSensor.sensors();

	logger.info("sensorMap=''", sensorMap);
    }

    /**
     * some experiments with tap
     * 
     * @throws IOException
     */
    private void testTapRule() throws IOException {
	// Rule r = new Rule("testTap");
	// Condition c1 = new Condition();
	// Action offAction = new Action();

	// hueSetScene(offSceneId,"wz off",lights)
	// hueSetSceneSettings(offSceneId,lights,bodyOff)
	// hueSetScene(offSceneId, "wz off", lights);

	// String r1 = hueCreateRule("tap 2-1");
	// r1 = hueAddTapButtonEqualsCondition(r1, tapDevice, 1);
	// r1 = hueAddSensorChangedCondition(r1, tapDevice);
	// r1 = hueAddGroupAction(r1, allLampsGroup, "scene", offSceneId);
	// String response1 = hueSetRule(r1);
	// logger.info("testTapRule response='{}'", response);
    }

    /**
     * Set initial tap rules
     * 
     * Concept: 1. create a scene for each button 2.on tap prtessed, associate
     * tap button with this scene by a rule
     * 
     * @throws IOException
     */
    private void initializeTapRules() throws IOException {
	logger.info("setTapRules() start...");

	hueSetScene(offSceneId, "wz off", lights);
	hueSetSceneSettings(offSceneId, lights, bodyOff);

	hueSetScene(scene2Id, "tap 2-2", lights);
	hueSetSceneSettings(scene2Id, lights, bodyOn2);

	hueSetScene(scene3Id, "tap 2-3", lights);
	hueSetSceneSettings(scene3Id, lights, bodyOn3);

	hueSetScene(scene4Id, "tap 2-4", lights);
	hueSetSceneSettings(scene4Id, lights, bodyOn4);

	hueDeleteAllRules();

	// Rule for big one when when Lights are on
	Rule r = new Rule("tap 2-1-on");
	// conditions
	r.addTapButtonEqualsCondition(tapDevice, 1);
	r.addTapDeviceChangedCondition(tapDevice);
	r.addFlagSensorCondition(lightsFlagSensor, true);
	r.addGroupAction(allLampsGroup, "scene", offSceneId);
	r.addSetFlagAction(lightsFlagSensor, false); // toggle the flag
	r.writeToBridge();

	// Rule for big one when when Lights are on
	setOnScene(scene2Id);

	createTapButtonRule(2, scene2Id);
	createTapButtonRule(3, scene3Id);
	createTapButtonRule(4, scene4Id);

	logger.info("setTapRules() done.");
    }

    /**
     * rule for this switch..
     * 
     * @param button
     * @param scene
     */
    public void createTapButtonRule(Integer button, String scene) {

	Rule r = new Rule("tap 2-" + button.toString());
	r.addTapButtonEqualsCondition(tapDevice, button);
	r.addTapDeviceChangedCondition(tapDevice);
	r.addGroupAction(allLampsGroup, "scene", scene);
	r.addSetFlagAction(lightsFlagSensor, true);
	r.writeToBridge();
    }
}
