package joymaster.epicfight.client;

import joymaster.epicfight.client.capabilites.entity.ClientPlayerData;
import joymaster.epicfight.client.events.engine.ControllEngine;
import joymaster.epicfight.client.events.engine.RenderEngine;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEngine {
	public static ClientEngine INSTANCE;
	public RenderEngine renderEngine;
	public ControllEngine inputController;
	
	private ClientPlayerData playerdata;
	private PlayerActingMode playerActingMode = PlayerActingMode.MINING;
	
	public ClientEngine() {
		INSTANCE = this;
		renderEngine = new RenderEngine();
		inputController = new ControllEngine();
	}
	
	public void toggleActingMode() {
		if(this.playerActingMode == PlayerActingMode.MINING) {
			this.switchToBattleMode();
		} else {
			this.switchToMiningMode();
		}
	}
	
	private void switchToMiningMode() {
		this.playerActingMode = PlayerActingMode.MINING;
		this.renderEngine.guiSkillBar.slideDown();
	}
	
	private void switchToBattleMode() {
		this.playerActingMode = PlayerActingMode.BATTLE;
		this.renderEngine.guiSkillBar.slideUp();
	}
	
	public PlayerActingMode getPlayerActingMode() {
		return this.playerActingMode;
	}
	
	public boolean isBattleMode() {
		return this.playerActingMode == PlayerActingMode.BATTLE;
	}
	
	public void setPlayerData(ClientPlayerData playerdata) {
		this.playerdata = playerdata;
	}
	
	public ClientPlayerData getPlayerData() {
		return this.playerdata;
	}
	
	public static enum PlayerActingMode {
		MINING, BATTLE
	}
}