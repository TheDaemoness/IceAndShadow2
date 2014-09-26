package iceandshadow2.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
	public static Vec3 getBlockSideCoords(int x, int y, int z, ForgeDirection dir) {
		double xN = 0.5+0.5*dir.offsetX;
		double yN = 0.5+0.5*dir.offsetY;
		double zN = 0.5+0.5*dir.offsetZ;
		return Vec3.createVectorHelper(x-0.05+1.10*xN, y-0.05+1.10*yN, z-0.05+1.10*zN);
	}
	public static Vec3 getBlockSideCoords(int x, int y, int z, ForgeDirection dir, Random r, float size) {
		double xN = 0.5+0.5*dir.offsetX;
		if(dir.offsetX == 0)
			xN += r.nextDouble()*size - size/2.0;
		double yN = 0.5+0.5*dir.offsetY;
		if(dir.offsetY == 0)
			yN += r.nextDouble()*size - size/2.0;
		double zN = 0.5+0.5*dir.offsetZ;
		if(dir.offsetZ == 0)
			zN += r.nextDouble()*size - size/2.0;
		return Vec3.createVectorHelper(x-0.05+1.10*xN, y-0.05+1.10*yN, z-0.05+1.10*zN);
	}
}
