package iceandshadow2.nyx.world.gen.ruins;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.items.tools.NyxItemBow;
import iceandshadow2.util.gen.Sculptor;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GenRuinsTowerLookout extends GenRuins {

	/**
	 * Determines whether or not ruins can be generated here. Does not do any
	 * building.
	 */
	@Override
	public boolean canGenerateHere(World var1, Random var2, int x, int y, int z) {
		for (int xdelta = -36; xdelta <= 36; ++xdelta) {
			for (int zdelta = -36; zdelta <= 36; ++zdelta)
				for (int ydelta = -4; ydelta <= 8; ++ydelta) {
					if (var1.getBlock(x + xdelta, y + ydelta, z + zdelta) == NyxBlocks.brickPale)
						return false;
				}
		}
		return Math.abs(x) > 16 || Math.abs(z) > 16;
	}

	/**
	 * Generates the basic structure of the building. May also even out terrain
	 * that the building is on.
	 */
	@Override
	public void buildPass(World world, Random var2, int x, int y, int z) {
		Sculptor.terrainFlatten(world, x - 3, y - 1, z - 3, x + 3, 4, z + 3);

		Sculptor.walls(world, x - 1, y - 3, z - 1, x + 1, y - 3, z + 1,
				world.getBiomeGenForCoords(x, z).fillerBlock, 0);
		Sculptor.cube(world, x - 2, y - 2, z - 2, x + 2, y - 2, z + 2,
				world.getBiomeGenForCoords(x, z).fillerBlock, 0);
		Sculptor.walls(world, x - 3, y - 1, z - 3, x + 3, y - 1, z + 3,
				world.getBiomeGenForCoords(x, z).fillerBlock, 0);
		Sculptor.cube(world, x - 2, y - 1, z - 2, x + 2, y, z + 2,
				NyxBlocks.brickPale, 0);
		Sculptor.cube(world, x - 4, y, z - 4, x + 4, y + 13, z + 4, Blocks.air,
				0);

		Sculptor.walls(world, x - 2, y, z - 2, x + 2, y + 10, z + 2,
				NyxBlocks.brickPale, 0);
		Sculptor.walls(world, x - 3, y + 11, z - 3, x + 3, y + 11, z + 3,
				NyxBlocks.brickPale, 0);
		Sculptor.corners(world, x - 2, y + 10, z - 2, x + 2, y + 10, z + 2,
				NyxBlocks.brickPale, 0);
	}

	/**
	 * "Ruins" the basic structure and adds a few decorative and functional
	 * touches to the building, like ladders, doorways, and spawners.
	 */
	@Override
	public void damagePass(World var1, Random var2, int x, int y, int z) {
		/*
		 * Direction (f) variable cheat sheet: 0: South (+z) 1: West (-x) 2:
		 * North (-z) 3: East (+x)
		 */

		// Destruction pass.
		for (int ydim = 4; ydim <= 12; ydim += 2) {
			final int xpos = x - 2 + var2.nextInt(5);
			final int zpos = z - 2 + var2.nextInt(5);
			if (var2.nextInt(3) != 0)
				Sculptor.blast(var1, xpos, y + ydim, zpos,
						1.5 + var2.nextDouble());
		}

		final int f = var2.nextInt(4);
		for (int xdim = -2; xdim <= 2; ++xdim) {
			for (int zdim = -2; zdim <= 2; ++zdim) {

				// Check to make sure we're dealing with a wall.
				if (MathHelper.abs_int(xdim) == 2
						|| MathHelper.abs_int(zdim) == 2) {
					// Swiss cheese pass.
					for (int ydim = 1; ydim < 10; ++ydim) {
						if (!var1.isAirBlock(x + xdim, y + ydim, z + zdim)) {
							if (var2.nextInt(5) == 0)
								var1.setBlock(x + xdim, y + ydim, z + zdim,
										Blocks.air);
							else if (var2.nextInt(3) == 0)
								var1.setBlock(x + xdim, y + ydim, z + zdim,
										NyxBlocks.brickPaleCracked);
						}
					}
				}

				// LADDER!
				for (int ydim = 10; ydim > -1; --ydim) {
					if (f == 0) {
						if (!var1.isAirBlock(x, y + ydim, z - 2))
							var1.setBlock(x, y + ydim, z - 1, Blocks.ladder,
									0x3, 0x2);
					} else if (f == 1) {
						if (!var1.isAirBlock(x + 2, y + ydim, z))
							var1.setBlock(x + 1, y + ydim, z, Blocks.ladder,
									0x4, 0x2);
					} else if (f == 2) {
						if (!var1.isAirBlock(x, y + ydim, z + 2))
							var1.setBlock(x, y + ydim, z + 1, Blocks.ladder,
									0x2, 0x2);
					} else if (f == 3) {
						if (!var1.isAirBlock(x - 2, y + ydim, z))
							var1.setBlock(x - 1, y + ydim, z, Blocks.ladder,
									0x5, 0x2);
					}
				}

				// Add an entrance to the tower.
				for (int ydim = 0; ydim < 2; ++ydim) {
					if (f == 0)
						var1.setBlockToAir(x, y + ydim, z + 2);
					else if (f == 1)
						var1.setBlockToAir(x - 2, y + ydim, z);
					else if (f == 2)
						var1.setBlockToAir(x, y + ydim, z - 2);
					else
						var1.setBlockToAir(x + 2, y + ydim, z);
				}
			}
		}
	}

	@Override
	public String getLowercaseName() {
		return "ruins-tower-lookout";
	}

	/**
	 * Adds primarily reward chests. Not all ruins will have rewards, but most
	 * will and a coder is free to have this return instantly.
	 */
	@Override
	public void rewardPass(World var1, Random var2, int x, int y, int z) {
		if (var2.nextInt(3) == 0)
			return;
		final int chestpos = var2.nextInt(4);

		// Create a chest.
		int xloc = x, zloc = z, chestf = 0;
		if (chestpos == 0) {
			zloc -= 1;
			xloc -= 1;
			chestf = 0x3;
		} else if (chestpos == 1) {
			zloc += 1;
			xloc -= 1;
			chestf = 0x4;
		} else if (chestpos == 2) {
			zloc += 1;
			xloc += 1;
			chestf = 0x2;
		} else {
			zloc -= 1;
			xloc += 1;
			chestf = 0x5;
		}
		var1.setBlock(xloc, y, zloc, Blocks.chest, chestf, 0x2);

		// Start filling the chest with goodies.
		final TileEntityChest chestent = (TileEntityChest) var1.getTileEntity(
				xloc, y, zloc);

		// Add more random loot.
		final int chestcontentamount = 4 + var2.nextInt(4);
		boolean rareflag = true;
		boolean boneflag = true;
		for (byte i = 0; i < chestcontentamount; ++i) {
			final int rewardid = var2.nextInt(100);
			ItemStack itemz = new ItemStack(NyxItems.icicle,
					1 + var2.nextInt(4));

			// Magic repository.
			if (rewardid == 0 && rareflag) {
				itemz = new ItemStack(NyxItems.magicRepo);
				rareflag = false;
			}

			// Long bow
			else if (rewardid < 5 && rareflag) {
				final NBTTagCompound c = new NBTTagCompound();
				if(var2.nextInt(3) == 0)
					itemz = new ItemStack(NyxItems.frostBowShort, 1,
							48 + var2.nextInt(96));
				else
					itemz = new ItemStack(NyxItems.frostBowLong, 1,
						32 + var2.nextInt(64));
				c.setInteger(NyxItemBow.nbtTierID, var2.nextInt(3)==0?1:2);
				itemz.setTagCompound(c);
				rareflag = false;
			}

			// Sword or armor!
			else if (rewardid < 10) {
				if (var2.nextInt(3) == 0) {
					itemz = new ItemStack(Items.diamond_sword);
					itemz.addEnchantment(Enchantment.smite, 1 + var2.nextInt(2));
				} else {
					switch (var2.nextInt(6)) {
					case 0:
					case 1:
						itemz = new ItemStack(Items.diamond_chestplate);
						break;
					case 2:
					case 3:
						itemz = new ItemStack(Items.diamond_leggings);
						break;
					case 4:
						itemz = new ItemStack(Items.diamond_helmet);
						itemz.addEnchantment(Enchantment.thorns,
								1 + var2.nextInt(2));
						break;
					case 5:
						itemz = new ItemStack(Items.diamond_boots);
						itemz.addEnchantment(Enchantment.featherFalling,
								1 + var2.nextInt(2));
						break;
					}
					itemz.addEnchantment(Enchantment.protection,
							1 + var2.nextInt(2));
				}
			}

			// Cortra.
			else if (rewardid < 20)
				itemz = new ItemStack(NyxItems.cortra, 2 + var2.nextInt(3));

			// Bones.
			else if (rewardid < 35)
				itemz = new ItemStack(NyxItems.boneCursed, 1);

			// Food.
			else if (rewardid < 50) {
				final int foodtype = var2.nextInt(20);
				if (foodtype < 6)
					itemz = new ItemStack(Items.golden_apple,
							1 + var2.nextInt(2));
				else if (foodtype < 12)
					itemz = new ItemStack(NyxItems.bread, 2 + var2.nextInt(4));
				else if (foodtype < 16)
					itemz = new ItemStack(NyxItems.cookie, 3 + var2.nextInt(5));
				else
					itemz = new ItemStack(NyxItems.silkBerries, 3 + var2.nextInt(3));
			}

			// Sanctified Bone
			else if (rewardid < 55 && boneflag) {
				itemz = new ItemStack(NyxItems.boneSanctified);
				boneflag = false;
			}

			// Ender pearls
			else if (rewardid < 70)
				itemz = new ItemStack(Items.ender_pearl, 1 + var2.nextInt(3));

			chestent.setInventorySlotContents(1 + var2.nextInt(26), itemz);
		}

		if (var2.nextInt(4) != 0)
			chestent.setInventorySlotContents(
				1 + var2.nextInt(chestent.getSizeInventory() - 1),
				new ItemStack(NyxItems.boneSanctified));
		if (var2.nextInt(3) != 0)
			chestent.setInventorySlotContents(
					1 + var2.nextInt(chestent.getSizeInventory() - 1),
					new ItemStack(NyxItems.draconium));
		chestent.setInventorySlotContents(0, new ItemStack(NyxItems.page, 1, 0));
	}

}
