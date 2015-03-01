/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.action.javarule.user;

import static org.openhab.action.javarule.user.Items.*;
import static org.openhab.core.library.types.OnOffType.*;
import static org.openhab.core.library.types.UpDownType.*;

import org.openhab.action.javarule.internal.GenericRule;
import org.openhab.action.javarule.internal.ItemsGenerator;
import org.openhab.action.javarule.internal.items.ColorItemProxy;
import org.openhab.core.items.Item;
import org.openhab.core.library.types.HSBType;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import static org.openhab.core.library.types.OnOffType.*;
//import static org.openhab.action.javarule.internal.rule.Items.*;

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
    public synchronized void receiveCommand(Item item, Command command) {
	logger.debug("receiveCommand({},{}) ", item.getName(),
		command.toString());

	switch (item.getName()) {
	case "testJavaRule":
	    logger.info("test java");
	    String items = ItemsGenerator.generateItemList();
	    logger.info("items: \n{}", items);

	    // gHue.getState();
	    // postUpdate(Tap_1_1.getName(), off);

	    // sendCommand(Color_1.getName(), "ON");

	    // Teststrecke für Proxires
	    try {
		ColorItemProxy cp1 = new ColorItemProxy("Color_2");

		// private static final String beach = "89.68681,18.82353,95";
		// hue, saturation, value
		// there's no nice Constuctor for that; no
		// Colors are still somewhat clumsy to handle
		// also transition times can't ´be handled with OpenHab API
		HSBType beach = HSBType.valueOf("89.68681,18.82353,95");
		// the same color
		// HSBType beach1 = new HSBType(Color.getHSBColor(89.68681F /
		// 360,
		// 0.1882353F, 0.95F));

		// HSBType.RED
		cp1.send(beach); // implies ON

		cp1.send(OFF);

		wait(1000);

		// shouldn't be possible! will be ignored..
		cp1.send(UP);

		Color_1.send(ON);
		Color_2.send(ON);
		Color_3.send(ON);

	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    break;
	}
    }
}
