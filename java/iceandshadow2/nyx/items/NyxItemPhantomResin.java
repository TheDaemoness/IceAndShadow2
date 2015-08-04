package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import net.minecraft.item.ItemStack;

public class NyxItemPhantomResin extends IaSBaseItemSingle implements IIaSGlowing {

	public NyxItemPhantomResin(String texName) {
		super(EnumIaSModule.NYX, texName);
	}
	
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 1;
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 0;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}
}
