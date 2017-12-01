package iceandshadow2.nyx.entities.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.util.IaSNourishmentHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityOrbNourishment extends EntityXPOrb {

	public EntityOrbNourishment(World w) {
		super(w);
	}

	public EntityOrbNourishment(World w, double x, double y, double z, int amount) {
		super(w, x, y, z, amount);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getTextureByXP() {
		return Math.max(0, Math.min(this.xpValue / 3, 10));
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer pl) {
		if (!this.worldObj.isRemote) {
			if (this.field_70532_c == 0 && pl.xpCooldown == 0) {
				pl.xpCooldown = 5;
				this.worldObj.playSoundAtEntity(pl, "random.orb", 0.1F,
						0.5F * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.8F));
				pl.onItemPickup(this, 1);
				IaSNourishmentHelper.feed(pl, this.xpValue);
				this.setDead();
			}
		}
	}

}
