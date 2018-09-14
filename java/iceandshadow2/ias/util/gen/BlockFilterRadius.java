package iceandshadow2.ias.util.gen;

import iceandshadow2.adapters.BlockPos2;
import iceandshadow2.adapters.BlockPos3;

/**
 * Filters out blocks that are not within a certain radius of the specified
 * block.
 */
public class BlockFilterRadius<T extends BlockPos2> extends BaseBlockFilter {
	protected double radiusSq;
	private final T center;

	public BlockFilterRadius(T center, double radius) {
		radiusSq = radius * radius;
		this.center = center;
	}

	@Override
	protected BlockPos3 getResult(BlockPos3 in, Object prevmsg, Object selfmsg) {
		if (in.distEuclid2(center) >= radiusSq)
			return null;
		return in;
	}
}
