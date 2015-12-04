package iceandshadow2.ias.handlers;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.api.IIaSApiSacrificeXp;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IIaSTool;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.items.NyxItemIngot;

public class IaSHandlerSacrificeXPBasics implements IIaSApiSacrificeXp {

	@Override
	public int getXpValue(ItemStack staque, Random rand) {
		if (staque.getItem() == Items.emerald)
			return 3;
		if (staque.getItem() == Items.gold_ingot)
			return 2;
		if (staque.getItem() == Items.blaze_rod)
			return 1;
		if (staque.getItem() == Items.golden_apple)
			return (staque.getItemDamage()==1)?150:20;
		if (staque.getItem() == Items.ghast_tear)
			return 2;
		if (staque.getItem() == Items.ender_pearl)
			return 3;
		if (staque.getItem() == Items.ender_eye)
			return 4;
		if (staque.getItem() == Items.nether_star)
			return 40;
		if (staque.getItem() == Items.diamond)
			return 4;
		return 0;
	}
}
