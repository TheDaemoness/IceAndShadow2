package iceandshadow2.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class IaSBlockCmp {
	public static boolean isOneOf(World wld, Block bl, Block... bls) {
		for(Block blcmp : bls) {
			if(blcmp == bl)
				return true;
		}
		return false;
	}
}
