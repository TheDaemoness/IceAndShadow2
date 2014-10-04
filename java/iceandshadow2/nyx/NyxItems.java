package iceandshadow2.nyx;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.items.*;
import iceandshadow2.util.EnumIaSModule;

public class NyxItems {
	public static Item teleportCrystal, seedObsidian, bloodstone,
		echirIngot, nifelhiumPowder, navistraShard, devora, cortraDust, exousium,
		exousicIceShard, poisonFruit, silkBerries, frostBowShort, frostBowLong;
	
	public static void init() {
		teleportCrystal = new NyxTeleportCrystal("TeleportCrystal").
				register().setCreativeTab(IaSCreativeTabs.misc);
		seedObsidian = new NyxSeedObsidian("SeedObsidian").
				register().setCreativeTab(IaSCreativeTabs.misc);
		echirIngot = new NyxIngotEchir("EchirIngot").
				register().setCreativeTab(IaSCreativeTabs.resources);
		nifelhiumPowder = new NyxNifelhiumPowder("NifelhiumPowder").
				register().setCreativeTab(IaSCreativeTabs.resources);
		bloodstone = new NyxBloodstone("Bloodstone").
				register().setCreativeTab(IaSCreativeTabs.resources);
		navistraShard = new NyxNavistraShard("NavistraShard").
				register().setCreativeTab(IaSCreativeTabs.resources);
		cortraDust = new NyxCortraDust("CortraDust").
				register().setCreativeTab(IaSCreativeTabs.resources);
		exousium = new NyxExousium("Exousium").
				register().setCreativeTab(IaSCreativeTabs.resources);
		devora = new NyxDevora("Devora").
				register().setCreativeTab(IaSCreativeTabs.resources);
		exousicIceShard = new IaSBaseItemSingle(EnumIaSModule.NYX,"ExousicIceShard").
				register().setCreativeTab(IaSCreativeTabs.resources);
		poisonFruit = new IaSBaseItemSingle(EnumIaSModule.NYX,"PoisonFruit").
				register().setCreativeTab(IaSCreativeTabs.resources);
		silkBerries = new NyxItemSilkBerries("SilkBerries").
				register().setCreativeTab(IaSCreativeTabs.resources);
	}
}
