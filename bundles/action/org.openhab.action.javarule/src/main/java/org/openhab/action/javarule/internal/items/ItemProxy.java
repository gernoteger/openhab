package org.openhab.action.javarule.internal.items;

import java.util.List;

import org.openhab.action.javarule.osgi.AbstractRuleActivator;
import org.openhab.core.events.EventPublisher;
import org.openhab.core.items.GenericItem;
import org.openhab.core.items.Item;
import org.openhab.core.items.ItemLookupException;
import org.openhab.core.items.ItemNotFoundException;
import org.openhab.core.items.ItemRegistry;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.openhab.core.types.TypeParser;
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

    private final String itemName;

    /**
     * construct from Name
     * 
     * @param itemName
     * @throws ItemLookupException
     */
    public ItemProxy(String itemName) {
	super();
	this.itemName = itemName;
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
	    ItemRegistry registry = AbstractRuleActivator.itemRegistryTracker
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
	internalSend(command);
    }

    /**
     * convenience method to call {@ref #send(Command)}.
     * 
     * @param commandString
     */
    public void send(String commandString) {
	Command command = parseCommand(commandString);
	internalSend(command);
    }

    /**
     * send command to bus
     * 
     * @param command
     */
    private void internalSend(Command command) {
	/**
	 * not the nicest implementation, since GenericItem doesn't publish
	 * methods
	 */
	EventPublisher publisher = AbstractRuleActivator.eventPublisherTracker
		.getService();
	if (publisher != null) {
	    publisher.sendCommand(getItem().getName(), command);
	} else {
	    logger.warn("EventPublisher not yet initialized");
	}
    }

    /**
     * parse Command String
     * 
     * @param commandString
     * @return
     */
    public Command parseCommand(String commandString) {
	return TypeParser.parseCommand(getItem().getAcceptedCommandTypes(),
		commandString);
    }

    /**
     * parse State String
     * 
     * @param stateString
     * @return
     */
    public State parseState(String stateString) {
	return TypeParser.parseState(getItem().getAcceptedDataTypes(),
		stateString);
    }

    /**
     * set state and notify listeners
     * 
     * @param state
     */
    public void setState(State state) {
	getItem().setState(state);
    }

    /**
     * convenience method to call {@link #setState(State)}
     * 
     * @param stateString
     */
    public void setState(String stateString) {
	setState(parseState(stateString));
    }

    @Override
    public State getState() {
	return getItem().getState();
    }

    /**
     * return
     * 
     * @return real subject
     * @throws ItemLookupException
     */
    public T getItem() {
	try {
	    return getItem(itemName);
	} catch (ItemLookupException e) {
	    // consume & produce runtime exception
	    throw new RuntimeException("couldn't find item '" + itemName + "'",
		    e);
	}
    }

    @Override
    public State getStateAs(Class<? extends State> typeClass) {
	return getItem().getStateAs(typeClass);
    }

    @Override
    public String getName() {
	return getItem().getName();
    }

    @Override
    public List<Class<? extends State>> getAcceptedDataTypes() {
	return getItem().getAcceptedDataTypes();
    }

    @Override
    public List<Class<? extends Command>> getAcceptedCommandTypes() {
	return getItem().getAcceptedCommandTypes();
    }

    @Override
    public List<String> getGroupNames() {
	return getItem().getGroupNames();
    }

    /**
     * hashCode is taken from item, so the proxy behaves like the Item
     */
    @Override
    public int hashCode() {
	return getItem().hashCode();
    }

    /**
     * similar to equals; can't override equals, since I would break the
     * {@link #equals(Object)} contract regarding symmetry due to the
     * implementation of {@link GenericItem#equals(Object)} {@link }.
     */
    public boolean isEqual(Item obj) {
	return getItem().equals(obj);
    }

    @Override
    public String toString() {
	return getItem().toString();
    }

}
