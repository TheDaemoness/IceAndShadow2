package iceandshadow2.nyx.world.gen.ruins;

import iceandshadow2.ias.items.tools.IaSTools;
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

public class GenRuinsGatestoneCache extends GenRuins {

	@Override
	public void buildPass(World var1, Random var2, int x, int y, int z) {
		var1.setBlock(x, y, z, NyxBlocks.gatestone);
		Sculptor.cube(var1, x - 1, y - 1, z - 2, x + 2, y - 1, z + 2, Blocks.bedrock, 0);
		Sculptor.walls(var1, x - 3, y, z - 3, x + 3, y + 2, z + 3, Blocks.bedrock, 0);
		Sculptor.corners(var1, x - 1, y + 2, z - 1, x + 1, y + 2, z + 1, Blocks.bedrock, 0);
		Sculptor.walls(var1, x - 1, y + 2, z - 1, x + 1, y + 3, z + 1, Blocks.bedrock, 0);
		var1.setBlock(x, y + 3, z, Blocks.glowstone);
		var1.setBlock(x, y + 4, z, Blocks.bedrock);
	}

	@Override
	public boolean canGenerateHere(World var1, Random var2, int x, int y, int z) {
		return true;
	}

	@Override
	public void damagePass(World var1, Random var2, int x, int y, int z) {
	}

	@Override
	public String getLowercaseName() {
		return "gatestone-cache";
	}

	@Override
	public void rewardPass(World var1, Random var2, int x, int y, int z) {
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
		final TileEntityChest chestent = (TileEntityChest) var1.getTileEntity(xloc, y, zloc);

		// Add more random loot.
		final int chestcontentamount = 12 + var2.nextInt(6);
		for (byte i = 0; i < chestcontentamount; ++i) {
			final int rewardid = var2.nextInt(100);
			ItemStack itemz = new ItemStack(NyxItems.boneSanctified);

			// Bloodstone.
			if (rewardid == 0)
				itemz = new ItemStack(NyxItems.bloodstone);

			// Exousium
			else if (rewardid < 5)
				itemz = new ItemStack(NyxItems.exousium, 1, 2);

			// Less exousium
			else if (rewardid < 15)
				itemz = new ItemStack(NyxItems.exousium, 1, 1);

			// Knives or armor!
			else if (rewardid < 25) {
				if (var2.nextInt(3) == 0) {
					itemz = new ItemStack(IaSTools.knife, 8 + var2.nextInt(8));
					itemz.addEnchantment(Enchantment.smite, 1 + var2.nextInt(2));
				} else {
					switch (var2.nextInt(6)) {
					case 0:
					case 1:
						itemz = new ItemStack(IaSTools.armorEchir[1]);
						break;
					case 2:
					case 3:
						itemz = new ItemStack(IaSTools.armorEchir[2]);
						break;
					case 4:
						itemz = new ItemStack(IaSTools.armorEchir[0]);
						itemz.addEnchantment(Enchantment.thorns, 1 + var2.nextInt(2));
						break;
					case 5:
						itemz = new ItemStack(IaSTools.armorEchir[3]);
						itemz.addEnchantment(Enchantment.featherFalling, 1 + var2.nextInt(2));
						break;
					}
					itemz.addEnchantment(Enchantment.protection, 1 + var2.nextInt(2));
				}
			}

			// Draconium
			else if (rewardid < 30)
				itemz = new ItemStack(NyxItems.draconium);

			// Heat.
			else if (rewardid < 60)
				itemz = new ItemStack(NyxItems.heat, 2 + var2.nextInt(4), 2);

			// Ender pearls
			else if (rewardid < 70)
				itemz = new ItemStack(Items.ender_pearl, 3 + var2.nextInt(6));

			chestent.setInventorySlotContents(1 + var2.nextInt(26), itemz);
		}

		chestent.setInventorySlotContents(1 + var2.nextInt(chestent.getSizeInventory() - 1),
				new ItemStack(NyxItems.draconium));
		chestent.setInventorySlotContents(0, new ItemStack(NyxItems.page, 1, 0));
	}

}
