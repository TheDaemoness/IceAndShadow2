package iceandshadow2.nyx;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
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
	}
}
