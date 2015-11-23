package iceandshadow2.nyx.blocks;

import java.util.ArrayList;
import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.render.fx.IaSFxManager;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockThermalCore extends IaSBaseBlockMulti {
	public NyxBlockThermalCore(String id) {
		super(EnumIaSModule.NYX, id, Material.fire, 2);
		setLightOpacity(0);
		setHardness(0.5F);
		setLightColor(1.0F, 0.5F, 0.0F);
		setResistance(NyxBlockStone.RESISTANCE);
		setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F);
		setTickRandomly(true);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) == 1)
			return 7;
		return 3;
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World w, int x,
			int y, int z, EntityPlayer ep,
			int p_149727_6_, float p_149727_7_, float p_149727_8_,
			float p_149727_9_) {
		if(w.getBlockMetadata(x, y, z) == 1)
			return false;
		w.func_147480_a(x, y, z, false);
		w.setBlock(x, y, z, this, 1, 0x2);
		IaSFxManager.spawnParticle(w, "shadowSmokeLarge",
				x+0.5, y+0.5, z+0.5, false, true);
		updateTick(w, x, y, z, w.rand);
		return true;
	}

	@Override
	public void onBlockDestroyedByExplosion(World w, int x, int y, int z,
			Explosion e) {
		w.setBlockToAir(x, y, z);
	}

	@Override
	public void updateTick(World w, int x, int y,
			int z, Random r) {
		if(w.getBlockMetadata(x, y, z) != 1)
			return;
		for(int xit = -1; xit<=1; ++xit) {
			for(int yit = -1; yit<=1; ++yit) {
				for(int zit = -1; zit<=1; ++zit) {
					if(NyxBlockThermalAir.canReplace(w, x+xit, y+yit, z+zit))
						w.setBlock(x+xit, y+1, z+zit, NyxBlocks.thermalAir, 7, 0x3);
				}
			}
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getMobilityFlag() {
		return 0;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		if(world.getBlockMetadata(x,y,z) == 0)
			return super.getDrops(world, x, y, z, metadata, fortune);
		return new ArrayList<ItemStack>();
	}

}
