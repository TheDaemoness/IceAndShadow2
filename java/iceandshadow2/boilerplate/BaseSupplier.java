package iceandshadow2.boilerplate;

import java.util.Iterator;

/**
 * Quick and dirty base code for suppliers, requiring implementation of only the
 * iterator functions.
 */
public abstract class BaseSupplier<T> implements ISupplier<T> {

	@Override
	public T call() throws Exception {
		if (hasNext())
			return next();
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		return this;
	}
}
