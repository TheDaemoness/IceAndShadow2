package iceandshadow2.ias.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import net.minecraft.item.ItemStack;

public class IaSBaseItemMultiTexturedGlow extends IaSBaseItemMultiTextured implements IIaSGlowing {

	public IaSBaseItemMultiTexturedGlow(EnumIaSModule mod, String id, int subtypes) {
		super(mod, id, subtypes);
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
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
