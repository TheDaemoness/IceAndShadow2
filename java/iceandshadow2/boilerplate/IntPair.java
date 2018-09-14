package iceandshadow2.boilerplate;

public class IntPair implements Comparable {
	protected int xValue, zValue;

	public IntPair() {
		xValue = 0;
		zValue = 0;
	}

	public IntPair(int x, int z) {
		xValue = x;
		zValue = z;
	}

	@Override
	public int compareTo(Object b) {
		if (b instanceof IntPair) {
		}
		return 0;
	}

	public boolean equals(IntPair b) {
		return x() == b.x() && z() == b.z();
	}

	@Override
	public boolean equals(Object b) {
		if (b instanceof IntPair)
			return equals((IntPair) b);
		return super.equals(b);
	}

	public boolean nonzero() {
		return xValue != 0 || zValue != 0;
	}

	public int x() {
		return xValue;
	}

	public int x(int newX) {
		x();
		xValue = newX;
		return xValue;
	}

	public int z() {
		return zValue;
	}

	public int z(int newZ) {
		z();
		zValue = newZ;
		return zValue;
	}
}
