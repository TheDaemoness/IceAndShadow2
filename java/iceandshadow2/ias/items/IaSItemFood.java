package iceandshadow2.ias.items;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.IIaSXpAltarSacrifice;
import iceandshadow2.util.EnumIaSModule;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class IaSItemFood extends ItemFood implements IIaSXpAltarSacrifice {

	protected int xpAltarValue, consume;
	
	public IaSItemFood(EnumIaSModule mod, String texName, int hungerVal, float hungerSat, boolean doWolvesEat) {
		super(hungerVal, hungerSat, doWolvesEat);
		setEatTime(32);
		this.setUnlocalizedName(mod.prefix+"Item"+texName);
		this.setTextureName(IceAndShadow2.MODID+':'+mod.prefix+texName);
	}

	public IaSItemFood setEatTime(int eat) {
		consume = eat;
		return this;
	}	

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return consume;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon("IceAndShadow:" + this.getUnlocalizedName().split("\\.")[1]);
	}

	public IaSItemFood setXpAltarMinimumValue(int val) {
		xpAltarValue = val;
		return this;
	}

	@Override
	public int getXpValue(World world, ItemStack is) {
		return xpAltarValue;
	}

	@Override
	public boolean rejectWhenZero() {
		return false;
	}

}
