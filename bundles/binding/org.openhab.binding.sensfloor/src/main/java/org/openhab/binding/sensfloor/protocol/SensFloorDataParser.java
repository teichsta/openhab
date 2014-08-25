/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.sensfloor.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Thomas Eichstaedt-Engelen
 * @since 1.6.0
 */
public class SensFloorDataParser {

	private static final Logger logger = 
		LoggerFactory.getLogger(SensFloorDataParser.class);
	
	
	public static SensFloorMessage parseData(byte[] data) {
		if (data == null) {
			return null;
		}
		
		byte crc = data[0];
		boolean isValid = (crc & 0xfd) == 0xfd && data.length == 17;
		
		if (isValid) {
			byte msgType = data[7];
			
			switch (msgType) {
				case (byte) 0x10:
				case (byte) 0x11:
					return new SensFloorSensorMessage(data);
				case (byte) 0x90:
					return new SensFloorEventMessage(data);
				default :
					logger.debug("Unknown message type '{}' (rawData={})", msgType, data);
					return null;
			}
		} else {
			logger.debug("Received sensor message '{}' is invalid", data);
			return null;
		}
	}
	
}
