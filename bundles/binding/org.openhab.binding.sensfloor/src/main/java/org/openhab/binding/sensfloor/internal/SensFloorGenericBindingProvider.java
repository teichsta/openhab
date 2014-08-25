/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.sensfloor.internal;

import org.openhab.binding.sensfloor.SensFloorBindingProvider;
import org.openhab.core.binding.BindingConfig;
import org.openhab.core.items.Item;
import org.openhab.core.library.items.SwitchItem;
import org.openhab.model.item.binding.AbstractGenericBindingProvider;
import org.openhab.model.item.binding.BindingConfigParseException;

/**
 * @author Thomas Eichstaedt-Engelen
 * @since 1.6.0
 */
public class SensFloorGenericBindingProvider extends
		AbstractGenericBindingProvider implements
		SensFloorBindingProvider {

	/**
	 * {@inheritDoc}
	 */
	public String getBindingType() {
		return "sensfloor";
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	public void validateItemType(Item item, String bindingConfig) throws BindingConfigParseException {
		if (!(item instanceof SwitchItem)) {
			throw new BindingConfigParseException(
				"item '" + item.getName() + "' is of type '" + item.getClass().getSimpleName() +
				"', only SwitchItems are allowed - please check your *.items configuration");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processBindingConfiguration(String context, Item item,
			String bindingConfig) throws BindingConfigParseException {
		super.processBindingConfiguration(context, item, bindingConfig);

		SensFloorBindingConfig config = new SensFloorBindingConfig();

		config.itemType = item.getClass();
		String commandType = bindingConfig.trim();

		addBindingConfig(item, config);
	}
	
	
	/**
	 * @{inheritDoc
	 */
	@Override
	public Class<? extends Item> getItemType(String itemName) {
		SensFloorBindingConfig config = (SensFloorBindingConfig) bindingConfigs.get(itemName);
		return config != null ? config.itemType : null;
	}
	
	
	class SensFloorBindingConfig implements BindingConfig {
		public Class<? extends Item> itemType = null;
		
		@Override
		public String toString() {
			return "sensfloorBindingConfig ["
					+ ", itemType=" + itemType
					+ "]";
		}
	}

	
}
