package org.openhab.action.javarule.internal.items;

import org.openhab.core.library.items.RollershutterItem;
import org.openhab.core.types.Command;

public class RollershutterItemProxy extends
	ItemProxy<RollershutterItem, Command> {

    public RollershutterItemProxy(String itemName) {
	super(itemName);
    }

}
