package iceandshadow2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Exception-suppressing FutureTask.
 */
public class IaSFuture<T> extends FutureTask<T> {
	protected Exception e = null;

	public IaSFuture(Callable arg0) {
		super(arg0);
	}

	public IaSFuture(Runnable arg0, T arg1) {
		super(arg0, arg1);
	}

	@Override
	public T get() {
		try {
			return super.get();
		} catch (final Exception e) {
			this.e = e;
			return null;
		}
	}

	public Exception getException() {
		return e;
	}
}
