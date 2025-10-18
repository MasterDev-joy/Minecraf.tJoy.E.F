package joymaster.epicfight.client.animation;

import joymaster.epicfight.collada.AnimationDataExtractor;
import joymaster.epicfight.animation.types.DynamicAnimation;
import joymaster.epicfight.animation.types.MixLinkAnimation;
import joymaster.epicfight.capabilities.entity.LivingData;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MixLayer extends BaseLayer
{
	protected String[] maskedJointNames;
	protected boolean linkEndPhase;
	protected MixLinkAnimation mixLinkAnimation;
	
	public MixLayer(DynamicAnimation animation)
	{
		super(animation);
		this.linkEndPhase = false;
		this.maskedJointNames = new String[0];
		this.mixLinkAnimation = new MixLinkAnimation();
	}
	
	public void setMixLinkAnimation(LivingData<?> entitydata, float timeModifier)
	{
		AnimationDataExtractor.getMixLinkAnimation(timeModifier + entitydata.getClientAnimator().baseLayer.animationPlayer.getPlay().getConvertTime(),
				this.animationPlayer.getCurrentPose(entitydata, Minecraft.getMinecraft().getRenderPartialTicks()), this.mixLinkAnimation);
	}
	
	public void setJointMask(String... maskedJoint)
	{
		this.maskedJointNames = maskedJoint;
	}
	
	public boolean jointMasked(String s)
	{
		for(String str : this.maskedJointNames)
		{
			if(s.equals(str))
				return true;
		}
		return false;
	}
}