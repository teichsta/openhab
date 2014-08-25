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
public class SensFloorSensorMessage extends SensFloorMessage {

	public SensFloorSensorMessage(byte[] rawData) {
		super(rawData);
	}
	
	public short getField1Value() {
		return getByte(9);
	}

	public short getField2Value() {
		return getByte(10);
	}

	public short getField3Value() {
		return getByte(11);
	}

	public short getField4Value() {
		return getByte(12);
	}

	public short getField5Value() {
		return getByte(13);
	}

	public short getField6Value() {
		return getByte(14);
	}

	public short getField7Value() {
		return getByte(15);
	}

	public short getField8Value() {
		return getByte(16);
	}

	
	@Override
	public String toString() {
		return "SensFloorSensorMessage ["
				+ "isValidMessage()=" + isValidMessage()
				+ ", getRoomId()=" + getRoomId()
				+ ", getSensorDeviceNo()=" + getSensorDeviceNo()
				+ ", getSensorDeviceRow()=" + getSensorDeviceRow()
				+ ", getField1Value()=" + getField1Value()
				+ ", getField2Value()=" + getField2Value()
				+ ", getField3Value()=" + getField3Value()
				+ ", getField4Value()=" + getField4Value()
				+ ", getField5Value()=" + getField5Value()
				+ ", getField6Value()=" + getField6Value()
				+ ", getField7Value()=" + getField7Value()
				+ ", getField8Value()=" + getField8Value()
				+ ", isSignificantChange()=" + isSignificantChange()
				+ ", isMasterMessage()=" + isMasterMessage()
				+ ", isStatusRequestMessage()=" + isStatusRequestMessage()
				+ ", isStatusResponseMessage()=" + isStatusResponseMessage()
				+ ", allowsNegativeCapacity()=" + allowsNegativeCapacity()
				+ ", isSensorMessage()=" + isSensorMessage()
				+ ", isTransceiverMessage()=" + isTransceiverMessage()
				+ ", isEventMessage()=" + isEventMessage() 
				+ ", isEventResponseMessage()=" + isEventResponseMessage() + "]";
	}
	
	
}
