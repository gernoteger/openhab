package org.openhab.action.javarule.user;

import static org.openhab.action.javarule.user.Items.*;

import java.util.HashMap;
import java.util.Map;

import org.openhab.action.javarule.internal.GenericRule;
import org.openhab.action.javarule.internal.items.GroupItemProxy;
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
public class HueRule extends GenericRule {
    protected static final Logger logger = LoggerFactory
	    .getLogger(HueRule.class);

    // some colors, mostly taken from hue
    private static final String off = "OFF";
    private static final String relax = "71.91209,83.13725,56";
    private static final String konzentration = "185.98352,17.2549,86";
    private static final String feetUp = "72.51648,81.96078,82";
    private static final String kathy = "46.19231,98.82353,93";
    private static final String beach = "89.68681,18.82353,95";
    private static final String ski = "188.23077,69.01961,100";
    private static final String sunset = "58.56044,98.82353,100";
    private static final String lesen = "84.23626,47.45098,94";
    private static final String aktivieren = "189.53297,90.98039,80";

    // and others
    private static final String warmweiss = "58.125,21.0,100";
    private static final String blau = "254.8760330578512,96.8,99.0";
    private static final String lila = "296.96202531645565,92.94117647058823,50";
    private static final String rot = "351,93.70,60";

    /**
     * map scene IDs to colors
     */
    private static final Map<Integer, String> commands = new HashMap<Integer, String>();
    static {
	commands.put(0, off);
	commands.put(1, konzentration);
	commands.put(12, ski);
	commands.put(7, aktivieren);
	commands.put(13, warmweiss);
	commands.put(2, lesen);
	commands.put(3, beach);
	// relax
	commands.put(4, feetUp);
	commands.put(11, relax);
	commands.put(5, sunset);
	commands.put(6, kathy);

	commands.put(8, lila);
	commands.put(9, rot);
	commands.put(10, blau);
    }

    @Override
    public void receiveCommand(Item item, Command command) throws Exception {
	if (sceneSchlafzimmer.isEqual(item)) {
	    setGroupScene(gLichtSchlafzimmer, command);
	} else if (sceneWohnzimmer.isEqual(item)) {
	    setGroupScene(gLichtWohnzimmer, command);
	}
    }

    /**
     * set values for a group..
     * 
     * @param group
     * @param newState
     */
    private void setGroupScene(GroupItemProxy group, Command command) {
	Integer newState = toInteger(command);
	String commandString = commands.get(newState);
	Command c = group.parseCommand(commandString);

	group.send(c);
    }
}
