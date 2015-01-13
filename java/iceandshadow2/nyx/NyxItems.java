package iceandshadow2.nyx;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.ias.items.IaSItemShears;
import iceandshadow2.nyx.items.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class NyxItems {
	public static Item teleportCrystal, seedObsidian, bloodstone,
		echirIngot, nifelhiumPowder, navistraShard, devora, cortraDust, exousium, draconium,
		exousicIceShard, toughGossamer, poisonFruit, poisonFruitFertile, silkBerries, 
		frostBowShort, frostBowLong, boneCursed, boneSanctified,
		vineBundle, bread, cookie, echirShears, icicle, page, rope, kitTightrope;
	
	public static void init() {
		teleportCrystal = new NyxItemTeleportCrystal("TeleportCrystal").
				register().setCreativeTab(IaSCreativeTabs.misc);
		seedObsidian = new NyxItemSeedObsidian("SeedObsidian").
				register().setCreativeTab(IaSCreativeTabs.misc);
		echirIngot = new NyxItemIngotEchir("EchirIngot").
				register().setCreativeTab(IaSCreativeTabs.resources);
		nifelhiumPowder = new NyxItemNifelhiumPowder("NifelhiumPowder").
				register().setCreativeTab(IaSCreativeTabs.resources);
		bloodstone = new NyxItemBloodstone("Bloodstone").
				register().setCreativeTab(IaSCreativeTabs.resources);
		navistraShard = new NyxItemNavistraShard("NavistraShard").
				register().setCreativeTab(IaSCreativeTabs.resources);
		cortraDust = new NyxItemCortraDust("CortraDust").
				register().setCreativeTab(IaSCreativeTabs.resources);
		exousium = new NyxItemExousium("Exousium").
				register().setCreativeTab(IaSCreativeTabs.resources);
		devora = new NyxItemDevora("Devora").
				register().setCreativeTab(IaSCreativeTabs.resources);
		exousicIceShard = new IaSBaseItemSingle(EnumIaSModule.NYX,"ExousicIceShard").
				register().setCreativeTab(IaSCreativeTabs.resources);
		draconium = new NyxItemDraconium("Draconium").
				register().setCreativeTab(IaSCreativeTabs.resources);
		poisonFruit = new NyxItemPlumPoison("PoisonFruit").
				register().setCreativeTab(IaSCreativeTabs.resources);
		poisonFruitFertile = new NyxItemPlumPoisonFertile("PoisonFruitFertile").
				register().setCreativeTab(IaSCreativeTabs.resources);
		toughGossamer = new IaSBaseItemSingle(EnumIaSModule.NYX,"ToughGossamer").
				register().setCreativeTab(IaSCreativeTabs.resources);
		silkBerries = new NyxItemSilkBerries("SilkBerries").
				register().setCreativeTab(IaSCreativeTabs.resources);
		frostBowShort = new NyxItemFrostShortBow("FrostShortBow").
				register().setCreativeTab(IaSCreativeTabs.combat);
		frostBowLong = new NyxItemFrostLongBow("FrostLongBow").
				register().setCreativeTab(IaSCreativeTabs.combat);
		boneCursed = new NyxItemBoneCursed("CursedBone").
				register().setCreativeTab(IaSCreativeTabs.resources);
		boneSanctified = new NyxItemBoneSanctified("SanctifiedBone").
				register().setCreativeTab(IaSCreativeTabs.resources);
		vineBundle = new NyxItemVineBundle("VineBundle").
				register().setCreativeTab(IaSCreativeTabs.resources);
		bread = new NyxItemFoodBread("Bread").
				register().setCreativeTab(CreativeTabs.tabFood);
		cookie = new NyxItemFoodCookie("Cookie").
				register().setCreativeTab(CreativeTabs.tabFood);
		echirShears = new IaSItemShears(EnumIaSModule.NYX,"EchirShears").
				register().setCreativeTab(IaSCreativeTabs.tools);
		icicle = new NyxItemIcicle("Icicle").
				register().setCreativeTab(IaSCreativeTabs.resources);
		page = new NyxItemLorePage("Page").
				register().setCreativeTab(IaSCreativeTabs.misc);
		rope = new NyxItemRope("Rope").
				register().setCreativeTab(IaSCreativeTabs.tools);
		kitTightrope = new NyxItemKitTightrope("KitTightrope").
				register().setCreativeTab(IaSCreativeTabs.tools);
	}
}
