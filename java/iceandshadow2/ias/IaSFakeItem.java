package iceandshadow2.ias;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.item.Item;

public class IaSFakeItem extends Item {
	public IaSFakeItem(EnumIaSModule mod, String texName) {
		this.setUnlocalizedName("fake"+mod.prefix+texName);
		this.setTextureName(IceAndShadow2.MODID+':'+mod.prefix+texName);
		IaSRegistration.register(this);
	}
}
