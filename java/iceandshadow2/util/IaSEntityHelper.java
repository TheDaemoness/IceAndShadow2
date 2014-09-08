package iceandshadow2.util;

import iceandshadow2.IaSFlags;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
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
			ret[2] = (int)(16-Math.abs(x%16));
		else
			ret[2] = (int)(x%16);
		if(z < 0)
			ret[3] = (int)(16-Math.abs(z%16));
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
		return ent.worldObj.getChunkFromChunkCoords(c[0],c[1]).getBlock(c[2], (int)Math.min(y, 255), c[3]);
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
}
