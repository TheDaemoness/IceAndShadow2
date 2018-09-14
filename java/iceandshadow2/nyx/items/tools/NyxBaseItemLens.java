package iceandshadow2.nyx.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.api.IIaSApiTransmuteLens;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;

public abstract class NyxBaseItemLens extends IaSBaseItemSingle implements IIaSGlowing, IIaSApiTransmuteLens {

	@SideOnly(Side.CLIENT)
	protected IIcon lensicon, altaricon;

	boolean overrideAltar;

	public NyxBaseItemLens(String id, boolean altar) {
		super(EnumIaSModule.NYX, id);
		overrideAltar = altar;
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (pass == 1)
			return lensicon;
		return itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getTextureName());
		lensicon = reg.registerIcon(getTextureName() + "Glow");
		if (overrideAltar)
			altaricon = reg.registerIcon(getTextureName() + "Altar");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}

}
