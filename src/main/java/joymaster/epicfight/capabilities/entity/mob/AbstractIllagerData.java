package joymaster.epicfight.capabilities.entity.mob;

import joymaster.epicfight.animation.LivingMotion;
import joymaster.epicfight.animation.types.StaticAnimation;
import joymaster.epicfight.client.animation.AnimatorClient;
import joymaster.epicfight.gamedata.Animations;
import joymaster.epicfight.gamedata.Models;
import joymaster.epicfight.model.Model;
import joymaster.epicfight.utils.game.IExtendedDamageSource;
import net.minecraft.entity.monster.AbstractIllager;

public abstract class AbstractIllagerData<T extends AbstractIllager> extends BipedMobData<T> {
	public AbstractIllagerData(Faction faction) {
		super(faction);
	}

	@Override
	protected void initAnimator(AnimatorClient animatorClient) {
		super.initAnimator(animatorClient);
		animatorClient.addLivingAnimation(LivingMotion.IDLE, Animations.ILLAGER_IDLE);
		animatorClient.addLivingAnimation(LivingMotion.WALKING, Animations.ILLAGER_WALK);
		animatorClient.addLivingAnimation(LivingMotion.DEATH, Animations.BIPED_DEATH);
	}

	@Override
	public StaticAnimation getHitAnimation(IExtendedDamageSource.StunType stunType) {
		if (stunType == IExtendedDamageSource.StunType.LONG)
			return Animations.BIPED_HIT_LONG;
		else
			return Animations.BIPED_HIT_SHORT;
	}

	@Override
	public <M extends Model> M getEntityModel(Models<M> modelDB) {
		return modelDB.ENTITY_ILLAGER;
	}
}