package iceandshadow2.nyx;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.nyx.blocks.*;
import iceandshadow2.nyx.blocks.ore.*;
import iceandshadow2.util.IaSRegistration;

public class NyxBlocks {
	public static Block stone, cryingObsidian, unstableIce, permafrost, 
	exousicIce, exousicWater, crystalBloodstone, thornyVines, silkBerryPod,
	stickyLog, stickyLeaves, poisonLeaves, poisonLog;
	
	public static Block oreEchir, oreDevora, oreNavistra, oreCortra, 
	oreNifelhium, oreExousium, oreDraconium;
	
	public static Fluid fluidExousia;
	
	public static void init() {
		stone = new NyxBlockStone("Stone").register();
		unstableIce = new NyxBlockUnstableIce("UnstableIce").register();
		permafrost = new NyxBlockPermafrost("Permafrost").register();
		cryingObsidian = new NyxBlockCryingObsidian("CryingObsidian").register();
		thornyVines = new NyxBlockThornyVines("ThornyVines").register();
		exousicIce = new NyxBlockIce("ExousicIce").register();
		poisonLeaves = new NyxBlockPoisonLeaves("PoisonLeaves").register();
		poisonLeaves = new NyxBlockPoisonLog("PoisonLog").register();
		
		oreEchir = new NyxBlockOreEchir("OreEchir").register();
		oreDevora = new NyxBlockOreDevora("OreDevora").register();
		oreNavistra = new NyxBlockOreNavistra("OreNavistra").register();
		oreCortra = new NyxBlockOreCortra("OreCortra").register();
		oreNifelhium = new NyxBlockOreNifelhium("OreNifelhium").register();
		oreExousium = new NyxBlockOreExousium("OreExousium").register();
		oreDraconium = new NyxBlockOreDraconium("OreDraconium").register();
		
		fluidExousia = new Fluid("nyxFluidExousicWater");
		fluidExousia.setDensity(1500).setLuminosity(6).setTemperature(10).setViscosity(500);
		IaSRegistration.register(fluidExousia);
		exousicWater = new NyxBlockWater("ExousicWater",fluidExousia).register();
		crystalBloodstone = new NyxBlockCrystalBloodstone("CrystalBloodstone").register();
	}
}
