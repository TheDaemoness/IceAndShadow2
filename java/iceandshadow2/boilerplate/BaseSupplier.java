package iceandshadow2.boilerplate;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Quick and dirty base code for suppliers, requiring implementation of only the iterator functions.
 */
public abstract class BaseSupplier<T> implements ISupplier<T> {

	@Override
	public Iterator<T> iterator() {
		return this;
	}
	
	@Override
	public T call() throws Exception {
		if(hasNext())
			return next();
		return null;
	}
}
