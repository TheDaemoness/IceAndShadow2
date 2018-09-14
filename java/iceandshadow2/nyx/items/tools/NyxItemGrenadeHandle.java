package iceandshadow2.nyx.items.tools;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemMultiTexturedGlow;

public class NyxItemGrenadeHandle extends IaSBaseItemMultiTexturedGlow {

	public NyxItemGrenadeHandle(String id) {
		super(EnumIaSModule.NYX, id, 2);
		setMaxStackSize(32);
	}
}
