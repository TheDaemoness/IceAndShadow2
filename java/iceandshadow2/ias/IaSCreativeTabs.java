package iceandshadow2.ias;

import iceandshadow2.EnumIaSModule;

public class IaSCreativeTabs {
	public static IaSCreativeTab blocks, magic, tools, combat, resources, misc;

	public static void init() {
		IaSCreativeTabs.blocks = new IaSCreativeTab("IaSBlocks", new IaSFakeBlock(
				EnumIaSModule.NYX, "Stone").getItem());
		IaSCreativeTabs.combat = new IaSCreativeTab("IaSCombat", new IaSFakeItem(
				EnumIaSModule.NYX, "FrostLongBow"));
		IaSCreativeTabs.tools = new IaSCreativeTab("IaSTools", new IaSFakeItem(
				EnumIaSModule.IAS, "CtIconTools"));
		IaSCreativeTabs.resources = new IaSCreativeTab("IaSMaterials", new IaSFakeItem(
				EnumIaSModule.NYX, "EchirIngot"));
		IaSCreativeTabs.misc = new IaSCreativeTab("IaSMisc", new IaSFakeItem(EnumIaSModule.NYX,
				"TeleportCrystal"));
	}
}
