package iceandshadow2.nyx;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.nyx.blocks.*;
import iceandshadow2.util.IaSRegistration;

public class NyxBlocks {
	public static Block stone, cryingObsidian, unstableIce, permafrost, exousicIce, exousicWater;
	
	public static Block oreEchir, oreDevora, oreNavistra, oreCortra, oreNifelhium, oreExousium;
	
	public static Fluid fluidExousia;
	
	public static void init() {
		stone = new NyxBlockStone("Stone").register().setCreativeTab(IaSCreativeTabs.blocks);
		unstableIce = new NyxBlockUnstableIce("UnstableIce").register().setCreativeTab(IaSCreativeTabs.blocks);
		permafrost = new NyxBlockPermafrost("Permafrost").register().setCreativeTab(IaSCreativeTabs.blocks);
		cryingObsidian = new NyxBlockCryingObsidian("CryingObsidian").register().setCreativeTab(IaSCreativeTabs.blocks);
		
		exousicIce = new NyxBlockIce("ExousicIce").register().setCreativeTab(IaSCreativeTabs.blocks);
		
		fluidExousia = new Fluid("nyxFluidExousicWater");
		fluidExousia.setDensity(1500).setLuminosity(5).setTemperature(10).setViscosity(500);
		IaSRegistration.register(fluidExousia);
		exousicWater = new NyxBlockWater("ExousicWater",fluidExousia).register().setCreativeTab(IaSCreativeTabs.blocks);
	}
}
