package iceandshadow2.ias.items.tools;

import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.items.IaSBaseItem;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.items.materials.NyxMaterialCortra;
import iceandshadow2.nyx.items.materials.NyxMaterialDevora;
import iceandshadow2.nyx.items.materials.NyxMaterialNavistra;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.registry.GameRegistry;

public class IaSTools {
		public static IaSItemTool tools[];
		public static IaSItemWeapon weapons[];
		public static IaSBaseItem toolsActiveEchir[];
		public static IaSBaseItem swordsActiveEchir[];
		public static IaSBaseItem armorActiveEchir[];
		public static IaSItemTool axe, pickaxe, spade;
		public static IaSItemWeapon sword;
		public static IaSItemThrowingKnife knife;
		
		public static IaSItemArmor
			armorEchir[], armorNavistra[], armorCortra[], armorCorpseskin[], armorSpiderSilk[];
		
	public static void init() {
		tools = new IaSItemTool[EnumIaSToolClass.values().length];
		weapons = new IaSItemWeapon[EnumIaSToolClass.values().length];
		toolsActiveEchir = new IaSBaseItem[EnumIaSToolClass.values().length];
		swordsActiveEchir = new IaSBaseItem[EnumIaSToolClass.values().length];
		armorActiveEchir = new IaSBaseItem[4];
		for(EnumIaSToolClass c : EnumIaSToolClass.values()) {
			if(c == EnumIaSToolClass.KNIFE)
				weapons[c.getClassId()] = new IaSItemThrowingKnife();
			else if(c.isWeapon())
				weapons[c.getClassId()] = new IaSItemWeapon(c);
			else
				tools[c.getClassId()] = new IaSItemTool(c);
			if(c.isWeapon()) {
				swordsActiveEchir[c.getClassId()] = new IaSItemEchirToolActive("ToolEchir"+c.toString()+"Active",c.getClassId(), true);
				GameRegistry.registerItem(swordsActiveEchir[c.getClassId()], "iasTool"+c.toString()+"EchirActive");
				GameRegistry.registerItem(weapons[c.getClassId()], "ias"+c.toString());
			}
			else {
				toolsActiveEchir[c.getClassId()] = new IaSItemEchirToolActive("ToolEchir"+c.toString()+"Active",c.getClassId(), false);
				GameRegistry.registerItem(toolsActiveEchir[c.getClassId()], "iasTool"+c.toString()+"EchirActive");
				GameRegistry.registerItem(tools[c.getClassId()], "ias"+c.toString());
			}
		}
		
		for(int i = 0; i < 4; ++i) {
			armorActiveEchir[i] = new IaSItemEchirArmorActive("ArmorEchir"+i+"Active",i);
			GameRegistry.registerItem(armorActiveEchir[i], "iasArmor"+i+"EchirActive");
		}
		
		axe = tools[EnumIaSToolClass.AXE.getClassId()];
		pickaxe = tools[EnumIaSToolClass.PICKAXE.getClassId()];
		spade = tools[EnumIaSToolClass.SPADE.getClassId()];
		sword = weapons[EnumIaSToolClass.SWORD.getClassId()];
		knife = (IaSItemThrowingKnife)weapons[EnumIaSToolClass.KNIFE.getClassId()];

		makeEchirToolRecipe(0, "ee ", "es ", " s ", Items.stick);
		makeEchirToolRecipe(0, "ee ", "es ", " s ", Items.bone);
		makeEchirToolRecipe(1, "eee", " s ", " s ", Items.stick);
		makeEchirToolRecipe(1, "eee", " s ", " s ", Items.bone);
		makeEchirToolRecipe(2, " e ", " s ", " s ", Items.stick);
		makeEchirToolRecipe(2, " e ", " s ", " s ", Items.bone);
		makeEchirWeaponRecipe(0, " e ", " e ", " s ", Items.stick);
		makeEchirWeaponRecipe(0, " e ", " e ", " s ", Items.bone);
		//makeEchirWeaponRecipe(1, " e", "s ", Items.stick);
		//makeEchirWeaponRecipe(1, " e", "s ", Items.bone);
		
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
		
		makeEchirArmorInfusionRecipe(NyxItems.navistraShard, armorNavistra);
		makeEchirArmorInfusionRecipe(NyxItems.cortraDust, armorCortra);
	}
	
	public static ItemStack setToolMaterial(Item it, String mat) {
		ItemStack is = new ItemStack(it);
		is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setString("iasMaterial", mat);
		return is;
	}
	
	public static ItemStack setToolMaterial(ItemStack is, String mat) {
		if(!is.hasTagCompound())
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setString("iasMaterial", mat);
		return is;
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
	
	protected static void makeEchirWeaponRecipe(int slot, String a, String b, String c, Item stick) {
		GameRegistry.addShapedRecipe(new ItemStack(toolsActiveEchir[slot],1),
				a,b,c,
				'e', new ItemStack(NyxItems.echirIngot,1,1),
				's', new ItemStack(stick));
		GameRegistry.addSmelting(new ItemStack(weapons[slot],1,0), new ItemStack(IaSTools.swordsActiveEchir[slot]), 0);
	}
	
	protected static void makeEchirWeaponRecipe(int slot, String a, String b, Item stick) {
		GameRegistry.addShapedRecipe(new ItemStack(toolsActiveEchir[slot],1),
				a,b,
				'e', new ItemStack(NyxItems.echirIngot,1,1),
				's', new ItemStack(stick));
		GameRegistry.addSmelting(new ItemStack(weapons[slot],1,0), new ItemStack(IaSTools.swordsActiveEchir[slot]), 0);
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
	protected static void makeEchirArmorInfusionRecipe(Item reagent, IaSItemArmor[] armors) {
		ItemStack r = new ItemStack(reagent);
		GameRegistry.addShapelessRecipe(new ItemStack(armors[0]), r, r, r, r, r, new ItemStack(armorActiveEchir[0]));
		GameRegistry.addShapelessRecipe(new ItemStack(armors[1]), r, r, r, r, r, r, r, r, new ItemStack(armorActiveEchir[1]));
		GameRegistry.addShapelessRecipe(new ItemStack(armors[2]), r, r, r, r, r, r, r, new ItemStack(armorActiveEchir[2]));
		GameRegistry.addShapelessRecipe(new ItemStack(armors[3]), r, r, r, r, new ItemStack(armorActiveEchir[3]));
	}
}
