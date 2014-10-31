package iceandshadow2.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;

public class IaSEntityHelper {
	
	public static int[] splitCoords(long x, long z) {
		int[] ret = new int[4];
		if(x < 0)
			ret[0] = (int)(x/16-1);
		else
			ret[0] = (int)(x/16);
		if(z < 0)
			ret[1] = (int)(z/16-1);
		else
			ret[1] = (int)(z/16);
		if(x < 0)
			ret[2] = (int)(15-Math.abs(x%16));
		else
			ret[2] = (int)(x%16);
		if(z < 0)
			ret[3] = (int)(15-Math.abs(z%16));
		else
			ret[3] = (int)(z%16);
		return ret;
	}
	
	public static Block getBlock(Entity ent, double offsetX, double offsetY, double offsetZ) {
		if(ent.posX < 0)
			offsetX -= 1;
		if(ent.posZ < 0)
			offsetZ -= 1;
		long x = (int)(ent.posX + offsetX);
		long y = (int)(ent.posY + offsetY);
		long z = (int)(ent.posZ + offsetZ);
		int[] c = splitCoords(x,z);
		return ent.worldObj.getChunkFromChunkCoords(c[0],c[1]).getBlock(c[2], (int)Math.min(Math.max(0,y), 255), c[3]);
	}
	
	public static BiomeGenBase getBiome(Entity ent, double offsetX, double offsetZ) {
		if(ent.posX < 0)
			offsetX -= 1;
		if(ent.posZ < 0)
			offsetZ -= 1;
		int x = (int)(ent.posX + offsetX);
		int z = (int)(ent.posZ + offsetZ);
		int[] c = splitCoords(x,z);
		return ent.worldObj.getChunkFromChunkCoords(c[0],c[1]).getBiomeGenForWorldCoords(c[2], c[3], ent.worldObj.getWorldChunkManager());
	}
	
	public static int getLight(Entity ent, double offsetX, double offsetY, double offsetZ) {
		if(ent.posX < 0)
			offsetX -= 1;
		if(ent.posZ < 0)
			offsetZ -= 1;
		int x = (int)(ent.posX + offsetX);
		int y = (int)(ent.posY + offsetY);
		int z = (int)(ent.posZ + offsetZ);
		int[] c = splitCoords(x,z);
		return ent.worldObj.getBlockLightValue(x, y, z);
	}
	
	public static float getTemperatureFloat(Entity ent, double offsetX, double offsetY, double offsetZ) {
		if(ent.posX < 0)
			offsetX -= 1;
		if(ent.posZ < 0)
			offsetZ -= 1;
		int x = (int)(ent.posX + offsetX);
		int y = (int)(ent.posY + offsetY);
		int z = (int)(ent.posZ + offsetZ);
		return getBiome(ent, offsetX, offsetZ).getFloatTemperature(x, y, z);
	}
	
	public static TempCategory getTemperatureCategory(Entity ent, double offsetX, double offsetZ) {
		return getBiome(ent, offsetX, offsetZ).getTempCategory();
	}
	public static Block getBlock(Entity ent) {
		return getBlock(ent,0,0,0);
	}
	
	public static BiomeGenBase getBiome(Entity ent) {
		return getBiome(ent,0,0);
	}
	
	public static float getTemperatureFloat(Entity ent) {
		return getTemperatureFloat(ent,0,0,0);
	}
	
	public static TempCategory getTemperatureCategory(Entity ent) {
		return getTemperatureCategory(ent,0,0);
	}
	
	public static int getLight(Entity ent) {
		return getLight(ent,0,0,0);
	}
	
	public static MovingObjectPosition getObjectPosition(World world, EntityLivingBase ent, boolean flag) {
		float f = 1.0F;
        float f1 = ent.prevRotationPitch + (ent.rotationPitch - ent.prevRotationPitch) * f;
        float f2 = ent.prevRotationYaw + (ent.rotationYaw - ent.prevRotationYaw) * f;
        double d0 = ent.prevPosX + (ent.posX - ent.prevPosX) * (double)f;
        double d1 = ent.prevPosY + (ent.posY - ent.prevPosY) * (double)f + (double)(world.isRemote ? ent.getEyeHeight() - 0.12F : ent.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = ent.prevPosZ + (ent.posZ - ent.prevPosZ) * (double)f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (ent instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP)ent).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return world.func_147447_a(vec3, vec31, flag, !flag, false);
	}
}
