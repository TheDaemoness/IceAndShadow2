package iceandshadow2.ias.items;

import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class IaSItemBlockMulti extends ItemBlock {

	IaSBaseBlockMulti theblock;

	public IaSItemBlockMulti(Block bl) throws Exception {
		super(bl);
		if (bl instanceof IaSBaseBlockMulti)
			theblock = (IaSBaseBlockMulti) bl;
		else
			throw new Exception("Block with iconString " + this.iconString
					+ " is not an IaS Multiblock!");
		setHasSubtypes(true);
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		return theblock.getIcon(0, par1);
	}

	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}

	@Override
	public String getUnlocalizedName() {
		return theblock.getUnlocalizedName();
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return theblock.getUnlocalizedName(par1ItemStack.getItemDamage());
	}

}
