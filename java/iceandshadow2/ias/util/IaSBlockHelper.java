package iceandshadow2.ias.util;

import java.util.Random;

import iceandshadow2.api.EnumIaSAspect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;

public class IaSBlockHelper {
	public static boolean breakBlock(World w, int x, int y, int z) {
		return breakBlock(w, x, y, z, true);
	}

	public static boolean breakBlock(World w, int x, int y, int z, boolean drop) {
		return w.func_147480_a(x, y, z, drop);
	}

	public static Vec3 getBlockSideCoords(int x, int y, int z, ForgeDirection dir) {
		final double xN = 0.5 + 0.5 * dir.offsetX;
		final double yN = 0.5 + 0.5 * dir.offsetY;
		final double zN = 0.5 + 0.5 * dir.offsetZ;
		return Vec3.createVectorHelper(x - 0.05 + 1.10 * xN, y - 0.05 + 1.10 * yN, z - 0.05 + 1.10 * zN);
	}

	public static Vec3 getBlockSideCoords(int x, int y, int z, ForgeDirection dir, Random r, float size) {
		double xN = 0.5 + 0.5 * dir.offsetX;
		if (dir.offsetX == 0)
			xN += r.nextDouble() * size - size / 2.0;
		double yN = 0.5 + 0.5 * dir.offsetY;
		if (dir.offsetY == 0)
			yN += r.nextDouble() * size - size / 2.0;
		double zN = 0.5 + 0.5 * dir.offsetZ;
		if (dir.offsetZ == 0)
			zN += r.nextDouble() * size - size / 2.0;
		return Vec3.createVectorHelper(x - 0.05 + 1.10 * xN, y - 0.05 + 1.10 * yN, z - 0.05 + 1.10 * zN);
	}

	/**
	 * Get the terrain height at a certain location.
	 */
	public static int getHeight(World w, int x, int z) {
		return w.getTopSolidOrLiquidBlock(x, z);
	}

	public static boolean isAdjacent(IBlockAccess w, int x, int y, int z, Block bl) {
		for (int i = 0; i < ForgeDirection.values().length; ++i) {
			final ForgeDirection dir = ForgeDirection.getOrientation(i);
			if (bl.getClass().isInstance(w.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)))
				return true;
		}
		return false;
	}
	
	public static boolean isAdjacent(IBlockAccess w, int x, int y, int z, EnumIaSAspect aspect) {
		for (int i = 0; i < ForgeDirection.values().length; ++i) {
			final ForgeDirection dir = ForgeDirection.getOrientation(i);
			if (EnumIaSAspect.getAspect(w.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) == aspect)
				return true;
		}
		return false;
	}

	public static boolean isAir(Block bl) {
		return bl == null || bl.getMaterial() instanceof MaterialTransparent;
	}

	public static boolean isFluid(Block bl) {
		return bl instanceof BlockLiquid || bl instanceof BlockFluidBase;
	}

	public static boolean isOneOf(World wld, Block bl, Block... bls) {
		for (final Block blcmp : bls)
			if (blcmp == bl)
				return true;
		return false;
	}

	public static boolean isTransient(World w, int x, int y, int z) {
		final Block bl = w.getBlock(x, y, z);
		if (isAir(bl))
			return true;
		if (bl.isReplaceable(w, x, y, z))
			return true;
		return false;
	}

	public static void makeSpawner(World w, int x, int y, int z, String entityName) {
		w.setBlock(x, y, z, Blocks.mob_spawner);
		final TileEntityMobSpawner tes = (TileEntityMobSpawner) w.getTileEntity(x, y, z);
		tes.func_145881_a().setEntityName(entityName);
	}
}
