package iceandshadow2.boilerplate;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

import iceandshadow2.ias.util.BlockPos3;
import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 * Chainable system for providing a same-type output as an input object.
 * 
 * To customize functionality, overload getNext, getSelf, getResult, and getMessage.
 * Ideally, these are stateless, allowing reuse across multiple callers.
 */
public abstract class Pipeline<T> extends BaseSupplier<T> {
	private Pipeline<T> next;
	private Callable<T> callback;
	
	public Pipeline() {}
	public Pipeline(Pipeline<T> tocopy) {
		this.callback = tocopy.callback;
		this.next = tocopy.next;
	}
	
	public Pipeline<T> setNext(Pipeline<T> mut) {
		next = mut;
		return mut==null?this:mut;
	}
	public Pipeline<T> setInput(final T input) {
		callback = new Callable<T>() {public T call() {return input;}};
		return this;
	}
	public Pipeline<T> setCallback(Callable<T> supplier) {
		callback = supplier;
		return this;
	}

	protected Object getSelfMessage(T in, Object prevmsg) {
		return null;
	}
	protected abstract T getResult(T in, Object prevmsg, Object selfmsg);
	protected Object getNextMessage(T in, Object prevmsg, Object selfmsg) {
		return null;
	}
	protected Pipeline<T> getNext(T in, Object prevmsg, Object selfmsg) {
		return next;
	}
	protected Pipeline<T> getSelf(T in, Object prevmsg) {
		return in != null?this:null;
	}
	
	public final T call(T in) {
		Object
		msg = null,
		prevmsg = null;
		
		Pipeline<T>
		current = getSelf(in, msg);
		
		while(current != null) {
			final Object selfmsg = current.getSelfMessage(in, prevmsg);
			in = (T) current.getResult(in, msg, selfmsg);
			msg = current.getNextMessage(in, msg, selfmsg);
			current = current.getNext(in, prevmsg, selfmsg);
			prevmsg = msg;
			if(current != null)
				current = current.getSelf(in, msg);
		}
		return in;
	}
	
	public final T call() throws Exception {
		return call(callback.call());
	}
	
	@Override
	public boolean hasNext() {
		return callback != null;
	}
	
	@Override
	public final T next() {
		try {
			return call();
		} catch (Exception e) {
			return null;
		}
	}
}
