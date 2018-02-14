package iceandshadow2.boilerplate;

/**
 * Utility functions for using the bits (esp. the sign bit) on an integer for something else.
 */
public class IntBits  {
	public static int encode(int value, boolean flag) {
		return ((value & (-1 >>> 1))+(flag?1:0))*(flag?-1:1);
	}
	public static int value(int value) {
		return Math.abs(value+(flag(value)?1:0));
	}
	public static boolean flag(int value) {
		return value<0;
	}
	
	//The following are common and simple-enough operations, but they're annoying to type out.
	
	public static boolean areAllSet(int value, int bitmask) {
		return (value & bitmask) == bitmask;
	}
	public static boolean areAnySet(int value, int bitmask) {
		return (value & bitmask) != 0;
	}
}
