package iceandshadow2.ias.items.tools;

import cpw.mods.fml.common.registry.GameRegistry;
import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.nyx.items.materials.*;

public class IaSTools {
		public static IaSItemTool tools[];
		public static IaSItemTool axe, pickaxe, spade, sword;
		public static IaSItemThrowingKnife knife;
		
	public static void init() {
		tools = new IaSItemTool[EnumIaSToolClass.values().length];
		for(EnumIaSToolClass c : EnumIaSToolClass.values()) {
			if(c == EnumIaSToolClass.KNIFE)
				tools[c.getClassId()] = new IaSItemThrowingKnife();
			else
				tools[c.getClassId()] = new IaSItemTool(c);
			GameRegistry.registerItem(tools[c.getClassId()], "ias"+c.toString());
		}
		
		axe = tools[EnumIaSToolClass.AXE.getClassId()];
		pickaxe = tools[EnumIaSToolClass.PICKAXE.getClassId()];
		spade = tools[EnumIaSToolClass.SPADE.getClassId()];
		sword = tools[EnumIaSToolClass.SWORD.getClassId()];
		knife = (IaSItemThrowingKnife)tools[EnumIaSToolClass.KNIFE.getClassId()];

		IaSRegistry.addToolMaterial(new NyxMaterialDevora());
		IaSRegistry.addToolMaterial(new NyxMaterialCortra());
		IaSRegistry.addToolMaterial(new NyxMaterialNavistra());
	}
}
