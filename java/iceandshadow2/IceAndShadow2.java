package iceandshadow2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.InitNyx;
import iceandshadow2.ias.handlers.*;
import iceandshadow2.nyx.forge.*;
import iceandshadow2.nyx.toolmats.*;
import iceandshadow2.nyx.world.NyxBiomes;
import iceandshadow2.render.IaSRenderers;
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
	public static final String VERSION = "Pre-Alpha 8";
	public static final int CONFIG_MAJ = 2;
	public static final int CONFIG_MIN = 1;

	private static IaSConfigManager cfg;
	private static Logger logger;

	private static boolean acceptRegistration = false;

	@Instance(MODID)
	public static IceAndShadow2 instance;

	public static Logger getLogger() {
		return logger;
	}

	private static List toPreRegister;
	private static List toPostRegister;

	public static Collection getPreRegistrationHandlers() {
		return Collections.unmodifiableList(toPreRegister);
	}
	public static Collection getPostRegistrationHandlers() {
		return Collections.unmodifiableList(toPostRegister);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		event.getModLog().info("Ice and Shadow 2, version " + VERSION + ".");
		logger = event.getModLog();
		if (event.getSide() == Side.SERVER)
			event.getModLog()
			.info("While being SMP compatible, pings > 100 can make Ice and Shadow exponentially harder. You've been warned.");
		cfg = new IaSConfigManager(event.getSuggestedConfigurationFile(),
				CONFIG_MAJ, CONFIG_MIN);

		IaSCreativeTabs.init();

		InitNyx.init(this);
		IaSDamageSources.init();

		toPreRegister = new ArrayList<Object>();
		addToolMaterials();
		IaSRegistry.preInit();
		toPreRegister.clear();
		IaSTools.init();

		if (event.getSide() == Side.CLIENT)
			IaSRenderers.init();
		acceptRegistration = true;
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (IaSFlags.flag_death_system)
			MinecraftForge.EVENT_BUS.register(new NyxDeathSystem());

		NyxBiomes.registerBiomes();
		MinecraftForge.EVENT_BUS.register(new NyxEventHandlerCold());
		GameRegistry.registerFuelHandler(new NyxFuelHandler());

		// Be nice, Thaumcraft.
		FMLInterModComms.sendMessage("Thaumcraft", "dimensionBlacklist", ""
				+ IaSFlags.dim_nyx_id + ":0");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		acceptRegistration = false;
		toPostRegister = new ArrayList<Object>();
		addPostInitHandlers();
		IaSRegistry.postInit();
		toPostRegister.clear();
	}

	private void addToolMaterials() {
		toPreRegister.add(new NyxMaterialDevora());
		toPreRegister.add(new NyxMaterialCortra());
		toPreRegister.add(new NyxMaterialNavistra());
	}

	private void addPostInitHandlers() {
		toPostRegister.add(new IaSHandlerTransmutationRepair());
		toPostRegister.add(new IaSHandlerDistillationHeat());
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new IaSServerCommand());
	}

	public static boolean isRegistrationPublic() {
		return acceptRegistration;
	}
}
