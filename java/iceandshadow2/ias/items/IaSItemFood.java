package iceandshadow2.ias.items;

import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSApiSacrificeXp;
import iceandshadow2.api.IIaSAspect;
import iceandshadow2.api.IIaSDescriptive;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.util.IaSRegistration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class IaSItemFood extends ItemFood implements IIaSApiSacrificeXp, IIaSModName, IIaSAspect, IIaSGlowing, IIaSDescriptive {

	protected int xpAltarValue, consume;
	private final EnumIaSModule MODULE;

	public IaSItemFood(EnumIaSModule mod, String texName, int hungerVal, float hungerSat, boolean doWolvesEat) {
		super(hungerVal, hungerSat, doWolvesEat);
		setEatTime(32);
		setUnlocalizedName(mod.prefix + texName);
		setTextureName(IceAndShadow2.MODID + ':' + mod.prefix + texName);
		MODULE = mod;
		setCreativeTab(IaSCreativeTabs.tools);
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
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return consume;
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.common;
	}

	@Override
	public String getTextureName() {
		return IceAndShadow2.MODID + ':' + getModName();
	}

	@Override
	public float getXpValue(ItemStack is, Random rand) {
		return xpAltarValue;
	}

	public final IaSItemFood register() {
		IaSRegistration.register(this);
		return this;
	}

	public IaSItemFood setEatTime(int eat) {
		consume = eat;
		return this;
	}

	public IaSItemFood setXpAltarMinimumValue(int val) {
		xpAltarValue = val;
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

	@Override
	public String getUnlocalizedDescription(EntityPlayer entityPlayer, ItemStack is) {
		return getModName();
	}

	@Override
	public boolean isHintWarning(EntityPlayer entityPlayer, ItemStack itemStack) {
		return false;
	}

	@Override
	public String getUnlocalizedHint(EntityPlayer entityPlayer, ItemStack itemStack) {
		return "";
	}

	@Override
	public String getLocalizedHintArgument(EntityPlayer entityPlayer, ItemStack itemStack) {
		return null;
	}

}
