package iceandshadow2.ias.items.tools;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class IaSItemArmor extends ItemArmor implements IIaSModName {

	public static ArmorMaterial MATERIAL_ECHIR = EnumHelper.addArmorMaterial(
			"Echir", 44, new int[] { 3, 8, 6, 3 }, 16);

	public static ArmorMaterial MATERIAL_NAVISTRA = EnumHelper
			.addArmorMaterial("Navistra", 88, new int[] { 4, 8, 6, 3 }, 8);

	public static ArmorMaterial MATERIAL_CORTRA = EnumHelper.addArmorMaterial(
			"Cortra", 33, new int[] { 3, 7, 5, 3 }, 24);

	public static ArmorMaterial MATERIAL_CORPSESKIN = EnumHelper
			.addArmorMaterial("Corpseskin", 22, new int[] { 2, 7, 5, 2 }, 12);

	public static ArmorMaterial MATERIAL_SPIDERSILK = EnumHelper
			.addArmorMaterial("NyxSpiderSilk", 22, new int[] { 2, 6, 4, 1 }, 12);

	static {
		MATERIAL_ECHIR.customCraftingMaterial = NyxItems.echirIngot;
		MATERIAL_NAVISTRA.customCraftingMaterial = NyxItems.navistraShard;
		MATERIAL_CORTRA.customCraftingMaterial = NyxItems.echirIngot;
		MATERIAL_SPIDERSILK.customCraftingMaterial = NyxItems.toughGossamer;
	}

	protected String armorTexString;

	public IaSItemArmor(ArmorMaterial arm, int leg, int head, String body) {
		super(arm, leg, head);
		armorTexString = body;
		this.setUnlocalizedName("iasArmor" + arm.name() + this.armorType);
		this.setTextureName("IceAndShadow2:armor/iasArmor" + arm.name()
				+ this.armorType);
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot,
			String type) {
		if (entity.isInvisible())
			return "IceAndShadow2:textures/armor/the_invisible_man.png";
		if (slot == 2)
			return armorTexString + "_2.png";
		return armorTexString + "_1.png";
	}

	@Override
	public int getDamage(ItemStack stack) {
		return ((IaSItemArmor) stack.getItem()).getArmorMaterial() == MATERIAL_NAVISTRA ? 0
				: super.getDamage(stack);
	}

	@Override
	public boolean isDamageable() {
		return this.getArmorMaterial() != MATERIAL_NAVISTRA;
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return EnumIaSModule.IAS;
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	@Deprecated
	public String getTexName() {
		return "IceAndShadow2:armor/" + this.getModName();
	}

	@Override
	public boolean isDamaged(ItemStack stack) {
		return ((IaSItemArmor) stack.getItem()).getArmorMaterial() == MATERIAL_NAVISTRA ? false
				: super.isDamaged(stack);
	}
}