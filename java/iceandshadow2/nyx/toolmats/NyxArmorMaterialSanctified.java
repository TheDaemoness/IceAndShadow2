package iceandshadow2.nyx.toolmats;

import iceandshadow2.ias.items.tools.IaSArmorMaterial;
import iceandshadow2.ias.util.IaSEntityHelper;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.DamageSource;

public class NyxArmorMaterialSanctified extends IaSArmorMaterial {

	public NyxArmorMaterialSanctified() {
		super("Sanctified", NyxItems.alabasterShard, 22, 3, 7, 5, 2, 28);
	}

	@Override
	public EnumRarity getRarity() {
		return EnumRarity.uncommon;
	}

	@Override
	public float onHurt(EntityLivingBase wearer, DamageSource dmg, float amount, double coverage, boolean major) {
		if (dmg.getEntity() != null) {
			dmg.getEntity().attackEntityFrom(DamageSource.causeIndirectMagicDamage(wearer, wearer),
					(float) (coverage / 2 + IaSEntityHelper.getMagicLevel(wearer) * coverage / 30));
		}
		return amount;
	}
}
