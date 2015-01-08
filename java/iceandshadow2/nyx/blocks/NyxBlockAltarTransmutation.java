package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBlockAltar;
import iceandshadow2.ias.interfaces.IIaSHasTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxBlockAltarTransmutation extends IaSBlockAltar implements IIaSHasTileEntity {

	public NyxBlockAltarTransmutation(EnumIaSModule mod, String id) {
		super(mod, id);
		this.setLightLevel(0.4F);
		this.setResistance(10.0F);
		this.setHardness(5.0F);
		this.setStepSound(Block.soundTypeStone);
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int x, int y,
			int z, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		return false;
	}

	public boolean burnItem(World wd, int x, int y, int z, ItemStack is) {
		return true;
	}
}
