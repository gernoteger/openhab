package org.openhab.action.javarule.internal;

import org.openhab.core.items.Item;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;

/**
 * the Interface for a Rule
 * 
 * @author gernot
 * 
 */
public interface Rule {

	/**
	 * state of item changed; override if you plan to use this
	 * 
	 * @param item
	 * @param oldState
	 * @param newState
	 * @throws Exception
	 */
	public void stateChanged(Item item, State oldState, State newState)
			throws Exception;

	/**
	 * state of item updated, but was not changed
	 * 
	 * @param item
	 * @param state
	 * @throws Exception
	 */
	public void stateUpdated(Item item, State state) throws Exception;

	/**
	 * handle a command
	 * 
	 * @param item
	 * @param command
	 * @throws Exception
	 */
	public void receiveCommand(Item item, Command command) throws Exception;

	/**
	 * called when module ist started or reconfigured. TODO: clarify that
	 * 
	 * @throws Exception
	 */
	public void onStartup() throws Exception;

}