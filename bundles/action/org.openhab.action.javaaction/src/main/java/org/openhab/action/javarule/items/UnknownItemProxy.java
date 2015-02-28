package org.openhab.action.javarule.items;

import org.openhab.core.items.GenericItem;
import org.openhab.core.types.Command;

/**
 * proxy for unknown ItemTypes
 * 
 * @author gernot
 * 
 */
public class UnknownItemProxy extends ItemProxy<GenericItem, Command> {

	public UnknownItemProxy(String itemName) {
		super(itemName);
	}

}
