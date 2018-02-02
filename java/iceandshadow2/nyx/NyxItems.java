package iceandshadow2.nyx;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.items.IaSBaseItem;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.ias.items.IaSItemShears;
import iceandshadow2.nyx.items.*;
import iceandshadow2.nyx.items.tools.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class NyxItems {
	public static Item teleportCrystal, bloodstone, nifelhiumPowder, navistraShard, devora, cortra, exousium, draconium,
			exousicIceShard, toughGossamer, poisonFruit, poisonFruitFertile, silkBerries, resin, frostBowShort,
			frostBowLong, frostSword, boneCursed, boneSanctified, heat, alabaster, vineBundle, bread, cookie, icicle,
			page, rope, kitTightrope, echirShears, crystalVial, extractorPoison, magicRepo, toxicCore, echirDust,
			alabasterShard, leaf, resinCurative, salt, flask, amber, amberNugget, clockwork, clockworkSmall, cursedPowder,
			remote, remoteDetonator, antenna;

	public static Item echirIngot, cortraIngot, draconiumIngot;

	//WARNING: MUST BE INITIALIZED DURING POSTINIT, NOT EARLIER!
	public static Item potion, grenade;

	public static void init() {
		NyxItems.teleportCrystal = new NyxItemTeleportCrystal("TeleportCrystal").register()
				.setCreativeTab(IaSCreativeTabs.misc);
		NyxItems.resin = new NyxItemResinEthereal("PhantomResin").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.echirDust = new IaSBaseItemSingleGlow(EnumIaSModule.NYX, "EchirDust").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.nifelhiumPowder = new NyxItemNifelhiumPowder("NifelhiumPowder").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.bloodstone = new NyxItemBloodstone("Bloodstone").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.navistraShard = new NyxItemNavistraShard("NavistraShard").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.cortra = new NyxItemCortra("Cortra").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.exousium = new NyxItemExousium("Exousium").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.devora = new NyxItemDevora("Devora").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.alabaster = new NyxItemAlabaster("Alabaster").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.alabasterShard = new NyxItemAlabasterShard("AlabasterShard").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.exousicIceShard = new NyxItemExousicIce("ExousicIceShard").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.draconium = new NyxItemDraconium("Draconium").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.salt = new NyxItemSalt("Salt").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.echirIngot = new NyxItemIngot("Echir", (IaSBaseItem)NyxItems.echirDust).register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.cortraIngot = new NyxItemIngot("Cortra", (IaSBaseItem)NyxItems.cortra).register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.draconiumIngot = new NyxItemIngot("Draconium", (IaSBaseItem)NyxItems.draconium).register()
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
		NyxItems.boneCursed = new NyxItemBoneCursed("CursedBone").register().setCreativeTab(IaSCreativeTabs.misc);
		NyxItems.boneSanctified = new NyxItemBoneSanctified("SanctifiedBone").register()
				.setCreativeTab(IaSCreativeTabs.misc);
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
		NyxItems.heat = new NyxItemResinThermal("Heat").register().setCreativeTab(IaSCreativeTabs.misc);
		// NyxItems.crystalVial = new
		// NyxItemCrystalVial("CrystalVial").register().setCreativeTab(IaSCreativeTabs.tools);
		// NyxItems.extractorPoison = new
		// NyxItemExtractorPoison("ExtractorPoison").register()
		// .setCreativeTab(IaSCreativeTabs.tools);
		// NyxItems.magicRepo = new NyxItemMagicRepo("MagicRepo").register()
		// .setCreativeTab(IaSCreativeTabs.misc);
		NyxItems.toxicCore = new NyxItemToxicCore("ToxicCore").register().setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.leaf = new IaSBaseItemSingle(EnumIaSModule.NYX, "ShapedLeaf").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.resinCurative = new NyxItemResinCurative("CurativeResin").register()
				.setCreativeTab(IaSCreativeTabs.misc);
		NyxItems.flask = new NyxItemFlask("Flask").register()
				.setCreativeTab(IaSCreativeTabs.tools);
		NyxItems.amber = new NyxItemAmber("Amber").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.amberNugget = new NyxItemAmberNugget("AmberNugget").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.clockwork = new NyxItemClockwork("Clockwork").register()
				.setCreativeTab(IaSCreativeTabs.misc);
		NyxItems.clockworkSmall = new NyxItemClockworkSmall("ClockworkSmall").register()
				.setCreativeTab(IaSCreativeTabs.misc);
		NyxItems.cursedPowder = new NyxItemCursedPowder("CursedPowder").register()
				.setCreativeTab(IaSCreativeTabs.resources);
		NyxItems.remote = new NyxItemRemote("Remote").register()
				.setCreativeTab(IaSCreativeTabs.tools);
		NyxItems.remoteDetonator = new NyxItemRemoteDetonator("RemoteDetonator").register()
				.setCreativeTab(IaSCreativeTabs.tools);
	}

	public static void lateInit() {
		potion = new NyxItemPotion("Potion").register().setCreativeTab(IaSCreativeTabs.misc);
		grenade = new NyxItemGrenade("Grenade").register().setCreativeTab(IaSCreativeTabs.tools);
	}
}
