package iceandshadow2.ias.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.interfaces.IIaSBlockLight;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.BlockFluidBase;

public class IaSBlockHelper {
	/**
	 * Forcibly harvests the specified block.
	 * Does NOT spawn the block's items, rather leaving that task up to the callee.
	 * Note that blocks that perform additional actions in harvestBlock may malfunction.
	 * @param fortune The fortune level. Negative levels imply silk touch with a fortune level = |fortune|-1
	 */
	public static List<ItemStack> harvest(EntityPlayer breaker, int x, int y, int z, int fortune, float chance, boolean hunger) {
		final World w = breaker.worldObj;
		final Block bl = w.getBlock(x, y, z);
		final int meta = w.getBlockMetadata(x, y, z);
		if(hunger)
			breaker.addExhaustion(0.025F);
		if(isAir(bl) || bl.isAir(w, x, y, z))
			return Arrays.asList();
		breaker.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(bl)], 1);
		
		final boolean silktouch = fortune<0;
		fortune = Math.abs(fortune)-(silktouch?1:0);
		final ArrayList ret = new ArrayList<ItemStack>();
        if (!w.isRemote && !w.restoringBlockSnapshots) {
    		ArrayList<ItemStack> items;
        	if(silktouch && bl.canSilkHarvest(w, breaker, x, y, z, meta)) {
        		items = (ArrayList<ItemStack>)Arrays.asList(new ItemStack(Item.getItemFromBlock(bl)));
        	} else
        		items = bl.getDrops(w, x, y, z, meta, fortune);
            chance = ForgeEventFactory.fireBlockHarvesting(items, w, bl, x, y, z, meta, fortune, chance, silktouch, breaker);

            ret.ensureCapacity(items.size());
            for (ItemStack is : items) {
                if (w.rand.nextFloat() <= chance)
                    ret.add(is);
            }
        }
        bl.onBlockHarvested(w, x, y, z, meta, breaker);
        bl.onBlockDestroyedByPlayer(w, x, y, z, meta);
        if(w.getBlock(x, y, z) == bl && w.getBlockMetadata(x, y, z) == meta)
        	breakBlock(w, x, y, z, false);
        return ret;
	}
	public static List<ItemStack> harvest(EntityPlayer breaker, int x, int y, int z, int fortune, float chance) {
		return harvest(breaker, x, y, z, fortune, chance, true);
	}
	public static List<ItemStack> harvest(EntityPlayer breaker, int x, int y, int z, int fortune) {
		return harvest(breaker, x, y, z, fortune, 1f);
	}
	public static List<ItemStack> harvest(EntityPlayer breaker, int x, int y, int z) {
		return harvest(breaker, x, y, z, EnchantmentHelper.getFortuneModifier(breaker));
	}
	
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
		if (dir.offsetX == 0) {
			xN += r.nextDouble() * size - size / 2.0;
		}
		double yN = 0.5 + 0.5 * dir.offsetY;
		if (dir.offsetY == 0) {
			yN += r.nextDouble() * size - size / 2.0;
		}
		double zN = 0.5 + 0.5 * dir.offsetZ;
		if (dir.offsetZ == 0) {
			zN += r.nextDouble() * size - size / 2.0;
		}
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
	public static int getDefaultLight(Block block, int meta) {
		if(block instanceof IIaSBlockLight)
			return ((IIaSBlockLight)block).getLightValue(meta);
		return block.getLightValue();
	}
}
