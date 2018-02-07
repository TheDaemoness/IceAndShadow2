package iceandshadow2.ias.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSRegistry;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IaSBaseItemMulti extends IaSBaseItemSingle {

	private final int subtypeCount;

	public IaSBaseItemMulti(EnumIaSModule mod, String id, int subtypes) {
		super(mod, id);
		setHasSubtypes(true);
		subtypeCount = subtypes;
		IaSRegistry.blacklistUncraft(this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int meta = 0; meta < getSubtypeCount(); ++meta) {
			par3List.add(new ItemStack(par1, 1, meta));
		}
	}

	public int getSubtypeCount() {
		return subtypeCount;
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return this.getUnlocalizedName() + par1ItemStack.getItemDamage();
	}
}
