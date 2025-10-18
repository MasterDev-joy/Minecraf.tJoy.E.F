package joymaster.epicfight.events;

import joymaster.epicfight.main.EpicFightMod;
import joymaster.epicfight.capabilities.ModCapabilities;
import joymaster.epicfight.capabilities.ProviderEntity;
import joymaster.epicfight.capabilities.ProviderItem;
import joymaster.epicfight.capabilities.entity.CapabilityEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid= EpicFightMod.MODID)
public class CapabilityEvent {
	@SubscribeEvent
	public static void attachItemCapability(AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getCapability(ModCapabilities.CAPABILITY_ITEM, null) == null) {
			ProviderItem prov = new ProviderItem(event.getObject().getItem(), false);
			if (prov.hasCapability()) {
				event.addCapability(new ResourceLocation(EpicFightMod.MODID, "item_cap"), prov);
			}
		}
	}
	
	@SubscribeEvent
	public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
		if(event.getObject().getCapability(ModCapabilities.CAPABILITY_ENTITY, null) == null) {
			ProviderEntity prov = new ProviderEntity(event.getObject());
			if(prov.hasCapability()) {
				CapabilityEntity entityCap = prov.getCapability(ModCapabilities.CAPABILITY_ENTITY, null);
				entityCap.onEntityConstructed(event.getObject());
				event.addCapability(new ResourceLocation(EpicFightMod.MODID, "entity_cap"), prov);
			}
		}
	}
}