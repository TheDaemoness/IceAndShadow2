package iceandshadow2.ias.util.gen;

import iceandshadow2.adapters.BlockPos3;
import iceandshadow2.boilerplate.Pipeline;

/**
 * Type-locked BlockPos3 pipeline with most of the programmability nailed down.
 * 
 * Default implementation takes the input and leaves it unchanged. Useful for
 * dodging null checks.
 */
public class BaseBlockFilter extends Pipeline<BlockPos3> {

	@Override
	protected final Pipeline<BlockPos3> getNext(BlockPos3 in, Object prevmsg, Object selfmsg) {
		return super.getNext(in, prevmsg, selfmsg);
	}

	@Override
	protected final Object getNextMessage(BlockPos3 BlockPos3, Object prevmsg, Object selfmsg) {
		return selfmsg;
	}

	@Override
	protected BlockPos3 getResult(BlockPos3 in, Object prevmsg, Object selfmsg) {
		return in;
	}

	@Override
	protected final Object getSelfMessage(BlockPos3 in, Object prevmsg) {
		return in;
	}

	// getResult left un-final for obvious reasons.
	// getSelf left un-final to allow disabling the stop-on-null feature.
}
