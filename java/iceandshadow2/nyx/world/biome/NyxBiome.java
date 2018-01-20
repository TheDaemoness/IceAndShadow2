package iceandshadow2.nyx.world.biome;

import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.ias.util.IaSWorldHelper;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.blocks.NyxBlockStone;
import iceandshadow2.nyx.entities.mobs.EntityNyxNecromancer;
import iceandshadow2.nyx.entities.mobs.EntityNyxSkeleton;
import iceandshadow2.nyx.world.gen.GenOre;
import iceandshadow2.nyx.world.gen.WorldGenNyxOre;
import iceandshadow2.nyx.world.gen.ruins.GenRuins;
import iceandshadow2.nyx.world.gen.ruins.GenRuinsGatestone;
import iceandshadow2.nyx.world.gen.ruins.GenRuinsTowerLookout;
import iceandshadow2.styx.Styx;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBiome extends BiomeGenBase {

	WorldGenNyxOre genSalt, genDevora, genEchir, genGemstone, genUnstableIce;

	protected boolean doGenNifelhium;
	protected boolean doGenDevora;
	protected boolean doGenUnstableIce;

	public NyxBiome(int par1, boolean register, float heightRoot, float heightVari, boolean isRare) {
		super(par1, register);
		setHeight(new Height(heightRoot, heightVari));
		setTemperatureRainfall(0.0F, 0.0F);
		spawnableCaveCreatureList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		topBlock = NyxBlocks.snow;
		fillerBlock = NyxBlocks.permafrost;

		doGenDevora = true;
		doGenNifelhium = true;
		doGenUnstableIce = true;

		spawnableMonsterList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityNyxSkeleton.class, 50, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityNyxNecromancer.class, 2, 1, 1));

		setBiomeName("Nyx");

		setColor(255 << 16 | 255 << 8 | 255);
	}

	@Override
	public void decorate(World par1World, Random par2Random, int xchunk, int zchunk) {

		genEchir = new WorldGenNyxOre(NyxBlocks.oreEchir, 12);
		genSalt = new WorldGenNyxOre(NyxBlocks.salt, 48);
		genGemstone = new WorldGenNyxOre(NyxBlocks.oreGemstone, 4);

		GenOre.genOreWater(NyxBlocks.oreExousium, par1World, xchunk, zchunk, 1 + par2Random.nextInt(3));
		
		GenOre.genOreStandard(genSalt, par1World, xchunk, zchunk, 8, 64, 5);
		
		{
		final int randx = par2Random.nextInt(16),
				randz = par2Random.nextInt(16);
		boolean prevsalt = false;
		for(int i = 7; i < 64; ++i) {
			if(par1World.getBlock(xchunk+randx, i, zchunk+randz) == NyxBlocks.salt) {
				if(prevsalt) {
					par1World.setBlock(xchunk+randx, i, zchunk+randz, NyxBlocks.oreCortra);
					break;
				}
				prevsalt = true;
			}
		 }
		}

		do {
			final int randx = par2Random.nextInt(16),
					randz = par2Random.nextInt(16);
			boolean prevair = false;
			for(int i = 64; i < 192; ++i) {
				final Block bl = par1World.getBlock(xchunk+randx, i, zchunk+randz);
				if(prevair) {
					if(bl instanceof NyxBlockStone) {
						for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
							if(par2Random.nextBoolean())
								par1World.setBlock(xchunk+randx+dir.offsetX, i+dir.offsetY, zchunk+randz+dir.offsetZ, NyxBlocks.oreDevora);
						}
						par1World.setBlock(xchunk+randx, i, zchunk+randz, NyxBlocks.oreDevora);
						break;
					}
				} else
					prevair = (bl == null || bl.getMaterial() instanceof MaterialTransparent);
			}
			if(doGenDevora)
				doGenDevora = false;
			else
				break;
		} while (true);

		// Unstable ice.
		if (doGenUnstableIce) {
			genUnstableIce = new WorldGenNyxOre(NyxBlocks.unstableIce, 24);
			GenOre.genOreStandard(genUnstableIce, par1World, xchunk, zchunk, 64, 156, 10);
		}

		GenOre.genOreStandard(genEchir, par1World, xchunk, zchunk, 156, 225, 4);
		GenOre.genOreStandard(genEchir, par1World, xchunk, zchunk, 96, 192, 8);
		GenOre.genOreStandard(genGemstone, par1World, xchunk, zchunk, 96, 192, 10);

		if (doGenNifelhium)
			GenOre.genOreSurface(NyxBlocks.oreNifelhium, par1World, xchunk, zchunk);

		genStructures(par1World, par2Random, xchunk, zchunk);

		for (int xit = 0; xit < 16; ++xit)
			for (int zit = 0; zit < 16; ++zit)
				if (par2Random.nextInt(24) == 0) {
					boolean inair = false;
					for (int yit = IaSBlockHelper.getHeight(par1World, xchunk + xit, zchunk + zit) - 1; yit > 63; --yit)
						if (!inair && IaSBlockHelper.isTransient(par1World, xchunk + xit, yit, zchunk + zit))
							inair = true;
						else if (inair && !IaSBlockHelper.isTransient(par1World, xchunk + xit, yit, zchunk + zit)) {
							if (par1World.isSideSolid(xchunk + xit, yit, zchunk + zit, ForgeDirection.UP)
									&& par2Random.nextBoolean()) {
								par1World.setBlock(xchunk + xit, yit + 1, zchunk + zit, NyxBlocks.icicles);
								break;
							}
							inair = false;
						}
				}

		genFoliage(par1World, par2Random, xchunk, zchunk);

		final int x = xchunk + par1World.rand.nextInt(16);
		final int z = zchunk + par1World.rand.nextInt(16);
		final int y = IaSBlockHelper.getHeight(par1World, x, z);
		if (y >= 230 && IaSWorldHelper.getRegionLevel(par1World, x, y, z) > 0) {
			boolean makestone = true;
			for (int xit = -32; xit <= 32 && makestone; ++xit)
				for (int zit = -32; zit <= 32 && makestone; ++zit)
					for (int yit = 230; yit <= 255 && makestone; ++yit)
						if (par1World.getBlock(x + xit, yit, z + zit) == NyxBlocks.crystalBloodstone)
							makestone = false;
			if (makestone)
				par1World.setBlock(x, y, z, NyxBlocks.crystalBloodstone);
		}
	}

	protected void genFoliage(World par1World, Random par2Random, int xchunk, int zchunk) {

	}

	protected void genStructures(World par1World, Random par2Random, int xchunk, int zchunk) {
		// Gatestone generation.
		final int x = xchunk - 4 + par2Random.nextInt(20);
		final int z = zchunk - 4 + par2Random.nextInt(20);
		final int y = Math.max(64, par1World.getTopSolidOrLiquidBlock(x, z));
		if ((xchunk & 127) == 0 && (zchunk & 127) == 0) {
			(new GenRuinsGatestone()).generate(par1World, par2Random, xchunk+8, y, zchunk+8);
		} else {
			if((xchunk/16)%2 != 1 || ((zchunk)/16)%2 != 1)
				return;
			GenRuins gengen = null;
			int i = 0;
			if (hasTowers() && par2Random.nextInt(3) == 0)
				gengen = new GenRuinsTowerLookout();
			else
				gengen = supplyRuins(i++);
			while(gengen != null && !gengen.generate(par1World, par2Random, x, y, z))
				gengen = supplyRuins(i++);
		}
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] meta, int a, int b, double c) {
		// byte b0 = (byte) (field_150604_aj & 255);
		int k = 0;
		// final int l = (int) (c / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		final int i1 = a & 15;
		final int j1 = b & 15;
		final int k1 = blocks.length / 256;

		final int xzmod = (j1 * 16 + i1) * k1;
		blocks[xzmod + 0] = Styx.ground;
		blocks[xzmod + 1] = Styx.air;
		blocks[xzmod + 2] = Styx.air;
		blocks[xzmod + 3] = Styx.ground;
		blocks[xzmod + 4] = Blocks.bedrock;
		if (rand.nextBoolean())
			blocks[xzmod + 5] = Blocks.bedrock;
		if (blocks[xzmod + 64] == null || blocks[xzmod + 64].isAir(world, a, 64, b)) {
			final boolean iced =
					blocks[xzmod + 61] != NyxBlocks.exousicWater
					|| blocks[xzmod + 60] != NyxBlocks.exousicWater
					|| blocks[xzmod + 59] != NyxBlocks.exousicWater;
			blocks[xzmod + 63] = iced?NyxBlocks.exousicIce:Blocks.air;
			blocks[xzmod + 62] = NyxBlocks.exousicWater;
			meta[xzmod + 62] = 15;
		}

		for (int yit = 255; yit >= 64; --yit) {
			final int index = xzmod + yit;
			final Block current = blocks[index];
			if (current == NyxBlocks.stone) {
				switch (k) {
				case 0:
					blocks[index] = topBlock;
					break;
				case -1:
					blocks[index] = fillerBlock;
					break;
				case -2:
				case -3:
					if (rand.nextInt(5 + k) != 0)
						blocks[index] = fillerBlock;
					// PRESERVE FALLTHROUGH;
				default:
					k = -4;
					break;
				}
				--k;
			} else
				k = Math.max(k, -1);
		}
	}

	protected boolean hasTowers() {
		return true;
	}

	public NyxBiome setBlocks(Block top, Block filler) {
		topBlock = top;
		fillerBlock = filler;
		return this;
	}

	protected GenRuins supplyRuins(int i) {
		return null;
	}

}
