package iceandshadow2.nyx.world.biome;

import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.entities.mobs.EntityNyxSkeleton;

public class NyxBiomeBarren extends NyxBiome {

	public NyxBiomeBarren(int par1, boolean register, float heightRoot,
			float heightVari, boolean isRare) {
		super(par1, register, heightRoot, heightVari, isRare);
		this.setBlocks(NyxBlocks.stone, NyxBlocks.stone);
		this.doGenNifelhium = false;
		this.doGenUnstableIce = false;

		this.setColor(96 << 16 | 96 << 8 | 96);
		
		this.spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityNyxSkeleton.class, 60, 2, 3));
	}
	
	/*
	public void decorate(World par1World, Random par2Random, int xchunk, int zchunk)
    {
		
		for(int xit = 0; xit < 16; ++xit) {
			for(int zit = 0; zit < 16; ++zit) {
	            int x = xchunk + xit + 8;
	            int z = zchunk + zit + 8;
				int y = par1World.getTopSolidOrLiquidBlock(x, z);
				if(y > 72 && (par1World.getBiomeGenForCoords(x, z) instanceof NyxBiomeBarren)) {
					int ydown = par2Random.nextInt(5);
					for(int yit = 0; yit < ydown; ++yit) {
						int bid = par1World.getBlockId(x, y-yit, z);
						if(bid != IaSIDs.nyxBlockCryingObsidian_id && 
							bid != IaSIDs.nyxBlockTorchAntiWisp_id &&
							bid != Block.obsidian.blockID )
								par1World.setBlockToAir(x, y-yit, z);
					}
				}
			}
		}
		super.decorate(par1World, par2Random, xchunk, zchunk);
		
		this.randomGenerator = par2Random;
		
		int e = this.randomGenerator.nextInt(4)+1;
        for (int i = 0; i < e; ++i)
        {
            int x = xchunk + this.randomGenerator.nextInt(16) + 8;
            int z = zchunk + this.randomGenerator.nextInt(16) + 8;
            int y = 255;
            for(y = 255; y > 0; --y) {
            	int bid = par1World.getBlockId(x, y-1, z);
            	if(bid == IaSBlocks.nyxBlockStone_id)
            		break;
            }
            if(y == 0)
            	continue;
            WorldGenerator var5 = this.getRandomWorldGenForTrees(this.randomGenerator);
            var5.generate(par1World, this.randomGenerator, x, y, z);
    		par1World.updateAllLightTypes(x, y, z);
        }
        
        NyxWorldGenVines nyxvines = new NyxWorldGenVines();
        for (int var6 = 0; var6 < 15; ++var6)
        {
            int var7 = xchunk + par2Random.nextInt(16) + 8;
            byte var8 = 64;
            int var9 = zchunk + par2Random.nextInt(16) + 8;
            nyxvines.generate(par1World, par2Random, var7, var8, var9);
    		par1World.updateAllLightTypes(var7, var8, var9);
        }
        this.randomGenerator = null;
    }
    
    public WorldGenerator getRandomWorldGenForTrees(Random rand) {
		return new NyxWorldGenPetrifiedTrees(false);
	}
	*/
}
