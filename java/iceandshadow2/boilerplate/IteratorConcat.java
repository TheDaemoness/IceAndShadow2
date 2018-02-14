package iceandshadow2.boilerplate;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

/**
 * Implements a list of iterators whose outputs are seamlessly concatenated.
 */
public class IteratorConcat<T, It extends Iterator<T>> extends BaseSupplier<T> implements IResetable {
	protected List<It> iters;
	protected Iterator<It> listiter;
	protected It currentiter;
	
	public IteratorConcat() {
		iters = new LinkedList<It>();
	}
	
	public boolean add(It newiter) {
		iters.add(newiter);
		reset();
		return true;
	}
	
	protected void assureValidIter() {
		while(!currentiter.hasNext()) {
			if(listiter.hasNext())
				currentiter = listiter.next();
			else {
				currentiter = null;
				break;
			}
		}
	}

	@Override
	public boolean hasNext() {
		assureValidIter();
		return currentiter != null; //Weak.
	}

	/**
	 * @throws NullPointerException upon being called when hasNext() is false.
	 */
	@Override
	public T next() {
		assureValidIter();
		return currentiter.next();
	}

	/**
	 * Resets the collection as much as possible. Does NOT reset contained iterators.
	 */
	@Override
	public boolean reset() {
		listiter = iters.iterator();
		currentiter = listiter.next();
		return false; //Does not reset individual iterators.
	}
}
