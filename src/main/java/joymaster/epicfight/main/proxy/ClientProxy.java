package joymaster.epicfight.main.proxy;

import joymaster.epicfight.capabilities.ProviderEntity;
import joymaster.epicfight.client.ClientEngine;
import joymaster.epicfight.client.input.ModKeys;
import joymaster.epicfight.client.model.ClientModels;

public class ClientProxy extends CommonProxy implements IProxy {
	@Override
	public void init() {
		super.init();
		new ClientEngine();
		ClientEngine.INSTANCE.renderEngine.buildRenderer();
		ProviderEntity.makeMapClient();
		ModKeys.registerKeys();
		ClientModels.LOGICAL_CLIENT.buildMeshData();
	}
}