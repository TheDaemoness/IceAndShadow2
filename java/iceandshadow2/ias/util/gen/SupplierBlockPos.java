package iceandshadow2.ias.util.gen;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import iceandshadow2.boilerplate.BaseSupplier;
import iceandshadow2.boilerplate.ISupplier;
import iceandshadow2.boilerplate.Pipeline;
import iceandshadow2.ias.util.BlockPos3;
import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 * Filterable system for obtaining block positions matching some pattern.
 */
public abstract class SupplierBlockPos extends BaseSupplier<BlockPos3> implements ISupplierBlock {

	protected long
	x=0,
	z=0;
	protected int y = 0;
	private boolean done = false;
	private BlockPos3 buffer;
	private Pipeline<BlockPos3> filter = new BaseBlockFilter();
	
	@Override
	public ISupplierBlock offsetCenter(long x, long y, long z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	protected abstract boolean thisHasNext();
	protected abstract BlockPos3 thisNext();
	
	private final void assureHasNext() {
		while(buffer == null && thisHasNext()) {
			final BlockPos3 toFilter = thisNext();
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
	public void addFilter(Pipeline<BlockPos3> filt) {
		filter = filt;
	}
}
