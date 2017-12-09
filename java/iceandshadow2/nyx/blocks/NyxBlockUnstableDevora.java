package iceandshadow2.nyx.blocks;

import java.util.ArrayList;
import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.render.fx.IaSFxManager;
import iceandshadow2.util.IaSBlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockUnstableDevora extends IaSBaseBlockSingle implements IIaSTechnicalBlock {
	public NyxBlockUnstableDevora(String id) {
		super(EnumIaSModule.NYX, id, Material.clay);
		setLightOpacity(0);
		setHardness(0.25F);
		setLightColor(1.0F, 0.5F, 0.0F);
		setResistance(0.5F);
		setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F);
		setTickRandomly(true);
		setLuminescence(0.5F);
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList<ItemStack> ises = new ArrayList<ItemStack>(1);
		ises.add(new ItemStack(NyxItems.devora));
		return ises;
	}

	@Override
	public int getMixedBrightnessForBlock(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_) {
		return p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, 15);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return new ItemStack(NyxItems.devora);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return false;
	}

	@Override
	public void onBlockDestroyedByExplosion(World w, int x, int y, int z, Explosion e) {
		updateTick(w, x, y, z, w.rand);
	}

	@Override
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity ent) {
		updateTick(w, x, y, z, w.rand);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		if (!w.getBlock(x, y - 1, z).isSideSolid(w, x, y, z, ForgeDirection.UP))
			updateTick(w, x, y, z, w.rand);
	}

	@Override
	public void randomDisplayTick(World w, int x, int y, int z, Random r) {
		IaSFxManager.spawnParticle(w, "vanilla_lava", x + 0.5, y, z + 0.5, 0.0, 0.0, 0.0, false, r.nextBoolean());
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		if (!w.isRemote) {
			IaSBlockHelper.breakBlock(w, x, y, z, false);
			w.createExplosion(null, x + 0.5, y + 0.25, z + 0.5, 4.5F, true);
		}
	}

}
