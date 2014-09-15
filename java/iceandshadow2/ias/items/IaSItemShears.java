package iceandshadow2.ias.items;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.interfaces.IIaSModName;
import iceandshadow2.util.EnumIaSModule;
import net.minecraft.item.ItemShears;

public class IaSItemShears extends ItemShears implements IIaSModName {
	public final EnumIaSModule MODULE;

	public IaSItemShears(EnumIaSModule mod, String texName) {
		super();
		this.setUnlocalizedName(mod.prefix+"Item"+texName);
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

}
