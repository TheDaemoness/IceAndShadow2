package iceandshadow2.ias;

import net.minecraft.item.ItemBlock;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;

public class IaSCreativeTabs {
	public static IaSCreativeTab blocks, magic, tools, combat, resources, misc;
	
	public static void init() {
		blocks = new IaSCreativeTab("IaSBlocks", NyxBlocks.stone.getItem(null, 0, 0, 0));
		//combat = new IaSCreativeTab("IaSCombat", null);
		//tools = new IaSCreativeTab("IaSTools", null);
		//resources = new IaSCreativeTab("IaSMaterials", null);
		misc = new IaSCreativeTab("IaSMisc", NyxItems.teleportCrystal);
	}
}
