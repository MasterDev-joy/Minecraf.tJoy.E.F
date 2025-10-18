package joymaster.epicfight.animation.types;

import joymaster.epicfight.utils.game.IExtendedDamageSource;
import net.minecraft.util.SoundEvent;

public class AnimationProperty<T> {
	public static final AnimationProperty<Integer> HIT_AT_ONCE = new AnimationProperty<Integer> ();
	public static final AnimationProperty<Float> DAMAGE_MULTIPLIER = new AnimationProperty<Float> ();
	public static final AnimationProperty<Float> DAMAGE_ADDER = new AnimationProperty<Float> ();
	public static final AnimationProperty<Float> ARMOR_NEGATION = new AnimationProperty<Float> ();
	public static final AnimationProperty<Float> IMPACT = new AnimationProperty<Float> ();
	public static final AnimationProperty<IExtendedDamageSource.DamageType> DAMAGE_TYPE = new AnimationProperty<IExtendedDamageSource.DamageType> ();
	public static final AnimationProperty<IExtendedDamageSource.StunType> STUN_TYPE = new AnimationProperty<IExtendedDamageSource.StunType> ();
	public static final AnimationProperty<SoundEvent> SWING_SOUND = new AnimationProperty<SoundEvent> ();
	public static final AnimationProperty<SoundEvent> HIT_SOUND = new AnimationProperty<SoundEvent> ();
	public static final AnimationProperty<Boolean> LOCK_ROTATION = new AnimationProperty<Boolean> ();
	public static final AnimationProperty<Boolean> DIRECTIONAL = new AnimationProperty<Boolean> ();
}