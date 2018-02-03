package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IaSBlockDeco extends IaSBaseBlockSingle {

	public IaSBlockDeco(EnumIaSModule mod, String id, Material par2Material) {
		super(mod, id, par2Material);
		setLightOpacity(4);
		fullCube = false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderType() {
		return 1;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return false;
	}

}
