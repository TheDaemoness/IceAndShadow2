package iceandshadow2.nyx.toolmats;

import iceandshadow2.ias.items.tools.IaSArmorMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.item.Item;

public class NyxArmorMaterialNavistra extends IaSArmorMaterial {
	
	public NyxArmorMaterialNavistra() {
		super("Navistra", NyxItems.navistraShard, 11, 4, 9, 7, 3, 8);
	}

	@Override
	public boolean isBreakable() {
		return false;
	}
}
