package iceandshadow2.util;

import iceandshadow2.ias.items.tools.IaSArmorMaterial;
import iceandshadow2.ias.items.tools.IaSItemArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ArmorMaterialInstance implements Comparable {
	ArmorMaterialInstance() {
		//Probably redundant, but C++ paranoia persists.
		coverage = 0;
		slotsfilled = new boolean[4];
		java.util.Arrays.fill(slotsfilled, false);
	}
	public IaSArmorMaterial material;
	public double coverage;
	public boolean[] slotsfilled;
	
	@Override
	public int compareTo(Object o) {
		return o==null?-1:-Double.compare(coverage, ((ArmorMaterialInstance)o).coverage);
	}
	
	public static ArmorMaterialInstance[] getEquipmentData(EntityLivingBase elb) {
		ArmorMaterialInstance materialMap[] = new ArmorMaterialInstance[4];
		java.util.Arrays.fill(materialMap, new ArmorMaterialInstance());
		boolean fullArmor = true;
		for(int i = 1; i <= 4; ++i) {
			final ItemStack armor = elb.getEquipmentInSlot(i);
			if(armor == null || armor.getItem() == null) {
				fullArmor = false;
				continue;
			}
			final IaSArmorMaterial mat = IaSArmorMaterial.getArmorMaterial(armor);
			if(mat == null)
				continue;
			int index = 0;
			while(materialMap[index].material != null && materialMap[index].material != mat) {
				++index;
			}
			materialMap[index].material = mat;
			final double maxDmg = Math.max(1, armor.getMaxDamage());
			final double curDmg = Math.max(0, armor.getItemDamage());
			materialMap[index].coverage += mat.getCoverage(i, 1-curDmg/maxDmg);
			materialMap[index].slotsfilled[i-1] = true;
		}
		java.util.Arrays.sort(materialMap);
		return materialMap;
	}
};
