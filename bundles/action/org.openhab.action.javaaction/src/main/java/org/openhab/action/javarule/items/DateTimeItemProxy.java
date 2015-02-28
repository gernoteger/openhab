package org.openhab.action.javarule.items;

import org.openhab.core.items.ItemLookupException;
import org.openhab.core.library.items.DateTimeItem;
import org.openhab.core.library.types.DateTimeType;

public class DateTimeItemProxy extends ItemProxy<DateTimeItem, DateTimeType> {

	public DateTimeItemProxy(String itemName) throws ItemLookupException {
		super(itemName);
	}

}
