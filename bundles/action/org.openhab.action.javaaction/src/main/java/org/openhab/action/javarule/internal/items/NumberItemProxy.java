package org.openhab.action.javarule.internal.items;

import org.openhab.core.library.items.NumberItem;
import org.openhab.core.library.types.DecimalType;

public class NumberItemProxy extends ItemProxy<NumberItem, DecimalType> {

    public NumberItemProxy(String itemName) {
	super(itemName);
    }

}
