package org.openhab.action.javarule.items;

import org.openhab.core.items.ItemLookupException;
import org.openhab.core.library.items.SwitchItem;
import org.openhab.core.library.types.OnOffType;

/**
 * proxy for SwitchItem
 * 
 * @author gernot
 * 
 */
public class SwitchItemProxy extends ItemProxy<SwitchItem, OnOffType> {

	public SwitchItemProxy(String itemName) throws ItemLookupException {
		super(itemName);
	}

}
