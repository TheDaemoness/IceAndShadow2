package iceandshadow2.ias.items.tools;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import iceandshadow2.ias.interfaces.IIaSModName;
import iceandshadow2.util.EnumIaSModule;
import net.minecraftforge.common.util.EnumHelper;

public class IaSItemArmor extends ItemArmor implements IIaSModName {

	public static ArmorMaterial MATERIAL_ECHIR = EnumHelper.addArmorMaterial("Echir", 44,
			new int[] { 3, 8, 6, 3 }, 16);

	public static ArmorMaterial MATERIAL_NAVISTRA = EnumHelper.addArmorMaterial("Navistra", 88,
			new int[] { 4, 9, 7, 4 }, 8);

	public static ArmorMaterial MATERIAL_CORTRA = EnumHelper.addArmorMaterial("Cortra", 33,
			new int[] { 3, 7, 5, 3 }, 24);
	
	public static ArmorMaterial MATERIAL_CORPSESKIN = EnumHelper.addArmorMaterial("Corpseskin", 22,
			new int[] { 2, 7, 5, 2 }, 12);
	
	public static ArmorMaterial MATERIAL_SPIDERSILK = EnumHelper.addArmorMaterial("NyxSpiderSilk", 22,
			new int[] { 2, 6, 4, 1 }, 12);

	protected String armorTexString;
	
	public IaSItemArmor(ArmorMaterial arm, int leg,
			int head, String body) {
		super(arm, leg, head);
		armorTexString = body;
		this.setUnlocalizedName("iasArmor"+arm.name()+this.armorType);
		this.setTextureName("IceAndShadow2:armor/iasArmor"+arm.name()+this.armorType);
	}
	
	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type) {
		if(entity.isInvisible())
			return "IceAndShadow2:textures/armor/the_invisible_man.png";
		if(slot == 2)
			return armorTexString+"_2.png";
		return armorTexString+"_1.png";
    }

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	@Deprecated
	public String getTexName() {
		return "IceAndShadow2:armor/"+this.getModName();
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return EnumIaSModule.IAS;
	}
}