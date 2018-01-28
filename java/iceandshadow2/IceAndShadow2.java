package iceandshadow2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.items.IaSItemStarterKit;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.InitNyx;
import iceandshadow2.ias.handlers.*;
import iceandshadow2.nyx.forge.*;
import iceandshadow2.nyx.toolmats.*;
import iceandshadow2.nyx.world.NyxBiomes;
import iceandshadow2.render.IaSRenderers;
import iceandshadow2.styx.Styx;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(name = "Ice and Shadow 2", modid = IceAndShadow2.MODID, dependencies = "required-after:FML;", version = IceAndShadow2.VERSION)
public class IceAndShadow2 {
	public static final String MODID = "IceAndShadow2";
	public static final String VERSION = "Pre-Alpha 17";
	public static final int CONFIG_MAJ = 3;
	public static final int CONFIG_MIN = 0;

	private static IaSConfigManager cfg;
	private static Logger logger;

	private static boolean acceptRegistration = false;

	@Instance(IceAndShadow2.MODID)
	public static IceAndShadow2 instance;

	private static List toPreRegister;

	private static List toPostRegister;

	public static Logger getLogger() {
		return IceAndShadow2.logger;
	}

	public static Collection getPostRegistrationHandlers() {
		return Collections.unmodifiableList(IceAndShadow2.toPostRegister);
	}

	public static Collection getPreRegistrationHandlers() {
		return Collections.unmodifiableList(IceAndShadow2.toPreRegister);
	}

	public static boolean isRegistrationPublic() {
		return IceAndShadow2.acceptRegistration;
	}

	private void addPostInitHandlers() {
		IceAndShadow2.toPostRegister.add(new IaSHandlerTransmutationRepair());
		IceAndShadow2.toPostRegister.add(new IaSHandlerTransmutationHeat());
		IceAndShadow2.toPostRegister.add(new IaSHandlerTransmutationUncraftFuse());
		IceAndShadow2.toPostRegister.add(new IaSHandlerTransmutationCraftShapeless());
		IceAndShadow2.toPostRegister.add(new IaSHandlerTransmutationCraft9());
	}

	private void addToolMaterials() {
		IceAndShadow2.toPreRegister.add(new NyxMaterialDevora());
		IceAndShadow2.toPreRegister.add(new NyxMaterialCortra());
		IceAndShadow2.toPreRegister.add(new NyxMaterialNavistra());
		IceAndShadow2.toPreRegister.add(new NyxMaterialExousium());
		IceAndShadow2.toPreRegister.add(new NyxMaterialIcicle());
		IceAndShadow2.toPreRegister.add(new NyxMaterialSanctified());
	}

	@EventHandler
	public void init1(FMLPreInitializationEvent event) {
		event.getModLog().info("Ice and Shadow 2, version " + IceAndShadow2.VERSION + ".");
		IceAndShadow2.logger = event.getModLog();
		if (event.getSide() == Side.SERVER) {
			event.getModLog().info(
					"While designed to be mostly multiplayer compatible, pings > 200 can make Ice and Shadow exponentially harder. You've been warned.");
		}
		IceAndShadow2.cfg = new IaSConfigManager(event.getSuggestedConfigurationFile(), IceAndShadow2.CONFIG_MAJ,
				IceAndShadow2.CONFIG_MIN);

		IaSCreativeTabs.init();
		IaSItemStarterKit.init();

		InitNyx.init(this);
		IaSDamageSources.init();

		Styx.init(this);

		IceAndShadow2.toPreRegister = new ArrayList<Object>();
		addToolMaterials();
		IceAndShadow2.toPreRegister.add(new IaSHandlerSacrificeXPBasics());
		IaSRegistry.preInit();
		IceAndShadow2.toPreRegister.clear();
		IaSTools.init();

		if (event.getSide() == Side.CLIENT) {
			IaSRenderers.init();
		}
		IceAndShadow2.acceptRegistration = true;
	}

	@EventHandler
	public void init2(FMLInitializationEvent event) {
		if (IaSFlags.flag_death_system) {
			MinecraftForge.EVENT_BUS.register(new NyxDeathSystem());
		}

		NyxBiomes.registerBiomes();
		MinecraftForge.EVENT_BUS.register(new IaSTooltipHandler());
		MinecraftForge.EVENT_BUS.register(new NyxEventHandlerCold());
		MinecraftForge.EVENT_BUS.register(new NyxBlockHandler());
		MinecraftForge.EVENT_BUS.register(new NyxEquipmentHandler());
		GameRegistry.registerFuelHandler(new NyxFuelHandler());

		// Be nice, Thaumcraft.
		FMLInterModComms.sendMessage("Thaumcraft", "dimensionBlacklist", "" + IaSFlags.dim_nyx_id + ":0");
	}

	@EventHandler
	public void init3(FMLPostInitializationEvent event) {
		IceAndShadow2.acceptRegistration = false;
		IceAndShadow2.toPostRegister = new ArrayList<Object>();
		addPostInitHandlers();
		IaSRegistry.postInit();
		IceAndShadow2.toPostRegister.clear();
		InitNyx.lateInit(this);
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new IaSServerCommand());
	}
}
