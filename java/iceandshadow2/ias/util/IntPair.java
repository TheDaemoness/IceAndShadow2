package iceandshadow2.ias.util;

public class IntPair implements Comparable {
	protected int xValue, zValue;
	
	public IntPair(int x, int z) {
		xValue = x;
		zValue = z;
	}
	
	public int x(int newX) {
		final int temp = x();
		xValue = newX;
		return xValue;
	}
	public int z(int newZ) {
		final int temp = z();
		zValue = newZ;
		return zValue;
	}
	public int x() {return xValue;}
	public int z() {return zValue;}
	
	@Override
	public boolean equals(Object b) {
		if(b instanceof IntPair)
			return equals((IntPair)b);
		return super.equals(b);
	}
	public boolean equals(IntPair b) {
		return x()==b.x() && z()==b.z();
	}
	
	@Override
	public int compareTo(Object b) {
		if(b instanceof IntPair) {
			IntPair ip = (IntPair)b;
		}
		return 0;
	}
}
