package iceandshadow2.compat;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import iceandshadow2.ias.bases.*;
import iceandshadow2.util.EnumIaSModule;
import cpw.mods.fml.common.registry.GameRegistry;

public class IaSRegistration {
	public static Block registerBlock(IaSBaseBlock bl) {
		return GameRegistry.registerBlock((Block)bl, bl.getUnlocalizedName().substring(5));
	}
	public static Item registerItem(IaSBaseItem it) {
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
