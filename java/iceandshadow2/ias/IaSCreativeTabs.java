package iceandshadow2.ias;

import iceandshadow2.EnumIaSModule;

public class IaSCreativeTabs {
	public static IaSCreativeTab blocks, magic, tools, resources, misc;

	public static void init() {
		IaSCreativeTabs.blocks = new IaSCreativeTab("IaSBlocks",
				new IaSFakeBlock(EnumIaSModule.NYX, "Stone").getItem());
		IaSCreativeTabs.tools = new IaSCreativeTab("IaSTools", new IaSFakeItem(EnumIaSModule.NYX, "FrostShortBow"));
		IaSCreativeTabs.resources = new IaSCreativeTab("IaSMaterials",
				new IaSFakeItem(EnumIaSModule.NYX, "EchirIngot"));
		IaSCreativeTabs.misc = new IaSCreativeTab("IaSMisc", new IaSFakeItem(EnumIaSModule.NYX, "TeleportCrystal"));
	}
}
