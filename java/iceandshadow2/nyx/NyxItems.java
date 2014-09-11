package iceandshadow2.nyx;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.nyx.items.*;

public class NyxItems {
	public static Item teleportCrystal, seedObsidian;
	
	public static Item echirIngot, nifelhiumDust, navistraShard, devoraChunk, devoraChunkSmall, cortraDust, exousium;
	
	public static void init() {
		teleportCrystal = new NyxTeleportCrystal("TeleportCrystal").register().setCreativeTab(IaSCreativeTabs.misc);
		seedObsidian = new NyxSeedObsidian("SeedObsidian").register().setCreativeTab(IaSCreativeTabs.misc);
	}
}
