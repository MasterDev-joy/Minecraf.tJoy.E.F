package joymaster.epicfight.events;

import joymaster.epicfight.client.ClientEngine;
import joymaster.epicfight.main.EpicFightMod;
import joymaster.epicfight.capabilities.ModCapabilities;
import joymaster.epicfight.capabilities.entity.player.PlayerData;
import joymaster.epicfight.capabilities.item.CapabilityItem;
import joymaster.epicfight.gamedata.Colliders;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@Mod.EventBusSubscriber(modid= EpicFightMod.MODID)
public class PlayerEvents {
	@SubscribeEvent
	public static void arrowLooseEvent(ArrowLooseEvent event) {
		Colliders.update();
	}
	
	@SubscribeEvent
	public static void itemUseStartEvent(LivingEntityUseItemEvent.Start event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getEntity();
			PlayerData<?> playerdata = (PlayerData<?>) event.getEntity().getCapability(ModCapabilities.CAPABILITY_ENTITY, null);
			CapabilityItem itemCap = playerdata.getHeldItemCapability(EnumHand.MAIN_HAND);

			if (playerdata.isInaction()) {
				event.setCanceled(true);
			} else if (event.getItem() == player.getHeldItemOffhand() && itemCap != null && itemCap.isTwoHanded()) {
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void itemUseStopEvent(LivingEntityUseItemEvent.Stop event) {
		if (event.getEntity().world.isRemote) {
			if (event.getEntity() instanceof EntityPlayerSP) {
				ClientEngine.INSTANCE.renderEngine.zoomOut(0);
			}
		}
	}
	
	@SubscribeEvent
	public static void itemUseTickEvent(LivingEntityUseItemEvent.Tick event) {
		if (event.getEntity() instanceof EntityPlayer) {
			if (event.getItem().getItem() instanceof ItemBow) {
				PlayerData<?> playerdata = (PlayerData<?>) event.getEntity().getCapability(ModCapabilities.CAPABILITY_ENTITY, null);

				if (playerdata.isInaction()) {
					event.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void playerTickPost(PlayerTickEvent event) {
		if (event.phase == Phase.END) {
			PlayerData<?> playerdata = (PlayerData<?>) event.player.getCapability(ModCapabilities.CAPABILITY_ENTITY, null);
			if (playerdata != null) {
				playerdata.resetSize();
			}
		}
	}
	
	@SubscribeEvent
	public static void attackEntityEvent(AttackEntityEvent event) {
		if (!event.getEntity().world.getGameRules().getBoolean("doVanillaAttack")) {
			event.setCanceled(true);
		}
	}
}