package iceandshadow2.ias.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class IaSBaseItem extends Item implements IIaSModName {
	private final EnumIaSModule MODULE;

	protected IaSBaseItem(EnumIaSModule mod) {
		super();
		this.MODULE = mod;
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.common;
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return this.MODULE;
	}

	public final IaSBaseItem register() {
		IaSRegistration.register(this);
		return this;
	}

}
