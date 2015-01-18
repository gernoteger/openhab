/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.action.hue.internal;

import java.util.Dictionary;

import org.openhab.core.scriptengine.action.ActionService;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

/**
 * This class registers an OSGi service for the Homematic action.
 * 
 * @author Gernot Eger
 * @since 1.7.0
 */
public class HueActionService implements ActionService, ManagedService {

	public void activate() {
	}

	public void deactivate() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getActionClassName() {
		return Hue.class.getCanonicalName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getActionClass() {
		return Hue.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updated(Dictionary<String, ?> properties) throws ConfigurationException {
	}

}
