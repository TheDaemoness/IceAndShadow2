package iceandshadow2.util;

import iceandshadow2.IIaSModName;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.ias.items.IaSItemBlockMulti;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class IaSRegistration {
	public static void register(Object obj) {
		if (obj instanceof Block)
			IaSRegistration.registerBlock((Block) obj);
		else if (obj instanceof Item)
			IaSRegistration.registerItem((Item) obj);
		else if (obj instanceof Fluid)
			IaSRegistration.registerFluid((Fluid) obj);
	}

	private static Block registerBlock(Block block) {
		if (block instanceof IaSBaseBlockMulti)
			return GameRegistry.registerBlock(block, IaSItemBlockMulti.class,
					((IIaSModName) block).getModName());
		else if (block instanceof IIaSModName)
			return GameRegistry.registerBlock(block,
					((IIaSModName) block).getModName());
		else
			return GameRegistry.registerBlock(block, block.getUnlocalizedName()
					.substring(5));
	}

	private static void registerFluid(Fluid obj) {
		FluidRegistry.registerFluid(obj);
	}

	private static Item registerItem(Item it) {
		if (it instanceof IIaSModName)
			GameRegistry.registerItem(it, ((IIaSModName) it).getModName());
		else
			GameRegistry.registerItem(it, it.getUnlocalizedName().substring(5));
		return it;
	}
}
