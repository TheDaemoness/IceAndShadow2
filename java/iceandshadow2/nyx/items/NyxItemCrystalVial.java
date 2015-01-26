package iceandshadow2.nyx.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.ias.items.IaSBaseItemSingle;

public class NyxItemCrystalVial extends IaSBaseItemSingle {
	
	public NyxItemCrystalVial(String texName) {
		super(EnumIaSModule.NYX, texName);
	}
}
