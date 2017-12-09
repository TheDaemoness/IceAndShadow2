package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxBlockIce extends IaSBaseBlockSingle {

	public NyxBlockIce(String texName) {
		super(EnumIaSModule.NYX, texName, Material.ice);
		setLuminescence(0.3F);
		setLightOpacity(7);
		setHardness(0.5F);
		setResistance(5.0F);
		setTickRandomly(true);
		setStepSound(Block.soundTypeGlass);
		slipperiness = 0.99F;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.FROZEN;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList is = new ArrayList<ItemStack>();
		final int r = 2 + world.rand.nextInt(3);
		for (int i = 0; i < r; ++i)
			is.add(new ItemStack(NyxItems.exousicIceShard, 1));
		return is;
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
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int s) {
		if (w.getBlock(x, y, z) == this)
			return false;
		return super.shouldSideBeRendered(w, x, y, z, s);
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		for (int xit = -1; xit <= 1; ++xit)
			for (int zit = -1; zit <= 1; ++zit) {
				if (xit != 0 && zit != 0)
					continue;
				if (w.isAirBlock(x + xit, y, z + zit) && r.nextInt(3) != 0)
					if (w.getBlock(x + xit, y - 1, z + zit) == NyxBlocks.exousicWater)
						w.setBlock(x + xit, y, z + zit, this);
			}
	}
}
