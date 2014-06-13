package iceandshadow2.nyx;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;

public class NyxBlocks {
	public static Block stone;
	
	public static void init() {
		stone = new IaSBaseBlockSingle(EnumIaSModule.NYX,"Stone",Material.rock).register();
	}
	
	public static void setCreativeTabs() {
		stone.setCreativeTab(CreativeTabs.tabBlock);
	}
}
