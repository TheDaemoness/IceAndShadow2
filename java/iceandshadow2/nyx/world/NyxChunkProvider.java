package iceandshadow2.nyx.world;

import iceandshadow2.ias.blocks.IaSBaseBlockFluid;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.blocks.NyxBlockAir;
import iceandshadow2.nyx.world.gen.ruins.GenRuinsCentral;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class NyxChunkProvider implements IChunkProvider {

	private final Random rand;
	private NoiseGeneratorOctaves[] noiseGen;
	private final NoiseGeneratorPerlin noiseGenStone;
	// public NoiseGeneratorOctaves noiseGenPublic;
	/**
	 * Reference to the World object.
	 */
	private final World worldObj;

	private final WorldType wt;
	private final double[] enigmaArray;
	private final float[] parabolicField;
	private double[] stoneNoise = new double[256];
	
	private Block[] ablock;
	private byte[] abyte;

	private BiomeGenBase[] biomesForGeneration;
	double[][] noiseArr;
	int[][] field_73219_j = new int[32][32];

	public NyxChunkProvider(World par1World, long par2, boolean par4) {
		noiseArr = new double[][]{null, null, null, null};
		ablock = new Block[1 << 16];
		abyte = new byte[1 << 16];
		this.worldObj = par1World;
		this.wt = WorldType.DEFAULT;
		this.rand = new Random(par2);
		this.noiseGen = new NoiseGeneratorOctaves[4];
		this.noiseGen[0] = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGen[1] = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGen[2] = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseGen[3] = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGenStone = new NoiseGeneratorPerlin(this.rand, 4);
		// this.noiseGenPublic = new NoiseGeneratorOctaves(this.rand, 10);
		this.enigmaArray = new double[825];
		this.parabolicField = new float[25];

		for (int j = -2; j <= 2; ++j) {
			for (int k = -2; k <= 2; ++k) {
				final float f = 10.0F / MathHelper.sqrt_float(j * j + k * k + 0.2F);
				this.parabolicField[j + 2 + (k + 2) * 5] = f;
			}
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

	public void genTerrain(int x, int z, Block[] blockArr) {
		final byte levelWater = 63;
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration,
				x * 4 - 2, z * 4 - 2, 10, 10);
		initNoiseField(x * 4, 0, z * 4);

		for (int xit = 0; xit < 4; ++xit) {
			final int l = xit * 5;
			final int i1 = (xit + 1) * 5;

			for (int zit = 0; zit < 4; ++zit) {
				final int k1 = (l + zit) * 33;
				final int l1 = (l + zit + 1) * 33;
				final int i2 = (i1 + zit) * 33;
				final int j2 = (i1 + zit + 1) * 33;

				for (int yit = 0; yit < 32; ++yit) {
					final double d0 = 0.125D;
					double d1 = this.enigmaArray[k1 + yit];
					double d2 = this.enigmaArray[l1 + yit];
					double d3 = this.enigmaArray[i2 + yit];
					double d4 = this.enigmaArray[j2 + yit];
					final double d5 = (this.enigmaArray[k1 + yit + 1] - d1) * d0;
					final double d6 = (this.enigmaArray[l1 + yit + 1] - d2) * d0;
					final double d7 = (this.enigmaArray[i2 + yit + 1] - d3) * d0;
					final double d8 = (this.enigmaArray[j2 + yit + 1] - d4) * d0;

					for (int l2 = 0; l2 < 8; ++l2) {
						final double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						final double d12 = (d3 - d1) * d9;
						final double d13 = (d4 - d2) * d9;

						for (int i3 = 0; i3 < 4; ++i3) {
							int arrIndex = i3 + xit * 4 << 12 | 0 + zit * 4 << 8 | yit * 8 + l2;
							arrIndex -= 256;
							final double d14 = 0.25D;
							final double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int k3 = 0; k3 < 4; ++k3) {
								if ((d15 += d16) > 0.0D)
									blockArr[arrIndex += 256] = NyxBlocks.stone;

								else if (yit * 8 + l2 < levelWater)
									blockArr[arrIndex += 256] = NyxBlocks.exousicWater;
								else if (yit * 8 + l2 < NyxBlockAir.ATMOS_HEIGHT)
									blockArr[arrIndex += 256] = null;
								else
									blockArr[arrIndex += 256] = NyxBlocks.air;
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
		}
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
		final BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
		return biomegenbase.getSpawnableList(p_73155_1_);
	}

	private void initNoiseField(int x, int y, int z) {
		class NoiseOctaveInit implements Runnable {
			final NyxChunkProvider parent;
			final public int which;
			final public int x, y, z, q;
			int r, s;
			final public double a, b, c;
			boolean usefull;
			
			NoiseOctaveInit(NyxChunkProvider parent, int which, int x, int y, int z, int q, double a, double b, double c) {
				usefull = false;
				this.parent = parent;
				this.which = which;
				this.x = x;
				this.y = y;
				this.z = z;
				this.q = q;
				this.a = a;
				this.b = b;
				this.c = c;
			}
			NoiseOctaveInit(NyxChunkProvider parent, int which, int x, int y, int z, int q, int r, int s, double a, double b, double c) {
				this(parent, which, x, y, z, q, a, b, c);
				usefull = true;
				this.r = r;
				this.s = s;
			}
			
			@Override
			public void run() {
				parent.noiseArr[which]=(usefull?
					parent.noiseGen[which].generateNoiseOctaves(parent.noiseArr[which], x, y, z, q, r, s, a, b, c):
					parent.noiseGen[which].generateNoiseOctaves(parent.noiseArr[which], x, y, z, q, a, b, c));
			}
		}
		Thread[] pool = new Thread[4];
		pool[3] = new Thread(new NoiseOctaveInit(this, 3, x, z, 5, 5, 200, 200, 0.5));
		pool[2] = new Thread(new NoiseOctaveInit(this, 2, x, y, z, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D));
		pool[0] = new Thread(new NoiseOctaveInit(this, 0, x, y, z, 5, 33, 5, 684.412D, 684.412D, 684.412D));
		pool[1] = new Thread(new NoiseOctaveInit(this, 1, x, y, z, 5, 33, 5, 684.412D, 684.412D, 684.412D));
		
		for(Thread t : pool) {
			t.run();
		}
		
		int l = 0;
		int i1 = 0;
		for (int j1 = 0; j1 < 5; ++j1) {
			for (int k1 = 0; k1 < 5; ++k1) {
				float f = 0.0F;
				float f1 = 0.0F;
				float f2 = 0.0F;
				final byte b0 = 2;
				final BiomeGenBase biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

				for (int l1 = -b0; l1 <= b0; ++l1) {
					for (int i2 = -b0; i2 <= b0; ++i2) {
						final BiomeGenBase biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
						final float hBase = biomegenbase1.rootHeight;
						final float hVari = biomegenbase1.heightVariation;

						float hMod = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (hBase + 2.0F);

						if (biomegenbase1.rootHeight > biomegenbase.rootHeight)
							hMod /= 2.0F;

						f += hVari * hMod;
						f1 += hBase * hMod;
						f2 += hMod;
					}
				}

				f /= f2;
				f1 /= f2;
				f = f * 0.9F + 0.1F;
				f1 = (f1 * 4.0F - 1.0F) / 8.0F;
				
				while(pool[3].isAlive()) {
					try {pool[3].join();}
					catch (InterruptedException e) {}
				}
				double d13 = this.noiseArr[3][i1] / 8000.0D;

				if (d13 < 0.0D)
					d13 = -d13 * 0.3D;

				d13 = d13 * 3.0D - 2.0D;

				if (d13 < 0.0D) {
					d13 /= 2.0D;
					d13 = Math.max(-1.0D, d13);

					d13 /= 1.4D;
					d13 /= 2.0D;
				} else {
					d13 = Math.min(1.0D, d13);
					d13 /= 8.0D;
				}

				++i1;
				double d12 = f1;
				final double d14 = f;
				d12 += d13 * 0.2D;
				d12 = d12 * 8.5D / 8.0D;
				final double d5 = 8.5D + d12 * 4.0D;
				
				for(int i = 0; i < 3; ++i) {
					try {pool[i].join();}
					catch (InterruptedException e) {--i;}
				}
				for (int j2 = 0; j2 < 33; ++j2) {
					double d6 = (j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

					if (d6 < 0.0D)
						d6 *= 4.0D;

					final double d7 = this.noiseArr[0][l] / 512.0D;
					final double d8 = this.noiseArr[1][l] / 512.0D;
					final double d9 = (this.noiseArr[2][l] / 10.0D + 1.0D) / 2.0D;
					double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

					if (j2 > 29) {
						final double d11 = (j2 - 29) / 3.0F;
						d10 = d10 * (1.0D - d11) + -10.0D * d11;
					}

					this.enigmaArray[l] = d10;
					++l;
				}
			}
		}
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
		final BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
		this.rand.setSeed(this.worldObj.getSeed());
		final long i1 = this.rand.nextLong() / 2L * 2L + 1L;
		final long j1 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed(xchunk * i1 + zchunk * j1 ^ this.worldObj.getSeed());
		int xit, zit, yval;

		if (xchunk == 0 && zchunk == 0) {
			new GenRuinsCentral().generate(this.worldObj, this.rand, 0, 0, 0);
		}

		biomegenbase.decorate(this.worldObj, this.rand, k, l);
		//SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, k + 8, l + 8, 16, 16, this.rand);
		k += 8;
		l += 8;

		for (xit = 0; xit < 16; ++xit) {
			for (zit = 0; zit < 16; ++zit) {
				yval = this.worldObj.getPrecipitationHeight(k + xit, l + zit);
				if (this.worldObj.func_147478_e(xit + k, yval, zit + l, true)) {
					if (this.worldObj.getBlock(xit + k, yval - 1, zit + l) != NyxBlocks.stone)
						;
					this.worldObj.setBlock(xit + k, yval, zit + l, Blocks.snow_layer, 0, 2);
				}
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
	public Chunk provideChunk(int x, int z) {
		this.rand.setSeed(z * 341873128712L + x * 132897987541L);
		class DoNoiseGen implements Runnable {
			final private NyxChunkProvider parent;
			final int x, z;
			DoNoiseGen(NyxChunkProvider dis, int X, int Z) {parent = dis; x = X; z = Z;}
			public void run() {
				final double d0 = 0.03125D;
				parent.stoneNoise = parent.noiseGenStone.func_151599_a(parent.stoneNoise, x * 16, z * 16, 16, 16, d0 * 2.0D,
						d0 * 2.0D, 1.0D);
				parent.biomesForGeneration = parent.worldObj.getWorldChunkManager().loadBlockGeneratorData(parent.biomesForGeneration,
						x * 16, z * 16, 16, 16);
			}
		}
		Thread t = new Thread(new DoNoiseGen(this, x, z));
		t.run();
		//NOTE: The following are wasteful on the first call of provideChunk. Meh.
		Arrays.fill(ablock, null);
		Arrays.fill(abyte, (byte)0);
		genTerrain(x, z, ablock);
		while(t.isAlive()) {
			try {t.join();}
			catch (InterruptedException e) {}
		}
		replaceBlocksForBiome(x, z, ablock, abyte, this.biomesForGeneration);

		final Chunk chunk = new Chunk(this.worldObj, ablock, abyte, x, z);
		for (int xit = 0; xit < 16; ++xit) {
			for (int zit = 0; zit < 16; ++zit) {
				for (int yit = 0; yit <= 64; ++yit) {
					if (chunk.getBlock(xit, yit, zit) instanceof IaSBaseBlockFluid)
						chunk.setBlockMetadata(xit, yit, zit, 15);
				}
			}
		}

		final byte[] abyte1 = chunk.getBiomeArray();

		for (int k = 0; k < abyte1.length; ++k)
			abyte1[k] = (byte) this.biomesForGeneration[k].biomeID;

		chunk.resetRelightChecks();
		return chunk;
	}

	@Override
	public void recreateStructures(int xchunk, int zchunk) {
	}

	protected void replaceBlocksForBiome(int x, int z, Block[] blockArr, byte[] metaArr, BiomeGenBase[] biomeArr) {
		for (int k = 0; k < 16; ++k) {
			for (int l = 0; l < 16; ++l) {
				final BiomeGenBase biomegenbase = biomeArr[l + k * 16];
				biomegenbase.genTerrainBlocks(this.worldObj, this.rand, blockArr, metaArr, x * 16 + k, z * 16 + l,
						this.stoneNoise[l + k * 16]);
			}
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
