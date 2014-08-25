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
public class SensFloorMessage {
	
	private byte[] rawData;
	
	
	public SensFloorMessage(byte[] rawData) {
		this.rawData = rawData;
	}
	
	
	protected byte getByte(int index) {
		return this.rawData[index];
	}
	
	public int getRoomId() {
		return ((0xFF & getByte(1)) << 8) | (0xFF & getByte(2));
	}
	
	public int getSensorDeviceNo() {
		return getByte(3);
	}
	
	public int getSensorDeviceRow() {
		return getByte(4);
	}
	
	
	// *****************************************************************************
	
	public boolean isValidMessage() {
		return (getByte(0) & 0xfd) == 0xfd;
	}
	
	public boolean isSignificantChange() {
		return (getByte(7) & 1) == 1;
	}
	
	public boolean isMasterMessage() {
		return (getByte(7) & 2) == 2;
	}
	
	public boolean isStatusRequestMessage() {
		return (getByte(7) & 4) == 4;
	}
	
	public boolean isStatusResponseMessage() {
		return (getByte(7) & 8) == 8;
	}
	
	public boolean allowsNegativeCapacity() {
		return (getByte(7) & 16) == 16;
	}
	
	public boolean isEventResponseMessage() {
		return (getByte(7) & 129) == 129;
	}
	
	public boolean isSensorMessage() {
		return !isTransceiverMessage();
	}
	
	public boolean isTransceiverMessage() {
		return (getByte(7) & 0x7) == 0x7;
	}
	
	public boolean isEventMessage() {
		return false;
	}
	
	protected byte getRawMessageType() {
		return getByte(7);
	}
	

}
