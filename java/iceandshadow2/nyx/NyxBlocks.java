package iceandshadow2.nyx;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import iceandshadow2.compat.IaSRegistration;
import iceandshadow2.ias.bases.IaSBlockSingle;
import iceandshadow2.util.EnumIaSModule;

public class NyxBlocks {
	public static Block test;
	
	public static void init() {
		test = IaSRegistration.registerBlock(new IaSBlockSingle(EnumIaSModule.MODULE_NYX,"Test",Material.portal));
	}
	
	public static void setCreativeTabs() {
		test.setCreativeTab(CreativeTabs.tabMisc);
	}
}
