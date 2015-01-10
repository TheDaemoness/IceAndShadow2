package iceandshadow2.nyx.blocks.utility;

import java.util.Map;
import java.util.TreeMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.blocks.IaSBaseBlockTileEntity;
import iceandshadow2.nyx.tileentities.NyxTeExaminationTable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class NyxBlockExaminationTable extends IaSBaseBlockTileEntity {

	@SideOnly(Side.CLIENT)
	protected IIcon iconTop, iconSide, iconBottom;

	public NyxBlockExaminationTable(String texName) {
		super(EnumIaSModule.NYX, texName, Material.rock);
		this.setResistance(10.0F);
		this.setHardness(5.0F);
		this.setBlockBounds(0.05F, 0.0F, 0.05F, 0.95F, 0.75F, 0.95F);
		this.setLightOpacity(7);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new NyxTeExaminationTable();
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, 
			EntityPlayer pl, int meta, float a, float b, float c) {
		TileEntity ent = w.getTileEntity(x, y, z);
		if(ent instanceof NyxTeExaminationTable) {
			NyxTeExaminationTable nteet = (NyxTeExaminationTable)ent;
			Map<String, Integer> chknow = new TreeMap<String, Integer>();
			Map<String, Integer> temp = IaSRegistry.handleExamination(pl, nteet.knowledge);
			if(temp != null) {
				for(String key : temp.keySet())
					chknow.put(key, temp.get(key));
			}
			temp = IaSRegistry.handleExaminationBook(pl, x, y, z, nteet.knowledge);
			if(temp != null) {
				for(String key : temp.keySet())
					chknow.put(key, temp.get(key));
			}
			for(String key : chknow.keySet())
				nteet.knowledge.put(key, chknow.get(key));
			return true;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = 
				reg.registerIcon("IceAndShadow2:"+this.getModName()+"Top");
		this.iconSide = 
				reg.registerIcon("IceAndShadow2:"+this.getModName()+"Side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return (par1 == 0 || par1 == 1 ? this.blockIcon : this.iconSide);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
