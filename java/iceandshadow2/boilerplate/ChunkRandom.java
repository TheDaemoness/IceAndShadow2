package iceandshadow2.boilerplate;

import java.util.Random;

import net.minecraft.world.World;

/**
 * Implements a wrapper for a PRNG designed for providing random numbers at certain coordinates.
 * Extends Java's Random class, which is an LCG.
 */
public class ChunkRandom extends Random {
	public static long calculateSeed(long tempseed, int modifier, int x, int z) {
		x = (int)Double.doubleToLongBits(Math.expm1(x));
		z = (int)Double.doubleToLongBits(Math.expm1(z));
		modifier = new Random((long)modifier+(long)(x*z)+x+z).nextInt();
		tempseed = (tempseed >>> (modifier & 63)) | (tempseed << (64-(modifier & 63)));
		tempseed ^= (modifier ^ x) | ((long)(~modifier ^ z) << 32);
		return tempseed;
	}
	public ChunkRandom() {
		super(0);
	}
	public ChunkRandom(long seed, int modifier, int x, int z) {
		super(calculateSeed(seed, modifier, x, z));
	}
	public void setSeed(long seed, int modifier, int x, int z) {
		this.setSeed(calculateSeed(seed, modifier, x, z));
	}
}
