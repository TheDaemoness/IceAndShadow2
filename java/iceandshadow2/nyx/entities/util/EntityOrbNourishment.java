package iceandshadow2.nyx.entities.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.util.IaSPlayerHelper;
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
				final float intensity = Math.min(1.0F, 0.5F + this.xpValue / 20.0F);
				this.worldObj.playSoundAtEntity(pl, "mob.zombie.unfect", intensity,
						1.0F - intensity - 0.1F + 0.2F * this.rand.nextFloat());
				pl.onItemPickup(this, 1);
				IaSPlayerHelper.feed(pl, this.xpValue);
				this.setDead();
			}
		}
	}

}
