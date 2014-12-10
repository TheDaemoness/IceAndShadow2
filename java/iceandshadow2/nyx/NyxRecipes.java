package iceandshadow2.nyx;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.items.*;

public class NyxRecipes {
	
	public static void init() {
		GameRegistry.addShapelessRecipe(new ItemStack(NyxItems.teleportCrystal, 1, 2), 
				new ItemStack(NyxItems.teleportCrystal, 1, 6), new ItemStack(NyxItems.exousium,1,2));
		
		GameRegistry.addSmelting(NyxBlocks.oreEchir, new ItemStack(NyxItems.echirIngot,1,1), 1);
		
		GameRegistry.addRecipe(new ItemStack(NyxItems.bread), "vvv", 'v', new ItemStack(NyxItems.vineBundle));
		GameRegistry.addRecipe(new ItemStack(Items.paper,3), "v", "v", "v", 'v', new ItemStack(NyxItems.vineBundle));
		GameRegistry.addRecipe(new ItemStack(NyxItems.cookie,8), "vbv", 
				'v', new ItemStack(NyxItems.vineBundle),
				'b', new ItemStack(NyxItems.silkBerries));
		
		GameRegistry.addRecipe(new ItemStack(NyxItems.echirShears), "e ", " e", 'e', new ItemStack(NyxItems.echirIngot,1,1));
        
		GameRegistry.addRecipe(new ItemStack(NyxBlocks.brickFrozen,4), "bb", "bb", 'b', new ItemStack(NyxBlocks.stone));
		
		GameRegistry.addShapelessRecipe(new ItemStack(NyxItems.poisonFruitFertile), 
				new ItemStack(NyxItems.poisonFruit), 
				new ItemStack(Items.dye,15), new ItemStack(Items.dye,15), new ItemStack(Items.dye,15));
		
		GameRegistry.addShapelessRecipe(new ItemStack(NyxBlocks.planks, 4, 0), new ItemStack(NyxBlocks.poisonLog));
		GameRegistry.addShapelessRecipe(new ItemStack(NyxBlocks.planks, 4, 1), new ItemStack(NyxBlocks.infestLog));

		GameRegistry.addSmelting(new ItemStack(NyxBlocks.poisonLog), new ItemStack(Blocks.log), 0); //Data values?
		GameRegistry.addSmelting(new ItemStack(NyxBlocks.infestLog), new ItemStack(Blocks.log), 0); //Data values?
		GameRegistry.addSmelting(new ItemStack(NyxBlocks.planks, 1, 0), new ItemStack(Blocks.planks, 1, 1), 0);
		GameRegistry.addSmelting(new ItemStack(NyxBlocks.planks, 1, 1), new ItemStack(Blocks.planks, 1, 2), 0);
		
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 1, 2), 
				new ItemStack(NyxBlocks.planks, 1, 1), new ItemStack(NyxItems.exousium,1,0));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.log, 1), 
				new ItemStack(NyxBlocks.infestLog), new ItemStack(NyxItems.exousium, 1, 0),
				new ItemStack(NyxItems.exousium, 1, 0), new ItemStack(NyxItems.exousium, 1, 0));
		
		GameRegistry.addShapelessRecipe(new ItemStack(NyxBlocks.brickPale, 2), new ItemStack(NyxBlocks.brickFrozen),
				new ItemStack(NyxItems.exousicIceShard), new ItemStack(NyxItems.exousicIceShard), new ItemStack(NyxItems.exousicIceShard));
		
		GameRegistry.addShapelessRecipe(new ItemStack(NyxBlocks.brickPale), new ItemStack(NyxBlocks.brickPaleCracked),
				new ItemStack(NyxItems.exousicIceShard));
	}
}
