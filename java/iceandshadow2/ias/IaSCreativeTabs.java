package iceandshadow2.ias;

import iceandshadow2.util.EnumIaSModule;

public class IaSCreativeTabs {
	public static IaSCreativeTab blocks, magic, tools, combat, resources, misc;
	
	public static void init() {
		blocks = new IaSCreativeTab("IaSBlocks", new IaSFakeBlock(EnumIaSModule.NYX,"Stone").getItem());
		combat = new IaSCreativeTab("IaSCombat", new IaSFakeItem(EnumIaSModule.NYX,"FrostLongBow"));
		tools = new IaSCreativeTab("IaSTools", new IaSFakeItem(EnumIaSModule.IAS,"CtIconTools"));
		resources = new IaSCreativeTab("IaSMaterials", new IaSFakeItem(EnumIaSModule.NYX,"EchirIngot"));
		misc = new IaSCreativeTab("IaSMisc", new IaSFakeItem(EnumIaSModule.NYX,"TeleportCrystal"));
	}
}
