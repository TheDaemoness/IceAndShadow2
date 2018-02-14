package iceandshadow2.ias.util.gen;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

import iceandshadow2.boilerplate.Pipeline;
import iceandshadow2.ias.util.BlockPos2;
import iceandshadow2.ias.util.BlockPos3;
import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 * Filters out blocks that are not within a certain radius of the specified block.
 */
public class BlockFilterRadius<T extends BlockPos2> extends BaseBlockFilter {
	protected double radiusSq;
	private T center;
	
	public BlockFilterRadius(T center, double radius) {
		radiusSq = radius*radius;
		this.center = center;
	}

	@Override
	protected BlockPos3 getResult(BlockPos3 in, Object prevmsg, Object selfmsg) {
		if(in.distEuclid2(center) >= radiusSq)
			return null;
		return in;
	}
}
