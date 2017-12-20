package iceandshadow2.ias.items.tools;

import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.items.IaSBaseItem;
import iceandshadow2.nyx.toolmats.*;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.registry.GameRegistry;

class IaSMaterialIconGetter extends Item {
	@Override
	public boolean getHasSubtypes() {
		return true;
	}

	@Override
	public void registerIcons(IIconRegister i) {
		for (final IaSToolMaterial m : IaSRegistry.getToolMaterials())
			m.registerIcons(i);
		IaSRegistry.getDefaultMaterial().registerIcons(i);
	}
}

public class IaSTools {
	private static Item iconery;

	public static final String NBT_ID = "iasMaterial";

	public static IaSItemTool tools[];
	public static IaSItemWeapon weapons[];
	public static IaSBaseItem toolsActiveEchir[];
	public static IaSBaseItem swordsActiveEchir[];
	public static IaSBaseItem armorActiveEchir[];
	public static IaSItemTool axe, pickaxe, spade;
	public static IaSItemWeapon sword;
	public static IaSItemThrowingKnife knife;

	public static IaSItemArmor armorEchir[], armorNavistra[], armorCortra[], armorSpiderSilk[], armorAlabaster[];
	public static IaSItemArmor[][] armor;
	public static IaSArmorMaterial armorMatEchir, armorMatNavistra, armorMatCortra, armorMatSpiderSilk,
			armorMatAlabaster;

	public static ItemStack getArmorForSlot(int slot, int tier) {
		if (tier == 0)
			return new ItemStack(IaSTools.armorSpiderSilk[slot], 0);
		if (tier == 1)
			return new ItemStack(IaSTools.armorEchir[slot], 0);
		if (tier == 2) {
			final ItemStack is = new ItemStack(IaSTools.armorCortra[slot], 0);
			if (slot == 4)
				is.addEnchantment(Enchantment.featherFalling, 3);
			else if (slot == 2)
				is.addEnchantment(Enchantment.thorns, 3);
			is.addEnchantment(Enchantment.projectileProtection, 3);
			return is;
		}
		return new ItemStack(IaSTools.armorNavistra[slot], 0);
	}

	public static void init() {
		IaSTools.iconery = new IaSMaterialIconGetter();
		GameRegistry.registerItem(IaSTools.iconery, "thisItemDoesNotExist");

		IaSTools.tools = new IaSItemTool[EnumIaSToolClass.values().length];
		IaSTools.weapons = new IaSItemWeapon[EnumIaSToolClass.values().length];
		IaSTools.toolsActiveEchir = new IaSBaseItem[EnumIaSToolClass.values().length];
		IaSTools.swordsActiveEchir = new IaSBaseItem[EnumIaSToolClass.values().length];
		IaSTools.armorActiveEchir = new IaSBaseItem[4];
		for (final EnumIaSToolClass c : EnumIaSToolClass.values()) {
			if (c == EnumIaSToolClass.KNIFE)
				IaSTools.weapons[c.getClassId()] = new IaSItemThrowingKnife();
			else if (c.isWeapon())
				IaSTools.weapons[c.getClassId()] = new IaSItemWeapon(c);
			else
				IaSTools.tools[c.getClassId()] = new IaSItemTool(c);
			if (c.isWeapon()) {
				if (c == EnumIaSToolClass.KNIFE)
					IaSTools.swordsActiveEchir[c.getClassId()] = new IaSItemEchirKnifeActive(
							"ToolEchir" + c.toString() + "Active", c.getClassId());
				else
					IaSTools.swordsActiveEchir[c.getClassId()] = new IaSItemEchirToolActive(
							"ToolEchir" + c.toString() + "Active", c.getClassId(), true);
				GameRegistry.registerItem(IaSTools.swordsActiveEchir[c.getClassId()],
						"iasTool" + c.toString() + "EchirActive");
				GameRegistry.registerItem(IaSTools.weapons[c.getClassId()], "ias" + c.toString());
			} else {
				IaSTools.toolsActiveEchir[c.getClassId()] = new IaSItemEchirToolActive(
						"ToolEchir" + c.toString() + "Active", c.getClassId(), false);
				GameRegistry.registerItem(IaSTools.toolsActiveEchir[c.getClassId()],
						"iasTool" + c.toString() + "EchirActive");
				GameRegistry.registerItem(IaSTools.tools[c.getClassId()], "ias" + c.toString());
			}
		}

		for (int i = 0; i < 4; ++i) {
			IaSTools.armorActiveEchir[i] = new IaSItemEchirArmorActive("ArmorEchir" + i + "Active", i);
			GameRegistry.registerItem(IaSTools.armorActiveEchir[i], "iasArmor" + i + "EchirActive");
		}

		IaSTools.axe = IaSTools.tools[EnumIaSToolClass.AXE.getClassId()];
		IaSTools.pickaxe = IaSTools.tools[EnumIaSToolClass.PICKAXE.getClassId()];
		IaSTools.spade = IaSTools.tools[EnumIaSToolClass.SPADE.getClassId()];
		IaSTools.sword = IaSTools.weapons[EnumIaSToolClass.SWORD.getClassId()];
		IaSTools.knife = (IaSItemThrowingKnife) IaSTools.weapons[EnumIaSToolClass.KNIFE.getClassId()];

		IaSTools.makeEchirToolRecipe(0, "ee", "es", " s", Items.stick);
		IaSTools.makeEchirToolRecipe(1, "eee", " s ", " s ", Items.stick);
		IaSTools.makeEchirToolRecipe(2, "e", "s", "s", Items.stick);
		IaSTools.makeEchirWeaponRecipe(0, "e", "e", "s", Items.stick, 1);
		IaSTools.makeEchirWeaponRecipe(1, " e", "s ", Items.stick, 3);

		IaSTools.armorMatEchir = new NyxArmorMaterialEchir();
		IaSTools.armorMatCortra = new NyxArmorMaterialCortra();
		IaSTools.armorMatNavistra = new NyxArmorMaterialNavistra();
		IaSTools.armorMatSpiderSilk = new NyxArmorMaterialSpiderSilk();
		IaSTools.armorMatAlabaster = new NyxArmorMaterialSanctified();

		
		IaSTools.armorEchir = new IaSItemArmor[4];
		IaSTools.armorCortra = new IaSItemArmor[4];
		IaSTools.armorNavistra = new IaSItemArmor[4];
		IaSTools.armorSpiderSilk = new IaSItemArmor[4];
		IaSTools.armorAlabaster = new IaSItemArmor[4];
		
		IaSTools.armor = new IaSItemArmor[5][];
		armor[0] = armorEchir;
		armor[1] = armorCortra;
		armor[2] = armorNavistra;
		armor[3] = armorSpiderSilk;
		armor[4] = armorAlabaster;

		IaSTools.initArmor(IaSTools.armorEchir, IaSTools.armorMatEchir);
		IaSTools.initArmor(IaSTools.armorCortra, IaSTools.armorMatCortra);
		IaSTools.initArmor(IaSTools.armorNavistra, IaSTools.armorMatNavistra);
		IaSTools.initArmor(IaSTools.armorSpiderSilk, IaSTools.armorMatSpiderSilk);
		IaSTools.armorMatEchir = new NyxArmorMaterialEchir();
		IaSTools.initArmor(IaSTools.armorAlabaster, IaSTools.armorMatAlabaster);

		IaSTools.makeEchirArmorRecipe("eee", "e e", 0);
		IaSTools.makeEchirArmorRecipe("e e", "eee", "eee", 1);
		IaSTools.makeEchirArmorRecipe("eee", "e e", "e e", 2);
		IaSTools.makeEchirArmorRecipe("e e", "e e", 3);

		GameRegistry.addShapedRecipe(new ItemStack(IaSTools.armorSpiderSilk[0]), "eee", "e e", 'e',
				new ItemStack(NyxItems.toughGossamer));
		GameRegistry.addShapedRecipe(new ItemStack(IaSTools.armorSpiderSilk[1]), "e e", "eee", "eee", 'e',
				new ItemStack(NyxItems.toughGossamer));
		GameRegistry.addShapedRecipe(new ItemStack(IaSTools.armorSpiderSilk[2]), "eee", "e e", "e e", 'e',
				new ItemStack(NyxItems.toughGossamer));
		GameRegistry.addShapedRecipe(new ItemStack(IaSTools.armorSpiderSilk[3]), "e e", "e e", 'e',
				new ItemStack(NyxItems.toughGossamer));

		IaSTools.axe.setCreativeTab(IaSCreativeTabs.tools);
		IaSTools.pickaxe.setCreativeTab(IaSCreativeTabs.tools);
		IaSTools.spade.setCreativeTab(IaSCreativeTabs.tools);
		IaSTools.sword.setCreativeTab(IaSCreativeTabs.tools);
		IaSTools.knife.setCreativeTab(IaSCreativeTabs.tools);

		GameRegistry.addRecipe(IaSTools.setToolMaterial(IaSTools.axe, "Icicle"), "ii", "is", " s", 'i',
				new ItemStack(NyxItems.icicle, 1), 's', new ItemStack(Items.stick));
		GameRegistry.addRecipe(IaSTools.setToolMaterial(IaSTools.pickaxe, "Icicle"), "iii", " s ", " s ", 'i',
				new ItemStack(NyxItems.icicle, 1), 's', new ItemStack(Items.stick));
		GameRegistry.addRecipe(IaSTools.setToolMaterial(IaSTools.spade, "Icicle"), "i", "s", "s", 'i',
				new ItemStack(NyxItems.icicle, 1), 's', new ItemStack(Items.stick));
		GameRegistry.addRecipe(IaSTools.setToolMaterial(IaSTools.sword, "Icicle"), "i", "i", "s", 'i',
				new ItemStack(NyxItems.icicle, 1), 's', new ItemStack(Items.stick));
		GameRegistry.addRecipe(IaSTools.setToolMaterial(new ItemStack(IaSTools.knife, 3), "Icicle"), " i", "s ", 'i',
				new ItemStack(NyxItems.icicle, 1), 's', new ItemStack(Items.stick));
	}

	protected static void initArmor(IaSItemArmor[] arm, IaSArmorMaterial mat) {
		for (int i = 0; i < 4; ++i) {
			arm[i] = new IaSItemArmor(mat, 3, i,
					"IceAndShadow2:textures/armor/" + mat.getArmorStats().name().toLowerCase());
			GameRegistry.registerItem(arm[i], arm[i].getModName());
			arm[i].setCreativeTab(IaSCreativeTabs.tools);
		}
	}

	protected static void makeEchirArmorRecipe(String a, String b, int slot) {
		GameRegistry.addShapedRecipe(new ItemStack(IaSTools.armorActiveEchir[slot], 1), a, b, 'e',
				new ItemStack(NyxItems.echirIngot, 1, 1));
		GameRegistry.addSmelting(new ItemStack(IaSTools.armorEchir[slot], 1, 0),
				new ItemStack(IaSTools.armorActiveEchir[slot]), 0);
	}

	protected static void makeEchirArmorRecipe(String a, String b, String c, int slot) {
		GameRegistry.addShapedRecipe(new ItemStack(IaSTools.armorActiveEchir[slot], 1), a, b, c, 'e',
				new ItemStack(NyxItems.echirIngot, 1, 1));
		GameRegistry.addSmelting(new ItemStack(IaSTools.armorEchir[slot], 1, 0),
				new ItemStack(IaSTools.armorActiveEchir[slot]), 0);
	}

	protected static void makeEchirToolRecipe(int slot, String a, String b, String c, Item stick) {
		GameRegistry.addShapedRecipe(new ItemStack(IaSTools.toolsActiveEchir[slot], 1), a, b, c, 'e',
				new ItemStack(NyxItems.echirIngot, 1, 1), 's', new ItemStack(stick));
		GameRegistry.addSmelting(new ItemStack(IaSTools.tools[slot], 1, 0),
				new ItemStack(IaSTools.toolsActiveEchir[slot]), 0);
	}

	protected static void makeEchirWeaponRecipe(int slot, String a, String b, Item stick, int yield) {
		GameRegistry.addShapedRecipe(new ItemStack(IaSTools.swordsActiveEchir[slot], yield), a, b, 'e',
				new ItemStack(NyxItems.echirIngot, 1, 1), 's', new ItemStack(stick));
		GameRegistry.addSmelting(new ItemStack(IaSTools.weapons[slot], 1, 0),
				new ItemStack(IaSTools.swordsActiveEchir[slot]), 0);
	}

	protected static void makeEchirWeaponRecipe(int slot, String a, String b, String c, Item stick, int yield) {
		GameRegistry.addShapedRecipe(new ItemStack(IaSTools.swordsActiveEchir[slot], yield), a, b, c, 'e',
				new ItemStack(NyxItems.echirIngot, 1, 1), 's', new ItemStack(stick));
		GameRegistry.addSmelting(new ItemStack(IaSTools.weapons[slot], 1, 0),
				new ItemStack(IaSTools.swordsActiveEchir[slot]), 0);
	}

	public static ItemStack setToolMaterial(Item it, String mat) {
		final ItemStack is = new ItemStack(it);
		is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setString(IaSTools.NBT_ID, mat);
		return is;
	}

	public static ItemStack setToolMaterial(ItemStack is, String mat) {
		if (!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setString(IaSTools.NBT_ID, mat);
		return is;
	}
}
