package iceandshadow2.nyx;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.items.*;
import iceandshadow2.util.EnumIaSModule;

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
	}
}
