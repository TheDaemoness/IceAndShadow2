package iceandshadow2.ias;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class IaSCreativeTab extends CreativeTabs {

	private final Item icon;

	public IaSCreativeTab(String lable, Item it) {
		super(lable);
		this.icon = it;
	}

	@Override
	public Item getTabIconItem() {
		return this.icon;
	}

}
