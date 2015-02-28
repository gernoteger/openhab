package org.openhab.action.javarule.items;

import org.openhab.core.items.ItemLookupException;
import org.openhab.core.library.items.DimmerItem;
import org.openhab.core.types.Command;

public class DimmerItemProxy extends ItemProxy<DimmerItem, Command> {

	public DimmerItemProxy(String itemName) throws ItemLookupException {
		super(itemName);
	}

}
