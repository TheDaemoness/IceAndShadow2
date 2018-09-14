package iceandshadow2.ias;

import net.minecraft.util.DamageSource;

public class IaSDamageSource extends DamageSource {

	protected float hungerDesat;

	public IaSDamageSource(String unlocalizedName, float hungerDmg, boolean bypass) {
		super(unlocalizedName);
		hungerDesat = hungerDmg;
		if (bypass)
			setDamageBypassesArmor();
	}

}
