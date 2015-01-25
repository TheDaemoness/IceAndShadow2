package iceandshadow2.ias.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;

public class IaSBaseItemSingle extends IaSBaseItem {

	public IaSBaseItemSingle(EnumIaSModule mod, String texName) {
		super(mod);
		this.setUnlocalizedName(mod.prefix + texName);
		this.setTextureName(IceAndShadow2.MODID + ':' + mod.prefix + texName);
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID + ':' + getModName();
	}
}
