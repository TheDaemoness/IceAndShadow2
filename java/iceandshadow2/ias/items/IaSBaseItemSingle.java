package iceandshadow2.ias.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class IaSBaseItemSingle extends IaSBaseItem {

	public IaSBaseItemSingle(EnumIaSModule mod, String texName) {
		super(mod);
		setUnlocalizedName(mod.prefix + texName);
		setTextureName(IceAndShadow2.MODID + ':' + mod.prefix + texName);
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public String getTextureName() {
		return IceAndShadow2.MODID + ':' + getModName();
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
		return getModName();
	}
}
