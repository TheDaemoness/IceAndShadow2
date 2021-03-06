package iceandshadow2.ias.util;

import iceandshadow2.IaSFlags;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class IaSWorldHelper {

	/**
	 * Loads a chunk at the specified block coordinates.
	 */
	public static Chunk loadChunk(World w, int x, int z) {
		final Chunk ck = w.getChunkFromChunkCoords(x/16-(x<0?1:0), z/16-(z<0?1:0));
		if(ck == null || ck.isEmpty() || !ck.isChunkLoaded)
			return w.getChunkProvider().loadChunk(
				x/16-(x<0?1:0),
				z/16-(z<0?1:0));
		else return ck;
	}

	public static int getDifficulty(World w) {
		if(w == null)
			return 2;
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

	/**
	 * IaS's screwy in-house distance formula. Do not feed to infants.
	 * Values returned are approximately double what they should be.
	 */
	public static long distance2(long x, long z) {
		x = Math.abs(x);
		z = Math.abs(z);
		return Math.max(x, z)*2+Math.min(x,z);
	}

	public static int getRegionLevel(World w, int x, int y, int z) {
		final int dist = (int)distance2(x, z)/2;
		if (dist < 96)
			return 0;
		if (dist < 192)
			return 1;
		if (dist < 384)
			return 2;
		if (dist < 768)
			return 3;
		if (dist < 1536)
			return 4;
		if (dist < 3072)
			return 5;
		if (dist < 6144)
			return 6;
		if (dist < 12288)
			return 7;
		if (dist < 24576)
			return 8;
		return 9;
	}
}
