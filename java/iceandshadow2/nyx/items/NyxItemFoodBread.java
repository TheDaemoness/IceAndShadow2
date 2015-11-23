package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSItemFood;

public class NyxItemFoodBread extends IaSItemFood {

	public NyxItemFoodBread(String texName) {
		super(EnumIaSModule.NYX, texName, 7, 16.8F, false);
		setEatTime(48);
	}

}
