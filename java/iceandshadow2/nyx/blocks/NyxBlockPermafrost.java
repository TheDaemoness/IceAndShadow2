package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxBlockPermafrost extends IaSBaseBlockSingle {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop, iconSide;

	public NyxBlockPermafrost(String id) {
		super(EnumIaSModule.NYX, id, Material.packedIce);
		setHardness(5.0F);
		setResistance(10.0F);
		this.setHarvestLevel("pickaxe", 1);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0 || side == 1)
			return iconTop;
		else
			return iconSide;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.iconTop = reg.registerIcon(this.getTexName() + "Top");
		this.iconSide = reg.registerIcon(this.getTexName() + "Side");
	}
}
