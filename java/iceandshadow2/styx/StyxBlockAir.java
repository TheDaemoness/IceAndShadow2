package iceandshadow2.styx;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockAirlike;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.ias.util.IaSWorldHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class StyxBlockAir extends StyxBlockNoReplace {

	public StyxBlockAir(String texName) {
		super(texName);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
		if (ent instanceof EntityPlayer) {
			IaSPlayerHelper.drainXP((EntityPlayer) ent,
					((EntityPlayer)ent).xpBarCap(),
					"Your life is being erased.",
					false);
		}
		else if (!world.isRemote)
			ent.attackEntityFrom(IaSDamageSources.dmgDrain, 10);
		if(ent.isSprinting()) {
			ent.motionX *= 1.02;
			ent.motionZ *= 1.02;
		} else if (ent.isSneaking()) {
			ent.motionX = 0;
			ent.motionZ = 0;
		}
		super.onEntityCollidedWithBlock(world, x, y, z, ent);
	}

}
