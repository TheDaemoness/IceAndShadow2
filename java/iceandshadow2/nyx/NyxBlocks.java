package iceandshadow2.nyx;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import iceandshadow2.ias.bases.IaSBlockSingle;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;

public class NyxBlocks {
	public static Block test, stone;
	
	public static void init() {
		test = new IaSBlockSingle(EnumIaSModule.MODULE_NYX,"Test",Material.portal);
		IaSRegistration.register(test);
	}
	
	public static void setCreativeTabs() {
		test.setCreativeTab(CreativeTabs.tabMisc);
	}
}
