package iceandshadow2.nyx.world.gen.ruins;

import iceandshadow2.IaSFlags;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.gen.Sculptor;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class GenRuinsCentral extends GenRuins {

	public static int getGenHeight(World w, int x, int z) {
		int y = 156;
		for(int xit = -1; xit <= 1; ++xit) {
			for(int zit = -4; zit <= 4; ++zit)
				y = Math.min(w.getPrecipitationHeight(x+xit, z+zit), y);
		}
		for(int xit = -4; xit <= 4; ++xit) {
			for(int zit = -1; zit <= 1; ++zit)
				y = Math.min(w.getPrecipitationHeight(x+xit, z+zit), y);
		}
		return y;
	}

	@Override
	public boolean generate(World var1, Random var2, int x, int y, int z) {
		if (canGenerateHere(var1, var2, x, y, z)) {
			y = GenRuinsCentral.getGenHeight(var1, x,z);
			if (IaSFlags.flag_report_ruins_gen)
				IceAndShadow2.getLogger().info(
						"[DEV] Generating " + getLowercaseName() + " @ ("
								+ x + "," + y + "," + z + ").");
			buildPass(var1, var2, x, y, z);
			damagePass(var1, var2, x, y, z);
			rewardPass(var1, var2, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public boolean canGenerateHere(World var1, Random r, int x, int y, int z) {
		return true;
	}

	@Override
	public void buildPass(World w, Random r, int x, int y, int z) {
		Sculptor.cylinder(w, x, y, z, 16, 1, Blocks.snow, 0);
		Sculptor.cylinder(w, x, y-1, z, 16, 1, NyxBlocks.permafrost, 0);
		Sculptor.cylinder(w, x, y-2, z, 15, 1, NyxBlocks.permafrost, 0);
		for(int i = 3; i <= 30; ++i)
			Sculptor.cylinder(w, x, Math.max(0, y-i), z, 16-i/2, 1, NyxBlocks.stone, 0);
		Sculptor.dome(w, x, y+1, z, 16, Blocks.air, 0);
		Sculptor.cylinder(w, x, y, z, 12, 1, NyxBlocks.brickPale, 0);

		//Walkways
		Sculptor.cube(w, x-7, y+4, z-1, x+7, y+4, z+1, Blocks.obsidian, 0);
		Sculptor.cube(w, x-1, y+4, z-7, x+1, y+4, z+7, Blocks.obsidian, 0);

		//Condom
		Sculptor.cube(w, x-1, y+1, z-1, x+1, y+1, z+1, Blocks.obsidian, 0);

		//Condom Lubricant
		w.setBlock(x, y+1, z, Blocks.crafting_table);

		//Platform
		Sculptor.cube(w, x-2, y+4, z-2, x+2, y+4, z+2, Blocks.obsidian, 0);
		Sculptor.cube(w, x-1, y+4, z-1, x+1, y+4, z+1, NyxBlocks.cryingObsidian, 1);

		//Walls
		Sculptor.walls(w, x-7, y, z-7, x+7, y+9, z+7, Blocks.obsidian, 0);
		Sculptor.walls(w, x-6, y+10, z-6, x+6, y+10, z+6, Blocks.obsidian, 0);

		//Outer Steps
		Sculptor.corners(w, x-8, y+2, z-1, x+8, y+3, z+1, Blocks.obsidian, 0);
		Sculptor.corners(w, x-1, y+2, z-8, x+1, y+3, z+8, Blocks.obsidian, 0);
		Sculptor.corners(w, x-9, y+2, z-1, x+9, y+2, z+1, Blocks.obsidian, 0);
		Sculptor.corners(w, x-1, y+2, z-9, x+1, y+2, z+9, Blocks.obsidian, 0);

		//Inner Steps
		Sculptor.corners(w, x-6, y+2, z-2, x+6, y+3, z+2, Blocks.obsidian, 0);
		Sculptor.corners(w, x-2, y+2, z-6, x+2, y+3, z+6, Blocks.obsidian, 0);
		Sculptor.corners(w, x-6, y+2, z-3, x+6, y+2, z+3, Blocks.obsidian, 0);
		Sculptor.corners(w, x-3, y+2, z-6, x+3, y+2, z+6, Blocks.obsidian, 0);

		Sculptor.cube(w, x-7, y+5, z, x+7, y+6, z, Blocks.air, 0);
		Sculptor.cube(w, x, y+5, z-7, x, y+6, z+7, Blocks.air, 0);
		for(int i = 0; i < 3; ++i) {
			final int maxradi = 2+(i==0?1:0);
			Sculptor.blast(w,
					x-6+r.nextInt(3),
					y+5+r.nextInt(4),
					z-7+i*5+r.nextInt(3), 2+r.nextInt(maxradi));
			Sculptor.blast(w,
					x+8-r.nextInt(3),
					y+5+r.nextInt(4),
					z-7+i*5+r.nextInt(3), 2+r.nextInt(maxradi));
			Sculptor.blast(w,
					x-7+i*5+r.nextInt(3),
					y+5+r.nextInt(4),
					z-6+r.nextInt(3), 2+r.nextInt(maxradi));
			Sculptor.blast(w,
					x-7+i*5+r.nextInt(3),
					y+5+r.nextInt(4),
					z+8-r.nextInt(3), 2+r.nextInt(maxradi));
		}
		//Podiums
		for(int xit=-4; xit<=4; xit += 8) {
			for(int zit=-4; zit<=4; zit += 8)
				Sculptor.cube(w, x+xit, y, z+zit, x+xit, y+4, z+zit, Blocks.obsidian, 0);
		}
	}

	@Override
	public void damagePass(World w, Random r, int x, int y, int z) {
	}


	@Override
	public void rewardPass(World w, Random r, int x, int y, int z) {
		final int ropechest = r.nextInt(4);
		final int hookchest = r.nextInt(4);
		final int rarechest = r.nextInt(4);
		final int bootchest = r.nextInt(4);
		final int tightropeA = r.nextInt(6); //Deliberate, may not spawn.
		final int tightropeB = r.nextInt(6); //Deliberate, may not spawn.
		final int lorepages = r.nextInt(4);
		y += 5;
		for(int chestpos = 0; chestpos <= 3; ++chestpos) {
			// Create a chest.
			int xloc = x, zloc = z, chestf = 0;
			if (chestpos == 0) {
				zloc -= 4;
				xloc -= 4;
				chestf = 0x3;
			} else if (chestpos == 1) {
				zloc += 4;
				xloc -= 4;
				chestf = 0x4;
			} else if (chestpos == 2) {
				zloc += 4;
				xloc += 4;
				chestf = 0x2;
			} else {
				zloc -= 4;
				xloc += 4;
				chestf = 0x5;
			}
			w.setBlock(xloc, y, zloc, Blocks.chest, chestf, 0x2);

			// Start filling the chest with goodies.
			final TileEntityChest chestent = (TileEntityChest) w.getTileEntity(
					xloc, y, zloc);
			final int quantity = 2 + r.nextInt(3);
			for(int i = 0; i < quantity; ++i) {
				ItemStack itemz = new ItemStack(Items.ender_pearl,2+r.nextInt(4));
				final int rewardid = r.nextInt(100);

				// Sword or armor!
				if (rewardid < 20) {
					if (r.nextInt(4) == 0) {
						itemz = new ItemStack(Items.diamond_sword);
						itemz.addEnchantment(Enchantment.smite, 1 + r.nextInt(2));
					} else {
						switch (r.nextInt(5)) {
						case 0:
						case 1:
							itemz = new ItemStack(Items.diamond_chestplate);
							break;
						case 2:
						case 3:
							itemz = new ItemStack(Items.diamond_leggings);
							break;
						case 4:
							itemz = new ItemStack(Items.diamond_boots);
							break;
						}
						itemz.addEnchantment(Enchantment.unbreaking,
								1);
						if(r.nextBoolean())
							itemz.addEnchantment(Enchantment.protection,
								1);
					}
				}

				// Echir Ingots
				else if (rewardid < 35) {
					itemz = new ItemStack(NyxItems.echirIngot, 3+r.nextInt(5));
				}

				// Sanctified Bone
				else if (rewardid < 55) {
					itemz = new ItemStack(NyxItems.boneSanctified);
				}

				// Berries!
				else if (rewardid < 75) {
					itemz = new ItemStack(NyxItems.silkBerries,
							4 + r.nextInt(8));
				}

				chestent.setInventorySlotContents(
						1 + r.nextInt(chestent.getSizeInventory() - 1),
						itemz);
			}
			if(chestpos == tightropeA) {
				chestent.setInventorySlotContents(
						1 + r.nextInt(chestent.getSizeInventory() - 1),
						new ItemStack(NyxItems.kitTightrope));
			}
			if(chestpos == tightropeB) {
				chestent.setInventorySlotContents(
						1 + r.nextInt(chestent.getSizeInventory() - 1),
						new ItemStack(NyxItems.kitTightrope));
			}
			if(chestpos == rarechest && r.nextInt(20) == 0) {
				chestent.setInventorySlotContents(
					1 + r.nextInt(chestent.getSizeInventory() - 1),
					new ItemStack(NyxItems.bloodstone));
			}
			if(chestpos == hookchest) {
				chestent.setInventorySlotContents(
						1 + r.nextInt(chestent.getSizeInventory() - 1),
						new ItemStack(NyxBlocks.hookClimbing));
			}
			if(chestpos == ropechest) {
				chestent.setInventorySlotContents(
						1 + r.nextInt(chestent.getSizeInventory() - 1),
						new ItemStack(NyxItems.rope));
			}
			if(chestpos == lorepages) {
				chestent.setInventorySlotContents(
						1 + r.nextInt(chestent.getSizeInventory() - 1),
						new ItemStack(NyxItems.page));
			}
			if(chestpos == bootchest) {
				final ItemStack is = new ItemStack(Items.diamond_boots);
				is.addEnchantment(Enchantment.featherFalling, 2+r.nextInt(2));
				is.addEnchantment(Enchantment.unbreaking, 1+r.nextInt(2));
				chestent.setInventorySlotContents(
						1 + r.nextInt(chestent.getSizeInventory() - 1),
						is);
			}
		}
	}

	@Override
	public String getLowercaseName() {
		return "central-ruins";
	}

}
