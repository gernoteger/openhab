package org.openhab.action.javarule.internal;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openhab.action.javarule.items.ColorItemProxy;
import org.openhab.action.javarule.items.ContactItemProxy;
import org.openhab.action.javarule.items.DateTimeItemProxy;
import org.openhab.action.javarule.items.DimmerItemProxy;
import org.openhab.action.javarule.items.GroupItemProxy;
import org.openhab.action.javarule.items.ItemProxy;
import org.openhab.action.javarule.items.NumberItemProxy;
import org.openhab.action.javarule.items.RollershutterItemProxy;
import org.openhab.action.javarule.items.StringItemProxy;
import org.openhab.action.javarule.items.SwitchItemProxy;
import org.openhab.action.javarule.items.UnknownItemProxy;
import org.openhab.core.items.GenericItem;
import org.openhab.core.items.GroupItem;
import org.openhab.core.items.Item;
import org.openhab.core.library.items.ColorItem;
import org.openhab.core.library.items.ContactItem;
import org.openhab.core.library.items.DateTimeItem;
import org.openhab.core.library.items.DimmerItem;
import org.openhab.core.library.items.NumberItem;
import org.openhab.core.library.items.RollershutterItem;
import org.openhab.core.library.items.StringItem;
import org.openhab.core.library.items.SwitchItem;
import org.openhab.core.types.Command;

/**
 * Code Generator for Items content
 * 
 * @author gernot
 * 
 */
public class ItemsGenerator {

	/**
	 * creates code to set Items for the Items class
	 * 
	 * @return
	 */
	public static String generateItemList() {

		final String intend = "  ";
		final String modifier = "static "; // "static final "

		// declaration
		StringWriter delareWriter = new StringWriter();
		PrintWriter dw = new PrintWriter(delareWriter, true);

		// set
		StringWriter setWriter = new StringWriter();
		PrintWriter sw = new PrintWriter(setWriter, true);

		dw.println("//==== copy code below ======");

		// declare item variables
		for (Item item : AbstractItems.allItems()) {
			String itemType = proxyClass(item).getSimpleName();

			String declare = intend + modifier + itemType + " "
					+ item.getName() + ";";
			String set = intend + intend + item.getName() + "= item(\""
					+ item.getName() + "\");";

			dw.println(declare);
			sw.println(set);
		}

		sw.flush();

		// create set part
		dw.println();
		// dw.println(intend + "/**");
		// dw.println(intend + " * set items to values from item registry");
		// dw.println(intend + " */");
		// dw.println(intend + "public static void setItems() {");
		// dw.print(setWriter.toString());
		// dw.println(intend + "}");
		// dw.println("//==== END copy code ======");
		//
		dw.flush();

		return delareWriter.toString();
	}

	/**
	 * map Item classes to proxy classes
	 * 
	 * @param item
	 * @return
	 */
	private static Class<? extends ItemProxy<? extends GenericItem, ? extends Command>> proxyClass(
			Item item) {
		if (item instanceof ColorItem) {
			return ColorItemProxy.class;
		} else if (item instanceof ContactItem) {
			return ContactItemProxy.class;
		} else if (item instanceof DateTimeItem) {
			return DateTimeItemProxy.class;
		} else if (item instanceof DimmerItem) {
			return DimmerItemProxy.class;
		} else if (item instanceof NumberItem) {
			return NumberItemProxy.class;
		} else if (item instanceof RollershutterItem) {
			return RollershutterItemProxy.class;
		} else if (item instanceof StringItem) {
			return StringItemProxy.class;
		} else if (item instanceof SwitchItem) {
			return SwitchItemProxy.class;
		} else if (item instanceof GroupItem) {
			return GroupItemProxy.class;
		}

		return UnknownItemProxy.class;
	}
}
