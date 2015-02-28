package org.openhab.action.javarule.items;

import java.util.List;

import org.openhab.action.javarule.internal.RulesActivator;
import org.openhab.core.events.EventPublisher;
import org.openhab.core.items.GenericItem;
import org.openhab.core.items.Item;
import org.openhab.core.items.ItemLookupException;
import org.openhab.core.items.ItemNotFoundException;
import org.openhab.core.items.ItemRegistry;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contract for my Proxies
 * 
 * @author gernot
 * 
 * @param <T>
 * @param <C>
 */
public class ItemProxy<T extends GenericItem, C extends Command> implements
		Item {

	protected static final Logger logger = LoggerFactory
			.getLogger(ItemProxy.class);

	private final T item;

	/**
	 * construct from Name
	 * 
	 * @param itemName
	 * @throws ItemLookupException
	 */
	public ItemProxy(String itemName) throws ItemLookupException {
		super();
		item = getItem(itemName);
	}

	/**
	 * get Item by Name and cast to proper Type
	 * 
	 * @param itemName
	 * @return
	 * @throws ItemLookupException
	 */
	@SuppressWarnings("unchecked")
	public T getItem(String itemName) throws ItemLookupException {
		try {
			ItemRegistry registry = RulesActivator.itemRegistryTracker
					.getService();
			if (registry == null) {
				throw new RuntimeException("ItemRegistry not yet initialized");
			}
			Item i = registry.getItem(itemName);

			try {
				// TODO: find better way to cast
				return (T) i;
			} catch (ClassCastException e) {
				logger.warn("couldn't cast Item {} to Type ", itemName, e);
				// TODO: find better Exception
				throw new ItemNotFoundException(itemName);
			}
		} catch (ItemNotFoundException e) {
			logger.warn("couldn't find Item '{}'", itemName, e);
			throw e;
		}
	}

	/**
	 * send a command for this item to the bus
	 * 
	 * @param command
	 */
	public void send(C command) {
		// item.send(command);
		/**
		 * not the nicest implementation, since GenericItem doesn't publish
		 * methods
		 */
		EventPublisher publisher = RulesActivator.eventPublisherTracker
				.getService();
		if (publisher != null) {
			publisher.sendCommand(item.getName(), command);
		} else {
			logger.warn("EventPublisher not yet initialized");
		}
	}

	/**
	 * set state and notify listeners
	 * 
	 * @param state
	 */
	public void setState(State state) {
		item.setState(state);
	}

	@Override
	public State getState() {
		return item.getState();
	}

	/**
	 * return
	 * 
	 * @return real subject
	 */
	public T getItem() {
		return item;
	}

	@Override
	public State getStateAs(Class<? extends State> typeClass) {
		return item.getStateAs(typeClass);
	}

	@Override
	public String getName() {
		return item.getName();
	}

	@Override
	public List<Class<? extends State>> getAcceptedDataTypes() {
		return item.getAcceptedDataTypes();
	}

	@Override
	public List<Class<? extends Command>> getAcceptedCommandTypes() {
		return item.getAcceptedCommandTypes();
	}

	@Override
	public List<String> getGroupNames() {
		return item.getGroupNames();
	}

	/**
	 * hashCode is taken from item, so the proxy behaves like the Item
	 */
	@Override
	public int hashCode() {
		return item.hashCode();
	}

	/**
	 * equals is tlken from item, so the proxy behaves like the Item
	 */
	@Override
	public boolean equals(Object obj) {
		return item.equals(obj);
	}

	@Override
	public String toString() {
		return item.toString();
	}

}
