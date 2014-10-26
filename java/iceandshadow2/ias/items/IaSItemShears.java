package iceandshadow2.ias.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;

public class IaSItemShears extends ItemShears implements IIaSModName {
	public final EnumIaSModule MODULE;

	public IaSItemShears(EnumIaSModule mod, String texName) {
		super();
		this.setUnlocalizedName(mod.prefix+texName);
		this.setTextureName(IceAndShadow2.MODID+':'+mod.prefix+texName);
		MODULE = mod;
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID+':'+MODULE.prefix+getModName();
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return MODULE;
	}
	
	public final Item register() {
		IaSRegistration.register(this);
		return this;
	}

}
