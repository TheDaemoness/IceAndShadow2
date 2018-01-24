package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IaSBlockDirectional extends IaSBaseBlockSingle {

	@SideOnly(Side.CLIENT)
	IIcon iconSide;

	public IaSBlockDirectional(EnumIaSModule mod, String texName, Material mat) {
		super(mod, texName, mat);
	}

	@Override
	protected ItemStack createStackedBlock(int par1) {
		return new ItemStack(this, 1, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {

		// Connector
		if ((meta & 0xB) == 0xB)
			return blockIcon;
		else if ((meta & 0x4) == 0x4) {
			if (side == 4 || side == 5)
				return blockIcon;
			else if (side == 0 || side == 1)
				return iconSide;
			else
				return iconSide;
		}

		// North-South
		else if ((meta & 0x8) == 0x8) {
			if (side == 2 || side == 3)
				return blockIcon;
			else if (side == 0 || side == 1)
				return iconSide;
			else
				return iconSide;
		} else if (side == 0 || side == 1)
			return blockIcon;
		else
			return iconSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return 31;
	}

	@Override
	public int onBlockPlaced(World par1World, int x, int y, int z, int face, float par6, float par7, float par8,
			int par9) {
		byte align = 0;

		switch (face) {
		case 0:
		case 1:
			align = 0;
			break;
		case 2:
		case 3:
			align = 0x8;
			break;
		case 4:
		case 5:
			align = 0x4;
		}

		return align;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(getTextureName() + "Top");
		iconSide = reg.registerIcon(getTextureName() + "Side");
	}
}
