package iceandshadow2.nyx.blocks;

import java.lang.reflect.Field;
import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockAirlike;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.render.fx.IaSFxManager;
import iceandshadow2.util.IaSBlockHelper;
import iceandshadow2.util.IaSPlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;

//NOTE: This is air used for underground and very high areas in Nyx.
//It's used to suffocate players and other such entities.
public class NyxBlockAir extends IaSBaseBlockAirlike {
	
	public static final short ATMOS_HEIGHT = 192;

	public NyxBlockAir(String id) {
		super(EnumIaSModule.NYX, id, Material.fire);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
		//TODO: Figure out a decent suffocation (or pseudo-suffocation) mechanism.
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		super.onNeighborBlockChange(w, x, y, z, bl);
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (dir == ForgeDirection.DOWN && y <= ATMOS_HEIGHT)
				continue;
			if (w.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).getMaterial() == Material.air)
				w.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, this);
		}
	}
}
