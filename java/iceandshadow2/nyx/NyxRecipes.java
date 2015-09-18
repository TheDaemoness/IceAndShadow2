package iceandshadow2.nyx;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class NyxRecipes {

	public static void init() {
		GameRegistry.addShapelessRecipe(new ItemStack(NyxItems.teleportCrystal,
				1, 2), new ItemStack(NyxItems.teleportCrystal, 1, 6),
				new ItemStack(NyxItems.exousium, 1, 2));

		GameRegistry.addSmelting(NyxBlocks.oreEchir, new ItemStack(
				NyxItems.echirIngot, 1, 1), 1);

		GameRegistry.addRecipe(new ItemStack(NyxItems.bread), "vvv", 'v',
				new ItemStack(NyxItems.vineBundle));
		GameRegistry.addRecipe(new ItemStack(Items.paper, 3), "v", "v", "v",
				'v', new ItemStack(NyxItems.vineBundle));
		GameRegistry.addRecipe(new ItemStack(NyxItems.cookie, 8), "vbv", 'v',
				new ItemStack(NyxItems.vineBundle), 'b', new ItemStack(
						NyxItems.silkBerries));

		GameRegistry.addRecipe(new ItemStack(NyxItems.echirShears), "e ", " e",
				'e', new ItemStack(NyxItems.echirIngot, 1, 1));

		GameRegistry.addRecipe(new ItemStack(NyxBlocks.brickFrozen, 4), "bb",
				"bb", 'b', new ItemStack(NyxBlocks.stone));

		GameRegistry.addShapelessRecipe(new ItemStack(
				NyxItems.poisonFruitFertile), new ItemStack(
						NyxItems.poisonFruit), new ItemStack(Items.dye, 15),
						new ItemStack(Items.dye, 15), new ItemStack(Items.dye, 15));

		GameRegistry.addShapelessRecipe(new ItemStack(NyxBlocks.planks, 4, 0),
				new ItemStack(NyxBlocks.poisonLog));
		GameRegistry.addShapelessRecipe(new ItemStack(NyxBlocks.planks, 4, 1),
				new ItemStack(NyxBlocks.infestLog));

		GameRegistry.addSmelting(new ItemStack(NyxBlocks.poisonLog),
				new ItemStack(Blocks.log), 0); // Data values?
		GameRegistry.addSmelting(new ItemStack(NyxBlocks.infestLog),
				new ItemStack(Blocks.log), 0); // Data values?
		GameRegistry.addSmelting(new ItemStack(NyxBlocks.planks, 1, 0),
				new ItemStack(Blocks.planks, 1, 1), 0);
		GameRegistry.addSmelting(new ItemStack(NyxBlocks.planks, 1, 1),
				new ItemStack(Blocks.planks, 1, 2), 0);

		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 1, 2),
				new ItemStack(NyxBlocks.planks, 1, 1), new ItemStack(
						NyxItems.exousium, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.log, 1),
				new ItemStack(NyxBlocks.infestLog), new ItemStack(
						NyxItems.exousium, 1, 0), new ItemStack(
								NyxItems.exousium, 1, 0), new ItemStack(
										NyxItems.exousium, 1, 0));

		GameRegistry.addShapelessRecipe(new ItemStack(Items.string, 4),
				new ItemStack(NyxItems.toughGossamer));
		GameRegistry.addShapelessRecipe(new ItemStack(NyxItems.rope),
				new ItemStack(NyxItems.toughGossamer),
				new ItemStack(NyxItems.toughGossamer),
				new ItemStack(NyxItems.toughGossamer));

		GameRegistry.addRecipe(new ItemStack(NyxBlocks.hookClimbing, 2), "ee",
				"ee", "e ", 'e', new ItemStack(NyxItems.echirIngot, 1, 1));
		GameRegistry.addRecipe(new ItemStack(NyxItems.kitTightrope), " e ",
				"hrh", 'h', new ItemStack(NyxBlocks.hookClimbing), 'e',
				new ItemStack(NyxItems.echirIngot, 1, 1), 'r', new ItemStack(
						NyxItems.rope));

		GameRegistry.addRecipe(new ItemStack(NyxBlocks.transmutationAltar),
				" d ", "coc", 'd', new ItemStack(NyxItems.draconium), 'c',
				new ItemStack(NyxItems.cortra), 'o', new ItemStack(
						Blocks.obsidian));

		GameRegistry.addShapelessRecipe(new ItemStack(NyxItems.heat, 4, 2),
				new ItemStack(NyxItems.heat, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(NyxItems.heat, 4, 1),
				new ItemStack(NyxItems.heat, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(NyxItems.heat, 4, 0),
				new ItemStack(NyxItems.heat, 1, 1));
		
		GameRegistry.addRecipe(new ItemStack(NyxItems.crystalVial,1,0), "c", "c",
				'c', new ItemStack(NyxItems.cortra,1,1));

		GameRegistry.addShapelessRecipe(new ItemStack(NyxItems.crystalVial,1,1),
				new ItemStack(NyxItems.crystalVial,1), new ItemStack(NyxItems.draconium),
				new ItemStack(NyxItems.echirIngot,1,1));
		
		GameRegistry.addShapelessRecipe(new ItemStack(NyxItems.crystalVial,1,1),
				new ItemStack(NyxItems.extractorPoison,1,13));
		
		GameRegistry.addRecipe(new ItemStack(NyxItems.magicRepo,1,0), " c ", "cac", " c ",
				'a', new ItemStack(NyxItems.alabaster),
				'c', new ItemStack(NyxItems.cortraIngot,1,1));
	}
}
