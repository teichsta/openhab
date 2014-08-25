/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.sensfloor.protocol;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.openhab.binding.sensfloor.internal.SensFloorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thomas Eichstaedt-Engelen
 * @since 1.6.0
 */
public class SensFloorSerialConnector implements SerialPortEventListener {

	private static final Logger logger = 
		LoggerFactory.getLogger(SensFloorSerialConnector.class);
	
	private String portName;
	private final static int BAUD = 115200;
	
	private CommPortIdentifier portId;
	private SerialPort serialPort;

	private InputStream inputStream;
	private OutputStream outputStream;
	
	private byte[] byteCache;
	
	
	public SensFloorSerialConnector(String portName) {
		this.portName = portName;
	}
	
	
	public void connect() throws SensFloorException {
		// parse ports and if the default port is found, initialized the reader
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			CommPortIdentifier id = (CommPortIdentifier) portList.nextElement();
			if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (id.getName().equals(portName)) {
					logger.debug("Serial port '{}' has been found.", portName);
					portId = id;
				}
			}
		}
		if (portId != null) {
			// initialize serial port
			try {
				serialPort = (SerialPort) portId.open("openHAB", 2000);
			} catch (PortInUseException e) {
				throw new SensFloorException(e);
			}

			try {
				inputStream = serialPort.getInputStream();
			} catch (IOException e) {
				throw new SensFloorException(e);
			}

			try {
				serialPort.addEventListener(this);
			} catch (TooManyListenersException e) {
				throw new SensFloorException(e);
			}

			// activate the DATA_AVAILABLE notifier
			serialPort.notifyOnDataAvailable(true);

			try {
				// set port parameters
				serialPort.setSerialPortParams(BAUD, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			} catch (UnsupportedCommOperationException e) {
				throw new SensFloorException(e);
			}

			try {
				// get the output stream
				outputStream = serialPort.getOutputStream();
			} catch (IOException e) {
				throw new SensFloorException(e);
			}
		} else {
			StringBuilder sb = new StringBuilder();
			portList = CommPortIdentifier.getPortIdentifiers();
			while (portList.hasMoreElements()) {
				CommPortIdentifier id = (CommPortIdentifier) portList.nextElement();
				if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					sb.append(id.getName() + "\n");
				}
			}
			throw new SensFloorException("Serial port '" + portName + "' could not be found. Available ports are:\n" + sb.toString());
		}
	}

	public void disconnect() throws SensFloorException {
		logger.debug("Disconnecting from serial port.");
		serialPort.removeEventListener();
		IOUtils.closeQuietly(inputStream);
		IOUtils.closeQuietly(outputStream);
		serialPort.close();
		logger.debug("Serial port closed.");
	}

	
	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			processDataAvailableEvent(event);
		}
	}
	
	private void processDataAvailableEvent(SerialPortEvent event) {
		byte[] localBuffer = new byte[32];
		try {
			// read data from serial device
			while (inputStream.available() > 0) {
				int bytesRead = inputStream.read(localBuffer);
				for (int index = 0; index < bytesRead; index++) {
					byte newByte = localBuffer[index];
					if (newByte == -3) {
						parseBytes(byteCache);
						byteCache = new byte[1];
						byteCache[0] = newByte;
					} else {
						byteCache = ArrayUtils.add(byteCache, newByte);
					}
				}
			}
			
		} catch (IOException e) {
			logger.debug("Error receiving data on serial port {}: {}", portName, e.getMessage());
		} catch (SensFloorException e) {
			e.printStackTrace();
		}
	}

	private void parseBytes(byte[] bytes) throws SensFloorException {
		SensFloorMessage message = SensFloorDataParser.parseData(bytes);
		if (message != null) {
			if (message instanceof SensFloorEventMessage) {
				logger.debug("Received message '{}' on serial port {}", message, portName);
			}
		}
	}
	
	
}
