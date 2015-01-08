package iceandshadow2.nyx.blocks.utility;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockTileEntity;
import iceandshadow2.nyx.tileentities.NyxTeExaminationTable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class NyxBlockExaminationTable extends IaSBaseBlockTileEntity {

	public NyxBlockExaminationTable(String texName) {
		super(EnumIaSModule.NYX, texName, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new NyxTeExaminationTable();
	}

	@Override
	public boolean onBlockActivated(World w, 
			int x, int y, int z, EntityPlayer pl,
			int meta, float a, float b, float c) {
		super.onBlockActivated(w, x, y, z, pl, meta, a, b, c);
		ItemStack it = pl.getEquipmentInSlot(0);
		if(it == null)
			return false;
		return false;
	}
	
	

}
