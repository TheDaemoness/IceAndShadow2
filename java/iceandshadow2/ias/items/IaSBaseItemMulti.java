package iceandshadow2.ias.items;

import iceandshadow2.util.EnumIaSModule;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class IaSBaseItemMulti extends IaSBaseItem {

	public IaSBaseItemMulti(EnumIaSModule mod) {
		super(mod);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return this.getUnlocalizedName()+par1ItemStack.getItemDamage();
	}

	public abstract int getSubtypeCount();
	
	@Override
	public boolean getHasSubtypes() {
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int meta = 0; meta < getSubtypeCount(); ++meta) {
            par3List.add(new ItemStack(par1, 1, meta));
        }
    }
}
