package joymaster.epicfight.main.proxy;

import joymaster.epicfight.capabilities.ModCapabilities;
import joymaster.epicfight.capabilities.ProviderEntity;
import joymaster.epicfight.network.ModNetworkManager;
import joymaster.epicfight.capabilities.ProviderItem;

public class CommonProxy implements IProxy {
	public void init() {
		ModCapabilities.registerCapabilities();
		ModNetworkManager.registerPackets();
		ProviderItem.makeMap();
		ProviderEntity.makeMap();
	}
}