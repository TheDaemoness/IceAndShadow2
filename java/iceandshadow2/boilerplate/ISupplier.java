package iceandshadow2.boilerplate;

import java.util.Iterator;
import java.util.concurrent.Callable;

/**
 * Interface for a general use supplying-type class that can fill most "provide
 * objects of type T" roles.
 */
public interface ISupplier<T> extends Iterator<T>, Iterable<T>, Callable<T> {
}
