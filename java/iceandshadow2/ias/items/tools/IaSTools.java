package iceandshadow2.ias.items.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemArmor;
import cpw.mods.fml.common.registry.GameRegistry;
import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.nyx.items.materials.*;

public class IaSTools {
		public static IaSItemTool tools[];
		public static IaSItemTool axe, pickaxe, spade, sword;
		public static IaSItemThrowingKnife knife;
		
		public static IaSItemArmor
			armorEchir[], armorNavistra[], armorCortra[], armorCorpseskin[], armorSpiderSilk[];
		
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
		
		armorEchir = new IaSItemArmor[4];
		initArmor(armorEchir, IaSItemArmor.MATERIAL_ECHIR, "IceAndShadow2:textures/armor/echir");
		armorCortra = new IaSItemArmor[4];
		initArmor(armorCortra, IaSItemArmor.MATERIAL_CORTRA, "IceAndShadow2:textures/armor/cortra");
		armorNavistra = new IaSItemArmor[4];
		initArmor(armorNavistra, IaSItemArmor.MATERIAL_NAVISTRA, "IceAndShadow2:textures/armor/navistra");
	}
	
	protected static void initArmor(IaSItemArmor[] arm, ItemArmor.ArmorMaterial mat, String tex) {
		for(int i = 0; i < 4; ++i) {
			arm[i] = new IaSItemArmor(mat, 3, i, tex);
			GameRegistry.registerItem(arm[i],arm[i].getModName());
			arm[i].setCreativeTab(IaSCreativeTabs.combat);
		}
		
	}
}
