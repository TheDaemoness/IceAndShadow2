package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.nyx.blocks.NyxBlockStone;
import net.minecraft.world.IBlockAccess;

public abstract class NyxBlockOre extends NyxBlockStone {

	public NyxBlockOre(String texName) {
		super(texName);
	}

	@Override
	public abstract int getExpDrop(IBlockAccess world, int metadata, int fortune);
}
