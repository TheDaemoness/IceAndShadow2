package iceandshadow2.util;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import iceandshadow2.ias.IIaSModName;
import cpw.mods.fml.common.registry.GameRegistry;

public class IaSRegistration {
	public static void register(Object obj) {
		if(obj instanceof Block)
			registerBlock((Block)obj);
		else if(obj instanceof Item)
			registerItem((Item)obj);
	}
	
	private static Block registerBlock(Block block) {
		if(block instanceof IIaSModName)
			return GameRegistry.registerBlock(block, ((IIaSModName)block).getModName());
		else
			return GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
	}
	private static Item registerItem(Item it) {
		if(it instanceof IIaSModName)
			GameRegistry.registerItem(it, ((IIaSModName)it).getModName());
		else
			GameRegistry.registerItem(it, it.getUnlocalizedName().substring(5));
		return it;
	}
}
