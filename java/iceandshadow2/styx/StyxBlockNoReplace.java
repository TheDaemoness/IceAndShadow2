package iceandshadow2.styx;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockAirlike;
import net.minecraft.world.IBlockAccess;

public class StyxBlockNoReplace extends IaSBaseBlockAirlike {

	public StyxBlockNoReplace(String texName) {
		super(EnumIaSModule.STYX, texName);
		setBlockUnbreakable();
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return false;
	}
}
