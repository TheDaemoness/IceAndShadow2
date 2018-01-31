package iceandshadow2.ias.util;

/**
 * Utility functions for using the sign bit on an integer for something else.
 */
public class IntFlagged  {
	public static int encode(int value, boolean flag) {
		return ((value & (-1 >>> 1))+(flag?1:0))*(flag?-1:1);
	}
	public static int value(int value) {
		return Math.abs(value+(flag(value)?1:0));
	}
	public static boolean flag(int value) {
		return value<0;
	}
}
