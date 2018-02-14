package iceandshadow2.ias.util.gen;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

import iceandshadow2.boilerplate.Pipeline;
import iceandshadow2.ias.util.BlockPos3;
import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 * Type-locked BlockPos3 pipeline with most of the programmability nailed down.
 * 
 * Default implementation takes the input and leaves it unchanged. Useful for dodging null checks.
 */
public class BaseBlockFilter extends Pipeline<BlockPos3> {

	protected final Object getSelfMessage(BlockPos3 in, Object prevmsg) {
		return in;
	}
	protected final Object getNextMessage(BlockPos3 BlockPos3, Object prevmsg, Object selfmsg) {
		return selfmsg;
	}
	@Override
	protected final Pipeline<BlockPos3> getNext(BlockPos3 in, Object prevmsg, Object selfmsg) {
		return super.getNext(in, prevmsg, selfmsg);
	}
	
	@Override
	protected BlockPos3 getResult(BlockPos3 in, Object prevmsg, Object selfmsg) {
		return in;
	}
	
	//getResult left un-final for obvious reasons.
	//getSelf left un-final to allow disabling the stop-on-null feature.
}
