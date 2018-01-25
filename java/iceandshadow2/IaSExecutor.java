package iceandshadow2;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A singleton that wraps provided runnables and callables in futures, then hands them off to a work-stealing thread pool.
 * A more interesting setup may be worked out later.
 */
public class IaSExecutor implements ExecutorService {
	private static final IaSExecutor obj = new IaSExecutor();
	public static IaSExecutor instance() {return obj;}

	ExecutorService executor;
	protected IaSExecutor() {
		executor = Executors.newWorkStealingPool(Math.max(1, Runtime.getRuntime().availableProcessors()-1));
	}
	public static IaSFuture push(Runnable r) {
		final IaSFuture f = new IaSFuture(r, null);
		obj.execute(f);
		return f;
	}
	public static IaSFuture push(Callable r) {
		final IaSFuture f = new IaSFuture(r);
		obj.execute(f);
		return f;
	}

	@Override
	public void execute(Runnable arg0) {
		executor.execute(arg0);
	}

	@Override
	public boolean awaitTermination(long arg0, TimeUnit arg1) throws InterruptedException {
		return executor.awaitTermination(arg0, arg1);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> arg0) throws InterruptedException {
		return executor.invokeAll(arg0);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> arg0, long arg1, TimeUnit arg2)
			throws InterruptedException {
		return executor.invokeAll(arg0, arg1, arg2);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> arg0) throws InterruptedException, ExecutionException {
		return executor.invokeAny(arg0);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> arg0, long arg1, TimeUnit arg2)
			throws InterruptedException, ExecutionException, TimeoutException {
		return executor.invokeAny(arg0, arg1, arg2);
	}

	@Override
	public boolean isShutdown() {
		return executor.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return executor.isTerminated();
	}

	@Override
	public void shutdown() {
		executor.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return executor.shutdownNow();
	}

	@Override
	public <T> Future<T> submit(Callable<T> arg0) {
		return executor.submit(arg0);
	}

	@Override
	public Future<?> submit(Runnable arg0) {
		return executor.submit(arg0);
	}

	@Override
	public <T> Future<T> submit(Runnable arg0, T arg1) {
		return executor.submit(arg0, arg1);
	}
}
