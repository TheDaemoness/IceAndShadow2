package iceandshadow2.ias;

import net.minecraft.util.DamageSource;

public class IaSDamageSources {
	public static DamageSource dmgStone, dmgVines, dmgPoisonwood, dmgStalagmite, dmgGatestone, dmgXpAltar, dmgFreezing,
			dmgDrain;

	public static void init() {
		IaSDamageSources.dmgStone = new IaSDamageSource("IceAndShadow2.nyxStoneDamage", 0.5F, true);
		IaSDamageSources.dmgVines = new IaSDamageSource("IceAndShadow2.nyxVinesDamage", 0.0F, false);
		IaSDamageSources.dmgPoisonwood = new IaSDamageSource("IceAndShadow2.nyxPoisonwoodDamage", 0.1F, true);
		IaSDamageSources.dmgStalagmite = new IaSDamageSource("IceAndShadow2.nyxStalagmiteDamage", 0.3F, false);
		IaSDamageSources.dmgGatestone = new IaSDamageSource("IceAndShadow2.nyxGatestoneDamage", 0.9F, true)
				.setMagicDamage();
		IaSDamageSources.dmgXpAltar = new IaSDamageSource("IceAndShadow2.nyxXpAltarDamage", 0.1F, true).setMagicDamage()
				.setDamageIsAbsolute();
		IaSDamageSources.dmgFreezing = new IaSDamageSource("IceAndShadow2.nyxFreezingDamage", 0.0F, true);
		IaSDamageSources.dmgDrain = new IaSDamageSource("IceAndShadow2.nyxDrainDamage", 0.0F, true)
				.setDamageBypassesArmor().setDamageIsAbsolute();
	}
}
