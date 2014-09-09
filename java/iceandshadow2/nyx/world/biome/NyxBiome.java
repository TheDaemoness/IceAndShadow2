package iceandshadow2.nyx.world.biome;

import java.util.Random; //Fuck you, Scala.

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class NyxBiome extends BiomeGenBase {

	private boolean rare;
	
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
		this.biomeName = "Nyx";
		this.topBlock = Blocks.snow_layer;
		this.fillerBlock = Blocks.snow;
		rare = isRare;
	}
	
	protected void genStructures(World par1World, Random par2Random, int xchunk, int zchunk) {
		
	}
	
	@Override
	public void decorate(World par1World, Random par2Random, int xchunk, int zchunk) {
		//genStructures(par1World, par2Random, xchunk, zchunk);
		return;
	}

	public Block getReplacementBlock(int xit, int yit, int zit, int dep, Random rand) {
		if(yit <= 62)
			return null;
		if(dep == 0)
			return this.topBlock;
		if(dep >= 1 && dep <= 2 + rand.nextInt(2))
			return this.fillerBlock;
		return null;
	}

}
