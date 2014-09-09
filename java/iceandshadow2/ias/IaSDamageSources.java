package iceandshadow2.ias;

import iceandshadow2.ias.IaSDamageSource;
import net.minecraft.util.DamageSource;

public class IaSDamageSources {
	public static DamageSource
		dmgStone,
		dmgVines,
		dmgPoisonwood,
		dmgStalagmite,
		dmgGatestone,
		dmgXpAltar,
		dmgFreezing;
	
	public static void init() {
		dmgStone = new IaSDamageSource("IceAndShadow.nyxStoneDamage",0.5F,true);
		dmgVines = new IaSDamageSource("IceAndShadow.nyxVinesDamage",0.0F,false);
		dmgPoisonwood = new IaSDamageSource("IceAndShadow.nyxPoisonwoodDamage",0.1F,true);
		dmgStalagmite = new IaSDamageSource("IceAndShadow.nyxStalagmiteDamage",0.3F,false);
		dmgGatestone = new IaSDamageSource("IceAndShadow.nyxGatestoneDamage",0.9F,true);
		dmgXpAltar = new IaSDamageSource("IceAndShadow.nyxXpAltarDamage",0.1F,true).setMagicDamage();
		dmgFreezing = new IaSDamageSource("IceAndShadow.nyxFreezingDamage",0.0F,true);
	}
}
