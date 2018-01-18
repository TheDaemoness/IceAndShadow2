package iceandshadow2.nyx.world;

import iceandshadow2.IaSExecutor;
import iceandshadow2.IaSFuture;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.blocks.NyxBlockAir;
import iceandshadow2.nyx.world.gen.ruins.GenRuinsCentral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

public class NyxChunkProvider implements IChunkProvider {

	private final Random rand;
	private final NoiseGeneratorOctaves[] noiseGen;
	// private final NoiseGeneratorPerlin noiseGenStone;
	// public NoiseGeneratorOctaves noiseGenPublic;
	/**
	 * Reference to the World object.
	 */
	private final World worldObj;

	private final double[] heightmap;
	private final float[] parabolicField;
	final byte magic = 5; //DO NOT CHANGE.
	final int radius = 7;
	//private final double[] stoneNoise = new double[256];

	private final Block[] ablock;
	private final byte[] abyte;

	//private BiomeGenBase[] biomesForGeneration;
	private final double[][] noiseArr;

	public NyxChunkProvider(World par1World, long par2, boolean par4) {
		noiseArr = new double[][] { null, null, null, null };
		ablock = new Block[1 << 16];
		abyte = new byte[1 << 16];
		worldObj = par1World;
		rand = new Random(par2);
		noiseGen = new NoiseGeneratorOctaves[4];
		noiseGen[0] = new NoiseGeneratorOctaves(rand, 16);
		noiseGen[1] = new NoiseGeneratorOctaves(rand, 16);
		noiseGen[2] = new NoiseGeneratorOctaves(rand, 8);
		noiseGen[3] = new NoiseGeneratorOctaves(rand, 16);
		// noiseGenStone = new NoiseGeneratorPerlin(rand, 4);
		// this.noiseGenPublic = new NoiseGeneratorOctaves(this.rand, 10);
		heightmap = new double[(int)magic*magic*33];
		
		parabolicField = new float[(int)radius*radius*4];
		for (int j = -radius; j <= radius; ++j)
			for (int k = -radius; k <= radius; ++k) {
				final float f = 1f / MathHelper.sqrt_float(j * j + k * k + 1);
				parabolicField[j + radius + (k + radius) * radius] = f;
			}
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public boolean chunkExists(int var1, int var2) {
		return true;
	}

	@Override
	/**
	 * Get nearest structure.
	 */
	public ChunkPosition func_147416_a(World var1, String var2, int var3, int var4, int var5) {
		return null;
	}

	public BiomeGenBase[] genTerrain(final int x, final int z, final Block[] blockArr, final byte[] metaArr) {
		final byte levelWater = 63;
		final double heightmapScale = 0.125D;
		final double hmdeltaScale = 0.25D;
		
		final BiomeGenBase[] biomes = initNoiseField(x, z);

		class DoGenTerrain implements Runnable {
			final int xit, zit;
			
			public DoGenTerrain(int xi, int zi) {
				xit = xi;
				zit = zi;
			}
			@Override
			public void run() {
				final int l = xit * 5;
				final int i1 = (xit + 1) * 5;
				final int k1 = (l + zit) * 33;
				final int l1 = (l + zit + 1) * 33;
				final int i2 = (i1 + zit) * 33;
				final int j2 = (i1 + zit + 1) * 33;
				for (int yi = 0; yi < 32; ++yi) {
					double d1 = heightmap[k1 + yi];
					double d2 = heightmap[l1 + yi];
					double d3 = heightmap[i2 + yi];
					double d4 = heightmap[j2 + yi];
					final double d5 = (heightmap[k1 + yi + 1] - d1) * heightmapScale;
					final double d6 = (heightmap[l1 + yi + 1] - d2) * heightmapScale;
					final double d7 = (heightmap[i2 + yi + 1] - d3) * heightmapScale;
					final double d8 = (heightmap[j2 + yi + 1] - d4) * heightmapScale;
					for (int yj = 0; yj < 8; ++yj) {
						double d10 = d1;
						double d11 = d2;
						final double d12 = (d3 - d1) * hmdeltaScale;
						final double d13 = (d4 - d2) * hmdeltaScale;
						for (int i3 = 0; i3 < 4; ++i3) {
							int arrIndex = i3 + xit * 4 << 12 | 0 + zit * 4 << 8 | yi * 8 + yj;
							arrIndex -= 256;
							final double d16 = (d11 - d10) * hmdeltaScale;
							double d15 = d10 - d16;
							
							for (int k3 = 0; k3 < 4; ++k3) {
								arrIndex += 256;
								if ((d15 += d16) > 0.0D)
									blockArr[arrIndex] = NyxBlocks.stone;
								else if (yi * 8 + yj < levelWater) {
									metaArr[arrIndex] = 15;
									blockArr[arrIndex] = NyxBlocks.exousicWater;
								} else if (yi * 8 + yj < NyxBlockAir.ATMOS_HEIGHT)
									blockArr[arrIndex] = Blocks.air;
								else
									blockArr[arrIndex] = NyxBlocks.air;
							}
							d10 += d12;
							d11 += d13;
						}
						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}		
			}
		};
		//NOTE: Calculate all but the last bit of terrain on seperate threads.
		IaSFuture[] fs = new IaSFuture[15];
		for (int i = 0; i < 15; ++i)
			fs[i] = IaSExecutor.push(new DoGenTerrain(i&3, i>>2));
		new DoGenTerrain(3, 3).run();
		for (int i = 0; i < 15; ++i)
			fs[i].get();
		return biomes;
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the
	 * given location.
	 */
	@Override
	public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_) {
		final BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
		return biomegenbase.getSpawnableList(p_73155_1_);
	}

	private BiomeGenBase[] initNoiseField(final int x, final int z) {
		class DoNoiseOctaveInit implements Runnable {
			final private int which;
			final private int p, q, r;
			final public double a, b, c;
			boolean useFull;

			DoNoiseOctaveInit(int which, int p, int q, double a, double b, double c, int r) {
				useFull = false;
				this.which = which;
				this.p = p;
				this.q = q;
				this.r = r;
				this.a = a;
				this.b = b;
				this.c = c;
			}
			DoNoiseOctaveInit(int which, int p, int q, double a, double b, double c) {
				this(which, p, q, a, b, c, 0);
			}

			DoNoiseOctaveInit(int which, int p, int q, int r, double a, double b, double c) {
				this(which, p, q, a, b, c, r);
				useFull = true;
			}

			@Override
			public void run() {
				noiseArr[which] = (useFull
						? noiseGen[which].generateNoiseOctaves(noiseArr[which], x*4, 0, z*4, p, q, r, a, b, c)
						: noiseGen[which].generateNoiseOctaves(noiseArr[which], x*4, z*4, p, q, a, b, c));
			}
		}
		IaSFuture
		ngi3 = IaSExecutor.push(new DoNoiseOctaveInit(3, 5, 5, 200, 200, 0.5)),
		ngi2 = IaSExecutor.push(new DoNoiseOctaveInit(2, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D)),
		ngi1 = IaSExecutor.push(new DoNoiseOctaveInit(1, 5, 33, 5, 684.412D, 684.412D, 684.412D)),
		ngi0 = IaSExecutor.push(new DoNoiseOctaveInit(0, 5, 33, 5, 684.412D, 684.412D, 684.412D));

		int heightmapIndex = 0;
		int noiseArr3Index = 0;

		//biomesForGeneration = worldObj.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, x - 2, z - 2, 10, 10);

		//BiomeGenBase[] biomes = NyxBiomeProvider.instance().getBiomeArray(null, x, z);
		class DoBiomeGen implements Callable<BiomeGenBase[]> {
			private final int offset;
			
			public DoBiomeGen(int i) {
				offset = i;
			}
			
			@Override
			public BiomeGenBase[] call() throws Exception {
				return NyxBiomeProvider.instance().getBiomeArray(null, x-1+(offset%3), z-1+(offset/3));
			}
			
		};
		IaSFuture<BiomeGenBase[]>[] biomes = new IaSFuture[9];
		for(int i = 0; i < 9; ++i)
			biomes[i] = IaSExecutor.push(new DoBiomeGen(i));
		for (int xi = 0; xi < magic; ++xi)
			for (int zi = 0; zi < magic; ++zi) {
				float
				f = 0.0F,
				f1 = 0.0F,
				f2 = 0.0F;
				
				final int
				xshift = xi==(magic/2)?((zi&1)==0?-1:0):(xi<radius?0:-1),
				zshift = zi==(magic/2)?((xi&1)==0?-1:0):(zi<radius?0:-1);
				
				//final BiomeGenBase biome = biomes[(3*xi+radius+xshift)+((3*zi+radius+zshift)<<4)];
				for (int xj = -radius; xj <= radius; ++xj)
					for (int zj = -radius; zj <= radius; ++zj) {
						final int
						xReal = 3*xi+xj+xshift,
						zReal = 3*zi+zj+zshift;
						final BiomeGenBase biomeAdjacent =
								biomes[4+(xReal>>4)+(zReal>>4)*3]
								.get()[(xReal&15)|((zReal&15)<<4)];
						final float
						hBase = biomeAdjacent.rootHeight,
						hVari = biomeAdjacent.heightVariation;
						
						float hMod = parabolicField[(xj+radius) + (zj+radius)*radius] / (hBase + 2.0F);

						f += hVari * hMod;
						f1 += hBase * hMod;
						f2 += hMod;
					}

				f /= f2;
				f1 /= f2;
				f = f * 0.9F + 0.1F;
				f1 = (f1 * 4.0F - 1.0F) / 8.0F;

				ngi3.get();
				double noiseValue = noiseArr[3][noiseArr3Index] / 8000.0D;
				++noiseArr3Index;

				if (noiseValue < 0.0D)
					noiseValue = -noiseValue * 0.3D;

				noiseValue = noiseValue * 3.0D - 2.0D;

				if (noiseValue < 0.0D) {
					noiseValue /= 2.0D;
					noiseValue = Math.max(-1.0D, noiseValue);

					noiseValue /= 1.4D;
					noiseValue /= 2.0D;
				} else {
					noiseValue = Math.min(1.0D, noiseValue);
					noiseValue /= 8.0D;
				}

				double d12 = f1;
				final double d14 = f;
				d12 += noiseValue * 0.2D;
				d12 *= 8.5D / 2.0D;
				final double d5 = 8.5D + d12;

				ngi0.get();
				ngi1.get();
				ngi2.get();
				
				for (int j2 = 0; j2 < 33; ++j2) {
					double d6 = (j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

					if (d6 < 0.0D)
						d6 *= 4.0D;

					final double d7 = noiseArr[0][heightmapIndex] / 512.0D;
					final double d8 = noiseArr[1][heightmapIndex] / 512.0D;
					final double d9 = (noiseArr[2][heightmapIndex] / 10.0D + 1.0D) / 2.0D;
					double height = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

					if (j2 > 29) {
						final double d11 = (j2 - 29) / 3.0F;
						height = -10.0D * d11 + height * (1.0D - d11);
					}

					heightmap[heightmapIndex] = height;
					++heightmapIndex;
				}
			}
		return biomes[4].get(); //Center.
	}

	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	@Override
	public Chunk loadChunk(int par1, int par2) {
		return provideChunk(par1, par2);
	}

	@Override
	public String makeString() {
		return "RandomDimNyxWorldSource";
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	@Override
	public void populate(IChunkProvider cp, int xchunk, int zchunk) {
		BlockFalling.fallInstantly = true;
		int k = xchunk * 16;
		int l = zchunk * 16;
		final BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(k + 16, l + 16);
		rand.setSeed(worldObj.getSeed());
		final long i1 = rand.nextLong() / 2L * 2L + 1L;
		final long j1 = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed(xchunk * i1 + zchunk * j1 ^ worldObj.getSeed());
		int xit, zit, yval;

		if (xchunk == 0 && zchunk == 0)
			new GenRuinsCentral().generate(worldObj, rand, 0, 0, 0);

		biomegenbase.decorate(worldObj, rand, k, l);
		// SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, k
		// + 8, l + 8, 16, 16, this.rand);
		k += 8;
		l += 8;

		for (xit = 0; xit < 16; ++xit)
			for (zit = 0; zit < 16; ++zit) {
				yval = worldObj.getPrecipitationHeight(k + xit, l + zit);
				if (worldObj.func_147478_e(xit + k, yval, zit + l, true)) {
					if (worldObj.getBlock(xit + k, yval - 1, zit + l) != NyxBlocks.stone)
						;
					worldObj.setBlock(xit + k, yval, zit + l, Blocks.snow_layer, 0, 2);
				}
			}

		BlockFalling.fallInstantly = false;
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it
	 * will generates all the blocks for the specified chunk from the map seed
	 * and chunk seed
	 */
	@Override
	public Chunk provideChunk(final int x, final int z) {
		//TODO: This really should be made thread-safe.
		try {
			Arrays.fill(ablock, Blocks.air);
			Arrays.fill(abyte, (byte) 0);
			//biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, x * 16, z * 16, 16, 16);
			BiomeGenBase biomesForGeneration[] = genTerrain(x, z, ablock, abyte);
			replaceBlocksForBiome(x, z, ablock, abyte, biomesForGeneration);

			final Chunk chunk = new Chunk(worldObj, ablock, abyte, x, z);

			final byte[] abyte1 = chunk.getBiomeArray();
			for (int k = 0; k < abyte1.length; ++k)
				abyte1[k] = (byte) biomesForGeneration[k].biomeID;

			chunk.isLightPopulated = false;
			return chunk;
		} finally {
			//TODO: Mutex unlocking, to make NyxChunkProvider mt-safe.
		}
	}

	@Override
	public void recreateStructures(int xchunk, int zchunk) {
	}

	protected void replaceBlocksForBiome(int x, int z, Block[] blockArr, byte[] metaArr, BiomeGenBase[] biomeArr) {
		for (int k = 0; k < 16; ++k)
			for (int l = 0; l < 16; ++l) {
				final BiomeGenBase biomegenbase = biomeArr[k+(l<<4)];
				biomegenbase.genTerrainBlocks(worldObj, rand, blockArr, metaArr, x * 16 + k, z * 16 + l,
						/*stoneNoise[l + k * 16]*/ 0);
			}
	}

	@Override
	public boolean saveChunks(boolean var1, IProgressUpdate var2) {
		return true;
	}

	@Override
	public void saveExtraData() {
		// TODO: EXTRA PER-CHUNK WORLD DATA.
	}

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}
}
