package iceandshadow2.ias;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.util.IaSRegistration;
import net.minecraft.item.Item;

public class IaSFakeItem extends Item {
	public IaSFakeItem(EnumIaSModule mod, String texName) {
		setUnlocalizedName("fake" + mod.prefix + texName);
		setTextureName(IceAndShadow2.MODID + ':' + mod.prefix + texName);
		IaSRegistration.register(this);
	}
}
