package iceandshadow2.ias.util.gen;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import iceandshadow2.boilerplate.IResetable;
import iceandshadow2.boilerplate.ISupplier;
import iceandshadow2.boilerplate.Pipeline;
import iceandshadow2.ias.util.BlockPos3;
import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 * Minimum interface for block iterators.
 */
public interface ISupplierBlock extends ISupplier<BlockPos3>, IResetable {
	
	public ISupplierBlock offsetCenter(long x, long y, long z);
	public void addFilter(Pipeline<BlockPos3> filt);
}
