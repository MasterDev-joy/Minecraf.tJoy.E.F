package joymaster.epicfight.capabilities.entity.mob;

import joymaster.epicfight.entity.ai.EntityAIAttackPattern;
import joymaster.epicfight.entity.ai.EntityAIChase;
import joymaster.epicfight.utils.game.IExtendedDamageSource;
import joymaster.epicfight.utils.math.Vec3f;
import joymaster.epicfight.utils.math.VisibleMatrix4f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.EnumDifficulty;

public class CaveSpiderData extends SpiderData<EntityCaveSpider> {
	@Override
	protected void initAI() {
		super.initAI();
        
		this.orgEntity.tasks.addTask(1, new EntityAIChase(this, this.orgEntity, 1.0D, false));
		this.orgEntity.tasks.addTask(1, new EntityAIAttackPattern(this, this.orgEntity, 0.0D, 2.0D, true, MobAttackPatterns.SPIDER_PATTERN));
	}
	
	@Override
	public boolean hurtEntity(Entity hitTarget, EnumHand handIn, IExtendedDamageSource source, float amount) {
		boolean succed = super.hurtEntity(hitTarget, handIn, source, amount);
		
		if (succed && hitTarget instanceof EntityLivingBase) {
			int i = 0;
            if (this.orgEntity.world.getDifficulty() == EnumDifficulty.NORMAL) {
                i = 7;
            } else if (this.orgEntity.world.getDifficulty() == EnumDifficulty.HARD) {
                i = 15;
            }
            
            if (i > 0) {
                ((EntityLivingBase)hitTarget).addPotionEffect(new PotionEffect(MobEffects.POISON, i * 20, 0));
            }
        }
		
		return succed;
	}
	
	@Override
	public VisibleMatrix4f getModelMatrix(float partialTicks) {
		VisibleMatrix4f mat = super.getModelMatrix(partialTicks);
		return VisibleMatrix4f.scale(new Vec3f(0.7F, 0.7F, 0.7F), mat, mat);
	}
}