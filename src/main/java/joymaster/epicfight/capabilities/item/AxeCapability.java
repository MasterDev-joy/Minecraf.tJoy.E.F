package joymaster.epicfight.capabilities.item;

import java.util.ArrayList;
import java.util.List;

import joymaster.epicfight.animation.types.StaticAnimation;
import joymaster.epicfight.gamedata.Animations;
import joymaster.epicfight.gamedata.Colliders;
import joymaster.epicfight.gamedata.Skills;
import joymaster.epicfight.gamedata.Sounds;
import joymaster.epicfight.physics.Collider;
import joymaster.epicfight.skill.Skill;
import joymaster.epicfight.utils.game.Pair;
import joymaster.epicfight.capabilities.entity.player.PlayerData;
import joymaster.epicfight.entity.ai.attribute.ModAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.SoundEvent;

public class AxeCapability extends MaterialItemCapability {
	protected static List<StaticAnimation> axeAttackMotions = new ArrayList<StaticAnimation> ();
	private Skill specialAttack;
	
	static {
		axeAttackMotions = new ArrayList<StaticAnimation> ();
		axeAttackMotions.add(Animations.AXE_AUTO1);
		axeAttackMotions.add(Animations.AXE_AUTO2);
		axeAttackMotions.add(Animations.AXE_DASH);
	}
	
	public AxeCapability(Item item) {
		super(item, WeaponCategory.AXE);
		
		specialAttack = material == ToolMaterial.WOOD ? null : Skills.GUILLOTINE_AXE;
	}
	
	@Override
	public List<StaticAnimation> getAutoAttckMotion(PlayerData<?> playerdata) {
		return axeAttackMotions;
	}

	@Override
	public Skill getSpecialAttack(PlayerData<?> playerdata) {
		return specialAttack;
	}
	
	@Override
	protected void registerAttribute() {
		int i = this.material.getHarvestLevel();
		
		if(i != 0) {
			this.addStyleAttibute(WieldStyle.ONE_HAND, Pair.of(ModAttributes.ARMOR_NEGATION, ModAttributes.getArmorNegationModifier(10.0D * i)));
		}
		
		this.addStyleAttibute(WieldStyle.ONE_HAND, Pair.of(ModAttributes.IMPACT, ModAttributes.getImpactModifier(2.0D + 0.5D * i)));
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