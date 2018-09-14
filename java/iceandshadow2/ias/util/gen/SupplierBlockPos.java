package iceandshadow2.ias.util.gen;

import iceandshadow2.boilerplate.BaseSupplier;
import iceandshadow2.boilerplate.Pipeline;
import iceandshadow2.adapters.BlockPos3;

/**
 * Filterable system for obtaining block positions matching some pattern.
 */
public abstract class SupplierBlockPos extends BaseSupplier<BlockPos3> implements ISupplierBlock {

	protected long x = 0, z = 0;
	protected int y = 0;
	private boolean done = false;
	private BlockPos3 buffer;
	private Pipeline<BlockPos3> filter = new BaseBlockFilter();

	@Override
	public void addFilter(Pipeline<BlockPos3> filt) {
		filter = filt;
	}

	private final void assureHasNext() {
		while (buffer == null && thisHasNext()) {
			final BlockPos3 toFilter = thisNext();
			buffer = filter.call(toFilter);
		}
		if (buffer == null)
			done = true;
	}

	@Override
	public final boolean hasNext() {
		if (!done)
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
	public ISupplierBlock offsetCenter(long x, long y, long z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	protected abstract boolean thisHasNext();

	protected abstract BlockPos3 thisNext();
}
