package iceandshadow2.nyx.world.biome;

import iceandshadow2.nyx.NyxBlocks;

import net.minecraft.init.Blocks;

public class NyxBiomeFrozen extends NyxBiome {

	
	public NyxBiomeFrozen(int par1, boolean register, float heightRoot,
			float heightVari, boolean isRare) {
		super(par1, register, heightRoot, heightVari, isRare);
		this.setBlocks(Blocks.snow, NyxBlocks.permafrost);
	}

	/*
	public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
		super.decorate(par1World, par2Random, par3, par4);
		
		randomGenerator = par2Random;
        for (int i = 0; i < 10; ++i)
        {
            int x = par3 + this.randomGenerator.nextInt(16) + 8;
            int z = par4 + this.randomGenerator.nextInt(16) + 8;
            int y = 255;
            for(y = 255; y > 0; --y) {
            	int bid = par1World.getBlockId(x, y, z);
            	if(i < 5) {
            		if(bid == Block.snow.blockID)
            			break;
            		else if(bid != 0) {
                		++y;
                		break;
                	}
            	}
            	else {
            		if(bid == IaSIDs.nyxBlockPermafrost_id) {
            			bid = par1World.getBlockId(x, y+1, z);
            			if(bid == Block.snow.blockID || bid == 0) {
            				++y;
            				break;
            			}
            			else
            				continue;
            		}
            	}
            }
            if(y == 0)
            	continue;
            if(i < 5) {
            	WorldGenerator var5 = this.getRandomWorldGenForTrees(this.randomGenerator);
            	var5.generate(par1World, this.randomGenerator, x, y, z);
            	par1World.updateAllLightTypes(x, y, z);
            }
            else
            	par1World.setBlock(x, y, z, IaSIDs.nyxBlockStalagmites_id);
        }
        randomGenerator = null;
    }
	
	public WorldGenerator getRandomWorldGenForTrees(Random rand) {
		return new NyxWorldGenPoisonTrees(false);
	}
	*/
}
