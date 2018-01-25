package iceandshadow2.nyx.items;

import cpw.mods.fml.common.registry.GameRegistry;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemMultiTextured;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class NyxItemSalt extends IaSBaseItemMultiTextured {

	public NyxItemSalt(String id) {
		super(EnumIaSModule.NYX, id, 3);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 2, 1), new ItemStack(this, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 2), new ItemStack(this, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.quartz, 3), new ItemStack(this, 1, 1), new ItemStack(Items.quartz),
				new ItemStack(this, 1, 1), new ItemStack(Items.quartz));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.redstone, 2), new ItemStack(this, 1, 2), new ItemStack(Items.redstone));
	}
}
