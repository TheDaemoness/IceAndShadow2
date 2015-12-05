package iceandshadow2.ias.items;

import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.IIaSApiSacrificeXp;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class IaSItemFood extends ItemFood implements IIaSApiSacrificeXp,
IIaSModName {

	protected int xpAltarValue, consume;
	private final EnumIaSModule MODULE;

	public IaSItemFood(EnumIaSModule mod, String texName, int hungerVal,
			float hungerSat, boolean doWolvesEat) {
		super(hungerVal, hungerSat, doWolvesEat);
		setEatTime(32);
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
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return this.consume;
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID + ':' + getModName();
	}

	@Override
	public float getXpValue(ItemStack is, Random rand) {
		return this.xpAltarValue;
	}

	public final IaSItemFood register() {
		IaSRegistration.register(this);
		return this;
	}

	public IaSItemFood setEatTime(int eat) {
		this.consume = eat;
		return this;
	}

	public IaSItemFood setXpAltarMinimumValue(int val) {
		this.xpAltarValue = val;
		return this;
	}

}
