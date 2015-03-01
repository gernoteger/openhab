package org.openhab.action.javarule.internal;

import java.util.Collection;

import org.openhab.action.javarule.osgi.AbstractRuleActivator;
import org.openhab.core.items.Item;
import org.openhab.core.items.ItemNotFoundException;
import org.openhab.core.items.ItemRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * partly generated singleton for Items
 * 
 * @author gernot
 * 
 */
public abstract class AbstractItems {

    protected static final Logger logger = LoggerFactory
	    .getLogger(AbstractItems.class);

    public static ItemRegistry getItemRegistry() {
	return AbstractRuleActivator.itemRegistryTracker.getService();
    }

    public static Collection<Item> allItems() {
	return getItemRegistry().getItems();
    }

    /**
     * get item with right type!
     * 
     * @param itemName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T item(String itemName) {
	try {
	    Item i = getItemRegistry().getItem(itemName);

	    return (T) i;
	} catch (ItemNotFoundException e) {
	    logger.warn("item not found: {}", itemName, e);
	    return null;
	}
    }
}
