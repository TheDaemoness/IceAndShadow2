package iceandshadow2.nyx.world.biome;

import iceandshadow2.nyx.world.gen.NyxGenPoisonTrees;
import iceandshadow2.util.IaSBlockHelper;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class NyxBiomeForest extends NyxBiome {
	
	public NyxBiomeForest(int par1, boolean register, float heightRoot,
			float heightVari, boolean isRare) {
		super(par1, register, heightRoot, heightVari, isRare);
		this.setBlocks(Blocks.snow, Blocks.snow);
	}

	public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
		super.decorate(par1World, par2Random, par3, par4);
        for (int i = 0; i < 10; ++i)
        {
            int x = par3 + par2Random.nextInt(16) + 8;
            int z = par4 + par2Random.nextInt(16) + 8;
            int y;
            if(i%2 == 0)
            	y = 192;
            else
            	y = 64;
            while(y >= 64 && y <= 192) {
            	Block bid = par1World.getBlock(x, y, z);
            	if(bid == Blocks.snow_layer)
            		break;
                if(i%2 == 0) {
                	if(!IaSBlockHelper.isAir(bid)) {
                		++y;
                		break;
                	}
                	--y;
                }
                else {
                	if(IaSBlockHelper.isAir(bid))
                		break;
                	++y;
                }
            }
            if(y == 0)
            	continue;
            WorldGenerator var5 = this.getRandomWorldGenForTrees(par2Random);
            var5.generate(par1World, par2Random, x, y, z);
        }
    }
	
	public WorldGenerator getRandomWorldGenForTrees(Random rand) {
		return new NyxGenPoisonTrees();
	}
}
