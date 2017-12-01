package iceandshadow2.nyx;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.ias.items.IaSItemShears;
import iceandshadow2.nyx.items.*;
import iceandshadow2.nyx.items.tools.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class NyxItems {
	public static Item teleportCrystal, seedObsidian, bloodstone, echirIngot, cortraIngot, draconiumIngot,
			nifelhiumPowder, navistraShard, devora, cortra, exousium, draconium, exousicIceShard, toughGossamer,
			poisonFruit, poisonFruitFertile, silkBerries, resin, frostBowShort, frostBowLong, frostSword, boneCursed,
			boneSanctified, heat, alabaster, vineBundle, bread, cookie, icicle, page, rope, kitTightrope, echirShears,
			crystalVial, extractorPoison, magicRepo, toxicCore;

	public static void init() {
		NyxItems.teleportCrystal = new NyxItemTeleportCrystal("TeleportCrystal").register()
				.setCreativeTab(IaSCreativeTabs.misc);
		NyxItems.seedObsidian = new NyxItemSeedObsidian("SeedObsidian").register().setCreativeTab(IaSCreativeTabs.misc);
		NyxItems.nifelhiumPowder = new NyxItemNifelhiumPowder("NifelhiumPowder").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.bloodstone = new NyxItemBloodstone("Bloodstone").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.navistraShard = new NyxItemNavistraShard("NavistraShard").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.cortra = new NyxItemCortra("Cortra").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.exousium = new NyxItemExousium("Exousium").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.devora = new NyxItemDevora("Devora").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.exousicIceShard = new NyxItemExousicIce("ExousicIceShard").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.draconium = new NyxItemDraconium("Draconium").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.echirIngot = new NyxItemIngot("EchirIngot").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.cortraIngot = new NyxItemIngot("CortraIngot").setTransmutation(NyxItems.cortra, 1).register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.draconiumIngot = new NyxItemIngot("DraconiumIngot").setTransmutation(NyxItems.draconium, 2).register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.poisonFruit = new NyxItemPlumPoison("PoisonFruit").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.poisonFruitFertile = new NyxItemPlumPoisonFertile("PoisonFruitFertile").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.toughGossamer = new IaSBaseItemSingle(EnumIaSModule.NYX, "ToughGossamer").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.silkBerries = new NyxItemSilkBerries("SilkBerries").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.frostBowShort = new NyxItemBowFrostShort("FrostShortBow").register()
				.setCreativeTab(IaSCreativeTabs.tools);
		NyxItems.frostBowLong = new NyxItemBowFrostLong("FrostLongBow").register()
				.setCreativeTab(IaSCreativeTabs.tools);
		NyxItems.frostSword = new NyxItemSwordFrost("FrostSword").register().setCreativeTab(IaSCreativeTabs.tools);
		NyxItems.boneCursed = new NyxItemBoneCursed("CursedBone").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.boneSanctified = new NyxItemBoneSanctified("SanctifiedBone").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.vineBundle = new NyxItemVineBundle("VineBundle").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.bread = new NyxItemFoodBread("Bread").register().setCreativeTab(CreativeTabs.tabFood);
		NyxItems.cookie = new NyxItemFoodCookie("Cookie").register().setCreativeTab(CreativeTabs.tabFood);
		NyxItems.echirShears = new IaSItemShears(EnumIaSModule.NYX, "EchirShears").register()
				.setCreativeTab(IaSCreativeTabs.tools);
		NyxItems.icicle = new NyxItemIcicle("Icicle").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.page = new NyxItemLorePage("Page").register().setCreativeTab(IaSCreativeTabs.misc);
		NyxItems.rope = new NyxItemRope("Rope").register().setCreativeTab(IaSCreativeTabs.tools);
		NyxItems.kitTightrope = new NyxItemKitTightrope("KitTightrope").register()
				.setCreativeTab(IaSCreativeTabs.tools);
		NyxItems.heat = new NyxItemHeat("Heat").register().setCreativeTab(IaSCreativeTabs.misc);
		NyxItems.crystalVial = new NyxItemCrystalVial("CrystalVial").register().setCreativeTab(IaSCreativeTabs.tools);
		NyxItems.extractorPoison = new NyxItemExtractorPoison("ExtractorPoison").register()
				.setCreativeTab(IaSCreativeTabs.tools);
		NyxItems.resin = new NyxItemPhantomResin("PhantomResin").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.alabaster = new NyxItemAlabaster("Alabaster").register().setCreativeTab(IaSCreativeTabs.resources);
		// NyxItems.magicRepo = new NyxItemMagicRepo("MagicRepo").register()
		// .setCreativeTab(IaSCreativeTabs.misc);
		NyxItems.toxicCore = new NyxItemToxicCore("ToxicCore").register().setCreativeTab(IaSCreativeTabs.resources);
	}
}
