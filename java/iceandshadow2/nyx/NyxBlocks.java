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
	public static Block stone, cryingObsidian, unstableIce, permafrost,
	exousicIce, exousicWater, crystalBloodstone, crystalExousium,
	thornyVines, silkBerryPod, infestLog, infestLeaves, poisonLeaves, poisonLog,
	brickFrozen, planks, gatestone, brickPale, brickPaleCracked,
	hookClimbing, transmutationAltar, examinationTable;

	public static Block oreEchir, oreDevora, oreNavistra, oreCortra,
	oreNifelhium, oreExousium, oreDraconium, oreGemstone;

	public static IaSBaseBlockTechnical hookTightropeX, hookTightropeZ, ropeX, ropeY, ropeZ, fakeFurnace;

	public static Fluid fluidExousia;

	public static void init() {
		stone = new NyxBlockStone("Stone").register();
		unstableIce = new NyxBlockUnstableIce("UnstableIce").register();
		permafrost = new NyxBlockPermafrost("Permafrost").register();
		cryingObsidian = new NyxBlockCryingObsidian("CryingObsidian")
		.register();
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
		crystalBloodstone = new NyxBlockCrystalBloodstone("CrystalBloodstone")
		.register();
		crystalExousium = new NyxBlockCrystalExousium("CrystalExousium")
		.register();

		fluidExousia = new Fluid("nyxFluidExousicWater");
		fluidExousia.setDensity(1500).setLuminosity(6).setTemperature(10)
		.setViscosity(500);
		IaSRegistration.register(fluidExousia);
		exousicWater = new NyxBlockWater("ExousicWater", fluidExousia)
		.register();

		brickFrozen = new NyxBlockBrickFrozen("FrozenBrick").register();
		brickPale = new NyxBlockBrickPale("PaleBrick").register();
		brickPaleCracked = new NyxBlockBrickPaleCracked("PaleBrickCracked")
		.register();
		gatestone = new NyxBlockGatestone("Gatestone").register();

		transmutationAltar = new NyxBlockAltarTransmutation(
				"TransmutationAltar").register();

		hookClimbing = new NyxBlockHookClimbing("ClimbingHook").register();
		hookTightropeX = new NyxBlockHookTightropeX("HookTightropeX")
		.register();
		hookTightropeZ = new NyxBlockHookTightropeZ("HookTightropeZ")
		.register();
		ropeY = new NyxBlockRopeY("RopeY").register();
		ropeX = new NyxBlockRopeX("RopeX").register();
		ropeZ = new NyxBlockRopeZ("RopeZ").register();

		GameRegistry.registerTileEntity(NyxTeTransmutationAltar.class,
				"nyxTeTransmutationAltar");
	}
}
