package iceandshadow2.nyx;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import iceandshadow2.nyx.blocks.*;

public class NyxBlocks {
	public static Block stone, cryingObsidian;
	
	public static void init() {
		stone = new NyxBlockStone("Stone").register();
		cryingObsidian = new NyxBlockCryingObsidian("CryingObsidian").register();
	}
	
	public static void setCreativeTabs() {
		stone.setCreativeTab(CreativeTabs.tabBlock);
		cryingObsidian.setCreativeTab(CreativeTabs.tabBlock);
	}
}
