package iceandshadow2.adapters;

import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSAspect;
import net.minecraft.block.Block;

public class BlockInstance implements IIaSAspect {
	protected final Block bl;
	protected final byte variant;

	public BlockInstance(BlockType bl, int variant) {
		this.bl = bl.getBlockForVariant(variant);
		this.variant = (byte) variant;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.getAspect(bl);
		// TODO: Fancier.
	}
}
