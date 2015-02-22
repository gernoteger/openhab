/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.action.javarule.internal.rule;

import static org.openhab.action.javarule.internal.rule.Items.*;

import org.openhab.action.javarule.internal.GenericRule;
import org.openhab.core.items.Item;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements a generic class for rules; does all the necessary registration and
 * stuff an calls helper methods.
 * 
 * @author gernot
 * 
 */
public class TestRule extends GenericRule {
	protected static final Logger logger = LoggerFactory
			.getLogger(TestRule.class);

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
			// postUpdate(Tap_1_1.getName(), off);

			sendCommand(Color_1.getName(), "ON");

			break;
		}
	}

}
