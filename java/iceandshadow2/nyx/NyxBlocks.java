package iceandshadow2.nyx;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.nyx.blocks.*;

public class NyxBlocks {
	public static Block stone, cryingObsidian, unstableIce;
	
	public static void init() {
		stone = new NyxBlockStone("Stone").register();
		unstableIce = new NyxBlockUnstableIce("UnstableIce").register();
		cryingObsidian = new NyxBlockCryingObsidian("CryingObsidian").register();
	}
	
	public static void setCreativeTabs() {
		stone.setCreativeTab(IaSCreativeTabs.blocks);
		cryingObsidian.setCreativeTab(IaSCreativeTabs.blocks);
		unstableIce.setCreativeTab(IaSCreativeTabs.blocks);
	}
}
