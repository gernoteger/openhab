package org.openhab.action.javarule.internal.items;

import org.openhab.core.library.items.DateTimeItem;
import org.openhab.core.library.types.DateTimeType;

public class DateTimeItemProxy extends ItemProxy<DateTimeItem, DateTimeType> {

    public DateTimeItemProxy(String itemName) {
	super(itemName);
    }

}
