/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fs20.internal;

import java.util.HashMap;
import java.util.Map;

import org.openhab.binding.fs20.FS20BindingConfig;
import org.openhab.binding.fs20.FS20BindingProvider;
import org.openhab.core.items.Item;
import org.openhab.core.library.items.DimmerItem;
import org.openhab.core.library.items.RollershutterItem;
import org.openhab.core.library.items.SwitchItem;
import org.openhab.model.item.binding.AbstractGenericBindingProvider;
import org.openhab.model.item.binding.BindingConfigParseException;

/**
 * This class is responsible for parsing the binding configuration.
 * 
 * @author Till Klocke
 * @since 1.4.0
 */
public class FS20GenericBindingProvider extends AbstractGenericBindingProvider
	implements FS20BindingProvider {

    private final Map<String, FS20BindingConfig> addressMap = new HashMap<String, FS20BindingConfig>();

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBindingType() {
	return "fs20";
    }

    /**
     * @{inheritDoc
     */
    @Override
    public void validateItemType(Item item, String bindingConfig)
	    throws BindingConfigParseException {
	if (!(item instanceof SwitchItem || item instanceof DimmerItem || item instanceof RollershutterItem)) {
	    throw new BindingConfigParseException(
		    "item '"
			    + item.getName()
			    + "' is of type '"
			    + item.getClass().getSimpleName()
			    + "', only Switch- Rollershutter- and DimmerItems are allowed - please check your *.items configuration");
	}

	FS20BindingConfig config = FS20BindingConfig.parse(bindingConfig);
	if (config == null) {
	    throw new BindingConfigParseException(
		    "The configured address must consist of 2 bytes housecode and 1 byte device address....");
	}
    }

    /**
     * Binding config is in the style of {fs20="HHHHAA"} {@inheritDoc}
     */
    @Override
    public void processBindingConfiguration(String context, Item item,
	    String bindingConfig) throws BindingConfigParseException {
	super.processBindingConfiguration(context, item, bindingConfig);

	FS20BindingConfig config = FS20BindingConfig.parse(bindingConfig);
	config.setItem(item);

	// parse bindingconfig here ...
	addressMap.put(config.getAddress(), config);

	if (item instanceof RollershutterItem) {
	    FS20Shutter.putItemConfig(config);
	}
	addBindingConfig(item, config);
    }

    @Override
    public FS20BindingConfig getConfigForItemName(String itemName) {
	if (super.bindingConfigs.containsKey(itemName)) {
	    return (FS20BindingConfig) super.bindingConfigs.get(itemName);
	}
	return null;
    }

    @Override
    public FS20BindingConfig getConfigForAddress(String address) {
	return addressMap.get(address);
    }

}
