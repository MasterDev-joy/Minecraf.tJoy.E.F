package joymaster.epicfight.capabilities.entity.mob;

import joymaster.epicfight.capabilities.entity.DataKeys;
import joymaster.epicfight.capabilities.entity.IRangedAttackMobCapability;
import joymaster.epicfight.client.animation.AnimatorClient;
import joymaster.epicfight.entity.ai.attribute.ModAttributes;
import joymaster.epicfight.gamedata.Models;
import joymaster.epicfight.model.Model;
import joymaster.epicfight.utils.game.IExtendedDamageSource;
import joymaster.epicfight.utils.game.IndirectDamageSourceExtended;
import joymaster.epicfight.utils.math.Vec3f;
import joymaster.epicfight.utils.math.VisibleMatrix4f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.AbstractSkeleton;

public class SkeletonData<T extends AbstractSkeleton> extends BipedMobData<T> implements IRangedAttackMobCapability {
	public SkeletonData() {
		super(Faction.UNDEAD);
	}
	
	public SkeletonData(Faction faction) {
		super(faction);
	}
	
	@Override
	public void onEntityJoinWorld(T entityIn) {
		super.onEntityJoinWorld(entityIn);
		this.orgEntity.getDataManager().register(DataKeys.STUN_ARMOR, Float.valueOf(0));
	}
	
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.registerIfAbsent(ModAttributes.MAX_STUN_ARMOR);
	}
	
	@Override
	protected void initAnimator(AnimatorClient animatorClient) {
		super.commonBipedCreatureAnimatorInit(animatorClient);
		super.initAnimator(animatorClient);
	}
	
	@Override
	public void updateMotion() {
		super.commonCreatureUpdateMotion();
	}
	
	@Override
	public VisibleMatrix4f getModelMatrix(float partialTicks) {
		float posY = 0;
		
		if (orgEntity.getRidingEntity() != null) {
			posY = 0.45F;
		}
		
		VisibleMatrix4f mat = super.getModelMatrix(partialTicks);
		return VisibleMatrix4f.translate(new Vec3f(0, posY, 0), mat, mat);
	}
	
	@Override
	public <M extends Model> M getEntityModel(Models<M> modelDB) {
		return modelDB.ENTITY_SKELETON;
	}
	
	@Override
	public IndirectDamageSourceExtended getRangedDamageSource(Entity damageCarrier) {
		IndirectDamageSourceExtended source = new IndirectDamageSourceExtended("arrow", this.orgEntity, damageCarrier, IExtendedDamageSource.StunType.SHORT);
		source.setImpact(1.0F);
		
		return source;
	}
}