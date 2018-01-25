package iceandshadow2.nyx.toolmats;

import iceandshadow2.ias.items.tools.IaSArmorMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class NyxArmorMaterialCortra extends IaSArmorMaterial {

	public NyxArmorMaterialCortra() {
		super("Cortra", NyxItems.cortraIngot, 33, 3, 7, 5, 3, 0);
	}

	@Override
	public float onHurt(EntityLivingBase wearer, DamageSource dmg, float amount, double coverage, boolean major) {
		if (dmg.isMagicDamage()) {
			amount = (float) Math.max(0, amount - (coverage / (major ? 2 : 3)));
		}
		return amount;
	}
}
