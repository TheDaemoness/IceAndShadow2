package iceandshadow2.ias.util.gen;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import iceandshadow2.boilerplate.ISupplier;
import iceandshadow2.boilerplate.IteratorConcat;
import iceandshadow2.boilerplate.Pipeline;
import iceandshadow2.ias.util.BlockPos3;
import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 * Filterable system for obtaining block positions matching some pattern.
 */
public class IteratorConcatBlock extends IteratorConcat<BlockPos3, SupplierBlockPos> implements ISupplierBlock {
	private Pipeline<BlockPos3> filter = new BaseBlockFilter();
	private BlockPos3 buffer;
	private boolean done = false;

	/**
	 * Offsets all currently added suppliers.
	 * If additional suppliers are added, they are not offset as well.
	 */
	@Override
	public ISupplierBlock offsetCenter(long x, long y, long z) {
		for(SupplierBlockPos p : iters)
			p.offsetCenter(x, y, z);
		return this;
	}

	/**
	 * Adds a master filter on top of all filters possessed by sub iterators.
	 */
	@Override
	public void addFilter(Pipeline<BlockPos3> filt) {
		filter = filt;
	}
	
	private final void assureHasNext() {
		while(buffer == null && super.hasNext()) {
			final BlockPos3 toFilter = super.next();
			buffer = filter.call(toFilter);
		}
		if(buffer == null)
			done = true;
	}

	@Override
	public final boolean hasNext() {
		if(!done)
			assureHasNext();
		return !done;
	}

	@Override
	public final BlockPos3 next() {
		assureHasNext();
		final BlockPos3 ret = buffer;
		buffer = null;
		return ret;
	}

	@Override
	public boolean reset() {
		super.reset();
		buffer = null;
		boolean fullreset = true;
		for(SupplierBlockPos sbp : iters) {
			fullreset &= sbp.reset();
		}
		return fullreset;
	}
}
