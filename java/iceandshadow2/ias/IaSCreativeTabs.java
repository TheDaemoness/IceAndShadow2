package iceandshadow2.ias;

public class IaSCreativeTabs {
	public static IaSCreativeTab blocks, magic, tools, combat, resources, misc;
	
	public static void init() {
		blocks = new IaSCreativeTab("Nyxian Blocks", null);
		magic = new IaSCreativeTab("Nyxian Consumables", null);
		combat = new IaSCreativeTab("Nyxian Combat", null);
		tools = new IaSCreativeTab("Nyxian Tools", null);
		resources = new IaSCreativeTab("Nyxian Resources", null);
		misc = new IaSCreativeTab("Nyxian Odds and Ends", null);
	}
}
