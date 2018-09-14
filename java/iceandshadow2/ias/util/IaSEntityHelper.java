package iceandshadow2.ias.util;

import java.util.List;

import iceandshadow2.ias.ai.EnumIaSSenses;
import iceandshadow2.ias.ai.IIaSMobGetters;
import iceandshadow2.ias.ai.IIaSSensate;
import iceandshadow2.nyx.entities.util.EntityOrbNourishment;
import iceandshadow2.styx.Styx;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;
import net.minecraft.world.chunk.Chunk;

public class IaSEntityHelper {

	public static EntityItem dropItem(Entity ent, ItemStack par1ItemStack) {
		if (par1ItemStack == null || ent == null)
			return null;
		if (par1ItemStack.stackSize == 0)
			return null;
		else {
			final EntityItem entityitem = new EntityItem(ent.worldObj, ent.posX + ent.width / 2, ent.posY,
					ent.posZ + ent.width / 2, par1ItemStack);
			entityitem.delayBeforeCanPickup = 10;
			if (ent.captureDrops)
				ent.capturedDrops.add(entityitem);
			else
				ent.worldObj.spawnEntityInWorld(entityitem);
			return entityitem;
		}
	}

	public static BiomeGenBase getBiome(Entity ent) {
		return IaSEntityHelper.getBiome(ent, 0, 0);
	}

	public static BiomeGenBase getBiome(Entity ent, double offsetX, double offsetZ) {
		if (ent.posX < 0)
			offsetX -= 1;
		if (ent.posZ < 0)
			offsetZ -= 1;
		final int x = (int) (ent.posX + offsetX);
		final int z = (int) (ent.posZ + offsetZ);
		final int[] c = IaSEntityHelper.splitCoords(x, z);
		return ent.worldObj.getChunkFromChunkCoords(c[0], c[1]).getBiomeGenForWorldCoords(c[2], c[3],
				ent.worldObj.getWorldChunkManager());
	}

	public static Block getBlock(Entity ent) {
		return IaSEntityHelper.getBlock(ent, 0, 0, 0);
	}

	public static Block getBlock(Entity ent, double offsetX, double offsetY, double offsetZ) {
		if (ent.posX < 0)
			offsetX -= 1;
		if (ent.posZ < 0)
			offsetZ -= 1;
		final long x = (int) (ent.posX + offsetX);
		final long y = (int) (ent.posY + offsetY);
		final long z = (int) (ent.posZ + offsetZ);
		final int[] c = IaSEntityHelper.splitCoords(x, z);
		return ent.worldObj.getChunkFromChunkCoords(c[0], c[1]).getBlock(c[2], (int) Math.min(Math.max(0, y), 255),
				c[3]);
	}

	public static int getLight(Entity ent) {
		return IaSEntityHelper.getLight(ent, 0, 0, 0);
	}

	public static int getLight(Entity ent, double offsetX, double offsetY, double offsetZ) {
		if (ent.posX < 0)
			offsetX -= 1;
		if (ent.posZ < 0)
			offsetZ -= 1;
		final int x = (int) (ent.posX + offsetX);
		final int y = (int) (ent.posY + offsetY);
		final int z = (int) (ent.posZ + offsetZ);
		IaSEntityHelper.splitCoords(x, z);
		return ent.worldObj.getBlockLightValue(x, y, z);
	}

	public static int getMagicLevel(EntityLivingBase elb) {
		if (elb instanceof EntityPlayer)
			return ((EntityPlayer) elb).experienceLevel;
		if (elb instanceof EntityMob) {
			final int maxhealth = (int) elb.getMaxHealth();
			return (maxhealth * maxhealth / 200);
		}
		return 0;
	}

	public static MovingObjectPosition getObjectPosition(World world, EntityLivingBase ent, boolean flag) {
		final float f = 1.0F;
		final float f1 = ent.prevRotationPitch + (ent.rotationPitch - ent.prevRotationPitch) * f;
		final float f2 = ent.prevRotationYaw + (ent.rotationYaw - ent.prevRotationYaw) * f;
		final double d0 = ent.prevPosX + (ent.posX - ent.prevPosX) * f;
		final double d1 = ent.prevPosY + (ent.posY - ent.prevPosY) * f
				+ (world.isRemote ? ent.getEyeHeight() - 0.12F : ent.getEyeHeight()); // isRemote
																						// check
																						// to
																						// revert
																						// changes
																						// to
		// ray trace position due to adding
		// the eye height clientside and
		// player yOffset differences
		final double d2 = ent.prevPosZ + (ent.posZ - ent.prevPosZ) * f;
		final Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
		final float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		final float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		final float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		final float f6 = MathHelper.sin(-f1 * 0.017453292F);
		final float f7 = f4 * f5;
		final float f8 = f3 * f5;
		double d3 = 5.0D;
		if (ent instanceof EntityPlayerMP)
			d3 = ((EntityPlayerMP) ent).theItemInWorldManager.getBlockReachDistance();
		final Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
		return world.func_147447_a(vec3, vec31, flag, !flag, false);
	}

	public static TempCategory getTemperatureCategory(Entity ent) {
		return IaSEntityHelper.getTemperatureCategory(ent, 0, 0);
	}

	public static TempCategory getTemperatureCategory(Entity ent, double offsetX, double offsetZ) {
		return IaSEntityHelper.getBiome(ent, offsetX, offsetZ).getTempCategory();
	}

	public static float getTemperatureFloat(Entity ent) {
		return IaSEntityHelper.getTemperatureFloat(ent, 0, 0, 0);
	}

	public static float getTemperatureFloat(Entity ent, double offsetX, double offsetY, double offsetZ) {
		if (ent.posX < 0)
			offsetX -= 1;
		if (ent.posZ < 0)
			offsetZ -= 1;
		final int x = (int) (ent.posX + offsetX);
		final int y = (int) (ent.posY + offsetY);
		final int z = (int) (ent.posZ + offsetZ);
		return IaSEntityHelper.getBiome(ent, offsetX, offsetZ).getFloatTemperature(x, y, z);
	}

	// Returns true is b is in front of a, by some fairly loose definitions of
	// "in front of".
	public static boolean isInFrontOf(Entity a, Entity b) {
		final double xdif = b.posX - a.posX;
		final double zdif = b.posZ - a.posZ;
		double ratio;

		if (1 + 2 * Math.sqrt(xdif * xdif + zdif * zdif) < (b.posY - a.posY))
			return false;

		if (xdif == 0.0)
			ratio = zdif / xdif;
		else
			ratio = 0;

		double ang = Math.atan(ratio) * 180.0 / Math.PI;
		if (xdif < 0)
			ang += 180.0;
		else if (zdif < 0)
			ang += 360.0;

		return ((ang - a.rotationYaw) % 360 <= 180);
	}

	public static boolean isTouchingGround(EntityLivingBase ent) {
		if (ent.isAirBorne)
			return false;
		if (ent.isRiding())
			return false;
		if (ent instanceof IIaSMobGetters && ((IIaSMobGetters) ent).couldFlyFasterWithBoots())
			return false;
		return ent.getEquipmentInSlot(1) == null;
	}

	public static void sensesEvent(EnumIaSSenses sense, Entity what, float range) {
		if (what.worldObj.isRemote)
			return;
		final List l = what.worldObj.getEntitiesWithinAABB(IIaSSensate.class,
				AxisAlignedBB.getBoundingBox(what.posX - range, what.posY - range, what.posZ - range, what.posX + range,
						what.posY + range, what.posZ + range));
		for (final Object o : l) {
			final Entity ent = (Entity) o;
			final IIaSSensate senses = (IIaSSensate) o;
			range = Math.min(range, senses.getMaxSenseRange(sense));
			if (ent.getDistanceSq(what.posX, what.posY, what.posZ) > range * range)
				continue;
			senses.notice(what, sense);
		}
	}

	public static void spawnItems(World w, List<ItemStack> items, int x, int y, int z) {
		spawnItems(w, items, x, y, z, 0, 0, 0, 1, 1, 1);
	}

	public static void spawnItems(World w, List<ItemStack> items, int x, int y, int z, Block bl) {
		spawnItems(w, items, x, y, z, bl.getBlockBoundsMinX(), bl.getBlockBoundsMinY(), bl.getBlockBoundsMinZ(),
				bl.getBlockBoundsMaxX(), bl.getBlockBoundsMaxY(), bl.getBlockBoundsMaxZ());
	}

	public static void spawnItems(World w, List<ItemStack> items, int x, int y, int z, double xL, double yL, double zL,
			double xH, double yH, double zH) {
		final double xW = (xH - xL) / 2, yW = (yH - yL) / 2, zW = (zH - zL) / 2;
		for (final ItemStack is : items) {
			final double xOffset = w.rand.nextFloat() * xW + xL + xW / 2,
					yOffset = w.rand.nextFloat() * yW + yL + yW / 2, zOffset = w.rand.nextFloat() * zW + zL + zW / 2;
			final EntityItem eis = new EntityItem(w, x + xOffset, y + yOffset, z + zOffset, is);
			eis.delayBeforeCanPickup = 10; // Standard Minecraft delay.
			w.spawnEntityInWorld(eis);
		}
	}

	public static void spawnNourishment(Entity who, int amount) {
		spawnNourishment(who.worldObj, who.posX, who.posY, who.posZ, amount);
	}

	public static void spawnNourishment(World w, double x, double y, double z, int amount) {
		if (!w.isRemote)
			w.spawnEntityInWorld(new EntityOrbNourishment(w, x, y, z, amount));
	}

	public static int[] splitCoords(long x, long z) {
		final int[] ret = new int[4];
		if (x < 0)
			ret[0] = (int) (x / 16 - 1);
		else
			ret[0] = (int) (x / 16);
		if (z < 0)
			ret[1] = (int) (z / 16 - 1);
		else
			ret[1] = (int) (z / 16);
		if (x < 0)
			ret[2] = (int) (15 - Math.abs(x % 16));
		else
			ret[2] = (int) (x % 16);
		if (z < 0)
			ret[3] = (int) (15 - Math.abs(z % 16));
		else
			ret[3] = (int) (z % 16);
		return ret;
	}

	public static void teleport(EntityLivingBase ent, int x, int y, int z) {
		if (!ent.worldObj.isRemote)
			return;
		final Chunk ck = IaSWorldHelper.loadChunk(ent.worldObj, x, z);
		if (ck == null || !ck.isChunkLoaded || ck.isEmpty())
			return;
		for (int gateY = 255; gateY >= 4; --gateY)
			if (ck.getBlock(x & 15, y, z & 15) == Styx.gatestone) {
				y = gateY;
				break;
			}
		ent.setSneaking(false);
		ent.setPositionAndUpdate(x + 0.5, y + 1, z + 0.5);
	}
}
