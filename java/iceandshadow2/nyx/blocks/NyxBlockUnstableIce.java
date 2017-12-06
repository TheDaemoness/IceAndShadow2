package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.blocks.IaSBaseBlockFalling;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxBlockUnstableIce extends IaSBaseBlockFalling {
	public NyxBlockUnstableIce(String par1) {
		super(EnumIaSModule.NYX, par1, Material.ice);
		setStepSound(Block.soundTypeGlass);
		setHardness(0.1F);
		setResistance(0.5F);
		setLightOpacity(4);
		setTickRandomly(true);
		this.slipperiness = 1.08F;
		this.setHarvestLevel("spade", 0);
	}

	@Override
	public int getMobilityFlag() {
		return 0;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int s) {
		if (w.getBlock(x, y, z) == this)
			return false;
		return super.shouldSideBeRendered(w, x, y, z, s);
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		super.updateTick(par1World, par2, par3, par4, par5Random);
		if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) >= 14) {
			if (par1World.provider.isHellWorld)
				par1World.setBlockToAir(par2, par3, par4);
			else
				par1World.setBlock(par2, par3, par4, Blocks.water);
		}
	}
	
	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.FROZEN;
	}
}
