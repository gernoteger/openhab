/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.action.javarule.internal;

import static org.openhab.core.events.EventConstants.TOPIC_PREFIX;
import static org.openhab.core.events.EventConstants.TOPIC_SEPERATOR;

import java.util.Collection;
import java.util.Dictionary;

import org.apache.commons.lang.StringUtils;
import org.openhab.core.items.GenericItem;
import org.openhab.core.items.Item;
import org.openhab.core.items.ItemNotFoundException;
import org.openhab.core.items.ItemRegistry;
import org.openhab.core.items.ItemRegistryChangeListener;
import org.openhab.core.items.StateChangeListener;
import org.openhab.core.service.AbstractActiveService;
import org.openhab.core.types.Command;
import org.openhab.core.types.EventType;
import org.openhab.core.types.State;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements a generic class for rules; does all the necessary registration and
 * stuff an calls helper methods. TODO: describe api
 * 
 * @author gernot
 * 
 */
public class RuleService extends AbstractActiveService implements
		ManagedService, EventHandler, ItemRegistryChangeListener,
		StateChangeListener {

	static private final Logger logger = LoggerFactory
			.getLogger(RuleService.class);

	private ItemRegistry itemRegistry = null;

	private long refreshInterval = 200;

	@Override
	public void activate() {
		// now add all registered items to the session
		if (itemRegistry != null) {
			for (Item item : itemRegistry.getItems()) {
				logger.info("itemRegistry: '" + item.getName() + "'");
				itemAdded(item);
			}
		}

		setProperlyConfigured(true);
	}

	@Override
	public void deactivate() {

		shutdown();
	}

	public void setItemRegistry(ItemRegistry itemRegistry) {
		this.itemRegistry = itemRegistry;
		itemRegistry.addItemRegistryChangeListener(this);
	}

	public void unsetItemRegistry(ItemRegistry itemRegistry) {
		itemRegistry.removeItemRegistryChangeListener(this);
		this.itemRegistry = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void updated(Dictionary config) throws ConfigurationException {
		if (config != null) {
			String evalIntervalString = (String) config.get("evalInterval");
			if (StringUtils.isNotBlank(evalIntervalString)) {
				refreshInterval = Long.parseLong(evalIntervalString);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void allItemsChanged(Collection<String> oldItemNames) {
		logger.info("allItemsChanged: count=" + oldItemNames.size());
		// first remove all previous items from the session
		for (String oldItemName : oldItemNames) {
			try {
				Item item = itemRegistry.getItem(oldItemName);
				internalItemRemoved(item);
			} catch (ItemNotFoundException e) {
				logger.warn("couldn't find item '{}'", oldItemName, e);
			}
		}

		// then add the current ones again
		Collection<Item> items = itemRegistry.getItems();
		for (Item item : items) {
			internalItemAdded(item);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void itemAdded(Item item) {
		logger.info("itemAdded: " + item.getName());

		internalItemAdded(item);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void itemRemoved(Item item) {
		internalItemRemoved(item);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stateChanged(Item item, State oldState, State newState) {
		logger.info("stateChanged " + item.getName() + " from "
				+ oldState.toString() + " -> " + newState.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stateUpdated(Item item, State state) {
		logger.info("stateUpdated {} to {} ", item.getName(), state.toString());
	}

	/**
	 * handle a command
	 * 
	 * @param item
	 * @param command
	 */
	public void receiveCommand(Item item, Command command) {
		logger.info("receiveCommand({},{}) ", item.getName(),
				command.toString());
	}

	/**
	 * handle a command from the event handler
	 */
	private void receiveCommand(String itemName, Command command) {
		logger.debug("receiveCommand({},{}) ", itemName, command.toString());
		try {
			Item item = itemRegistry.getItem(itemName);
			receiveCommand(item, command);
		} catch (ItemNotFoundException e) {
		}
	}

	/**
	 * do necessary registering for items
	 * 
	 * @param item
	 */
	private void internalItemAdded(Item item) {
		if (item == null) {
			logger.debug("Item must not be null here!");
			return;
		}

		if (item instanceof GenericItem) {
			GenericItem genericItem = (GenericItem) item;
			genericItem.addStateChangeListener(this);
		}

	}

	private void internalItemRemoved(Item item) {
		if (item instanceof GenericItem) {
			GenericItem genericItem = (GenericItem) item;
			genericItem.removeStateChangeListener(this);
		}

	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected synchronized void execute() {

	}

	@Override
	protected long getRefreshInterval() {
		return refreshInterval;
	}

	@Override
	protected String getName() {
		return "Rule Evaluation Service";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleEvent(Event event) {
		logger.info("handleEvent({}) ", event.toString());

		String itemName = (String) event.getProperty("item");

		String topic = event.getTopic();
		String[] topicParts = topic.split(TOPIC_SEPERATOR);

		if (!(topicParts.length > 2) || !topicParts[0].equals(TOPIC_PREFIX)) {
			return; // we have received an event with an invalid topic
		}
		String operation = topicParts[1];

		if (operation.equals(EventType.COMMAND.toString())) {
			Command command = (Command) event.getProperty("command");
			if (command != null)
				receiveCommand(itemName, command);
		}
	}
}
