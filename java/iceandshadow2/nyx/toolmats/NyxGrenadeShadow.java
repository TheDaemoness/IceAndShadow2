package iceandshadow2.nyx.toolmats;

import iceandshadow2.api.IaSGrenadeLogic;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.projectile.EntityGrenade;
import iceandshadow2.nyx.entities.projectile.EntityShadowBall;
import iceandshadow2.render.fx.IaSFxManager;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxGrenadeShadow extends IaSGrenadeLogic {

	@Override
	public void onDetonate(EntityGrenade ent) {
		if(!ent.worldObj.isRemote) {
			ent.worldObj.spawnEntityInWorld(new EntityShadowBall(ent.worldObj, ent.posX, ent.posY, ent.posZ, true, true));
		}
	}
	
	@Override
	public String getName() {
		return "nyxShadow";
	}
	
	@Override
	public void onSpawnParticle(World w, double x, double y, double z) {
		IaSFxManager.spawnParticle(w, "shadowSmokeSmall", x, y, z,
				0.25-w.rand.nextDouble()/2,
				0.25-w.rand.nextDouble()/2,
				0.25-w.rand.nextDouble()/2,
				true, false);
	}

	@Override
	public ItemStack getCraftingStack(boolean second) {
		return new ItemStack(NyxItems.cursedPowder);
	}
}
