package iceandshadow2;

import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.nyx.InitNyx;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(name = "Ice and Shadow 2", modid = IceAndShadow2.MODID, dependencies="required-after:FML", version = IceAndShadow2.VERSION)
public class IceAndShadow2 {
    public static final String MODID = "IceAndShadow2";
    public static final String VERSION = "Alpha 0.0";
    public static final int CONFIG_MAJ = 2;
    public static final int CONFIG_MIN = 0;
    
    private static IaSConfigManager cfg;
    
    @Instance(MODID)
    public static IceAndShadow2 instance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	event.getModLog().info("Ice and Shadow 2, version " + VERSION + ".");
    	if(event.getSide() == Side.SERVER)
        	event.getModLog().warn("While being SMP compatible, pings > 100 can make Ice and Shadow exponentially harder. You've been warned.");
    	cfg = new IaSConfigManager(event.getSuggestedConfigurationFile(), CONFIG_MAJ, CONFIG_MIN);
    	cfg.read();
    	if(cfg.needsWrite())
    		cfg.write();
    	InitNyx.init();
    	IaSCreativeTabs.init();
    	InitNyx.postInit();
    	IaSDamageSources.init();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
		//Be nice, Thaumcraft.
		FMLInterModComms.sendMessage("Thaumcraft", "dimensionBlacklist", ""+IaSFlags.dim_nyx_id+":0");
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}
