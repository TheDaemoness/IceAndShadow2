package iceandshadow2.ias.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.interfaces.IIaSGlowing;

public class IaSBaseItemSingleGlow extends IaSBaseItemSingle implements IIaSGlowing {
	
	public IaSBaseItemSingleGlow(EnumIaSModule mod, String texName) {
		super(mod, texName);
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
