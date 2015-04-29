package org.openhab.binding.fs20.internal;

import java.util.HashMap;
import java.util.Map;

import org.openhab.binding.fs20.FS20BindingConfig;
import org.openhab.core.events.EventPublisher;
import org.openhab.core.library.items.RollershutterItem;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.StopMoveType;
import org.openhab.core.library.types.UpDownType;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.openhab.core.types.UnDefType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * shutter; has its own state!!
 * 
 * @author gernot
 * 
 */
public class FS20Shutter {

    private static final Logger logger = LoggerFactory
	    .getLogger(FS20Shutter.class);

    /**
     * central shutter registry
     */
    private static final Map<String, FS20Shutter> shutters = new HashMap<String, FS20Shutter>();

    private final FS20BindingConfig config;

    private State state = null;

    public State getState() {
	return state;
    }

    public void setState(State state) {
	this.state = state;
	getItem().setState(state); // set the item????
	// TODO: post update here??
    }

    public FS20Shutter(FS20BindingConfig config) {
	this.config = config;
    }

    /**
     * create an item and insert into the list; update config
     * 
     * @param config
     * @return
     */
    protected static void putItemConfig(FS20BindingConfig config) {
	FS20Shutter shutter = new FS20Shutter(config);

	shutters.put(config.getItem().getName(), shutter);
    }

    /**
     * change to command if needed. null indicates "No FurtherActio"
     * 
     * @param itemName
     * @param command
     * @param eventPublisher
     * @return new Command or null if "No Action"
     */
    protected static Command handleCommand(String itemName, Command command,
	    EventPublisher eventPublisher) {
	FS20Shutter shutter = shutters.get(itemName);
	if (shutter == null) {
	    return command;
	}

	Command next = shutter.doCommand(command);

	// / TODO: passt gar nicht!
	// set state
	eventPublisher.postUpdate(itemName, shutter.state);

	return next;
    }

    /**
     * shutter is moving
     * 
     * @return
     */
    private boolean isMoving() {
	return true;
    }

    /**
     * shutter is moving up
     * 
     * @return
     */
    private boolean isMovingUp() {
	if (!isMoving())
	    return false;
	// TODO: check movement!!
	return false;
    }

    /**
     * get next fitting command and start internal actions
     * 
     * @param command
     * @return command to be executed on device
     */
    private Command doCommand(Command command) {
	// TODO: implement
	if (command instanceof StopMoveType) {
	    // depends where we are
	    RollershutterItem i = getItem();

	    if (i.getState() instanceof UnDefType) {
		return null; // no action when stop on undefined...
	    }

	    PercentType state = (PercentType) i.getStateAs(PercentType.class);
	    switch (state.intValue()) {
	    case 100:
		setState(PercentType.ZERO);
		return OnOffType.ON;
	    case 0:
		setState(PercentType.HUNDRED);
		return OnOffType.OFF;
	    default:
		logger.warn("invalid state {}", state);
		return null;
	    }
	} else if (command.equals(UpDownType.DOWN)) {
	    setState(PercentType.HUNDRED);
	    return OnOffType.ON;
	} else if (command.equals(UpDownType.UP)) {
	    setState(PercentType.ZERO);
	    return OnOffType.OFF;
	}
	return command;
    }

    private RollershutterItem getItem() {
	return (RollershutterItem) config.getItem();
    }
}
