/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.sensfloor.internal;

import java.util.Dictionary;

import org.openhab.binding.sensfloor.SensFloorBindingProvider;
import org.openhab.binding.sensfloor.protocol.SensFloorSerialConnector;
import org.openhab.core.binding.AbstractBinding;
import org.openhab.core.events.EventPublisher;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Binding to receive data from Swegon ventilation system.
 * 
 * @author Thomas Eichstaedt-Engelen
 * @since 1.6.0
 */
public class SensFloorBinding extends AbstractBinding<SensFloorBindingProvider> implements ManagedService {

	private static final Logger logger = LoggerFactory.getLogger(SensFloorBinding.class);

	private String serialPort = null;

	private SensFloorSerialConnector serialConnector = null;
	
	
	public SensFloorBinding() {
	}
	
	
	public void activate() {
		logger.debug("Activate");
	}

	public void deactivate() {
		logger.debug("Deactivate");
		
		try {
			serialConnector.disconnect();
		} catch (SensFloorException e) {
			e.printStackTrace();
		}
	}

	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	public void unsetEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = null;
	}
	
	
	/**
	 * @{inheritDoc
	 */
	@Override
	public void updated(Dictionary<String, ?> config) throws ConfigurationException {

		logger.debug("Configuration updated, config {}", config != null ? true : false);

		if (config != null) {
			serialPort = (String) config.get("serialPort");
		}
		
		serialConnector = new SensFloorSerialConnector(serialPort);
		try {
			serialConnector.connect();
		} catch (SensFloorException e) {
			e.printStackTrace();
		}
	}

}
