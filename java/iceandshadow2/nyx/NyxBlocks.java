package iceandshadow2.nyx;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import iceandshadow2.ias.bases.IaSBlockSingle;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;

public class NyxBlocks {
	public static Block stone;
	
	public static void init() {
		stone = new IaSBlockSingle(EnumIaSModule.NYX,"Stone",Material.rock).register();
	}
	
	public static void setCreativeTabs() {
		stone.setCreativeTab(CreativeTabs.tabBlock);
	}
}
