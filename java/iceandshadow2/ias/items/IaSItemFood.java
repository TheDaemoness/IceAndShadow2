package iceandshadow2.ias.items;

import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.IIaSXpAltarSacrifice;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class IaSItemFood extends ItemFood implements IIaSXpAltarSacrifice, IIaSModName {

	protected int xpAltarValue, consume;
	private final EnumIaSModule MODULE;
	
	public IaSItemFood(EnumIaSModule mod, String texName, int hungerVal, float hungerSat, boolean doWolvesEat) {
		super(hungerVal, hungerSat, doWolvesEat);
		setEatTime(32);
		this.setUnlocalizedName(mod.prefix+texName);
		this.setTextureName(IceAndShadow2.MODID+':'+mod.prefix+texName);
		MODULE = mod;
	}

	public IaSItemFood setEatTime(int eat) {
		consume = eat;
		return this;
	}	

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return consume;
	}

	public IaSItemFood setXpAltarMinimumValue(int val) {
		xpAltarValue = val;
		return this;
	}

	@Override
	public boolean rejectWhenZero() {
		return false;
	}
	
	public final IaSItemFood register() {
		IaSRegistration.register(this);
		return this;
	}
	
	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}
	
	@Override
	public String getTexName() {
		return IceAndShadow2.MODID+':'+getModName();
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return MODULE;
	}

	@Override
	public int getXpValue(ItemStack is, Random rand) {
		return xpAltarValue;
	}

}
