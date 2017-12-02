package iceandshadow2.nyx.blocks;

import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockFluid;
import iceandshadow2.render.fx.IaSFxManager;
import iceandshadow2.util.IaSBlockHelper;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;

public class NyxBlockWater extends IaSBaseBlockFluid {

	public NyxBlockWater(String id, Fluid fluid) {
		super(EnumIaSModule.NYX, id, fluid);
		setLightOpacity(4);
		setResistance(300F);
		this.setTickRandomly(true);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
		if (!(ent instanceof EntityFX)) {
			if (ent.worldObj.isRemote && ent.worldObj.rand.nextBoolean())
				IaSFxManager.spawnParticle(ent.worldObj, "shadowSmokeLarge", ent.posX, ent.posY, ent.posZ, 0, 0, 0,
						false, true);
			ent.attackEntityFrom(DamageSource.wither, 2);
			if (ent instanceof EntityLivingBase)
				((EntityLivingBase) ent).addPotionEffect(new PotionEffect(Potion.wither.id, 65, 1));
		}
		super.onEntityCollidedWithBlock(world, x, y, z, ent);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);
		final Block bl = world.getBlock(x, y - 1, z);
		if (!world.isRemote && !(bl instanceof BlockFluidBase) && bl.getHarvestLevel(world.getBlockMetadata(x, y - 1, z)) < rand.nextInt(2))
			IaSBlockHelper.breakBlock(world, x, y - 1, z, false);
	}

}
