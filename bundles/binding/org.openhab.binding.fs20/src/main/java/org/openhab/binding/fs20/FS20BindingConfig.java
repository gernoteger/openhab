/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fs20;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openhab.core.binding.BindingConfig;
import org.openhab.core.items.Item;

/**
 * Binding config for FS20 devices.
 * 
 * @author Till Klocke
 * 
 */
public class FS20BindingConfig implements BindingConfig {

    /**
     * The complete address including Housecode of the device
     */
    private final String address;
    /**
     * only for Rollershutter: time for a full move
     */
    private final Integer moveDurationMillis;

    private Item item = null;

    /***
     * possible extra options when communicating with the device. currently
     * unused.
     */
    private String extraOpts;

    private final static Pattern DURATION_PATTERN = Pattern
	    .compile("t=([0-9]+)(ms)?");

    private final static Pattern ADRESS_PATTERN = Pattern
	    .compile("[A-F0-9]{6}");

    /**
     * parse form config String
     * 
     * @param config
     * @return FS20BindingConfig or null if not parseable
     */
    public static FS20BindingConfig parse(String config) {
	// extract adress
	// could be done better..
	// strip whitespaces & create elements
	String[] tokens = config.replaceAll("\\s", "").split(";");
	Integer duration_ms = null;
	if (tokens.length < 1)
	    return null;

	if (!ADRESS_PATTERN.matcher(tokens[0]).matches()) {
	    return null;
	}

	// parse duration
	if (tokens.length > 1) {
	    Matcher m = DURATION_PATTERN.matcher(tokens[1]);
	    if (m.matches()) {
		String duration = m.group(1);
		if (duration != null) {
		    duration_ms = Integer.parseInt(duration);
		}
	    }
	}

	return new FS20BindingConfig(tokens[0], duration_ms);
    }

    private FS20BindingConfig(String address, Integer moveDurationMillis) {
	this.address = address;
	this.moveDurationMillis = moveDurationMillis;
    }

    public String getExtraOpts() {
	return extraOpts;
    }

    public void setExtraOpts(String extraOpts) {
	this.extraOpts = extraOpts;
    }

    public String getAddress() {
	return address;
    }

    public Item getItem() {
	return item;
    }

    public void setItem(Item item) {
	this.item = item;
    }

    public Integer getMoveDurationMillis() {
	return moveDurationMillis;
    }

}
