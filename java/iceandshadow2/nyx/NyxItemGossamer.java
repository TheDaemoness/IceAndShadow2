package iceandshadow2.nyx;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSRegistry;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.items.IaSBaseItemSingle;

public class NyxItemGossamer extends IaSBaseItemSingle {

	public NyxItemGossamer(String texName) {
		super(EnumIaSModule.NYX, texName);
		IaSRegistry.blacklistUncraft(this);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.INFESTATION;
	}
}
