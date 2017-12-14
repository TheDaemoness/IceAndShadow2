package iceandshadow2.ias.util;

import iceandshadow2.IaSFlags;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class IaSWorldHelper {

	public static int getDifficulty(World w) {
		return w.difficultySetting.getDifficultyId();
	}

	public static int getRegionArmorMod(Entity ent) {
		if (ent.dimension != IaSFlags.dim_nyx_id)
			return 0;
		return (int) ((Math.abs(ent.posX) + Math.abs(ent.posZ)) / 500);
	}

	public static int getRegionLevel(Entity ent) {
		if (ent.dimension != IaSFlags.dim_nyx_id)
			return 0;
		final int lvl = IaSWorldHelper.getRegionLevel(ent.worldObj, (int) ent.posX, (int) ent.posY, (int) ent.posZ);
		return lvl + (IaSWorldHelper.getDifficulty(ent.worldObj) >= 3 ? 1 : 0);
	}

	public static int getRegionLevel(World w, int x, int y, int z) {
		final int dist = (int) Math.sqrt(x * x + z * z);
		if (dist >= 196608)
			return 12;
		if (dist >= 98304)
			return 11;
		if (dist >= 49152)
			return 10;
		if (dist >= 24576)
			return 9;
		if (dist >= 12288)
			return 8;
		if (dist >= 6144) // Rare biomes are no longer rerolled.
			return 7;
		if (dist >= 3072) // Rare mob spawns.
			return 6;
		if (dist >= 1536) // Sniper skeletons.
			return 5;
		if (dist >= 768) // Rare biomes stop being rerolled.
			return 4;
		if (dist >= 384) // Knife skeletons.
			return 3;
		if (dist >= 192) // Rare biomes spawn.
			return 2;
		if (dist >= 96) // Magic skeletons + spiders.
			return 1;
		return 0;
	}
}
