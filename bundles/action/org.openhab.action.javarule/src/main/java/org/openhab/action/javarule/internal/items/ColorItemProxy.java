package org.openhab.action.javarule.internal.items;

import org.openhab.core.library.items.ColorItem;
import org.openhab.core.types.Command;

public class ColorItemProxy extends ItemProxy<ColorItem, Command> {

    public ColorItemProxy(String itemName) {
	super(itemName);
    }

}
