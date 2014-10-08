package iceandshadow2.nyx.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.util.EnumIaSModule;

public class NyxItemDevora extends IaSBaseItemMulti implements IIaSGlowing {

	@SideOnly(Side.CLIENT)
	protected IIcon smallIcon;
	
	public NyxItemDevora(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		GameRegistry.addShapelessRecipe(new ItemStack(this,8,1), new ItemStack(this,1,0));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if(dmg == 1)
			return smallIcon;
		return this.itemIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(this.getTexName());
		this.smallIcon = reg.registerIcon(this.getTexName()+"Small");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}

}
