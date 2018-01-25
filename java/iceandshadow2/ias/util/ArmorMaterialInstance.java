package iceandshadow2.ias.util;

import iceandshadow2.ias.items.tools.IaSArmorMaterial;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ArmorMaterialInstance implements Comparable {
	public static ArmorMaterialInstance[] getEquipmentData(EntityLivingBase elb) {
		final ArmorMaterialInstance materialMap[] = new ArmorMaterialInstance[4];
		java.util.Arrays.fill(materialMap, new ArmorMaterialInstance());
		for (int i = 1; i <= 4; ++i) {
			final ItemStack armor = elb.getEquipmentInSlot(i);
			if (armor == null || armor.getItem() == null) {
				continue;
			}
			final IaSArmorMaterial mat = IaSArmorMaterial.getArmorMaterial(armor);
			if (mat == null) {
				continue;
			}
			int index = 0;
			while (index < materialMap.length && materialMap[index].material != null
					&& materialMap[index].material != mat) {
				++index;
			}
			if (index >= materialMap.length) {
				continue;
			}
			materialMap[index].material = mat;
			final double maxDmg = Math.max(1, armor.getMaxDamage());
			final double curDmg = Math.max(0, armor.getItemDamage());
			materialMap[index].coverage += mat.getCoverage(i, 1 - curDmg / maxDmg);
			materialMap[index].slotsfilled[i - 1] = true;
		}
		java.util.Arrays.sort(materialMap);
		return materialMap;
	}

	public IaSArmorMaterial material;
	public double coverage;
	public boolean[] slotsfilled;

	ArmorMaterialInstance() {
		// Probably redundant, but C++ paranoia persists.
		coverage = 0;
		slotsfilled = new boolean[4];
		java.util.Arrays.fill(slotsfilled, false);
	}

	@Override
	public int compareTo(Object o) {
		return o == null ? -1 : -Double.compare(coverage, ((ArmorMaterialInstance) o).coverage);
	}
};
