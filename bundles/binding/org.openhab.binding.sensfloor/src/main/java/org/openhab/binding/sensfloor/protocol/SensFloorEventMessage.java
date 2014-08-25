/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.sensfloor.protocol;


/**
 * @author Thomas Eichstaedt-Engelen
 * @since 1.6.0
 */
public class SensFloorEventMessage extends SensFloorMessage {

	public SensFloorEventMessage(byte[] rawData) {
		super(rawData);
	}
	
	public int getTransceiverChannel() {
		return getByte(8);
	}
	
	public int getEventType() {
		return getByte(9);
	}
	
	public boolean isEventType1() {
		return (getByte(9) & 1) == 1;
	}

	public boolean isDownFallEvent() {
		return (getByte(9) & 2) == 2;
	}

	public boolean isEventType3() {
		return (getByte(9) & 4) == 4;
	}

	public boolean isEventType4() {
		return (getByte(9) & 8) == 8;
	}

	public boolean isEventType5() {
		return (getByte(9) & 16) == 16;
	}

	public boolean isEventType6() {
		return (getByte(9) & 32) == 32;
	}

	public boolean isEventType7() {
		return (getByte(9) & 64) == 64;
	}
	
	public boolean isEventType8() {
		return (getByte(9) & 128) == 128;
	}

	public int getXPos() {
		return getByte(11);
	}
	
	public int getYPos() {
		return getByte(12);
	}

	public int getTimeStamp() {
		return ((0xFF & getByte(13)) << 32 | (0xFF & getByte(14)) << 16 | (0xFF & getByte(15)) << 8 | 0xFF & getByte(16));
	}

	@Override
	public String toString() {
		return "SensFloorEventMessage ["
				+ "isValidMessage()=" + isValidMessage()
				+ ", getRoomId()=" + getRoomId()
				+ ", getSensorDeviceNo()=" + getSensorDeviceNo()
				+ ", getSensorDeviceRow()=" + getSensorDeviceRow()
				+ ", getTransceiverChannel()=" + getTransceiverChannel() 
				+ ", getEventType()=" + getEventType()
				+ ", getXPos()=" + getXPos() 
				+ ", getYPos()=" + getYPos()
				+ ", getTimeStamp()=" + getTimeStamp() + "]";
	}

}
