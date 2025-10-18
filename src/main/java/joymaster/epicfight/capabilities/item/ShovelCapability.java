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

public class ShovelCapability extends MaterialItemCapability {
	public ShovelCapability(Item item) {
		super(item, WeaponCategory.SHOVEL);
	}
	
	@Override
	protected void registerAttribute() {
		double impact = this.material.getHarvestLevel() * 0.5D + 1.5D;
		this.addStyleAttibute(WieldStyle.ONE_HAND, Pair.of(ModAttributes.IMPACT, ModAttributes.getImpactModifier(impact)));
	}
	
	@Override
	public List<StaticAnimation> getAutoAttckMotion(PlayerData<?> playerdata) {
		return AxeCapability.axeAttackMotions;
	}

	@Override
	public SoundEvent getHitSound() {
		return Sounds.BLUNT_HIT;
	}

	@Override
	public Collider getWeaponCollider() {
		return Colliders.tools;
	}
}