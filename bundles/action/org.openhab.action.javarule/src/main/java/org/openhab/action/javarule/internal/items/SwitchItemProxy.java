package org.openhab.action.javarule.internal.items;

import org.openhab.core.library.items.SwitchItem;
import org.openhab.core.library.types.OnOffType;

/**
 * proxy for SwitchItem
 * 
 * @author gernot
 * 
 */
public class SwitchItemProxy extends ItemProxy<SwitchItem, OnOffType> {

    public SwitchItemProxy(String itemName) {
	super(itemName);
    }

}
