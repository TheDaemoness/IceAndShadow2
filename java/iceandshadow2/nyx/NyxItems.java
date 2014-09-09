package iceandshadow2.nyx;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.nyx.items.*;

public class NyxItems {
	public static Item teleportCrystal, seedObsidian;
	
	public static void init() {
		teleportCrystal = new NyxTeleportCrystal("TeleportCrystal").register();
		seedObsidian = new NyxSeedObsidian("SeedObsidian").register();
	}
	
	public static void setCreativeTabs() {
		teleportCrystal.setCreativeTab(IaSCreativeTabs.misc);
		seedObsidian.setCreativeTab(IaSCreativeTabs.misc);
	}
}
