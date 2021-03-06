package org.openhab.action.javarule.osgi;

import static org.openhab.core.events.EventConstants.*;

import java.util.Collection;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openhab.action.javarule.internal.ItemsGenerator;
import org.openhab.action.javarule.internal.Rule;
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
 * The Ruel Service is a wrapper to find all Rules classes; It registers itself
 * as listener to notifications an commands, and calls the rules identified
 * there
 * 
 * @author gernot
 * 
 */
public abstract class AbstractRuleService extends AbstractActiveService
	implements ManagedService, EventHandler, ItemRegistryChangeListener,
	StateChangeListener {

    protected static final Logger logger = LoggerFactory
	    .getLogger(AbstractRuleService.class);
    private ItemRegistry itemRegistry = null;

    private long refreshInterval = 200;

    // initialize with empty list for safety
    private static List<? extends Rule> rules = new LinkedList<Rule>();

    abstract protected List<? extends Rule> findRules();

    public AbstractRuleService() {
	super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
	// now add all registered items to the session
	if (itemRegistry != null) {
	    for (Item item : itemRegistry.getItems()) {
		logger.info("itemRegistry: '" + item.getName() + "'");
		itemAdded(item);
	    }
	}

	// register my dependencies
	rules = findRules();

	setProperlyConfigured(true);

	reconfigure();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deactivate() {

	shutdown();
    }

    public ItemRegistry getItemRegistry() {
	return itemRegistry;
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
    public void updated(Dictionary<String, ?> config)
	    throws ConfigurationException {
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
	logger.debug("allItemsChanged: count=" + oldItemNames.size());
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

	reconfigure();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void itemAdded(Item item) {
	logger.debug("itemAdded: " + item.getName());

	internalItemAdded(item);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void itemRemoved(Item item) {
	internalItemRemoved(item);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.openhab.action.javarule.internal.Rule#stateChanged(org.openhab.core
     * .items.Item, org.openhab.core.types.State, org.openhab.core.types.State)
     */
    @Override
    public void stateChanged(Item item, State oldState, State newState) {
	logger.trace("stateChanged({},{},{}) ", item.getName(), oldState,
		newState);
	for (Rule rule : rules) {
	    try {
		rule.stateChanged(item, oldState, newState);
	    } catch (Exception e) {
		logger.warn("stateChanged {} ({},{},{}) failed", rule
			.getClass().getName(), item, oldState, newState, e);
	    }
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.openhab.action.javarule.internal.Rule#stateUpdated(org.openhab.core
     * .items.Item, org.openhab.core.types.State)
     */
    @Override
    public void stateUpdated(Item item, State state) {
	logger.trace("stateUpdated({},{}) ", item.getName(), state.toString());

	for (Rule rule : rules) {
	    try {
		rule.stateUpdated(item, state);
	    } catch (Exception e) {
		logger.warn("stateUpdated {} ({}, {}) failed", rule.getClass()
			.getName(), item, state, e);
	    }
	}

    }

    /**
     * handle a command from the event handler
     */
    private void receiveCommand(String itemName, Command command) {
	logger.trace("receiveCommand({},{}) ", itemName, command.toString());
	try {
	    Item item = itemRegistry.getItem(itemName);
	    // receiveCommand(item, command);
	    for (Rule rule : rules) {
		try {
		    rule.receiveCommand(item, command);
		} catch (Exception e) {
		    logger.warn("receiveCommand {} ({}, {}) failed", rule
			    .getClass().getName(), item, command, e);
		}
	    }
	} catch (ItemNotFoundException e) {
	}
    }

    /**
     * will be called when Items are updated; can be called multiple times. If
     * properly configured, it will call the Rules' corresponding method
     * (possibly multiple times)
     */
    private void reconfigure() {

	if (isProperlyConfigured()) {

	    // Print code for Items Creation to Logfile
	    String items = ItemsGenerator.generateItemList();
	    logger.info("items: \n{}", items);

	    for (Rule rule : rules) {
		try {
		    rule.reconfigure();
		} catch (Exception e) {
		    logger.warn("reconfigure {}  failed", rule.getClass()
			    .getName(), e);
		}
	    }
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
	logger.debug("handleEvent({}) ", event.toString());

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