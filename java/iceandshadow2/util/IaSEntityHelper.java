package iceandshadow2.util;

import iceandshadow2.nyx.entities.mobs.IIaSMobGetters;
import iceandshadow2.nyx.entities.util.EntityOrbNourishment;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;

public class IaSEntityHelper {
	
	public static boolean isTouchingGround(EntityLivingBase ent) {
		if(ent.isAirBorne)
			return false;
		if(ent.isRiding())
			return false;
		if(ent instanceof IIaSMobGetters && ((IIaSMobGetters)ent).couldFlyFasterWithBoots())
			return false;
		return ent.getEquipmentInSlot(1) == null;
	}
	
	//Returns true is b is in front of a, by some fairly loose definitions of "in front of".
	public static boolean isInFrontOf(Entity a, Entity b) {
		final double xdif = b.posX - a.posX;
		final double zdif = b.posZ - a.posZ;
		final double range = Math.sqrt(xdif * xdif + zdif * zdif);
		if (1 + 2 * range < Math.abs(b.posY - a.posY))
			return false;
		
		//NOTE: Using the notation for polar coordinates I'm used to, z in MC is x, and x in MC is y.
		double ratio = xdif==0?0:Math.abs(xdif/zdif);
		double ang = Math.atan(ratio) * 180.0 / Math.PI;
		if(zdif < 0)
			ang=180-ang;

		double delta = Math.abs(ang-Math.abs(a.rotationYaw));
		return delta <= 75;
	}

	public static EntityItem dropItem(Entity ent, ItemStack par1ItemStack) {
		if (par1ItemStack.stackSize == 0) {
			return null;
		} else {
			final EntityItem entityitem = new EntityItem(ent.worldObj, ent.posX + ent.width / 2, ent.posY,
					ent.posZ + ent.width / 2, par1ItemStack);
			entityitem.delayBeforeCanPickup = 10;
			if (ent.captureDrops) {
				ent.capturedDrops.add(entityitem);
			} else {
				ent.worldObj.spawnEntityInWorld(entityitem);
			}
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
		if (ent instanceof EntityPlayerMP) {
			d3 = ((EntityPlayerMP) ent).theItemInWorldManager.getBlockReachDistance();
		}
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
	
	public static void spawnNourishment(World w, double x, double y, double z, int amount) {
		if(!w.isRemote)
			w.spawnEntityInWorld(new EntityOrbNourishment(w, x, y, z, amount));
	}
	public static void spawnNourishment(Entity who, int amount) {
		spawnNourishment(who.worldObj, who.posX, who.posY, who.posZ, amount);
	}
}
