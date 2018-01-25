package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class IaSBaseBlockMulti extends IaSBaseBlock implements IIaSModName {

	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;

	private final byte subtypeCount;

	public IaSBaseBlockMulti(EnumIaSModule mod, String id, Material mat, int subtypes) {
		super(mod, mat);
		setBlockName(mod.prefix + id);
		subtypeCount = (byte) Math.min(subtypes, 16);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta >= getSubtypeCount())
			return icons[0];
		return icons[meta];
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs p_149666_2_, List list) {
		for (int meta = 0; meta < getSubtypeCount(); ++meta) {
			list.add(new ItemStack(par1, 1, meta));
		}
	}

	public int getSubtypeCount() {
		return subtypeCount;
	}

	@Override
	public String getTextureName() {
		return IceAndShadow2.MODID + ':' + getModName();
	}

	public String getUnlocalizedName(int val) {
		return super.getUnlocalizedName() + val;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[getSubtypeCount()];
		for (byte i = 0; i < getSubtypeCount(); ++i) {
			icons[i] = reg.registerIcon(getTextureName() + i);
		}
	}

}
