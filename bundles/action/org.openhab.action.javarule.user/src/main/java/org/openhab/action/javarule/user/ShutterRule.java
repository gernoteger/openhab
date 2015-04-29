package org.openhab.action.javarule.user;

import static org.openhab.action.javarule.user.Items.*;
import static org.openhab.core.library.types.OnOffType.*;
import static org.openhab.core.library.types.StopMoveType.*;
import static org.openhab.core.library.types.UpDownType.*;

import org.openhab.action.javarule.internal.items.RollershutterItemProxy;
import org.openhab.action.javarule.internal.items.SwitchItemProxy;
import org.openhab.core.items.Item;
import org.openhab.core.library.items.RollershutterItem;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.UpDownType;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * for shutter
 * 
 * @author gernot
 * 
 */

/*********************************************************************
 * Shutter mappings mit FS20 Gegenstück sowohl als shutter item, als auch als
 * switch
 * 
 * sw1_1 => wohnzimmer west gShutWZ_West sw1_2 => Terassentür sw1_3 =>
 * Wohnzimmer süd gShutWZ_Sued sw1_4 => Bad sw1_5 => Schlafzimmer sw1_6 =>
 * Gästezimmer sw1_7 => frei sw1_8 => Alle
 * 
 * nur shutter items (ohne
 * 
 *********************************************************************/

public class ShutterRule extends
	org.openhab.action.javarule.internal.GenericRule {

    protected static final Logger logger = LoggerFactory
	    .getLogger(ShutterRule.class);

    @Override
    public void receiveCommand(Item item, Command command) throws Exception {
	if (shutWZ_west_linksUI.isEqual(item)) {
	    // implement rollershutter logic
	    // Übersetzen von RollerShutter auf switch
	    RollershutterItemProxy sensor = shutWZ_west_linksUI;
	    SwitchItemProxy actor = shutWZ_west_links;

	    if (command.equals(UP)) {
		actor.send(OFF);
	    } else if (command.equals(DOWN)) {
		actor.send(ON);
	    } else if (command.equals(STOP)) {
		// depends where we are
		RollershutterItem i = sensor.getItem();
		State s1 = i.getState();
		UpDownType s2 = (UpDownType) i.getStateAs(UpDownType.class);

		PercentType state = (PercentType) i
			.getStateAs(PercentType.class);
		switch (state.intValue()) {
		case 100:
		    actor.send(ON);
		    break;
		case 0:
		    actor.send(OFF);
		    break;
		default:
		    logger.warn("invalid state {}", state);
		}
	    }
	}
    }

    /**
     * update; new state is here..
     * 
     * @param item
     * @param newState
     */
    private void handleUpdate(Item item, State newState) {

	if (sw1_1.isEqual(item) || sw1_2.isEqual(item)) {
	    if (newState instanceof OnOffType) {
		shutWZ_west_links.send((OnOffType) newState);
		shutWZ_west_rechts.send((OnOffType) newState);
	    }
	}
    }

    @Override
    public void stateUpdated(Item item, State state) throws Exception {
	handleUpdate(item, state);
    }

    @Override
    public void stateChanged(Item item, State oldState, State newState)
	    throws Exception {
	handleUpdate(item, newState);
    }
}
