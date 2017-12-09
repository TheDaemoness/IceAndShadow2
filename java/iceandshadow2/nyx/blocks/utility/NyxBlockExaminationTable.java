package iceandshadow2.nyx.blocks.utility;

import java.util.Map;
import java.util.TreeMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.blocks.IaSBaseBlockTileEntity;
import iceandshadow2.nyx.blocks.NyxBlockStone;
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
		setResistance(NyxBlockStone.RESISTANCE);
		setHardness(NyxBlockStone.HARDNESS);
		setBlockBounds(0.05F, 0.0F, 0.05F, 0.95F, 0.75F, 0.95F);
		setLightOpacity(7);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new NyxTeExaminationTable();
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.ANCIENT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return par1 == 0 || par1 == 1 ? blockIcon : iconSide;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer pl, int meta, float a, float b,
			float c) {
		final TileEntity ent = w.getTileEntity(x, y, z);
		if (ent instanceof NyxTeExaminationTable) {
			final NyxTeExaminationTable nteet = (NyxTeExaminationTable) ent;
			final Map<String, Integer> chknow = new TreeMap<String, Integer>();
			Map<String, Integer> temp = IaSRegistry.handleExamination(pl, nteet.knowledge);
			if (temp != null)
				for (final String key : temp.keySet())
					chknow.put(key, temp.get(key));
			temp = IaSRegistry.handleExaminationBook(pl, x, y, z, nteet.knowledge);
			if (temp != null)
				for (final String key : temp.keySet())
					chknow.put(key, temp.get(key));
			for (final String key : chknow.keySet())
				nteet.knowledge.put(key, chknow.get(key));
			return true;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon("IceAndShadow2:" + getModName() + "Top");
		iconSide = reg.registerIcon("IceAndShadow2:" + getModName() + "Side");
	}
}
