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

public class NyxItemCortra extends IaSBaseItemMulti {

	@SideOnly(Side.CLIENT)
	protected IIcon crystalIcon;
	
	public NyxItemCortra(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		GameRegistry.addSmelting(new ItemStack(this,1,0), new ItemStack(this,1,1), 0);
		GameRegistry.addShapelessRecipe(new ItemStack(this,1,0), new ItemStack(this,1,1));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 1)
			return crystalIcon;
		return this.itemIcon;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(this.getTexName() + "Dust");
		this.crystalIcon = reg.registerIcon(this.getTexName() + "Crystal");
	}
}
