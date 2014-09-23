package iceandshadow2.ias.items.tools;

import cpw.mods.fml.common.registry.GameRegistry;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.nyx.items.materials.*;

public class IaSTools {
		public static IaSItemAxe axe;
		public static IaSItemPickaxe pickaxe;
		public static IaSItemSpade spade;
		public static IaSItemSword sword;
		public static IaSItemThrowingKnife knife;
		
	public static void init() {
		axe = new IaSItemAxe();
		GameRegistry.registerItem(axe, "iasAxe");
		pickaxe = new IaSItemPickaxe();
		GameRegistry.registerItem(pickaxe, "iasPickaxe");
		spade = new IaSItemSpade();
		GameRegistry.registerItem(spade, "iasSpade");
		sword = new IaSItemSword();
		GameRegistry.registerItem(sword, "iasSword");
		knife = new IaSItemThrowingKnife();
		GameRegistry.registerItem(knife, "iasKnife");
		
		//IaSRegistry.addToolMaterial(new NyxMaterialEchir());
	}
}
