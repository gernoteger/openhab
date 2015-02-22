package org.openhab.action.javarule.internal;

import org.openhab.action.javarule.actions.BusEvent;
import org.openhab.core.items.Item;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.openhab.core.types.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Saves as the Base Class for all user-implemented Rules
 * 
 * @author gernot
 * 
 */
public class GenericRule implements Rule {

	protected static final Logger logger = LoggerFactory
			.getLogger(GenericRule.class);

	public GenericRule() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stateChanged(Item item, State oldState, State newState)
			throws Exception {
		logger.trace("stateChanged({},{},{}) ", item.getName(), oldState,
				newState);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stateUpdated(Item item, State state) throws Exception {
		logger.trace("stateUpdated({},{}) ", item.getName(), state.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void receiveCommand(Item item, Command command) throws Exception {
		logger.trace("receiveCommand({},{}) ", item.getName(), command);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reconfigure() throws Exception {
		logger.trace("reconfigure()");

	}

	/**
	 * get item with right type!
	 * 
	 * @param itemName
	 * @return
	 */
	public static <T> T item(String itemName) {
		return AbstractItems.item(itemName);
	}

	/**
	 * helpers to send TODO: refactor this!!
	 * 
	 * @param itemName
	 * @param stateString
	 */
	static public void postUpdate(String itemName, String stateString) {
		BusEvent.postUpdate(itemName, stateString);
	}

	static public void postUpdate(Item item, State state) {
		BusEvent.postUpdate(item.getName(), state.toString());
	}

	static public void sendCommand(String itemName, String commandString) {
		BusEvent.sendCommand(itemName, commandString);
	}

	static public void sendCommand(Item item, Command command) {
		BusEvent.sendCommand(item.getName(), command.toString());
	}

	/**
	 * convenience function: convert a Type to integer
	 * 
	 * @param v
	 * @return
	 */
	public static Integer toInteger(Type v) {
		return ((DecimalType) v).intValue();
	}
}