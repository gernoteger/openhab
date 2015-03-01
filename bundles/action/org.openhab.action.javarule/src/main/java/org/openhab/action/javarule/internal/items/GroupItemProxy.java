package org.openhab.action.javarule.internal.items;

import org.openhab.core.items.GenericItem;
import org.openhab.core.types.Command;

public class GroupItemProxy extends ItemProxy<GenericItem, Command> {

    public GroupItemProxy(String itemName) {
	super(itemName);
    }

}
