package org.openhab.action.javarule.internal.items;

import org.openhab.core.library.items.DimmerItem;
import org.openhab.core.types.Command;

public class DimmerItemProxy extends ItemProxy<DimmerItem, Command> {

    public DimmerItemProxy(String itemName) {
	super(itemName);
    }

}
