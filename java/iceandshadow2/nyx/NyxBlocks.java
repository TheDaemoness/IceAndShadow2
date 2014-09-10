package iceandshadow2.nyx;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.nyx.blocks.*;

public class NyxBlocks {
	public static Block stone, cryingObsidian, unstableIce, permafrost;
	
	public static void init() {
		stone = new NyxBlockStone("Stone").register().setCreativeTab(IaSCreativeTabs.blocks);
		unstableIce = new NyxBlockUnstableIce("UnstableIce").register().setCreativeTab(IaSCreativeTabs.blocks);
		permafrost = new NyxBlockPermafrost("Permafrost").register().setCreativeTab(IaSCreativeTabs.blocks);
		cryingObsidian = new NyxBlockCryingObsidian("CryingObsidian").register().setCreativeTab(IaSCreativeTabs.blocks);
	}
}
