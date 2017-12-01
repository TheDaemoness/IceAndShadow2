package iceandshadow2.nyx.world.biome;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.entities.mobs.EntityNyxSkeleton;
import iceandshadow2.nyx.world.gen.GenOre;
import iceandshadow2.nyx.world.gen.WorldGenNyxOre;
import iceandshadow2.nyx.world.gen.ruins.GenRuins;
import iceandshadow2.nyx.world.gen.ruins.GenRuinsTowerLookout;
import iceandshadow2.util.IaSWorldHelper;
import iceandshadow2.util.gen.Sculptor;

import java.util.Random; //Fuck you, Scala.

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBiome extends BiomeGenBase {

	private final boolean rare;

	WorldGenNyxOre genDevora, genEchir, genCortra, genDraconium, genGemstone, genUnstableIce;

	protected boolean doGenNifelhium;
	protected boolean doGenDevora;
	protected boolean doGenUnstableIce;

	public NyxBiome(int par1, boolean register, float heightRoot, float heightVari, boolean isRare) {
		super(par1, register);
		setHeight(new Height(heightRoot, heightVari));
		setTemperatureRainfall(0.0F, 0.0F);
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.topBlock = Blocks.snow;
		this.fillerBlock = NyxBlocks.permafrost;

		this.doGenDevora = true;
		this.doGenNifelhium = true;
		this.doGenUnstableIce = true;

		this.spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityNyxSkeleton.class, 40, 1, 2));

		setBiomeName("Nyx");
		this.rare = isRare;

		setColor(255 << 16 | 255 << 8 | 255);
	}

	@Override
	public void decorate(World par1World, Random par2Random, int xchunk, int zchunk) {

		this.genEchir = new WorldGenNyxOre(NyxBlocks.oreEchir, 12);
		this.genCortra = new WorldGenNyxOre(NyxBlocks.oreCortra, 10);
		this.genDraconium = new WorldGenNyxOre(NyxBlocks.oreDraconium, 8);
		this.genGemstone = new WorldGenNyxOre(NyxBlocks.oreGemstone, 4);

		if (this.doGenDevora)
			this.genDevora = new WorldGenNyxOre(NyxBlocks.oreDevora, 8);

		// Unstable ice.
		if (this.doGenUnstableIce) {
			this.genUnstableIce = new WorldGenNyxOre(NyxBlocks.unstableIce, 36);
			GenOre.genOreStandard(this.genUnstableIce, par1World, xchunk, zchunk, 0, 128, 10);
		}

		if (this.doGenDevora)
			GenOre.genOreStandard(this.genDevora, par1World, xchunk, zchunk, 96, 255, 20);

		GenOre.genOreStandard(this.genEchir, par1World, xchunk, zchunk, 160, 255, 4);
		GenOre.genOreStandard(this.genEchir, par1World, xchunk, zchunk, 128, 255, 6);
		GenOre.genOreStandard(this.genCortra, par1World, xchunk, zchunk, 128, 225, 8);
		GenOre.genOreStandard(this.genDraconium, par1World, xchunk, zchunk, 225, 255, 3);
		GenOre.genOreStandard(this.genGemstone, par1World, xchunk, zchunk, 96, 192, 10);

		if (this.doGenNifelhium)
			GenOre.genOreSurface(NyxBlocks.oreNifelhium, par1World, xchunk, zchunk);

		GenOre.genOreWater(NyxBlocks.oreExousium, par1World, xchunk, zchunk, 1 + par2Random.nextInt(3));

		genStructures(par1World, par2Random, xchunk, zchunk);

		for (int xit = 0; xit < 16; ++xit) {
			for (int zit = 0; zit < 16; ++zit) {
				if (par2Random.nextInt(24) == 0) {
					boolean inair = false;
					for (int yit = par1World.getPrecipitationHeight(xchunk + xit, zchunk + zit) - 1; yit > 0; --yit) {
						if (!inair && par1World.isAirBlock(xchunk + xit, yit, zchunk + zit)) {
							inair = true;
						} else if (inair && !par1World.isAirBlock(xchunk + xit, yit, zchunk + zit)) {
							if (par1World.isSideSolid(xchunk + xit, yit, zchunk + zit, ForgeDirection.UP)
									&& par2Random.nextBoolean()) {
								par1World.setBlock(xchunk + xit, yit + 1, zchunk + zit, NyxBlocks.icicles);
								break;
							}
							inair = false;
						}
					}
				}
			}
		}

		genFoliage(par1World, par2Random, xchunk, zchunk);

		final int x = xchunk + par1World.rand.nextInt(16);
		final int z = zchunk + par1World.rand.nextInt(16);
		final int y = par1World.getPrecipitationHeight(x, z);
		if (y >= 230 && IaSWorldHelper.getRegionLevel(par1World, x, y, z) > 0) {
			boolean makestone = true;
			for (int xit = -32; xit <= 32 && makestone; ++xit) {
				for (int zit = -32; zit <= 32 && makestone; ++zit) {
					for (int yit = 230; yit <= 255 && makestone; ++yit) {
						if (par1World.getBlock(x + xit, yit, z + zit) == NyxBlocks.crystalBloodstone)
							makestone = false;
					}
				}
			}
			if (makestone)
				par1World.setBlock(x, y, z, NyxBlocks.crystalBloodstone);
		}
	}

	protected void genFoliage(World par1World, Random par2Random, int xchunk, int zchunk) {

	}

	protected void genStructures(World par1World, Random par2Random, int xchunk, int zchunk) {
		// Gatestone generation.
		if (xchunk % 128 == 0 && zchunk % 128 == 0) {
			final int x = xchunk + 8;
			final int z = zchunk + 8;
			final int y = par1World.getTopSolidOrLiquidBlock(x, z);
			for (int xit = -1; xit <= 1; ++xit) {
				for (int zit = -1; zit <= 1; ++zit) {
					par1World.setBlock(x + xit, y - 1, z + zit, Blocks.obsidian);
					for (int yit = y + 3; yit > y; --yit)
						par1World.setBlockToAir(x + xit, yit, z + zit);

				}
			}
			par1World.setBlock(x, y - 1, z, Blocks.bedrock);
			Sculptor.terrainFlatten(par1World, x - 1, y - 2, z - 1, x + 1, 4, z + 1);
			par1World.setBlock(x, y, z, NyxBlocks.gatestone, 1 + par1World.rand.nextInt(2), 0x2);
		} else {
			final int x = xchunk + 8;
			final int z = zchunk + 8;
			final int y = par1World.getTopSolidOrLiquidBlock(x, z);
			GenRuins gengen = null;
			if (hasTowers() && par2Random.nextInt(16) == 0)
				gengen = new GenRuinsTowerLookout();
			if (gengen == null)
				gengen = supplyRuins();
			if (gengen != null)
				gengen.generate(par1World, par2Random, x, y, z);
		}
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] meta, int a, int b, double c) {
		Block block = this.topBlock;
		byte b0 = (byte) (this.field_150604_aj & 255);
		Block block1 = this.fillerBlock;
		int k = -1;
		final int l = (int) (c / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		final int i1 = a & 15;
		final int j1 = b & 15;
		final int k1 = blocks.length / 256;

		for (int l1 = 255; l1 >= 0; --l1) {
			final int i2 = (j1 * 16 + i1) * k1 + l1;

			if (l1 <= 0 + rand.nextInt(3)) {
				blocks[i2] = Blocks.bedrock;
			} else if (l1 < 63 && blocks[i2].getMaterial() != Material.air
					&& blocks[i2].getMaterial() != Material.water) {
				blocks[i2] = NyxBlocks.stone;
			} else if (l1 == 63 && (blocks[i2] == null || blocks[i2].getMaterial() == Material.air
					|| blocks[i2] == Blocks.snow_layer)) {
				blocks[i2] = NyxBlocks.exousicIce;
				blocks[i2 - 1] = NyxBlocks.exousicWater;
			} else {
				final Block block2 = blocks[i2];

				if (block2 != null && block2.getMaterial() != Material.air) {
					if (block2 == NyxBlocks.stone) {
						if (k == -1) {
							if (l <= 0) {
								block = null;
								b0 = 0;
								block1 = NyxBlocks.stone;
							} else if (l1 >= 59 && l1 <= 64) {
								block = this.topBlock;
								b0 = (byte) (this.field_150604_aj & 255);
								block1 = this.fillerBlock;
							}

							if (l1 < 63 && (block == null || block.getMaterial() == Material.air
									|| block == Blocks.snow_layer)) {
								block = NyxBlocks.exousicIce;
								b0 = 0;
							}

							k = l;

							if (l1 >= 62) {
								blocks[i2] = block;
								meta[i2] = b0;
							} else if (l1 < 56 - l) {
								block = null;
								block1 = NyxBlocks.stone;
								blocks[i2] = NyxBlocks.unstableIce;
							} else {
								blocks[i2] = block1;
							}
						} else if (k > 0) {
							--k;
							blocks[i2] = block1;
						}
					}
				} else {
					k = -1;
				}
			}
		}
	}

	protected boolean hasTowers() {
		return true;
	}

	public boolean isRare() {
		return this.rare;
	}

	public NyxBiome setBlocks(Block top, Block filler) {
		this.topBlock = top;
		this.fillerBlock = filler;
		return this;
	}

	protected GenRuins supplyRuins() {
		return null;
	}

}
