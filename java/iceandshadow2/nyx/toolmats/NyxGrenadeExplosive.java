package iceandshadow2.nyx.toolmats;

import iceandshadow2.api.IaSGrenadeLogic;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.projectile.EntityGrenade;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxGrenadeExplosive extends IaSGrenadeLogic {

	@Override
	public void onDetonate(EntityGrenade ent) {
		if(!ent.worldObj.isRemote) {
			ent.worldObj.createExplosion(ent.getThrower(), ent.posX, ent.posY, ent.posZ, 3.5f, false);
			ent.worldObj.createExplosion(ent.getThrower(), ent.posX, ent.posY, ent.posZ, 2f, true);
		}
	}
	
	@Override
	public String getName() {
		return "nyxExplosive";
	}

	@Override
	public ItemStack getCraftingStack(boolean second) {
		return new ItemStack(NyxItems.devora, 1, 1);
	}
}
