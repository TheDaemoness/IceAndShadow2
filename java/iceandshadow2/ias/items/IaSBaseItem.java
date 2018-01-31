package iceandshadow2.ias.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSAspect;
import iceandshadow2.api.IIaSDescriptive;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.util.IaSRegistration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class IaSBaseItem extends Item implements IIaSModName, IIaSAspect, IIaSGlowing, IIaSDescriptive {

	@Override
	public String getLocalizedHintArgument(EntityPlayer entityPlayer, ItemStack itemStack) {
		return null;
	}

	private final EnumIaSModule MODULE;

	protected IaSBaseItem(EnumIaSModule mod) {
		super();
		MODULE = mod;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return MODULE.aspect;
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return MODULE;
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumIaSAspect.getRarity(getAspect());
	}

	public final IaSBaseItem register() {
		IaSRegistration.register(this);
		return this;
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
