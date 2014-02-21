package iceandshadow2.nyx;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import iceandshadow2.ias.bases.IaSItemSingle;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;

public class NyxItems {
	public static Item test;
	
	public static void init() {
		test = IaSRegistration.registerItem(new IaSItemSingle(EnumIaSModule.MODULE_NYX,"Test"));
	}
	
	public static void setCreativeTabs() {
		test.setCreativeTab(CreativeTabs.tabMisc);
	}
}
