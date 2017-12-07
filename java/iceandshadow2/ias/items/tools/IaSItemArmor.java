package iceandshadow2.ias.items.tools;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class IaSItemArmor extends ItemArmor implements IIaSModName {

	public static ArmorMaterial MATERIAL_ECHIR = EnumHelper.addArmorMaterial("Echir", 44, new int[] { 3, 8, 6, 3 }, 16);

	public static ArmorMaterial MATERIAL_NAVISTRA = EnumHelper.addArmorMaterial("Navistra", 88,
			new int[] { 4, 9, 7, 3 }, 4);

	public static ArmorMaterial MATERIAL_CORTRA = EnumHelper.addArmorMaterial("Cortra", 33, new int[] { 3, 7, 5, 3 },
			20);

	public static ArmorMaterial MATERIAL_SPIDERSILK = EnumHelper.addArmorMaterial("NyxSpiderSilk", 22,
			new int[] { 2, 6, 4, 2 }, 12);
	
	public static ArmorMaterial MATERIAL_ALABASTER = EnumHelper.addArmorMaterial("Alabaster", 22,
			new int[] { 3, 7, 5, 3 }, 24);

	static {
		IaSItemArmor.MATERIAL_ECHIR.customCraftingMaterial = NyxItems.echirIngot;
		IaSItemArmor.MATERIAL_NAVISTRA.customCraftingMaterial = NyxItems.navistraShard;
		IaSItemArmor.MATERIAL_CORTRA.customCraftingMaterial = NyxItems.cortraIngot;
		IaSItemArmor.MATERIAL_SPIDERSILK.customCraftingMaterial = NyxItems.toughGossamer;
		IaSItemArmor.MATERIAL_ALABASTER.customCraftingMaterial = NyxItems.alabasterShard;
	}

	protected String armorTexString;

	public IaSItemArmor(ArmorMaterial arm, int leg, int head, String body) {
		super(arm, leg, head);
		this.armorTexString = body;
		setUnlocalizedName("iasArmor" + arm.name() + this.armorType);
		setTextureName("IceAndShadow2:armor/iasArmor" + arm.name() + this.armorType);
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type) {
		if (entity.isInvisible())
			return "IceAndShadow2:textures/armor/the_invisible_man.png";
		if (slot == 2)
			return this.armorTexString + "_2.png";
		return this.armorTexString + "_1.png";
	}

	@Override
	public int getDamage(ItemStack stack) {
		return ((IaSItemArmor) stack.getItem()).getArmorMaterial() == IaSItemArmor.MATERIAL_NAVISTRA ? 0
				: super.getDamage(stack);
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
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.common;
	}

	@Override
	@Deprecated
	public String getTexName() {
		return "IceAndShadow2:armor/" + getModName();
	}

	@Override
	public boolean isDamageable() {
		return getArmorMaterial() != IaSItemArmor.MATERIAL_NAVISTRA;
	}

	@Override
	public boolean isDamaged(ItemStack stack) {
		return ((IaSItemArmor) stack.getItem()).getArmorMaterial() == IaSItemArmor.MATERIAL_NAVISTRA ? false
				: super.isDamaged(stack);
	}
}