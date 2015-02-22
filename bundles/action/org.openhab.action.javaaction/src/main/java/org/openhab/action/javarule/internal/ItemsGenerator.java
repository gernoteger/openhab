package org.openhab.action.javarule.internal;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openhab.core.items.Item;

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
	static String generateItemList() {

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
			String itemType = item.getClass().getSimpleName();

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
		dw.println(intend + "/**");
		dw.println(intend + " * set items to values from item registry");
		dw.println(intend + " */");
		dw.println(intend + "public static void setItems() {");
		dw.print(setWriter.toString());
		dw.println(intend + "}");
		dw.println("//==== END copy code ======");

		dw.flush();

		return delareWriter.toString();
	}
}
