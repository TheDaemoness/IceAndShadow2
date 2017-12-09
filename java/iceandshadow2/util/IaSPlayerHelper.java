package iceandshadow2.util;

import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class IaSPlayerHelper {
	private static boolean dochat = true;

	public static void alertPlayer(EntityPlayer plai, String str) {
		if (IaSPlayerHelper.dochat && plai.worldObj.isRemote) {
			final ChatComponentText txt = new ChatComponentText(str);
			txt.setChatStyle(new ChatStyle().setItalic(true).setBold(true).setColor(EnumChatFormatting.RED));
			plai.addChatMessage(new ChatComponentText(""));
			plai.addChatMessage(txt);
			plai.addChatMessage(new ChatComponentText(""));
			IaSPlayerHelper.dochat = false;
		} else
			IaSPlayerHelper.dochat = true;
	}

	public static int drainXP(EntityPlayer pl, final int amount, String warning, boolean bypass) {
		if (pl.xpCooldown > 0 && !bypass)
			return -1;
		if (amount <= 0)
			return -1;
		pl.xpCooldown = pl.maxHurtResistantTime / (pl.inventory.hasItem(NyxItems.bloodstone) ? 2 : 1);
		if (!pl.isPotionActive(Potion.confusion) && warning != null)
			IaSPlayerHelper.alertPlayer(pl, warning);
		pl.addPotionEffect(new PotionEffect(Potion.confusion.id, 45, 0));
		int original = (int) (pl.experience * pl.xpBarCap());
		pl.experience = 0;
		final int originalLevel = pl.experienceLevel;
		for (pl.experienceLevel = 0; pl.experienceLevel < originalLevel; ++pl.experienceLevel)
			original += pl.xpBarCap();
		pl.experienceLevel = 0;
		pl.experienceTotal = Math.max(0, original - amount);
		int xpPool = pl.experienceTotal;
		while (xpPool > pl.xpBarCap()) {
			xpPool -= pl.xpBarCap();
			++pl.experienceLevel;
		}
		pl.experience = (float) xpPool / pl.xpBarCap();
		final int drained = original - pl.experienceTotal;
		if (drained < amount)
			pl.attackEntityFrom(IaSDamageSources.dmgDrain, amount - drained);
		return drained;
	}

	public static void feed(EntityPlayer pl, int amount) {
		regen(pl, amount, true);
	}

	public static boolean giveItem(EntityPlayer plai, ItemStack is) {
		final boolean added = plai.inventory.addItemStackToInventory(is);
		if (!added && !plai.worldObj.isRemote) {
			final EntityItem item = new EntityItem(plai.worldObj, plai.posX, plai.posY + plai.getEyeHeight() / 2.0,
					plai.posZ, is);
			plai.worldObj.spawnEntityInWorld(item);
		}
		return added;
	}

	public static void messagePlayer(EntityPlayer plai, String str) {
		if (IaSPlayerHelper.dochat && plai.worldObj.isRemote) {
			final ChatComponentText txt = new ChatComponentText(str);
			txt.setChatStyle(new ChatStyle().setItalic(true).setColor(EnumChatFormatting.GRAY));
			plai.addChatMessage(txt);
			IaSPlayerHelper.dochat = false;
		} else
			IaSPlayerHelper.dochat = true;
	}

	public static void regen(EntityPlayer pl, int amount) {
		regen(pl, amount, false);
	}

	public static void regen(EntityPlayer pl, int amount, boolean overflow) {
		final float heals = amount / 2.0F;
		if (pl.getHealth() + heals > pl.getMaxHealth()) {
			final float delta = pl.getMaxHealth() - pl.getHealth();
			pl.heal(delta);
			if (overflow) {
				int time = (int) ((heals - delta) * 25);
				final PotionEffect pe = pl.getActivePotionEffect(Potion.regeneration);
				if (pe != null && pe.getAmplifier() >= 0)
					time += pe.getDuration() / (pe.getAmplifier() + 1);
				pl.addPotionEffect(new PotionEffect(Potion.regeneration.id, time, 1));
			}
		} else
			pl.heal(heals);
		pl.getFoodStats().addStats((int) (heals * 2), (int) (heals * 2));
	}
}
