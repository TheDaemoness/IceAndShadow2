package iceandshadow2.util;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import iceandshadow2.ias.bases.*;
import cpw.mods.fml.common.registry.GameRegistry;

public class IaSRegistration {
	public static void register(Object obj) {
		if(obj instanceof Block)
			registerBlock((Block)obj);
		else if(obj instanceof Item)
			registerItem((Item)obj);
	}
	
	private static Block registerBlock(Block block) {
		if(block instanceof IaSBlockSingle)
			return GameRegistry.registerBlock((Block)block, ((IaSBlockSingle)block).getModName());
		else
			return GameRegistry.registerBlock((Block)block, block.getUnlocalizedName().substring(5));
	}
	private static Item registerItem(Item it) {
		if(it instanceof IaSItemSingle)
			GameRegistry.registerItem((Item)it, ((IaSItemSingle)it).getModName());
		else
			GameRegistry.registerItem((Item)it, it.getUnlocalizedName().substring(5));
		return it;
	}
	
	public static void setCreativeTab(IaSBaseItem it, CreativeTabs ct) {
		it.setCreativeTab(ct);
	}
	public static void setCreativeTab(IaSBaseBlock bl, CreativeTabs ct) {
		bl.setCreativeTab(ct);
	}
}
