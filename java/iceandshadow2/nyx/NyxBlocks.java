package iceandshadow2.nyx;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.nyx.blocks.*;
import iceandshadow2.nyx.blocks.ore.*;
import iceandshadow2.util.IaSRegistration;

public class NyxBlocks {
	public static Block stone, cryingObsidian, unstableIce, permafrost, exousicIce, exousicWater, crystalBloodstone;
	
	public static Block oreEchir, oreDevora, oreNavistra, oreCortra, oreNifelhium, oreExousium, oreDraconium;
	
	public static Fluid fluidExousia;
	
	public static void init() {
		stone = new NyxBlockStone("Stone").register().setCreativeTab(IaSCreativeTabs.blocks);
		unstableIce = new NyxBlockUnstableIce("UnstableIce").register().setCreativeTab(IaSCreativeTabs.blocks);
		permafrost = new NyxBlockPermafrost("Permafrost").register().setCreativeTab(IaSCreativeTabs.blocks);
		cryingObsidian = new NyxBlockCryingObsidian("CryingObsidian").register().setCreativeTab(IaSCreativeTabs.blocks);
		
		exousicIce = new NyxBlockIce("ExousicIce").register().setCreativeTab(IaSCreativeTabs.blocks);
		
		oreEchir = new NyxBlockOreEchir("OreEchir").register().setCreativeTab(IaSCreativeTabs.blocks);
		oreDevora = new NyxBlockOreDevora("OreDevora").register().setCreativeTab(IaSCreativeTabs.blocks);
		oreNavistra = new NyxBlockOreNavistra("OreNavistra").register().setCreativeTab(IaSCreativeTabs.blocks);
		oreCortra = new NyxBlockOreCortra("OreCortra").register().setCreativeTab(IaSCreativeTabs.blocks);
		oreNifelhium = new NyxBlockOreNifelhium("OreNifelhium").register().setCreativeTab(IaSCreativeTabs.blocks);
		oreExousium = new NyxBlockOreExousium("OreExousium").register().setCreativeTab(IaSCreativeTabs.blocks);
		oreDraconium = new NyxBlockOreDraconium("OreDraconium").register().setCreativeTab(IaSCreativeTabs.blocks);
		
		fluidExousia = new Fluid("nyxFluidExousicWater");
		fluidExousia.setDensity(1500).setLuminosity(6).setTemperature(10).setViscosity(500);
		IaSRegistration.register(fluidExousia);
		exousicWater = new NyxBlockWater("ExousicWater",fluidExousia).register().setCreativeTab(IaSCreativeTabs.blocks);
		crystalBloodstone = new NyxBlockCrystalBloodstone("CrystalBloodstone").register().setCreativeTab(IaSCreativeTabs.blocks);
	}
}
