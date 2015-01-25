package iceandshadow2.ias;

import net.minecraft.util.DamageSource;

public class IaSDamageSources {
	public static DamageSource dmgStone, dmgVines, dmgPoisonwood,
			dmgStalagmite, dmgGatestone, dmgXpAltar, dmgFreezing;

	public static void init() {
		dmgStone = new IaSDamageSource("IceAndShadow2.nyxStoneDamage", 0.5F,
				true);
		dmgVines = new IaSDamageSource("IceAndShadow2.nyxVinesDamage", 0.0F,
				false);
		dmgPoisonwood = new IaSDamageSource(
				"IceAndShadow2.nyxPoisonwoodDamage", 0.1F, true);
		dmgStalagmite = new IaSDamageSource(
				"IceAndShadow2.nyxStalagmiteDamage", 0.3F, false);
		dmgGatestone = new IaSDamageSource("IceAndShadow2.nyxGatestoneDamage",
				0.9F, true).setMagicDamage();
		dmgXpAltar = new IaSDamageSource("IceAndShadow2.nyxXpAltarDamage",
				0.1F, true).setMagicDamage().setDamageIsAbsolute();
		dmgFreezing = new IaSDamageSource("IceAndShadow2.nyxFreezingDamage",
				0.0F, true);
	}
}
