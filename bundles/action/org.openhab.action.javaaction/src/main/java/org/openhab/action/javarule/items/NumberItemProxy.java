package org.openhab.action.javarule.items;

import org.openhab.core.items.ItemLookupException;
import org.openhab.core.library.items.NumberItem;
import org.openhab.core.library.types.DecimalType;

public class NumberItemProxy extends ItemProxy<NumberItem, DecimalType> {

	public NumberItemProxy(String itemName) throws ItemLookupException {
		super(itemName);
	}

}
