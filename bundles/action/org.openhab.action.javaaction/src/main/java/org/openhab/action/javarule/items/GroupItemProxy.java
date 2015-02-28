package org.openhab.action.javarule.items;

import org.openhab.core.items.GenericItem;
import org.openhab.core.items.ItemLookupException;
import org.openhab.core.types.Command;

public class GroupItemProxy extends ItemProxy<GenericItem, Command> {

	public GroupItemProxy(String itemName) throws ItemLookupException {
		super(itemName);
	}

}
