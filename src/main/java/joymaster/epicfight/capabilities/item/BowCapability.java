package joymaster.epicfight.capabilities.item;

import joymaster.epicfight.gamedata.Animations;
import net.minecraft.item.Item;

public class BowCapability extends RangedWeaponCapability {
	public BowCapability(Item item) {
		super(item, null, Animations.BIPED_BOW_AIM, Animations.BIPED_BOW_REBOUND);
	}

	@Override
	public final HandProperty getHandProperty() {
		return HandProperty.TWO_HANDED;
	}
}