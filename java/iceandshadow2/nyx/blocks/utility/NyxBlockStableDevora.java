package iceandshadow2.nyx.blocks.utility;

import java.util.ArrayList;
import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;
import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.render.fx.IaSFxManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockStableDevora extends IaSBaseBlockSingle {
	public NyxBlockStableDevora(String id) {
		super(EnumIaSModule.NYX, id, Material.clay);
		setHardness(0.25F);
		setResistance(0.5F);
		this.setLuminescence(0.2f);
	}

	@Override
	public void onBlockDestroyedByExplosion(World w, int x, int y, int z, Explosion e) {
		IaSBlockHelper.breakBlock(w, x, y, z, false);
		if(!w.isRemote) {
			IaSBlockHelper.breakBlock(w, x, y, z, false);
			w.createExplosion(e.getExplosivePlacedBy(), x+0.5, y+0.5, z+0.5, 5.5f, true);
		}
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.LAND;
	}
	
	
}
