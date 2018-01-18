package iceandshadow2.nyx.world.gen.ruins;

import iceandshadow2.IaSFlags;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.ias.util.gen.Sculptor;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.styx.Styx;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class GenRuinsCentral extends GenRuins {
	
	public static final int PLATFORM_OFFSET = 4;

	public static int getGenHeight(World w, int x, int z) {
		int y = 156;
		for (int xit = -1; xit <= 1; ++xit)
			for (int zit = -4; zit <= 4; ++zit)
				y = Math.min(w.getTopSolidOrLiquidBlock(x + xit, z + zit), y);
		for (int xit = -4; xit <= 4; ++xit)
			for (int zit = -1; zit <= 1; ++zit)
				y = Math.min(w.getTopSolidOrLiquidBlock(x + xit, z + zit), y);
		return y;
	}

	@Override
	public void buildPass(World w, Random r, int x, int y, int z) {
		for (int i = 2; i <= 6; ++i) {
			final ForgeDirection dir = ForgeDirection.getOrientation(i);
			int distance = 0;
			for (int j = 15; j <= 96
					&& !IaSBlockHelper.isTransient(w, x + j * dir.offsetX, y + 1, z + j * dir.offsetZ); ++j)
				++distance;
			if (distance >= 2 && distance < 96)
				for (int j = 0; j < distance; ++j) {
					w.setBlock(x + j * dir.offsetX, y, z + j * dir.offsetZ, NyxBlocks.brickExousic);
					w.setBlockToAir(x + j * dir.offsetX, y + 1, z + j * dir.offsetZ);
					w.setBlockToAir(x + j * dir.offsetX, y + 2, z + j * dir.offsetZ);
				}
		}

		Sculptor.cylinder(w, x, y, z, 16, 1, Blocks.snow, 0);
		Sculptor.cylinder(w, x, y - 1, z, 16, 1, NyxBlocks.permafrost, 0);
		Sculptor.cylinder(w, x, y - 2, z, 15, 1, NyxBlocks.permafrost, 0);
		for (int i = 3; i <= 30; ++i)
			Sculptor.cylinder(w, x, Math.max(0, y - i), z, 16 - i / 2, 1, NyxBlocks.stone, 0);
		Sculptor.dome(w, x, y + 1, z, 16, Blocks.air, 0);
		Sculptor.cylinder(w, x, y, z, 12, 1, NyxBlocks.brickExousic, 0);

		// Walkways
		Sculptor.cube(w, x - 7, y + 4, z - 1, x + 7, y + 4, z + 1, Blocks.obsidian, 0);
		Sculptor.cube(w, x - 1, y + 4, z - 7, x + 1, y + 4, z + 7, Blocks.obsidian, 0);

		// Condom
		Sculptor.cube(w, x - 2, y, z - 2, x + 2, y + 1, z + 2, Blocks.obsidian, 0);
		Sculptor.cube(w, x - 1, y + 1, z - 1, x + 1, y + 1, z + 1, Blocks.air, 0);

		// Condom Lubricant
		w.setBlock(x, y + 1, z, Blocks.crafting_table);

		// Platform
		Sculptor.cube(w, x - 2, y + 4, z - 2, x + 2, y + 4, z + 2, Blocks.obsidian, 0);
		Sculptor.cube(w, x - 1, y + PLATFORM_OFFSET, z - 1, x + 1, y + PLATFORM_OFFSET, z + 1, NyxBlocks.cryingObsidian, 1);

		// Walls
		Sculptor.walls(w, x - 7, y, z - 7, x + 7, y + 9, z + 7, Blocks.obsidian, 0);
		Sculptor.walls(w, x - 6, y + 10, z - 6, x + 6, y + 10, z + 6, Blocks.obsidian, 0);

		// Outer Steps
		Sculptor.corners(w, x - 8, y + 2, z - 1, x + 8, y + 3, z + 1, Blocks.obsidian, 0);
		Sculptor.corners(w, x - 1, y + 2, z - 8, x + 1, y + 3, z + 8, Blocks.obsidian, 0);
		Sculptor.corners(w, x - 9, y + 2, z - 1, x + 9, y + 2, z + 1, Blocks.obsidian, 0);
		Sculptor.corners(w, x - 1, y + 2, z - 9, x + 1, y + 2, z + 9, Blocks.obsidian, 0);

		// Inner Steps
		Sculptor.corners(w, x - 6, y + 2, z - 2, x + 6, y + 3, z + 2, Blocks.obsidian, 0);
		Sculptor.corners(w, x - 2, y + 2, z - 6, x + 2, y + 3, z + 6, Blocks.obsidian, 0);
		Sculptor.corners(w, x - 6, y + 2, z - 3, x + 6, y + 2, z + 3, Blocks.obsidian, 0);
		Sculptor.corners(w, x - 3, y + 2, z - 6, x + 3, y + 2, z + 6, Blocks.obsidian, 0);

		Sculptor.cube(w, x - 7, y + 5, z, x + 7, y + 6, z, Blocks.air, 0);
		Sculptor.cube(w, x, y + 5, z - 7, x, y + 6, z + 7, Blocks.air, 0);
		for (int i = 0; i < 3; ++i) {
			final int maxradi = 2 + (i == 0 ? 1 : 0);
			Sculptor.blast(w, x - 6 + r.nextInt(3), y + 5 + r.nextInt(4), z - 7 + i * 5 + r.nextInt(3),
					2 + r.nextInt(maxradi));
			Sculptor.blast(w, x + 8 - r.nextInt(3), y + 5 + r.nextInt(4), z - 7 + i * 5 + r.nextInt(3),
					2 + r.nextInt(maxradi));
			Sculptor.blast(w, x - 7 + i * 5 + r.nextInt(3), y + 5 + r.nextInt(4), z - 6 + r.nextInt(3),
					2 + r.nextInt(maxradi));
			Sculptor.blast(w, x - 7 + i * 5 + r.nextInt(3), y + 5 + r.nextInt(4), z + 8 - r.nextInt(3),
					2 + r.nextInt(maxradi));
		}

		// Protection
		Sculptor.cube(w, x - 1, y + 5, z - 1, x + 1, y + 7, z + 1, Styx.reserved, 0);

		// Podiums
		for (int xit = -4; xit <= 4; xit += 8)
			for (int zit = -4; zit <= 4; zit += 8)
				Sculptor.cube(w, x + xit, y, z + zit, x + xit, y + 4, z + zit, Blocks.obsidian, 0);
		// Broken transmutation altars.
		for (int xit = -5; xit <= 5; xit += 5)
			for (int zit = -5; zit <= 5; zit += 5)
				w.setBlock(x + xit, y + 2, z + zit, NyxBlocks.transmutationAltarBroken);
	}

	@Override
	public boolean canGenerateHere(World var1, Random r, int x, int y, int z) {
		return true;
	}

	@Override
	public void damagePass(World w, Random r, int x, int y, int z) {
	}

	@Override
	public boolean generate(World var1, Random var2, int x, int y, int z) {
		if (canGenerateHere(var1, var2, x, y, z)) {
			y = GenRuinsCentral.getGenHeight(var1, x, z);
			if (IaSFlags.flag_report_ruins_gen)
				IceAndShadow2.getLogger()
						.info("[DEV] Generating " + getLowercaseName() + " @ (" + x + "," + y + "," + z + ").");
			buildPass(var1, var2, x, y, z);
			damagePass(var1, var2, x, y, z);
			rewardPass(var1, var2, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public String getLowercaseName() {
		return "central-ruins";
	}

	@Override
	public void rewardPass(World w, Random r, int x, int y, int z) {
		final int ropechest = r.nextInt(4);
		final int hookchest = r.nextInt(4);
		final int cortrachest = r.nextInt(4);
		final int bootchest = r.nextInt(4);
		final int tightropeA = r.nextInt(5); // Deliberate, may not spawn.
		final int tightropeB = r.nextInt(6); // Deliberate, may not spawn.
		final int lorepages = r.nextInt(4);
		y += 5;
		for (int chestpos = 0; chestpos <= 3; ++chestpos) {
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
			final TileEntityChest chestent = (TileEntityChest) w.getTileEntity(xloc, y, zloc);
			final int quantity = 2 + r.nextInt(3);
			for (int i = 0; i < quantity; ++i) {
				ItemStack itemz = new ItemStack(Items.ender_pearl, 2 + r.nextInt(4));
				final int rewardid = r.nextInt(100);

				// Primed Ingots!
				if (rewardid < 20)
					itemz = new ItemStack(NyxItems.echirIngot, 8 - r.nextInt(5) / 2, 1);
				else if (rewardid < 35)
					itemz = new ItemStack(NyxItems.devora, 2 + r.nextInt(3));
				else if (rewardid < 55)
					itemz = new ItemStack(NyxItems.boneSanctified);
				else if (rewardid < 70)
					itemz = new ItemStack(Items.experience_bottle, 2 + r.nextInt(4));

				chestent.setInventorySlotContents(1 + r.nextInt(chestent.getSizeInventory() - 1), itemz);
			}
			if (chestpos == tightropeA)
				chestent.setInventorySlotContents(1 + r.nextInt(chestent.getSizeInventory() - 1),
						new ItemStack(NyxItems.kitTightrope));
			if (chestpos == tightropeB)
				chestent.setInventorySlotContents(1 + r.nextInt(chestent.getSizeInventory() - 1),
						new ItemStack(NyxItems.kitTightrope));
			if (chestpos == cortrachest)
				chestent.setInventorySlotContents(1 + r.nextInt(chestent.getSizeInventory() - 1),
						new ItemStack(NyxItems.cortra, 1 + r.nextInt(2)));
			if (chestpos == hookchest)
				chestent.setInventorySlotContents(1 + r.nextInt(chestent.getSizeInventory() - 1),
						new ItemStack(NyxBlocks.hookClimbing));
			if (chestpos == ropechest)
				chestent.setInventorySlotContents(1 + r.nextInt(chestent.getSizeInventory() - 1),
						new ItemStack(NyxItems.rope, 2));
			if (chestpos == lorepages)
				chestent.setInventorySlotContents(1 + r.nextInt(chestent.getSizeInventory() - 1),
						new ItemStack(NyxItems.page));
			if (chestpos == bootchest) {
				final ItemStack is = new ItemStack(IaSTools.armorNavistra[3]);
				chestent.setInventorySlotContents(1 + r.nextInt(chestent.getSizeInventory() - 1), is);
			}
		}
	}

}
