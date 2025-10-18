package joymaster.epicfight.entity.ai;

import java.util.List;

import joymaster.epicfight.animation.types.attack.AttackAnimation;
import joymaster.epicfight.capabilities.entity.MobData;
import net.minecraft.entity.EntityCreature;

public class EntityAIPatternWithChance extends EntityAIAttackPattern
{
	protected final float executeChance;
	
	public EntityAIPatternWithChance(MobData mobdata, EntityCreature attacker, double patternMinRange, double patternMaxRange, float chance, boolean affectHorizon,
			List<AttackAnimation> pattern)
	{
		super(mobdata, attacker, patternMinRange, patternMaxRange, affectHorizon, pattern);
		this.executeChance = chance;
	}
	
	@Override
    public boolean shouldExecute()
    {
		return super.shouldExecute() && attacker.getRNG().nextFloat() < executeChance;
    }
}