package iceandshadow2.boilerplate;

/**
 * Utility for storing a number of tiny (< 1 nibble, on average) distinct
 * integer values within one long. Performs minimal range checking, except upon
 * construction.
 */
public class DenseIntArrayEncoder {
	protected final int[] sizes;

	public DenseIntArrayEncoder(int[] encodings) {
		int truesize = encodings.length;
		for (final int value : encodings)
			if (value <= 1)
				--truesize;
		sizes = new int[truesize];
		int i = 0;
		for (final int value : encodings)
			if (value > 1)
				sizes[i++] = value;
	}

	public int getLength() {
		return sizes.length;
	}

	public long getMax() {
		long ret = 1;
		for (final int size : sizes)
			ret *= size;
		return ret;
	}

	public int[] read(long value) {
		final int[] ret = new int[sizes.length];
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = (int) (value % (sizes[i]));
			value /= sizes[i];
		}
		return ret;
	}

	public boolean setSize(int count, int newsize) {
		if (count < 0 || count >= sizes.length || newsize <= 0)
			return false;
		sizes[count] = newsize;
		return true;
	}

	public long write(int values[]) {
		final int e = Math.min(values.length, sizes.length);
		long ret = 0, multi = 1;
		for (int i = 0; i < e; ++i) {
			ret += values[i] * multi;
			multi *= sizes[i];
		}
		return ret;
	}
}
