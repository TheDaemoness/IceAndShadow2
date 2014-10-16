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
		public static IaSItemTool axe, pickaxe, spade, sword;
		public static IaSItemThrowingKnife knife;
		
		public static IaSItemArmor
			armorEchir[], armorNavistra[], armorCortra[], armorCorpseskin[], armorSpiderSilk[];
		
	public static void init() {
		tools = new IaSItemTool[EnumIaSToolClass.values().length];
		toolsActiveEchir = new IaSBaseItem[EnumIaSToolClass.values().length];
		for(EnumIaSToolClass c : EnumIaSToolClass.values()) {
			if(c == EnumIaSToolClass.KNIFE)
				tools[c.getClassId()] = new IaSItemThrowingKnife();
			else
				tools[c.getClassId()] = new IaSItemTool(c);
			toolsActiveEchir[c.getClassId()] = new IaSItemEchirToolActive("ToolEchir"+c.toString()+"Active",c.getClassId());
			GameRegistry.registerItem(tools[c.getClassId()], "ias"+c.toString());
			GameRegistry.registerItem(toolsActiveEchir[c.getClassId()], "ias"+c.toString()+"EchirActive");
		}
		
		axe = tools[EnumIaSToolClass.AXE.getClassId()];
		pickaxe = tools[EnumIaSToolClass.PICKAXE.getClassId()];
		spade = tools[EnumIaSToolClass.SPADE.getClassId()];
		sword = tools[EnumIaSToolClass.SWORD.getClassId()];
		knife = (IaSItemThrowingKnife)tools[EnumIaSToolClass.KNIFE.getClassId()];

		makeEchirToolRecipe(axe, "ee ", "es ", " s ", Items.stick);
		makeEchirToolRecipe(axe, "ee ", "es ", " s ", Items.bone);
		makeEchirToolRecipe(pickaxe, "eee", " s ", " s ", Items.stick);
		makeEchirToolRecipe(pickaxe, "eee", " s ", " s ", Items.bone);
		makeEchirToolRecipe(spade, " e ", " s ", " s ", Items.stick);
		makeEchirToolRecipe(spade, " e ", " s ", " s ", Items.bone);
		makeEchirToolRecipe(sword, " e ", " e ", " s ", Items.stick);
		makeEchirToolRecipe(sword, " e ", " e ", " s ", Items.bone);
		
		IaSRegistry.addToolMaterial(new NyxMaterialDevora());
		IaSRegistry.addToolMaterial(new NyxMaterialCortra());
		IaSRegistry.addToolMaterial(new NyxMaterialNavistra());
		
		armorEchir = new IaSItemArmor[4];
		initArmor(armorEchir, IaSItemArmor.MATERIAL_ECHIR, "IceAndShadow2:textures/armor/echir");
		armorCortra = new IaSItemArmor[4];
		initArmor(armorCortra, IaSItemArmor.MATERIAL_CORTRA, "IceAndShadow2:textures/armor/cortra");
		armorNavistra = new IaSItemArmor[4];
		initArmor(armorNavistra, IaSItemArmor.MATERIAL_NAVISTRA, "IceAndShadow2:textures/armor/navistra");
		
		makeEchirArmorRecipe(armorEchir[0], "eee", "e e");
		makeEchirArmorRecipe(armorEchir[1], "e e", "eee", "eee");
		makeEchirArmorRecipe(armorEchir[2], "eee", "e e", "e e");
		makeEchirArmorRecipe(armorEchir[3], "e e", "e e");
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
	
	protected static void makeEchirToolRecipe(Item it, String a, String b, String c, Item stick) {
		GameRegistry.addShapedRecipe(new ItemStack(it,1),
				a,b,c,
				'e', new ItemStack(NyxItems.echirIngot,1,1),
				's', new ItemStack(stick));
		int classId = ((IaSItemTool)it).getIaSToolClass().getClassId();
		GameRegistry.addSmelting(it, new ItemStack(IaSTools.toolsActiveEchir[classId]), 0);
	}
	
	protected static void makeEchirArmorRecipe(Item it, String a, String b, String c) {
		GameRegistry.addShapedRecipe(new ItemStack(it,1),
				a,b,c,
				'e', new ItemStack(NyxItems.echirIngot,1,1));
	}
	protected static void makeEchirArmorRecipe(Item it, String a, String b) {
		GameRegistry.addShapedRecipe(new ItemStack(it,1),
				a,b,
				'e', new ItemStack(NyxItems.echirIngot,1,1));
	}
}
