package iceandshadow2.api;

import iceandshadow2.ias.util.IntFlagged;
import iceandshadow2.nyx.entities.projectile.EntityGrenade;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * The base class for grenade logic.
 */
public abstract class IaSGrenadeLogic {
	public static final int BASE_FUSE = 40; //Translates to two seconds.
	public static final int BASE_FUSE_IMPACT = -11; //Translates to half a second after impact.
	
	public final int fuseLimit;
	public final boolean fuseOnImpact;
	private int id;
	
	/**
	 * Instantiate a grenade object that detonates after 40 ticks (about 2 seconds).
	 * @param cooked How long the grenade has been held in-hand for. If looking to manipulate the base fuse time, please use the other constructor.
	 */
	public IaSGrenadeLogic()  {
		this(BASE_FUSE);
	}
	/**
	 * Instatitates a grenade object with a bit more control over the fuse.
	 * @param fuse How long the fuse lasts for. If passed a negative or zero value, the grenade will detonate that many ticks after impact.
	 * @param cooked How long the grenade has been held in-hand for. Ignored if the fuse starts on impact.
	 */
	public IaSGrenadeLogic(int fuse)  {
		fuseOnImpact = IntFlagged.flag(fuse);
		fuseLimit = IntFlagged.value(fuse);
		id = -1;
	}
	
	/**
	 * DO NOT CALL THIS YOURSELF. Doing so prevents the grenade logic type from being registered with IaS2.
	 */
	public final boolean setId(int id) {
		if(this.id >= 0)
			return false;
		this.id = id;
		return true;
	}
	
	/**
	 * Returns this grenade logic's id with IaS2. A value of -1 indicates that this grenade type is unregistered.
	 */
	public final int getId() {
		return id;
	}
	
	/**
	 * Called when the grenade detonates.
	 */
	public abstract void onDetonate(EntityGrenade ent);
	
	/**
	 * Called every tick on the client side only.
	 */
	public abstract void onSpawnParticle(World w, double x, double y, double z, boolean fuseLit);
}
