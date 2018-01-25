package iceandshadow2.nyx.world.gen.ruins;

import java.util.Random;

import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.ias.util.gen.Sculptor;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.blocks.utility.NyxBlockRail;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class GenRuinsMines extends GenRuins {

	private final int SHAFT_FLOOR = 16;

	protected boolean placeRail(World w, int x, int y, int z, ForgeDirection toward, ForgeDirection curve) {
		final Block support = w.getBlock(x, y-1, z);
		if(curve == null || curve == toward || curve == toward.getOpposite()) {
			if(IaSBlockHelper.isAir(support)) {
				if(!IaSBlockHelper.isAir(w.getBlock(x, y-2, z))) {
					switch(toward) {
					case NORTH:
						w.setBlock(x, y-1, z, NyxBlocks.rail, 5, 2); break;
					case SOUTH:
						w.setBlock(x, y-1, z, NyxBlocks.rail, 4, 2); break;
					case WEST:
						w.setBlock(x, y-1, z, NyxBlocks.rail, 2, 2); break;
					case EAST:
						w.setBlock(x, y-1, z, NyxBlocks.rail, 3, 2); break;
					default:
						break;
					}
					return true;
				}
			} else if(toward == ForgeDirection.WEST || toward == ForgeDirection.EAST) {
				w.setBlock(x, y, z, NyxBlocks.rail, 1, 2);
				w.setBlock(x, y-1, z, NyxBlocks.stone);
			} else if(toward == ForgeDirection.NORTH || toward == ForgeDirection.SOUTH) {
				w.setBlock(x, y, z, NyxBlocks.rail, 0, 2);
				w.setBlock(x, y-1, z, NyxBlocks.stone);
			}
		} else {
			//ewwe
			//ssnn
			int md = 5;
			for(int i = 0; i < 4; ++i) {
				final ForgeDirection
					ew = ((i+1)&2)==1?ForgeDirection.WEST:ForgeDirection.EAST,
					sn = (i&2)==1?ForgeDirection.NORTH:ForgeDirection.SOUTH;
				if((toward == ew && curve == sn) || (curve == ew && toward == sn)) {
					md = 6+i;
					break;
				}
			}
			if(md != 0) {
				w.setBlock(x, y, z, NyxBlocks.rail, md, 2);
				w.setBlock(x, y-1, z, NyxBlocks.stone);
			}
		}
		return false;
	}

	protected ForgeDirection pick(ForgeDirection normal, int xRequirement, int zRequirement) {
		if(Math.abs(xRequirement) > Math.abs(zRequirement)) {
			normal = xRequirement>0?ForgeDirection.EAST:ForgeDirection.WEST;
		} else if(Math.abs(zRequirement) > Math.abs(xRequirement)) {
			normal = zRequirement>0?ForgeDirection.SOUTH:ForgeDirection.NORTH;
		}
		return normal;
	}

	protected void makeRails(World w, ForgeDirection toward, int xP, int yP, int zP, int xN, int yN, int zN) {
		int xRequirement = xN-xP;
		int zRequirement = zN-zP;
		ForgeDirection old = toward;
		int
			x = xP,
			y = yP,
			z = zP;
		while(xRequirement != 0 || zRequirement != 0) {
			final Block
				space = w.getBlock(x+toward.offsetX, y, z+toward.offsetZ),
				support = w.getBlock(x+toward.offsetX, y-1, z+toward.offsetZ);
			if(!IaSBlockHelper.isAir(space) || IaSBlockHelper.isAir(support)) {
				toward = pick(toward, xRequirement, zRequirement);
				old = toward;
			}
			y -= placeRail(w, x+toward.offsetX, y, z+toward.offsetZ, old, toward)?1:0;
			x += toward.offsetX;
			z += toward.offsetZ;
			xRequirement -= toward.offsetX;
			zRequirement -= toward.offsetZ;
			if(xRequirement == 0 || zRequirement == 0) {
				toward = pick(toward, xRequirement, zRequirement);
				old = toward;
			}
		}
	}

	protected void genRewardsChest(World w, Random r, int x, int y, int z, EnumRarity quality) {
		final Block bl = w.getBlock(x, y, z);
		if(IaSBlockHelper.isAir(bl) || bl instanceof NyxBlockRail) {
			--y;
		}
		Sculptor.cube(w, x-1, y-1, z-1, x+1, y, z+1, quality == EnumRarity.common?NyxBlocks.brickExousic:Blocks.obsidian, 0);
		w.setBlock(x, y, z, Blocks.chest, r.nextInt(4), 0);

		final TileEntityChest chestent = (TileEntityChest) w.getTileEntity(x, y, z);

		// Add more random loot.
		chestent.setInventorySlotContents(0, new ItemStack(NyxItems.page, 1));
		int amount;
		switch(quality) {
			default: amount = 5; break;
			case uncommon: amount = 8; break;
			case rare: amount = 12; break;
			case epic: amount = 17; break;
		}
		final int chestcontentamount = amount + r.nextInt(amount/2);
		boolean rareflag = quality!=EnumRarity.common;
		for (byte i = 0; i < chestcontentamount; ++i) {
			final int rewardid = r.nextInt(100);
			ItemStack item = new ItemStack(r.nextBoolean()?NyxItems.echirDust:NyxItems.salt, 3 + r.nextInt(3), 0);

			if(rewardid < 5 && rareflag) {
				item = new ItemStack(NyxItems.navistraShard, 1 + r.nextInt(2));
				rareflag = quality==EnumRarity.epic;
			} else if(rewardid < 15) { //Pickaxe.
				if(quality == EnumRarity.common || quality == EnumRarity.uncommon) {
					item = IaSTools.setToolMaterial(IaSTools.pickaxe,
							quality == EnumRarity.uncommon&&r.nextBoolean()?(r.nextBoolean()?"Devora":"Cortra"):"Echir");
					item.setItemDamage(r.nextInt(64));
				} else {
					item = new ItemStack(quality==EnumRarity.epic?NyxItems.alabaster:NyxItems.alabasterShard);
				}
			} else if(rewardid < 30) { //Cortra.
				item = new ItemStack(NyxItems.cortra, 2+r.nextInt(3));
			} else if(rewardid < 40) { //Rations.
				if(quality == EnumRarity.common || quality == EnumRarity.uncommon) {
					item = new ItemStack(NyxItems.bread);
				} else {
					item = new ItemStack(NyxItems.amber);
				}
			} else if(rewardid < 55) { //Ropes.
				if(quality == EnumRarity.common || quality == EnumRarity.uncommon) {
					item = new ItemStack(NyxItems.rope);
				} else {
					item = new ItemStack(NyxItems.draconium, 1, quality==EnumRarity.epic?1:0);
				}
			} else if(rewardid < 65) {
				item = new ItemStack(NyxItems.devora, 2+r.nextInt(5));
			} else if(rewardid < 75 && (rareflag || r.nextBoolean())) {
				item = new ItemStack(NyxItems.amber);
				rareflag = quality==EnumRarity.epic;
			}

			chestent.setInventorySlotContents(1+i, item);
		}

		if (r.nextInt(amount-3) > 1) {
			chestent.setInventorySlotContents(0, new ItemStack(NyxItems.clockwork));
		}
	}

	/**
	 * Generates a tunnel into the ground.
	 * @return False if arm generation determined this is a dead-end.
	 */
	protected boolean genArm(World w, Random r, int x, int y, int z, ForgeDirection dir, int antichance) {
		if(y <= 6)
			return false;
		final int xO = x, yO = y, zO = z;
		final int length = 24+r.nextInt(16);

		//Generate the arm itself.
		switch(dir) {
		case UP:
			if(r.nextBoolean() || y < SHAFT_FLOOR) {
				break;
			}
			final boolean hellend = r.nextInt(100) == 0;
			if(hellend) {
				Sculptor.blast(w, x, y, z, 5);
			} else {
				Sculptor.sphere(w, x, y, z, 5, Blocks.web, 0);
			}
			Sculptor.sphere(w, x, y, z, 3, Blocks.air, 0);
			y -= 2;
			Sculptor.cube(w, x-1, y-4, z-1, x+1, y, z+1, Blocks.obsidian, 0);
			w.setBlock(x, y, z, NyxBlocks.infestSpawner, 1, 2);
			w.setBlock(x, y-4, z, NyxBlocks.infestSpawner, hellend?2:1, 2);
			genRewardsChest(w, r, x, y-2, z, hellend?EnumRarity.epic:EnumRarity.rare);
			return false;
		case DOWN:
			y -= genShaft(w, r, x, y, z, length/2, antichance);
			//Preserve fallthrough.
		case UNKNOWN:
			break;
		default:
			boolean nospawner = true;
			if(antichance > 6 || r.nextInt(antichance+3) > 3)
				return false;
			final int[]
				slope = {0, r.nextInt(2), r.nextInt(3)/2},
				slant = {(r.nextInt(5)-2)/2, r.nextInt(5)-2, r.nextInt(3)-1};
			for(int i = 0; i < length; ++i) {
				final int
					slopeIndex = (i/(1+length/slope.length)),
					slantIndex = (i/(1+length/slant.length));
				for(int xi = -6; xi <= 6; ++xi) {
					for(int zi = -6; zi <= 6; ++zi) {
						for(int yi = -2; yi <= 5; ++yi) {
							final Block bl = w.getBlock(x+xi, y+yi, z+zi);
							if(bl == NyxBlocks.exousicWater || bl == Blocks.chest)
								return true;
							if(bl == Blocks.bedrock) {
								++y;
								slope[slopeIndex] = 0;
							} else if(bl == NyxBlocks.infestSpawner && w.getBlockMetadata(x+xi, y+yi, z+zi) == 0) {
								w.setBlock(x+xi, y-3, z+zi, NyxBlocks.infestSpawner);
								w.setBlockToAir(x+xi, y+yi, z+zi);
							}
						}
					}
				}
				Sculptor.dome(w,
					x-1+r.nextInt(3),
					y,
					z-1+r.nextInt(3),
					r.nextInt(3)==0?4:3, Blocks.air, 0);
				if(r.nextInt(14) == 0 && !nospawner) {
					w.setBlock(x, y-3, z, NyxBlocks.infestSpawner);
					nospawner = true;
				} else {
					nospawner = false;
				}
				final ForgeDirection
					slantMod = dir.getRotation(slant[slantIndex]<0?ForgeDirection.DOWN:ForgeDirection.UP);
				x += dir.offsetX + slantMod.offsetX*(r.nextInt(1+Math.abs(slant[slantIndex])));
				y -= r.nextInt(1+slope[slopeIndex])>0?1:0;
				z += dir.offsetZ + slantMod.offsetZ*(r.nextInt(1+Math.abs(slant[slantIndex])));
			}
			makeRails(w, dir, xO, yO, zO, x, y, z);
		}

		//Generate the connecting arms, recursively.
		ForgeDirection ndir = ForgeDirection.getOrientation(r.nextInt(ForgeDirection.values().length));
		boolean result = true;
		if(ndir == dir && r.nextInt(3) == 0 && ndir != ForgeDirection.DOWN) {
			final ForgeDirection which = r.nextBoolean()?ForgeDirection.UP:ForgeDirection.DOWN;
			if(genArm(w, r, x, y, z, ndir.getRotation(which), antichance+1)) {
				genArm(w, r, x, y, z, ndir.getRotation(which.getOpposite()), antichance+1);
			} else {
				result = false;
			}
		} else {
			if(ndir == dir && ndir == ForgeDirection.UNKNOWN) {
				ndir = ForgeDirection.DOWN;
			} else if(ndir == dir.getOpposite()) {
				ndir = dir;
			}
			result=genArm(w, r, x, y, z, ndir, antichance+1);
		}
		if(dir == ForgeDirection.UNKNOWN || dir == ForgeDirection.DOWN) {
			genRewardsChest(w, r, x, y, z, EnumRarity.uncommon);
		} else if(dir != ForgeDirection.UP) {
			if(!result) {
				genRewardsChest(w, r, x, y, z, EnumRarity.common);
			} else {
				w.setBlock(x, y-3, z, NyxBlocks.infestSpawner);
			}
		}
		return true;
	}

	protected int genShaft(World w, Random r, int x, int y, int z, int height, int antichance) {
		if(y <= SHAFT_FLOOR)
			return 0;
		final int e = Math.max(y-height, SHAFT_FLOOR);
		int yi = y;
		for(; yi > e; yi -= 1+r.nextInt(2)) {
			Sculptor.dome(w, x-1+r.nextInt(3), yi, z-1+r.nextInt(3), r.nextInt(4)==0?3:4, Blocks.air, 0);
			if(yi < 64) {
				if(yi%6 ==0) {
					genArm(w, r, x, yi, z, ForgeDirection.getOrientation(2+r.nextInt(4)), antichance);
				}
			}
		}
		Sculptor.corners(w, x-1, yi-1, z-1, x+1, y+3, z+1, NyxBlocks.planks, 1);
		return y-yi;
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
		return var2.nextInt(4) == 0;
	}

	@Override
	public void damagePass(World w, Random r, int x, int y, int z) {
		//No-op.
	}

	@Override
	public String getLowercaseName() {
		return "infested-mineshaft";
	}

	@Override
	public void rewardPass(World var1, Random var2, int x, int y, int z) {
		//No-op. The rewards are actually generated by the build pass.
	}

}
