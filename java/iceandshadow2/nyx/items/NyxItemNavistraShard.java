package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.items.IaSBaseItemSingle;

public class NyxItemNavistraShard extends IaSBaseItemSingle {

	public NyxItemNavistraShard(String texName) {
		super(EnumIaSModule.NYX, texName);
	}
	
	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NAVISTRA;
	}
}
