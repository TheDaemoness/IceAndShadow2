package iceandshadow2.ias.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;

public class IaSItemShears extends ItemShears implements IIaSModName {
	public final EnumIaSModule MODULE;

	public IaSItemShears(EnumIaSModule mod, String texName) {
		super();
		setUnlocalizedName(mod.prefix + texName);
		setTextureName(IceAndShadow2.MODID + ':' + mod.prefix + texName);
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

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID + ':' + this.MODULE.prefix + getModName();
	}

	public final Item register() {
		IaSRegistration.register(this);
		return this;
	}

}
