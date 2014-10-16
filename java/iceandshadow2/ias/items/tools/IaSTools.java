package iceandshadow2.ias.items.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.registry.GameRegistry;
import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.items.IaSBaseItem;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.items.materials.*;

public class IaSTools {
		public static IaSItemTool tools[];
		public static IaSBaseItem toolsActiveEchir[];
		public static IaSBaseItem armorActiveEchir[];
		public static IaSItemTool axe, pickaxe, spade, sword;
		public static IaSItemThrowingKnife knife;
		
		public static IaSItemArmor
			armorEchir[], armorNavistra[], armorCortra[], armorCorpseskin[], armorSpiderSilk[];
		
	public static void init() {
		tools = new IaSItemTool[EnumIaSToolClass.values().length];
		toolsActiveEchir = new IaSBaseItem[EnumIaSToolClass.values().length];
		armorActiveEchir = new IaSBaseItem[4];
		for(EnumIaSToolClass c : EnumIaSToolClass.values()) {
			if(c == EnumIaSToolClass.KNIFE)
				tools[c.getClassId()] = new IaSItemThrowingKnife();
			else
				tools[c.getClassId()] = new IaSItemTool(c);
			toolsActiveEchir[c.getClassId()] = new IaSItemEchirToolActive("ToolEchir"+c.toString()+"Active",c.getClassId());
			GameRegistry.registerItem(toolsActiveEchir[c.getClassId()], "iasTool"+c.toString()+"EchirActive");
			GameRegistry.registerItem(tools[c.getClassId()], "ias"+c.toString());
		}
		
		for(int i = 0; i < 4; ++i) {
			armorActiveEchir[i] = new IaSItemEchirArmorActive("ArmorEchir"+i+"Active",i);
			GameRegistry.registerItem(armorActiveEchir[i], "iasArmor"+i+"EchirActive");
		}
		
		axe = tools[EnumIaSToolClass.AXE.getClassId()];
		pickaxe = tools[EnumIaSToolClass.PICKAXE.getClassId()];
		spade = tools[EnumIaSToolClass.SPADE.getClassId()];
		sword = tools[EnumIaSToolClass.SWORD.getClassId()];
		knife = (IaSItemThrowingKnife)tools[EnumIaSToolClass.KNIFE.getClassId()];

		makeEchirToolRecipe(0, "ee ", "es ", " s ", Items.stick);
		makeEchirToolRecipe(0, "ee ", "es ", " s ", Items.bone);
		makeEchirToolRecipe(1, "eee", " s ", " s ", Items.stick);
		makeEchirToolRecipe(1, "eee", " s ", " s ", Items.bone);
		makeEchirToolRecipe(2, " e ", " s ", " s ", Items.stick);
		makeEchirToolRecipe(2, " e ", " s ", " s ", Items.bone);
		makeEchirToolRecipe(3, " e ", " e ", " s ", Items.stick);
		makeEchirToolRecipe(3, " e ", " e ", " s ", Items.bone);
		
		IaSRegistry.addToolMaterial(new NyxMaterialDevora());
		IaSRegistry.addToolMaterial(new NyxMaterialCortra());
		IaSRegistry.addToolMaterial(new NyxMaterialNavistra());
		
		makeEchirToolInfusionRecipe(NyxItems.devora, "Devora");
		makeEchirToolInfusionRecipe(NyxItems.navistraShard, "Navistra");
		makeEchirToolInfusionRecipe(NyxItems.cortraDust, "Cortra");
		
		armorEchir = new IaSItemArmor[4];
		initArmor(armorEchir, IaSItemArmor.MATERIAL_ECHIR, "IceAndShadow2:textures/armor/echir");
		armorCortra = new IaSItemArmor[4];
		initArmor(armorCortra, IaSItemArmor.MATERIAL_CORTRA, "IceAndShadow2:textures/armor/cortra");
		armorNavistra = new IaSItemArmor[4];
		initArmor(armorNavistra, IaSItemArmor.MATERIAL_NAVISTRA, "IceAndShadow2:textures/armor/navistra");
		
		makeEchirArmorRecipe("eee", "e e", 0);
		makeEchirArmorRecipe("e e", "eee", "eee", 1);
		makeEchirArmorRecipe("eee", "e e", "e e", 2);
		makeEchirArmorRecipe("e e", "e e", 3);
	}
	
	public static ItemStack getArmorForSlot(int slot, int tier) {
		if(tier == 0)
			return new ItemStack(armorCortra[slot],0);
		if(tier == 1)
			return new ItemStack(armorEchir[slot],0);
		if(tier == 2) {
			ItemStack is = new ItemStack(armorCortra[slot],0);
			if(slot == 4)
				is.addEnchantment(Enchantment.featherFalling, 3);
			is.addEnchantment(Enchantment.projectileProtection, 2);
			is.addEnchantment(Enchantment.thorns, 3);
			return is;
		}
		return new ItemStack(armorNavistra[slot],0);
	}
	
	protected static void initArmor(IaSItemArmor[] arm, ItemArmor.ArmorMaterial mat, String tex) {
		for(int i = 0; i < 4; ++i) {
			arm[i] = new IaSItemArmor(mat, 3, i, tex);
			GameRegistry.registerItem(arm[i],arm[i].getModName());
			arm[i].setCreativeTab(IaSCreativeTabs.combat);
		}	
	}
	
	protected static void makeEchirToolRecipe(int slot, String a, String b, String c, Item stick) {
		GameRegistry.addShapedRecipe(new ItemStack(toolsActiveEchir[slot],1),
				a,b,c,
				'e', new ItemStack(NyxItems.echirIngot,1,1),
				's', new ItemStack(stick));
		GameRegistry.addSmelting(new ItemStack(tools[slot],1,0), new ItemStack(IaSTools.toolsActiveEchir[slot]), 0);
	}
	
	protected static void makeEchirArmorRecipe(String a, String b, String c, int slot) {
		GameRegistry.addShapedRecipe(new ItemStack(armorActiveEchir[slot],1),
				a,b,c,
				'e', new ItemStack(NyxItems.echirIngot,1,1));
		GameRegistry.addSmelting(new ItemStack(armorEchir[slot],1,0), new ItemStack(IaSTools.armorActiveEchir[slot]), 0);
	}
	protected static void makeEchirArmorRecipe(String a, String b, int slot) {
		GameRegistry.addShapedRecipe(new ItemStack(armorActiveEchir[slot],1),
				a,b,
				'e', new ItemStack(NyxItems.echirIngot,1,1));
		GameRegistry.addSmelting(new ItemStack(armorEchir[slot],1,0), new ItemStack(IaSTools.armorActiveEchir[slot]), 0);
	}
	protected static void makeEchirToolInfusionRecipe(Item reagent, String result) {
		ItemStack reag = new ItemStack(reagent);
		for(EnumIaSToolClass tool : EnumIaSToolClass.values()) {
			ItemStack tule = new ItemStack(toolsActiveEchir[tool.getClassId()]);
			ItemStack rslt = new ItemStack(tools[tool.getClassId()]);
			rslt.setTagCompound(new NBTTagCompound());
			rslt.getTagCompound().setString("iasMaterial", result);
			GameRegistry.addShapelessRecipe(rslt, reag, reag, reag, tule);
		}
	}
}
