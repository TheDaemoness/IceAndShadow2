package iceandshadow2.nyx;

import iceandshadow2.nyx.blocks.NyxBlockBrickFrozen;
import iceandshadow2.nyx.blocks.NyxBlockBrickPale;
import iceandshadow2.nyx.blocks.NyxBlockBrickPaleCracked;
import iceandshadow2.nyx.blocks.NyxBlockCryingObsidian;
import iceandshadow2.nyx.blocks.NyxBlockGatestone;
import iceandshadow2.nyx.blocks.NyxBlockIce;
import iceandshadow2.nyx.blocks.NyxBlockInfestedLeaves;
import iceandshadow2.nyx.blocks.NyxBlockInfestedLog;
import iceandshadow2.nyx.blocks.NyxBlockPermafrost;
import iceandshadow2.nyx.blocks.NyxBlockPlanks;
import iceandshadow2.nyx.blocks.NyxBlockPoisonLeaves;
import iceandshadow2.nyx.blocks.NyxBlockPoisonLog;
import iceandshadow2.nyx.blocks.NyxBlockSilkBerryPod;
import iceandshadow2.nyx.blocks.NyxBlockStone;
import iceandshadow2.nyx.blocks.NyxBlockThornyVines;
import iceandshadow2.nyx.blocks.NyxBlockUnstableIce;
import iceandshadow2.nyx.blocks.NyxBlockWater;
import iceandshadow2.nyx.blocks.ore.NyxBlockCrystalBloodstone;
import iceandshadow2.nyx.blocks.ore.NyxBlockOreCortra;
import iceandshadow2.nyx.blocks.ore.NyxBlockOreDevora;
import iceandshadow2.nyx.blocks.ore.NyxBlockOreDraconium;
import iceandshadow2.nyx.blocks.ore.NyxBlockOreEchir;
import iceandshadow2.nyx.blocks.ore.NyxBlockOreExousium;
import iceandshadow2.nyx.blocks.ore.NyxBlockOreGemstone;
import iceandshadow2.nyx.blocks.ore.NyxBlockOreNavistra;
import iceandshadow2.nyx.blocks.ore.NyxBlockOreNifelhium;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;

public class NyxBlocks {
	public static Block stone, cryingObsidian, unstableIce, permafrost, 
	exousicIce, exousicWater, crystalBloodstone, thornyVines, silkBerryPod,
	infestLog, infestLeaves, poisonLeaves, poisonLog, brickFrozen, planks,
	gatestone, brickPale, brickPaleCracked;
	
	public static Block oreEchir, oreDevora, oreNavistra, oreCortra, 
	oreNifelhium, oreExousium, oreDraconium, oreGemstone;
	
	public static Fluid fluidExousia;
	
	public static void init() {
		stone = new NyxBlockStone("Stone").register();
		unstableIce = new NyxBlockUnstableIce("UnstableIce").register();
		permafrost = new NyxBlockPermafrost("Permafrost").register();
		cryingObsidian = new NyxBlockCryingObsidian("CryingObsidian").register();
		thornyVines = new NyxBlockThornyVines("ThornyVines").register();
		exousicIce = new NyxBlockIce("ExousicIce").register();
		poisonLeaves = new NyxBlockPoisonLeaves("PoisonwoodLeaves").register();
		poisonLog = new NyxBlockPoisonLog("PoisonwoodLog").register();
		infestLeaves = new NyxBlockInfestedLeaves("InfestedLeaves").register();
		infestLog = new NyxBlockInfestedLog("InfestedLog").register();
		silkBerryPod = new NyxBlockSilkBerryPod("SilkBerryPod").register();
		planks = new NyxBlockPlanks("Planks").register();

		oreGemstone = new NyxBlockOreGemstone("OreGemstone").register();
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
		
		brickFrozen = new NyxBlockBrickFrozen("FrozenBrick").register();
		brickPale = new NyxBlockBrickPale("PaleBrick").register();
		brickPaleCracked = new NyxBlockBrickPaleCracked("PaleBrickCracked").register();
		gatestone = new NyxBlockGatestone("Gatestone").register();
	}
}
