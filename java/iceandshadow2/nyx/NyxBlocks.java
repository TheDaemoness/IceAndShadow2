package iceandshadow2.nyx;

import cpw.mods.fml.common.registry.GameRegistry;
import iceandshadow2.ias.blocks.IaSBaseBlock;
import iceandshadow2.ias.blocks.IaSBaseBlockTechnical;
import iceandshadow2.ias.util.IaSRegistration;
import iceandshadow2.nyx.blocks.*;
import iceandshadow2.nyx.blocks.ore.*;
import iceandshadow2.nyx.blocks.technical.*;
import iceandshadow2.nyx.blocks.utility.*;
import iceandshadow2.nyx.tileentities.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;

public class NyxBlocks {
	public static Block stone, cryingObsidian, unstableIce, permafrost, dirt, exousicIce, exousicWater,
			crystalBloodstone, crystalExousium, thornyVines, silkBerryPod, infestLog, infestLeaves, poisonLeaves,
			poisonLog, brickFrozen, planks, gatestone, brickExousic, brickExousicCracked, hookClimbing,
			transfusionAltar, examinationTable, hardShadow, unstableDevora, xpAltar, icicles, gravel, stoneGrowing,
			airDrain, transfusionAltarBroken, sanctifiedObsidian, snow, salt, infestSpawner, antenna,
			detonator, stableDevora;

	public static Block oreEchir, oreDevora, oreNavistra, oreCortra, oreNifelhium, oreExousium, oreDraconium,
			oreGemstone;


	public static Block rail, railPowered;

	public static IaSBaseBlockTechnical hookTightropeX, hookTightropeZ, ropeX, ropeY, ropeZ, fakeFurnace, stoneMemory,
			virtualLadder;

	public static Fluid fluidExousia;

	public static void init() {
		NyxBlocks.stone = new NyxBlockStone("Stone").register();
		NyxBlocks.gravel = new NyxBlockGravel("Gravel").register();
		NyxBlocks.snow = new NyxBlockSnow("Snow").register();
		NyxBlocks.unstableIce = new NyxBlockUnstableIce("UnstableIce").register();
		NyxBlocks.permafrost = new NyxBlockPermafrost("Permafrost").register();
		NyxBlocks.salt = new NyxBlockSalt("SaltBlock").register();
		NyxBlocks.dirt = new NyxBlockDirt("Dirt").register();
		NyxBlocks.cryingObsidian = new NyxBlockObsidianSanguine("CryingObsidian").register();
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
		NyxBlocks.oreDraconium = Blocks.air; // new
												// NyxBlockOreDraconium("OreDraconium").register();
		NyxBlocks.crystalBloodstone = new NyxBlockCrystalBloodstone("CrystalBloodstone").register();
		NyxBlocks.crystalExousium = new NyxBlockCrystalExousium("CrystalExousium").register();
		NyxBlocks.icicles = new NyxBlockIcicles("Icicles").register();

		NyxBlocks.fluidExousia = new Fluid("nyxFluidExousicWater");
		NyxBlocks.fluidExousia.setDensity(1500).setLuminosity(6).setTemperature(10).setViscosity(500);
		IaSRegistration.register(NyxBlocks.fluidExousia);
		NyxBlocks.exousicWater = new NyxBlockWater("ExousicWater", NyxBlocks.fluidExousia).register();

		NyxBlocks.brickFrozen = new NyxBlockBrickFrozen("FrozenBrick").register();
		NyxBlocks.brickExousic = new NyxBlockBrickExousic("PaleBrick").register();
		NyxBlocks.brickExousicCracked = new NyxBlockBrickExousicCracked("PaleBrickCracked").register();
		NyxBlocks.gatestone = new NyxBlockGatestone("Gatestone").register();
		NyxBlocks.sanctifiedObsidian = new NyxBlockObsidianSanctified("SanctifiedObsidian").register();

		NyxBlocks.transfusionAltar = new NyxBlockAltarTransfusion("TransmutationAltar").register();
		NyxBlocks.transfusionAltarBroken = new NyxBlockAltarTransfusionBroken("TransmutationAltarBroken")
				.register();
		NyxBlocks.xpAltar = new NyxBlockAltarExperience("AltarExperience").register();
		NyxBlocks.infestSpawner = new NyxBlockInfestedSpawner("InfestedSpawner").register();

		NyxBlocks.hookClimbing = new NyxBlockHookClimbing("ClimbingHook").register();
		NyxBlocks.hookTightropeX = new NyxBlockHookTightropeX("HookTightropeX").register();
		NyxBlocks.hookTightropeZ = new NyxBlockHookTightropeZ("HookTightropeZ").register();
		NyxBlocks.ropeY = new NyxBlockRopeY("RopeY").register();
		NyxBlocks.ropeX = new NyxBlockRopeX("RopeX").register();
		NyxBlocks.ropeZ = new NyxBlockRopeZ("RopeZ").register();

		NyxBlocks.hardShadow = new NyxBlockHardShadow("HardShadow").register();
		NyxBlocks.unstableDevora = new NyxBlockUnstableDevora("UnstableDevora").register();
		NyxBlocks.airDrain = new NyxBlockAir("Air").register();

		GameRegistry.registerTileEntity(NyxTeTransfusionAltar.class, "nyxTeTransmutationAltar");
		GameRegistry.registerTileEntity(NyxTeRefCount.class, "nyxTeRefCount");

		NyxBlocks.stoneMemory = new NyxBlockStoneMemory("StoneRegen").register();
		NyxBlocks.stoneGrowing = new NyxBlockStoneGrowing("StoneGrowing").register();
		NyxBlocks.virtualLadder = new NyxBlockVirtualLadder("StoneVirtualLadder").register();

		NyxBlocks.rail = new NyxBlockRail("Rail", false, true).register();
		NyxBlocks.railPowered = new NyxBlockRailPowered("RailPowered").register();
		
		NyxBlocks.antenna = new NyxBlockAntenna("Antenna").register();
		NyxBlocks.detonator = new NyxBlockDetonator("Detonator").register();
		NyxBlocks.stableDevora = new NyxBlockStableDevora("StableDevora").register();
	}
}
