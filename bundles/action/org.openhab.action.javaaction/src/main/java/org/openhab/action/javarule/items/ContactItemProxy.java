package org.openhab.action.javarule.items;

import org.openhab.core.items.ItemLookupException;
import org.openhab.core.library.items.ContactItem;
import org.openhab.core.library.types.OpenClosedType;

public class ContactItemProxy extends ItemProxy<ContactItem, OpenClosedType> {

	public ContactItemProxy(String itemName) throws ItemLookupException {
		super(itemName);
	}

}
