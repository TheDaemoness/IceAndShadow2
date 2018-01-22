package iceandshadow2.nyx.world.gen.ruins;

import java.util.Random;

import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.ias.util.gen.Sculptor;
import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class GenRuinsMines extends GenRuins {
	
	private final int SHAFT_FLOOR = 16;
	
	protected void genRewardsChest(World w, Random r, int x, int y, int z, EnumRarity quality) {
		
	}
	
	protected boolean genArm(World w, Random r, int x, int y, int z, ForgeDirection dir, int antichance) {
		if(y <= 6)
			return false;
		final int xO = x, yO = y, zO = z;
		int length = 24+r.nextInt(16);
		
		//Generate the arm itself.
		switch(dir) {
		case UP:
			if(r.nextBoolean() || y < SHAFT_FLOOR)
				break;
			final boolean hellend = r.nextInt(100) == 0;
			if(hellend)
				Sculptor.blast(w, x, y, z, 5);
			else
				Sculptor.sphere(w, x, y, z, 5, Blocks.web, 0);
			Sculptor.sphere(w, x, y, z, 3, Blocks.air, 0);
			y -= 2;
			Sculptor.cube(w, x-1, y-3, z-1, x+1, y, z+1, Blocks.obsidian, 0);
			w.setBlock(x, y-1, z, NyxBlocks.infestSpawner, hellend?2:1, 2);
			genRewardsChest(w, r, x, y-2, z, hellend?EnumRarity.epic:EnumRarity.rare);
			return false;
		case DOWN:
			genShaft(w, r, x, y, z, length/2, antichance);
			genArm(w, r, x, y-length/2, z, ForgeDirection.getOrientation(1+r.nextInt(5)), antichance);
			return true;
		case UNKNOWN:
			break;
		default:
			if(antichance > 6 || r.nextInt(antichance+3) > 3) {
				genRewardsChest(w, r, x, y-1, z, EnumRarity.common);
				return false;
			}
			final int[]
				slope = {0, r.nextInt(2), r.nextInt(3)/2},
				slant = {(r.nextInt(5)-2)/2, r.nextInt(5)-2, r.nextInt(3)-1};
			for(int i = 0; i < length; ++i) {
				final int
					slopeIndex = (i/(1+length/slope.length)),
					slantIndex = (i/(1+length/slant.length));
				for(int xi = -5; xi <= 5; ++xi) {
					for(int zi = -5; zi <= 5; ++zi) {
						for(int yi = -1; yi <= 5; ++yi) {
							final Block bl = w.getBlock(x+xi, y+yi, z+zi);
							if(bl == NyxBlocks.exousicWater)
								return true;
							if(bl == Blocks.bedrock) {
								++y;
								slope[slopeIndex] = 0;
							} else if(bl == NyxBlocks.infestSpawner && w.getBlockMetadata(x+xi, y+yi, z+zi) == 0) {
								w.setBlock(x+xi, y-2, z+zi, NyxBlocks.infestSpawner);
								w.setBlockToAir(x+xi, y+yi, z+zi);
							}
						}
					}
				}
				Sculptor.dome(w, x-1+r.nextInt(3), y, z-1+r.nextInt(3), r.nextInt(3)==0?4:3, Blocks.air, 0);
				if(r.nextInt(14) == 0)
					w.setBlock(x, y-2, z, NyxBlocks.infestSpawner);
				final ForgeDirection
					slantMod = dir.getRotation(slant[slantIndex]<0?ForgeDirection.DOWN:ForgeDirection.UP);	
				x += dir.offsetX + slantMod.offsetX*(r.nextInt(1+Math.abs(slant[slantIndex])));
				y -= r.nextInt(1+slope[slopeIndex])>0?1:0;
				z += dir.offsetZ + slantMod.offsetZ*(r.nextInt(1+Math.abs(slant[slantIndex])));
			}
		}
		
		//Generate the connecting arms, recursively.
		ForgeDirection ndir = ForgeDirection.getOrientation(r.nextInt(ForgeDirection.values().length));
		if(ndir == dir && r.nextInt(3) == 0) {
			final ForgeDirection which = r.nextBoolean()?ForgeDirection.UP:ForgeDirection.DOWN;
			if(genArm(w, r, x, y, z, ndir.getRotation(which), antichance+1))
				genArm(w, r, x, y, z, ndir.getRotation(which.getOpposite()), antichance+1);
		} else {
			if(ndir == dir.getOpposite())
				ndir = dir;
			genArm(w, r, x, y, z, ndir, antichance+1);
		}
		if(dir == dir.UNKNOWN)
			genRewardsChest(w, r, xO, yO, zO, EnumRarity.uncommon);
		else if(ndir != dir.UP) {
			if(dir == dir.DOWN)
				genRewardsChest(w, r, x, y, z, EnumRarity.uncommon);
			else
				w.setBlock(x, y-3, z, NyxBlocks.infestSpawner);
		}
		return true;
	}
	
	protected void genShaft(World w, Random r, int x, int y, int z, int height, int antichance) {
		final int e = Math.max(y-height, SHAFT_FLOOR);
		int yi = y;
		for(; yi > e; yi -= 1+r.nextInt(2)) {
			Sculptor.dome(w, x-1+r.nextInt(3), yi, z-1+r.nextInt(3), r.nextInt(4)==0?3:4, Blocks.air, 0);
			if(yi < 64) {
				if(yi%6 ==0)
					genArm(w, r, x, yi, z, ForgeDirection.getOrientation(2+r.nextInt(4)), antichance);
			}
		}
		Sculptor.corners(w, x-1, yi-1, z-1, x+1, y+3, z+1, NyxBlocks.planks, 1);
	}

	@Override
	public void buildPass(World w, Random r, int x, int y, int z) {
		genArm(w, r, x, SHAFT_FLOOR, z, ForgeDirection.NORTH, 0);
		genArm(w, r, x, SHAFT_FLOOR, z, ForgeDirection.SOUTH, 0);
		genArm(w, r, x, SHAFT_FLOOR, z, ForgeDirection.WEST, 0);
		genArm(w, r, x, SHAFT_FLOOR, z, ForgeDirection.EAST, 0);
		genShaft(w, r, x, y, z, y, 0);
		genRewardsChest(w, r, x, SHAFT_FLOOR-1, z, EnumRarity.common);
	}

	@Override
	public boolean canGenerateHere(World var1, Random var2, int x, int y, int z) {
		if(y > 190)
			return false;
		for(; y > 8; --y) {
			for(int xi = -5; xi <= 5; ++xi) {
				for(int zi = -5; zi <= 5; ++zi) {
					if(var1.getBlock(x+xi, y, z+zi) == NyxBlocks.exousicWater)
						return false;
				}
			}
		}
		return var2.nextBoolean();
	}

	@Override
	public void damagePass(World w, Random r, int x, int y, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLowercaseName() {
		return "infested-mineshaft";
	}

	@Override
	public void rewardPass(World var1, Random var2, int x, int y, int z) {
		// TODO Auto-generated method stub

	}

}
