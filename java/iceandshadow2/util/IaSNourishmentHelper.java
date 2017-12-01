package iceandshadow2.util;

import net.minecraft.entity.player.EntityPlayer;

public class IaSNourishmentHelper {
	public static void feed(EntityPlayer pl, int amount) {
		regen(pl, amount);
	}

	public static void regen(EntityPlayer pl, int amount) {
		pl.heal(amount / 2.0F);
		pl.getFoodStats().addStats(amount, amount);
	}
}
