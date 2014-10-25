package iceandshadow2.nyx.items;

import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.util.EnumIaSModule;

public class NyxItemFoodBread extends IaSItemFood {

	public NyxItemFoodBread(String texName) {
		super(EnumIaSModule.NYX, texName, 7, 16.8F, false);
		this.setEatTime(48);
	}

}
