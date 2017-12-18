package iceandshadow2.styx;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockAirlike;
import net.minecraft.block.Block;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class StyxBlockNoReplace extends IaSBaseBlockAirlike {

	public StyxBlockNoReplace(String texName) {
		super(EnumIaSModule.STYX, texName);
		this.setBlockUnbreakable();
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return false;
	}
}
