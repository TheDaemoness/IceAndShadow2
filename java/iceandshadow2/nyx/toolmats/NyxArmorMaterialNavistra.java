package iceandshadow2.nyx.toolmats;

import iceandshadow2.ias.items.tools.IaSArmorMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class NyxArmorMaterialNavistra extends IaSArmorMaterial {

	public NyxArmorMaterialNavistra() {
		super("Navistra", NyxItems.navistraShard, 11, 4, 9, 7, 3, 8);
	}

	@Override
	public int getRenderPasses() {
		return 1;
	}

	@Override
	public boolean isBreakable() {
		return false;
	}

	@Override
	public float onHurt(EntityLivingBase wearer, DamageSource dmg, float amount, double coverage, boolean major) {
		if (dmg.isProjectile())
			amount = (float) Math.max(0, amount - (coverage / (major ? 1 : 2)));
		return amount;
	}

	@Override
	public void onTick(EntityLivingBase wearer, double coverage, boolean major) {
		wearer.motionX *= 1 - 0.01 * coverage;
		wearer.motionZ *= 1 - 0.01 * coverage;
		if (wearer.motionY > 0 && wearer.isOnLadder())
			wearer.motionY *= 1 - 0.01 * coverage;
		if (wearer.isSprinting() && major)
			wearer.setSprinting(false);
		super.onTick(wearer, coverage, major);
	}
}
