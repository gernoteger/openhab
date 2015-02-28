package org.openhab.action.javarule.items;

import org.openhab.core.items.ItemLookupException;
import org.openhab.core.library.items.StringItem;
import org.openhab.core.library.types.StringType;

public class StringItemProxy extends ItemProxy<StringItem, StringType> {

	public StringItemProxy(String itemName) throws ItemLookupException {
		super(itemName);
	}

}
