package joymaster.epicfight.capabilities;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import joymaster.epicfight.capabilities.entity.CapabilityEntity;
import joymaster.epicfight.capabilities.entity.mob.CaveSpiderData;
import joymaster.epicfight.capabilities.entity.mob.CreeperData;
import joymaster.epicfight.capabilities.entity.mob.EndermanData;
import joymaster.epicfight.capabilities.entity.mob.EvokerData;
import joymaster.epicfight.capabilities.entity.mob.IronGolemData;
import joymaster.epicfight.capabilities.entity.mob.SkeletonData;
import joymaster.epicfight.capabilities.entity.mob.SpiderData;
import joymaster.epicfight.capabilities.entity.mob.StrayData;
import joymaster.epicfight.capabilities.entity.mob.VexData;
import joymaster.epicfight.capabilities.entity.mob.VindicatorData;
import joymaster.epicfight.capabilities.entity.mob.WitchData;
import joymaster.epicfight.capabilities.entity.mob.WitherSkeletonData;
import joymaster.epicfight.capabilities.entity.mob.ZombieData;
import joymaster.epicfight.capabilities.entity.mob.ZombieVillagerData;
import joymaster.epicfight.capabilities.entity.mob.ZombifiedPiglinData;
import joymaster.epicfight.capabilities.entity.player.ServerPlayerData;
import joymaster.epicfight.client.capabilites.entity.ClientPlayerData;
import joymaster.epicfight.client.capabilites.entity.RemoteClientPlayerData;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ProviderEntity implements ICapabilityProvider {
	private static final Map<Class<? extends Entity>, Supplier<CapabilityEntity<?>>> capabilityMap =
			new HashMap<Class<? extends Entity>, Supplier<CapabilityEntity<?>>> ();
	
	public static void makeMap() {
		capabilityMap.put(EntityPlayerMP.class, ServerPlayerData::new);
		capabilityMap.put(EntityZombie.class, ZombieData<EntityZombie>::new);
		capabilityMap.put(EntityCreeper.class, CreeperData::new);
		capabilityMap.put(EntityEnderman.class, EndermanData::new);
		capabilityMap.put(EntitySkeleton.class, SkeletonData<EntitySkeleton>::new);
		capabilityMap.put(EntityWitherSkeleton.class, WitherSkeletonData::new);
		capabilityMap.put(EntityStray.class, StrayData::new);
		capabilityMap.put(EntityPigZombie.class, ZombifiedPiglinData::new);
		capabilityMap.put(EntityZombieVillager.class, ZombieVillagerData::new);
		capabilityMap.put(EntityHusk.class, ZombieData<EntityHusk>::new);
		capabilityMap.put(EntitySpider.class, SpiderData::new);
		capabilityMap.put(EntityCaveSpider.class, CaveSpiderData::new);
		capabilityMap.put(EntityIronGolem.class, IronGolemData::new);
		capabilityMap.put(EntityVindicator.class, VindicatorData::new);
		capabilityMap.put(EntityEvoker.class, EvokerData::new);
		capabilityMap.put(EntityWitch.class, WitchData::new);
		capabilityMap.put(EntityVex.class, VexData::new);
	}
	
	public static void makeMapClient() {
		capabilityMap.put(EntityOtherPlayerMP.class, RemoteClientPlayerData<EntityOtherPlayerMP>::new);
		capabilityMap.put(EntityPlayerSP.class, ClientPlayerData::new);
	}
	
	private CapabilityEntity<?> capability;
	
	public ProviderEntity(Entity entity) {
		if(capabilityMap.containsKey(entity.getClass())) {
			capability = capabilityMap.get(entity.getClass()).get();
		}
	}
	
	public boolean hasCapability() {
		return capability != null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ModCapabilities.CAPABILITY_ENTITY && this.capability != null ? true : false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == ModCapabilities.CAPABILITY_ENTITY && this.capability != null) {
			return (T) this.capability;
		}
		return null;
	}
}