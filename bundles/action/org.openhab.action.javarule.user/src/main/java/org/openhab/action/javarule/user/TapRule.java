package org.openhab.action.javarule.user;

import static org.openhab.action.javarule.user.Items.*;

import org.openhab.action.javarule.internal.GenericRule;
import org.openhab.core.items.Item;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
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
    static final String bodyOn3 = "{\"on\":true,\"bri\":240,\"hue\":15331,\"sat\":121\"transitiontime\":0}";
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

    String onTapScene = null;
    Boolean sceneOn;

    @Override
    public void receiveCommand(Item item, Command command) throws Exception {
	super.receiveCommand(item, command);

    }

    @Override
    public void stateUpdated(Item item, State state) throws Exception {
	super.stateUpdated(item, state);

	/**
	 * handle tap rules
	 */
	if (item.equals(Tap_1_1)) {
	    logger.debug("pressed {}", item);

	    // Rule r = new Rule("me");

	}
    }
}
