package joymaster.epicfight.entity.ai;

import java.util.List;

import joymaster.epicfight.animation.types.attack.AttackAnimation;
import joymaster.epicfight.capabilities.entity.LivingData.EntityState;
import joymaster.epicfight.capabilities.entity.mob.BipedMobData;
import net.minecraft.entity.monster.EntityMob;

public class EntityAIPatternSuccessive extends EntityAIAttackPattern
{
	public EntityAIPatternSuccessive(BipedMobData mobdata, EntityMob attacker, double patternMinRange, boolean affectHorizon,
			double patternMaxRange, boolean successive, List<AttackAnimation> pattern)
	{
		super(mobdata, attacker, patternMinRange, patternMaxRange, affectHorizon, pattern);
	}
	
	@Override
	protected boolean canExecuteAttack()
    {
    	return super.canExecuteAttack() || (mobdata.getEntityState() == EntityState.POST_DELAY);
    }
	
	@Override
    public void resetTask()
    {
		this.patternIndex = 0;
    }
}