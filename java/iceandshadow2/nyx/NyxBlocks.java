package iceandshadow2.nyx;

import cpw.mods.fml.common.registry.GameRegistry;
import iceandshadow2.ias.blocks.IaSBaseBlockTechnical;
import iceandshadow2.nyx.blocks.*;
import iceandshadow2.nyx.blocks.ore.*;
import iceandshadow2.nyx.blocks.technical.*;
import iceandshadow2.nyx.blocks.utility.*;
import iceandshadow2.nyx.tileentities.*;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;

public class NyxBlocks {
	public static Block stone, cryingObsidian, unstableIce, permafrost, dirt,
	exousicIce, exousicWater, crystalBloodstone, crystalExousium,
	thornyVines, silkBerryPod, infestLog, infestLeaves, poisonLeaves, poisonLog,
	brickFrozen, planks, gatestone, brickPale, brickPaleCracked,
	hookClimbing, transmutationAltar, examinationTable,
	hardShadow, thermalCore, thermalAir, xpAltar;

	public static Block oreEchir, oreDevora, oreNavistra, oreCortra,
	oreNifelhium, oreExousium, oreDraconium, oreGemstone;

	public static IaSBaseBlockTechnical hookTightropeX, hookTightropeZ, ropeX, ropeY, ropeZ, fakeFurnace;

	public static Fluid fluidExousia;

	public static void init() {
		NyxBlocks.stone = new NyxBlockStone("Stone").register();
		NyxBlocks.unstableIce = new NyxBlockUnstableIce("UnstableIce").register();
		NyxBlocks.permafrost = new NyxBlockPermafrost("Permafrost").register();
		NyxBlocks.dirt = new NyxBlockDirt("Dirt").register();
		NyxBlocks.cryingObsidian = new NyxBlockCryingObsidian("CryingObsidian")
		.register();
		NyxBlocks.thornyVines = new NyxBlockThornyVines("ThornyVines").register();
		NyxBlocks.exousicIce = new NyxBlockIce("ExousicIce").register();
		NyxBlocks.poisonLeaves = new NyxBlockPoisonLeaves("PoisonwoodLeaves").register();
		NyxBlocks.poisonLog = new NyxBlockPoisonLog("PoisonwoodLog").register();
		NyxBlocks.infestLeaves = new NyxBlockInfestedLeaves("InfestedLeaves").register();
		NyxBlocks.infestLog = new NyxBlockInfestedLog("InfestedLog").register();
		NyxBlocks.silkBerryPod = new NyxBlockSilkBerryPod("SilkBerryPod").register();
		NyxBlocks.planks = new NyxBlockPlanks("Planks").register();

		NyxBlocks.oreGemstone = new NyxBlockOreGemstone("OreGemstone").register();
		NyxBlocks.oreEchir = new NyxBlockOreEchir("OreEchir").register();
		NyxBlocks.oreDevora = new NyxBlockOreDevora("OreDevora").register();
		NyxBlocks.oreNavistra = new NyxBlockOreNavistra("OreNavistra").register();
		NyxBlocks.oreCortra = new NyxBlockOreCortra("OreCortra").register();
		NyxBlocks.oreNifelhium = new NyxBlockOreNifelhium("OreNifelhium").register();
		NyxBlocks.oreExousium = new NyxBlockOreExousium("OreExousium").register();
		NyxBlocks.oreDraconium = new NyxBlockOreDraconium("OreDraconium").register();
		NyxBlocks.crystalBloodstone = new NyxBlockCrystalBloodstone("CrystalBloodstone")
		.register();
		NyxBlocks.crystalExousium = new NyxBlockCrystalExousium("CrystalExousium")
		.register();

		NyxBlocks.fluidExousia = new Fluid("nyxFluidExousicWater");
		NyxBlocks.fluidExousia.setDensity(1500).setLuminosity(6).setTemperature(10)
		.setViscosity(500);
		IaSRegistration.register(NyxBlocks.fluidExousia);
		NyxBlocks.exousicWater = new NyxBlockWater("ExousicWater", NyxBlocks.fluidExousia)
		.register();

		NyxBlocks.brickFrozen = new NyxBlockBrickFrozen("FrozenBrick").register();
		NyxBlocks.brickPale = new NyxBlockBrickPale("PaleBrick").register();
		NyxBlocks.brickPaleCracked = new NyxBlockBrickPaleCracked("PaleBrickCracked")
		.register();
		NyxBlocks.gatestone = new NyxBlockGatestone("Gatestone").register();

		NyxBlocks.transmutationAltar = new NyxBlockAltarTransmutation(
				"TransmutationAltar").register();
		NyxBlocks.xpAltar = new NyxBlockAltarExperience("AltarExperience").register();

		NyxBlocks.hookClimbing = new NyxBlockHookClimbing("ClimbingHook").register();
		NyxBlocks.hookTightropeX = new NyxBlockHookTightropeX("HookTightropeX")
		.register();
		NyxBlocks.hookTightropeZ = new NyxBlockHookTightropeZ("HookTightropeZ")
		.register();
		NyxBlocks.ropeY = new NyxBlockRopeY("RopeY").register();
		NyxBlocks.ropeX = new NyxBlockRopeX("RopeX").register();
		NyxBlocks.ropeZ = new NyxBlockRopeZ("RopeZ").register();

		NyxBlocks.hardShadow = new NyxBlockHardShadow("HardShadow").register();
		NyxBlocks.thermalCore = new NyxBlockThermalCore("ThermalCore").register();
		NyxBlocks.thermalAir = new NyxBlockThermalAir("ThermalAir").register();

		GameRegistry.registerTileEntity(NyxTeTransmutationAltar.class,
				"nyxTeTransmutationAltar");
	}
}
