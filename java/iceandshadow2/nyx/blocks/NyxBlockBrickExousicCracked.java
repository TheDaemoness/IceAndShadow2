package iceandshadow2.nyx.blocks;

import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class NyxBlockBrickExousicCracked extends NyxBlockBrickExousic {
	public NyxBlockBrickExousicCracked(String id) {
		super(id);
		setResistance(NyxBlockStone.RESISTANCE / 2);
		setHardness(NyxBlockStone.HARDNESS / 2);
		//Note: these break to stone memories as a form of opportunism by Nyx's stone. Nyx hates exousium.
	}

	@Override
	public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_,
			Explosion p_149723_5_) {
		super.onBlockDestroyedByExplosion(p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_, p_149723_5_);
		p_149723_1_.setBlock(p_149723_2_, p_149723_3_, p_149723_4_, NyxBlocks.stoneMemory);
	}

	@Override
	public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_,
			int p_149664_5_) {
		super.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
		p_149664_1_.setBlock(p_149664_2_, p_149664_3_, p_149664_4_, NyxBlocks.stoneMemory);
	}
}
