package joymaster.epicfight.capabilities.entity;

import joymaster.epicfight.utils.game.IndirectDamageSourceExtended;
import net.minecraft.entity.Entity;

public interface IRangedAttackMobCapability {
	public abstract IndirectDamageSourceExtended getRangedDamageSource(Entity damageCarrier);
}