package iceandshadow2.ias.items.tools;

import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSAspect;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.EnumHelper;

public class IaSArmorMaterial implements IIaSAspect, IIaSGlowing {

	public static IaSArmorMaterial getArmorMaterial(ItemStack is) {
		if (is == null || !(is.getItem() instanceof IaSItemArmor))
			return null;
		return ((IaSItemArmor) is.getItem()).getIaSArmorMaterial();
	}

	ArmorMaterial m;

	public IaSArmorMaterial(String name, Item material, int durabilitymod, int head, int torso, int legs, int feet,
			int enchantability) {
		m = EnumHelper.addArmorMaterial(name, durabilitymod, new int[] { head, torso, legs, feet }, enchantability);
		m.customCraftingMaterial = material;
	}

	/**
	 * Gets the base stats provided by this piece of armor.
	 *
	 * @return
	 */
	public ArmorMaterial getArmorStats() {
		return m;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.getAspect(getArmorStats().customCraftingMaterial);
	}

	/**
	 * Gets the armor coverage as a function of what slot the armor is in and
	 * its durability.
	 *
	 * @param slot
	 *            The slot the armor is in. Cheat sheet: 1 - Head, 4 - Feet.
	 * @param durability
	 *            The armor's durability, expressed as a value from 0 to 1,
	 *            where 1 is fully repaired.
	 * @return The coverage provided by that piece of armor. The cumulative
	 *         coverage from a full set of same-type armor at full durability
	 *         should be 10.
	 */
	public double getCoverage(int slot, double durability) {
		switch (slot) {
		case 1:
			return 2 * Math.cbrt(durability);
		case 2:
			return 4 * Math.cbrt(durability);
		case 3:
			return 3 * Math.cbrt(durability);
		case 4:
			return 1;
		default:
			return 0;
		}
	}

	/**
	 * Gets the rarity to display.
	 */
	public EnumRarity getRarity() {
		return EnumRarity.common;
	}

	/**
	 * Can be overriden to completely disable armor durability.
	 */
	public boolean isBreakable() {
		return true;
	}

	/**
	 * Called by Nyxian armors upon their wearer taking damage. Called one for
	 * each type of armor the player wears.
	 *
	 * @param coverage
	 *            A value from 1-10 representing how much of that type of armor
	 *            the player has equipped.
	 * @param major
	 *            Whether the player has major coverage. Given for convenience.
	 * @return The amount of damage the player should take.
	 */
	public float onHurt(EntityLivingBase wearer, DamageSource dmg, float amount, double coverage, boolean major) {
		return amount;
	}

	/**
	 * Called every tick when the wearer is wielding armor.
	 *
	 * @param coverage
	 *            A value from 1-10 representing how much of that type of armor
	 *            the player has equipped.
	 * @param boots
	 *            Whether the boots are equipped, for convenience.
	 * @param major
	 *            Whether the player has major coverage. Given for convenience.
	 */
	public void onTick(EntityLivingBase wearer, double coverage, boolean major) {

	}

	public int getRenderPasses() {
		return 2;
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}
}
