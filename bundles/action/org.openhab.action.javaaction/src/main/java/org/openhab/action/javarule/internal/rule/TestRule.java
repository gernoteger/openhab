/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.action.javarule.internal.rule;

import org.openhab.action.javarule.internal.Items;
import org.openhab.core.items.Item;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;

//import static org.openhab.action.javarule.internal.Items.*;

/**
 * Implements a generic class for rules; does all the necessary registration and
 * stuff an calls helper methods.
 * 
 * @author gernot
 * 
 */
public class TestRule extends ItemsRule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void receiveCommand(Item item, Command command) {
		logger.debug("receiveCommand({},{}) ", item.getName(),
				command.toString());

		switch (item.getName()) {
		case "testJavaRule":
			logger.info("test java");
			// String items = generateItemList();
			// logger.info("items: \n{}", items);

			// gHue.getState();
			// String off = OnOffType.OFF.name();
			// postUpdate(Tap_1_1.getName(), off);

			sendCommand(Color_1.getName(), "ON");
			break;
		}
	}

	@Override
	public void stateChanged(Item item, State oldState, State newState)
			throws Exception {
		logger.debug("stateChanged({},{},{}) ", item.getName(), oldState,
				newState);

	}

	@Override
	public void onStartup() throws Exception {
		logger.debug("onStartup()");
	}

	/**
	 * create a List of Items for inclusion
	 * 
	 * @return
	 */
	String generateItemList() {
		final StringBuilder items = new StringBuilder();
		final String eol = "\n";
		final String intend = "  ";
		final String modifier = ""; // "static final "

		items.append("{" + eol);
		for (Item item : Items.allItems()) {
			items.append(intend + modifier + item.getClass().getSimpleName()
					+ " " + item.getName());
			items.append("= item(\"" + item.getName() + "\");");
			items.append(eol);
		}
		items.append("}" + eol);
		return items.toString();
	}
}
