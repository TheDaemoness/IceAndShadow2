package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSBlockClimbable;
import iceandshadow2.api.IIaSBlockThawable;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxBlockPermafrost extends IaSBaseBlockSingle implements IIaSBlockThawable, IIaSBlockClimbable {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop, iconSide;

	public NyxBlockPermafrost(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		this.setHarvestLevel("pickaxe", 0);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0 || side == 1)
			return this.iconTop;
		else
			return this.iconSide;
	}

	@Override
	public Block onThaw(World w, int x, int y, int z) {
		return NyxBlocks.dirt;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.iconTop = reg.registerIcon(getTexName() + "Top");
		this.iconSide = reg.registerIcon(getTexName() + "Side");
	}
}
