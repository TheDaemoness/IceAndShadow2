package iceandshadow2.nyx.world;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.biome.NyxBiome;

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
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.event.terraingen.TerrainGen;

public class NyxChunkProvider implements IChunkProvider {
	
    private Random rand;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorOctaves noiseGen4;
    private NoiseGeneratorPerlin noiseGenStone;
    public NoiseGeneratorOctaves noiseGenPublic;
    /**
     * Reference to the World object.
     */
    private World worldObj;
    
    private WorldType wt;
    private final double[] enigmaArray;
    private final float[] parabolicField;
    private double[] stoneNoise = new double[256];
    
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseArr3;
    double[] noiseArr1;
    double[] noiseArr2;
    double[] noiseArr4;
    int[][] field_73219_j = new int[32][32];

    public NyxChunkProvider(World par1World, long par2, boolean par4)
    {
        this.worldObj = par1World;
        this.wt = WorldType.DEFAULT;
        this.rand = new Random(par2);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGenStone = new NoiseGeneratorPerlin(this.rand, 4); //Do we even need this anymore?
        this.noiseGenPublic = new NoiseGeneratorOctaves(this.rand, 10); //Or this?
        this.enigmaArray = new double[825];
        this.parabolicField = new float[25];

        for (int j = -2; j <= 2; ++j) {
            for (int k = -2; k <= 2; ++k) {
                float f = 10.0F / MathHelper.sqrt_float((float)(j * j + k * k) + 0.2F);
                this.parabolicField[j + 2 + (k + 2) * 5] = f;
            }
        }
    }

    public void genTerrain(int x, int z, Block[] blockArr)
    {
        byte b0 = 63;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
        this.initNoiseField(x * 4, 0, z * 4);

        for (int xit = 0; xit < 4; ++xit) {
            int l = xit * 5;
            int i1 = (xit + 1) * 5;

            for (int zit = 0; zit < 4; ++zit) {
                int k1 = (l + zit) * 33;
                int l1 = (l + zit + 1) * 33;
                int i2 = (i1 + zit) * 33;
                int j2 = (i1 + zit + 1) * 33;

                for (int yit= 0; yit < 32; ++yit) {
                    double d0 = 0.125D;
                    double d1 = this.enigmaArray[k1 + yit];
                    double d2 = this.enigmaArray[l1 + yit];
                    double d3 = this.enigmaArray[i2 + yit];
                    double d4 = this.enigmaArray[j2 + yit];
                    double d5 = (this.enigmaArray[k1 + yit + 1] - d1) * d0;
                    double d6 = (this.enigmaArray[l1 + yit + 1] - d2) * d0;
                    double d7 = (this.enigmaArray[i2 + yit + 1] - d3) * d0;
                    double d8 = (this.enigmaArray[j2 + yit + 1] - d4) * d0;

                    for (int l2 = 0; l2 < 8; ++l2) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i3 = 0; i3 < 4; ++i3) {
                            int arrIndex = i3 + xit * 4 << 12 | 0 + zit * 4 << 8 | yit * 8 + l2;
                            arrIndex -= 256;
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int k3 = 0; k3 < 4; ++k3)
                            {
                                if ((d15 += d16) > 0.0D)
                                    blockArr[arrIndex += 256] = NyxBlocks.stone;
                                
                                else if (yit * 8 + l2 < b0)
                                    blockArr[arrIndex += 256] = Blocks.ice;
                                
                                else
                                    blockArr[arrIndex += 256] = null;
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

    public void replaceBlocksForBiome(int x, int z, Block[] blockArr, byte[] metaArr, BiomeGenBase[] biomeArr)
    {
        double d0 = 0.03125D;
        //this.stoneNoise = this.perlinGen4.func_151599_a(this.stoneNoise, (double)(x * 16), (double)(z * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);
        
        for (int xit = 0; xit < 16; ++xit) {
            for (int zit = 0; zit < 16; ++zit) {
            	for (int yit = 0; yit <= 2; ++yit) {
                	int arrIndex = xit << 12 | zit << 8 | yit;
                	if(this.rand.nextInt(yit+1) == 0)
                		blockArr[arrIndex] = Blocks.bedrock;
                }
                int var12 = (int)(this.stoneNoise[zit + xit * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
                BiomeGenBase biomegen = biomeArr[zit + xit * 16];
                int dep = 0;
        		if(!(biomegen instanceof NyxBiome))
        			continue;
                for (int yit = 255; yit >= 0; --yit) {
                	int arrIndex = xit << 12 | zit << 8 | yit;
                	if(blockArr[arrIndex] == Blocks.water) {
                		blockArr[arrIndex] = Blocks.ice;
                		if(dep == 1)
                			break;
                		++dep;
                	}
                	else if(blockArr[arrIndex] == NyxBlocks.stone) {
                		Block rep = ((NyxBiome)biomegen).getReplacementBlock(xit, yit, zit, dep, this.worldObj.rand);
                		if(rep == null)
                			break;
                		++dep;
                	}
                }
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int par1, int par2) {
        return this.provideChunk(par1, par2);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int x, int z) {
        this.rand.setSeed((long)z * 341873128712L + (long)x * 132897987541L);
        Block[] ablock = new Block[65536];
        byte[] abyte = new byte[65536];
        this.genTerrain(x, z, ablock);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.replaceBlocksForBiome(x, z, ablock, abyte, this.biomesForGeneration);

        Chunk chunk = new Chunk(this.worldObj, ablock, abyte, x, z);
        
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k)
            abyte1[k] = (byte)this.biomesForGeneration[k].biomeID;

        return chunk;
    }

    private void initNoiseField(int x, int y, int z) {
        double d0 = 684.412D;
        double d1 = 684.412D;
        double d2 = 512.0D;
        double d3 = 512.0D;
        this.noiseArr4 = this.noiseGen4.generateNoiseOctaves(this.noiseArr4, x, z, 5, 5, 200.0D, 200.0D, 0.5D);
        this.noiseArr3 = this.noiseGen3.generateNoiseOctaves(this.noiseArr3, x, y, z, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
        this.noiseArr1 = this.noiseGen1.generateNoiseOctaves(this.noiseArr1, x, y, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.noiseArr2 = this.noiseGen2.generateNoiseOctaves(this.noiseArr2, x, y, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        boolean flag1 = false;
        boolean flag = false;
        int l = 0;
        int i1 = 0;
        double d4 = 8.5D;

        for (int j1 = 0; j1 < 5; ++j1) {
            for (int k1 = 0; k1 < 5; ++k1) {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

                for (int l1 = -b0; l1 <= b0; ++l1) {
                    for (int i2 = -b0; i2 <= b0; ++i2) {
                        BiomeGenBase biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float hBase = biomegenbase1.rootHeight;
                        float hVari = biomegenbase1.heightVariation;

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
                double d13 = this.noiseArr4[i1] / 8000.0D;

                if (d13 < 0.0D)
                    d13 = -d13 * 0.3D;

                d13 = d13 * 3.0D - 2.0D;

                if (d13 < 0.0D) {
                    d13 /= 2.0D;
                    d13 = Math.max(-1.0D, d13);

                    d13 /= 1.4D;
                    d13 /= 2.0D;
                }
                else {
                    d13 = Math.min(1.0D, d13);
                    d13 /= 8.0D;
                }

                ++i1;
                double d12 = (double)f1;
                double d14 = (double)f;
                d12 += d13 * 0.2D;
                d12 = d12 * 8.5D / 8.0D;
                double d5 = 8.5D + d12 * 4.0D;

                for (int j2 = 0; j2 < 33; ++j2) {
                    double d6 = ((double)j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

                    if (d6 < 0.0D)
                        d6 *= 4.0D;

                    double d7 = this.noiseArr1[l] / 512.0D;
                    double d8 = this.noiseArr2[l] / 512.0D;
                    double d9 = (this.noiseArr3[l] / 10.0D + 1.0D) / 2.0D;
                    double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

                    if (j2 > 29)  {
                        double d11 = (double)((float)(j2 - 29) / 3.0F);
                        d10 = d10 * (1.0D - d11) + -10.0D * d11;
                    }

                    this.enigmaArray[l] = d10;
                    ++l;
                }
            }
        }
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider cp, int par2, int par3)
    {
        BlockFalling.fallInstantly = true;
        int k = par2 * 16;
        int l = par3 * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)par2 * i1 + (long)par3 * j1 ^ this.worldObj.getSeed());
        boolean flag = false;

        int xit, zit, yval;

        biomegenbase.decorate(this.worldObj, this.rand, k, l);
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, k + 8, l + 8, 16, 16, this.rand);
        k += 8;
        l += 8;

        for (xit = 0; xit < 16; ++xit) {
            for (zit = 0; zit < 16; ++zit) {
                yval = this.worldObj.getPrecipitationHeight(k + xit, l + zit);

                if (this.worldObj.isBlockFreezable(xit + k, yval - 1, zit + l))
                    this.worldObj.setBlock(xit + k, yval - 1, zit + l, Blocks.ice, 0, 2);

                if (this.worldObj.func_147478_e(xit + k, yval, zit + l, true))
                    this.worldObj.setBlock(xit + k, yval, zit + l, Blocks.snow_layer, 0, 2);
            }
        }

        BlockFalling.fallInstantly = false;
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
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType var1, int var2, int var3,
			int var4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String makeString() {
		return "RandomDimNyxWorldSource";
	}

	@Override
	public void recreateStructures(int var1, int var2) {
		return;
	}

	@Override
	public boolean saveChunks(boolean var1, IProgressUpdate var2) {
		return true;
	}

	@Override
	public void saveExtraData() {
		//TODO: EXTRA PER-CHUNK WORLD DATA.
	}

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	@Override
	/**
	 * Get nearest structure.
	 */
	public ChunkPosition func_147416_a(World var1, String var2, int var3,
			int var4, int var5) {
		return null;
	}
}
