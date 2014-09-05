package iceandshadow2.nyx;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.blocks.*;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;

public class NyxBlocks {
	public static Block stone, cryingObsidian, portal;
	
	public static void init() {
		stone = new NyxBlockStone("Stone").register();
		stone = new NyxBlockPortal("Portal").register();
	}
	
	public static void setCreativeTabs() {
		stone.setCreativeTab(CreativeTabs.tabBlock);
		portal.setCreativeTab(CreativeTabs.tabBlock);
	}
}
