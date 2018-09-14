package iceandshadow2.ias.util.gen;

import iceandshadow2.boilerplate.IResetable;
import iceandshadow2.boilerplate.ISupplier;
import iceandshadow2.boilerplate.Pipeline;
import iceandshadow2.adapters.BlockPos3;

/**
 * Minimum interface for block iterators.
 */
public interface ISupplierBlock extends ISupplier<BlockPos3>, IResetable {

	public void addFilter(Pipeline<BlockPos3> filt);

	public ISupplierBlock offsetCenter(long x, long y, long z);
}
