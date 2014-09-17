package iceandshadow2.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class IaSBlockHelper {
	public static boolean isOneOf(World wld, Block bl, Block... bls) {
		for(Block blcmp : bls) {
			if(blcmp == bl)
				return true;
		}
		return false;
	}
	public static boolean isAir(Block bl) {
		return bl.getMaterial() == Material.air;
	}
}
