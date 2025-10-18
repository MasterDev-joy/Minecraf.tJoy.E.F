package joymaster.epicfight.capabilities.entity.player;

import java.util.UUID;

import joymaster.epicfight.animation.LivingMotion;
import joymaster.epicfight.animation.types.StaticAnimation;
import joymaster.epicfight.capabilities.entity.DataKeys;
import joymaster.epicfight.capabilities.entity.LivingData;
import joymaster.epicfight.client.animation.AnimatorClient;
import joymaster.epicfight.entity.ai.attribute.ModAttributes;
import joymaster.epicfight.entity.event.EntityEventListener;
import joymaster.epicfight.entity.event.PlayerEvent;
import joymaster.epicfight.gamedata.Animations;
import joymaster.epicfight.gamedata.Models;
import joymaster.epicfight.gamedata.Skills;
import joymaster.epicfight.model.Model;
import joymaster.epicfight.skill.SkillContainer;
import joymaster.epicfight.skill.SkillSlot;
import joymaster.epicfight.utils.game.IExtendedDamageSource;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public abstract class PlayerData<T extends EntityPlayer> extends LivingData<T> {
	private static final UUID ACTION_EVENT_UUID = UUID.fromString("e6beeac4-77d2-11eb-9439-0242ac130002");
	protected float yaw;
	protected EntityEventListener eventListeners;
	protected int tickSinceLastAction;
	public SkillContainer[] skills;
	
	public PlayerData() {
		SkillSlot[] slots = SkillSlot.values();
		this.skills = new SkillContainer[SkillSlot.values().length];
		for(SkillSlot slot : slots) {
			this.skills[slot.getIndex()] = new SkillContainer(this);
		}
	}
	
	@Override
	public void onEntityJoinWorld(T entityIn) {
		super.onEntityJoinWorld(entityIn);
		this.eventListeners = new EntityEventListener(this);
		this.skills[SkillSlot.DODGE.getIndex()].setSkill(Skills.ROLL);
		if (!this.orgEntity.getDataManager().entries.containsKey(DataKeys.STUN_ARMOR.getId())) {
			this.orgEntity.getDataManager().register(DataKeys.STUN_ARMOR, Float.valueOf(0.0F));
		}
		this.tickSinceLastAction = 40;
		this.eventListeners.addEventListener(EntityEventListener.Event.ON_ACTION_SERVER_EVENT, PlayerEvent.makeEvent(ACTION_EVENT_UUID, (player) -> {
			player.tickSinceLastAction = 0;
			return false;
		}));
	}
	
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.registerIfAbsent(ModAttributes.MAX_STUN_ARMOR);
		this.registerIfAbsent(ModAttributes.OFFHAND_ATTACK_DAMAGE);
		this.registerIfAbsent(ModAttributes.OFFHAND_ATTACK_SPEED);
	}
	
	@Override
	public void initAnimator(AnimatorClient animatorClient) {
		animatorClient.mixLayer.setJointMask("Root", "Torso");
		animatorClient.addLivingAnimation(LivingMotion.IDLE, Animations.BIPED_IDLE);
		animatorClient.addLivingAnimation(LivingMotion.WALKING, Animations.BIPED_WALK);
		animatorClient.addLivingAnimation(LivingMotion.RUNNING, Animations.BIPED_RUN);
		animatorClient.addLivingAnimation(LivingMotion.SNEAKING, Animations.BIPED_SNEAK);
		animatorClient.addLivingAnimation(LivingMotion.SWIMMING, Animations.BIPED_SWIM);
		animatorClient.addLivingAnimation(LivingMotion.FLOATING, Animations.BIPED_FLOAT);
		animatorClient.addLivingAnimation(LivingMotion.KNEELING, Animations.BIPED_KNEEL);
		animatorClient.addLivingAnimation(LivingMotion.FALL, Animations.BIPED_FALL);
		animatorClient.addLivingAnimation(LivingMotion.MOUNT, Animations.BIPED_MOUNT);
		animatorClient.addLivingAnimation(LivingMotion.FLYING, Animations.BIPED_FLYING);
		animatorClient.addLivingAnimation(LivingMotion.DEATH, Animations.BIPED_DEATH);
		animatorClient.addLivingAnimation(LivingMotion.JUMPING, Animations.BIPED_JUMP);
		animatorClient.addLivingMixAnimation(LivingMotion.BLOCKING, Animations.BIPED_BLOCK);
		animatorClient.addLivingMixAnimation(LivingMotion.AIMING, Animations.BIPED_BOW_AIM);
		animatorClient.addLivingMixAnimation(LivingMotion.RELOADING, Animations.BIPED_CROSSBOW_RELOAD);
		animatorClient.addLivingMixAnimation(LivingMotion.SHOTING, Animations.BIPED_BOW_REBOUND);
		animatorClient.setCurrentLivingMotionsToDefault();
	}
	
	public void changeYaw(float amount) {
		this.yaw = amount;
	}
	
	@Override
	public void updateOnServer() {
		super.updateOnServer();
		this.tickSinceLastAction++;
		
		float stunArmor = this.getStunArmor();
		float maxStunArmor = this.getMaxStunArmor();
		
		if(stunArmor < maxStunArmor && this.tickSinceLastAction > 60) {
			float stunArmorFactor = 1.0F + (stunArmor / maxStunArmor);
			float healthFactor = this.orgEntity.getHealth() / this.orgEntity.getMaxHealth();
			this.setStunArmor(stunArmor + maxStunArmor * 0.01F * healthFactor * stunArmorFactor);
		}
		
		if (maxStunArmor < stunArmor) {
			this.setStunArmor(maxStunArmor);
		}
	}
	
	@Override
	public void update() {
		if(this.orgEntity.getRidingEntity() == null) {
			for(SkillContainer container : this.skills) {
				if(container != null)
					container.update();
			}
		}
		super.update();
	}
	
	public SkillContainer getSkill(SkillSlot slot) {
		return this.skills[slot.getIndex()];
	}
	
	public SkillContainer getSkill(int slotIndex) {
		return this.skills[slotIndex];
	}
	
	public float getAttackSpeed() {
		return (float) this.getAttributeValue(SharedMonsterAttributes.ATTACK_SPEED);
	}
	
	public EntityEventListener getEventListener() {
		return this.eventListeners;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amount) {
		if(super.attackEntityFrom(damageSource, amount)) {
			this.tickSinceLastAction = 0;
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public IExtendedDamageSource getDamageSource(IExtendedDamageSource.StunType stunType, IExtendedDamageSource.DamageType damageType, int id) {
		return IExtendedDamageSource.causePlayerDamage(orgEntity, stunType, damageType, id);
	}
	
	@Override
	public StaticAnimation getHitAnimation(IExtendedDamageSource.StunType stunType) {
		if(orgEntity.getRidingEntity() != null) {
			return Animations.BIPED_HIT_ON_MOUNT;
		} else {
			switch(stunType)
			{
			case LONG:
				return Animations.BIPED_HIT_LONG;
			case SHORT:
				return Animations.BIPED_HIT_SHORT;
			case HOLD:
				return Animations.BIPED_HIT_SHORT;
			default:
				return null;
			}
		}
	}
	
	@Override
	public <M extends Model> M getEntityModel(Models<M> modelDB) {
		return modelDB.ENTITY_BIPED;
	}
}