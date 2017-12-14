package iceandshadow2.styx;

import iceandshadow2.IceAndShadow2;
import net.minecraft.block.Block;

public class Styx {
	public static final Block reserved, air, ground, escape, gatestone;

	static {
		reserved = new StyxBlockNoReplace("Reserved").register();
		air = new StyxBlockAir("Air").register();
		ground = new StyxBlockBarrier("Barrier").register();
		escape = new StyxBlockEscape("Escape").register();
		gatestone = new StyxBlockGatestone("Gatestone").register();
	}

	public static void init(IceAndShadow2 ias) {
	};
}
