package iceandshadow2.nyx;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.items.*;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;

public class NyxItems {
	public static Item test, teleportCrystal;
	
	public static void init() {
		test = new IaSBaseItemSingle(EnumIaSModule.NYX,"Test").register();
		teleportCrystal = new NyxTeleportCrystal("TeleportCrystal").register();
	}
	
	public static void setCreativeTabs() {
		test.setCreativeTab(CreativeTabs.tabMisc);
		teleportCrystal.setCreativeTab(CreativeTabs.tabMisc);
	}
}
