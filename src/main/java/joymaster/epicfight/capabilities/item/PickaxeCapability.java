package joymaster.epicfight.capabilities.item;

import java.util.List;

import joymaster.epicfight.animation.types.StaticAnimation;
import joymaster.epicfight.gamedata.Colliders;
import joymaster.epicfight.gamedata.Sounds;
import joymaster.epicfight.physics.Collider;
import joymaster.epicfight.utils.game.Pair;
import joymaster.epicfight.capabilities.entity.player.PlayerData;
import joymaster.epicfight.entity.ai.attribute.ModAttributes;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;

public class PickaxeCapability extends MaterialItemCapability {
	public PickaxeCapability(Item item) {
		super(item, WeaponCategory.PICKAXE);
	}
	
	@Override
	protected void registerAttribute() {
		this.addStyleAttibute(WieldStyle.ONE_HAND, Pair.of(ModAttributes.IMPACT, ModAttributes.getImpactModifier(-0.4D + 0.1D * this.material.getHarvestLevel())));
		this.addStyleAttibute(WieldStyle.ONE_HAND, Pair.of(ModAttributes.ARMOR_NEGATION, ModAttributes.getArmorNegationModifier(6.0D * this.material.getHarvestLevel())));
	}
	
	@Override
	public List<StaticAnimation> getAutoAttckMotion(PlayerData<?> playerdata) {
		return AxeCapability.axeAttackMotions;
	}

	@Override
	public SoundEvent getHitSound() {
		return Sounds.BLADE_HIT;
	}

	@Override
	public Collider getWeaponCollider() {
		return Colliders.tools;
	}
}