package joymaster.epicfight.client.renderer.layer;

import joymaster.epicfight.capabilities.entity.LivingData;
import joymaster.epicfight.utils.math.VisibleMatrix4f;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class Layer<E extends EntityLivingBase, T extends LivingData<E>> {
	public abstract void renderLayer(T entitydata, E entityliving, VisibleMatrix4f[] poses, float partialTicks);
}