package iceandshadow2.ias.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSAspect;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.util.IaSRegistration;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;

public class IaSItemShears extends ItemShears implements IIaSModName, IIaSAspect, IIaSGlowing {
	public final EnumIaSModule MODULE;

	public IaSItemShears(EnumIaSModule mod, String texName) {
		super();
		setUnlocalizedName(mod.prefix + texName);
		setTextureName(IceAndShadow2.MODID + ':' + mod.prefix + texName);
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
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.common;
	}

	@Override
	public String getTextureName() {
		return IceAndShadow2.MODID + ':' + MODULE.prefix + getModName();
	}

	public final Item register() {
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
}
