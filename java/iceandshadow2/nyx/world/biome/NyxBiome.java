package iceandshadow2.nyx.world.biome;

import iceandshadow2.nyx.NyxBlocks;

import java.util.Random; //Fuck you, Scala.

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class NyxBiome extends BiomeGenBase {

	private boolean rare;

	WorldGenMinable genNavistra, genDevora, genEchir, genCortra, genDraconium, genUnstableIce;

	protected boolean doGenNifelhium;
	protected boolean doGenDevora;
	protected boolean doGenUnstableIce;

	public boolean isRare() {
		return rare;
	}

	public NyxBiome(int par1, boolean register, float heightRoot, float heightVari, boolean isRare) {
		super(par1, register);
		this.setHeight(new Height(heightRoot, heightVari));
		this.setTemperatureRainfall(0.0F, 0.0F);
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.topBlock = Blocks.snow;
		this.fillerBlock = NyxBlocks.permafrost;

		doGenDevora = true;
		doGenNifelhium = true;
		doGenUnstableIce = true;

		this.setBiomeName("Nyx");
		rare = isRare;

		this.setColor(257);
	}

	public NyxBiome setBlocks(Block top, Block filler) {
		this.topBlock = top;
		this.fillerBlock = filler;
		return this;
	}

	protected void genStructures(World par1World, Random par2Random, int xchunk, int zchunk) {

	}

	@Override
	public void decorate(World par1World, Random par2Random, int xchunk, int zchunk) {
		genEchir = new WorldGenMinable(NyxBlocks.oreEchir, 10,
				NyxBlocks.stone);
		genNavistra = new WorldGenMinable(NyxBlocks.oreNavistra, 5,
				NyxBlocks.stone);
		genCortra = new WorldGenMinable(NyxBlocks.oreCortra, 10,
				NyxBlocks.stone);
		genDraconium = new WorldGenMinable(NyxBlocks.oreDraconium,
				4, NyxBlocks.stone);

		if (doGenDevora)
			genDevora = new WorldGenMinable(NyxBlocks.oreDevora, 8,
					NyxBlocks.stone);

		// Unstable ice.
		if (doGenUnstableIce) {
			genUnstableIce = new WorldGenMinable(
					NyxBlocks.unstableIce, 36,
					NyxBlocks.stone);
			NyxOreGen.genOreStandard(genUnstableIce, par1World, xchunk, zchunk, 0,
					128, 10);
		}


		if (doGenDevora)
			NyxOreGen.genOreStandard(genDevora, par1World, xchunk, zchunk, 96, 255,
					20);

		NyxOreGen.genOreStandard(genEchir, par1World, xchunk, zchunk, 192, 255, 4);
		NyxOreGen.genOreStandard(genEchir, par1World, xchunk, zchunk, 96, 255, 4);
		NyxOreGen.genOreStandard(genNavistra, par1World, xchunk, zchunk, 64, 96, 2);
		NyxOreGen.genOreStandard(genCortra, par1World, xchunk, zchunk, 128, 225, 10);
		NyxOreGen.genOreStandard(genDraconium, par1World, xchunk, zchunk, 192, 255, 3);

		if (doGenNifelhium)
			NyxOreGen.genOreSurface(NyxBlocks.oreNifelhium, par1World, xchunk,
					zchunk);

		NyxOreGen.genOreWater(NyxBlocks.oreExousium, par1World, xchunk, zchunk,
				1 + par2Random.nextInt(3));

		genStructures(par1World, par2Random, xchunk, zchunk);

		return;
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] meta, int a, int b, double c)
	{
		boolean flag = true;
		Block block = this.topBlock;
		byte b0 = (byte)(this.field_150604_aj & 255);
		Block block1 = this.fillerBlock;
		int k = -1;
		int l = (int)(c / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int i1 = a & 15;
		int j1 = b & 15;
		int k1 = blocks.length / 256;

		for (int l1 = 255; l1 >= 0; --l1)
		{
			int i2 = (j1 * 16 + i1) * k1 + l1;

			if (l1 <= 0 + rand.nextInt(3)) {
				blocks[i2] = Blocks.bedrock;
			}
			else if (l1 < 63 && (
					blocks[i2].getMaterial() != Material.air && 
					blocks[i2].getMaterial() != Material.water)) {
				blocks[i2] = NyxBlocks.stone;
			}
			else if (l1 == 63 && (blocks[i2] == null || blocks[i2].getMaterial() == Material.air)) {
				blocks[i2] = NyxBlocks.exousicIce;
				blocks[i2-1] = NyxBlocks.exousicWater;
			}
			else
			{
				Block block2 = blocks[i2];

				if (block2 != null && block2.getMaterial() != Material.air)
				{
					if (block2 == NyxBlocks.stone)
					{
						if (k == -1)
						{
							if (l <= 0)
							{
								block = null;
								b0 = 0;
								block1 = NyxBlocks.stone;
							}
							else if (l1 >= 59 && l1 <= 64)
							{
								block = this.topBlock;
								b0 = (byte)(this.field_150604_aj & 255);
								block1 = this.fillerBlock;
							}

							if (l1 < 63 && (block == null || block.getMaterial() == Material.air))
							{
								block = NyxBlocks.exousicIce;
								b0 = 0;
							}

							k = l;

							if (l1 >= 62)
							{
								blocks[i2] = block;
								meta[i2] = b0;
							}
							else if (l1 < 56 - l)
							{
								block = null;
								block1 = NyxBlocks.stone;
								blocks[i2] = NyxBlocks.unstableIce;
							}
							else
							{
								blocks[i2] = block1;
							}
						}
						else if (k > 0)
						{
							--k;
							blocks[i2] = block1;
						}
					}
				}
				else
				{
					k = -1;
				}
			}
		}
	}

}
