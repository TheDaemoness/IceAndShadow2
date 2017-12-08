package iceandshadow2.ias.items.tools;

import java.util.List;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.ias.ai.IIaSMobGetters;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class IaSItemArmor extends ItemArmor implements IIaSModName {
	
	protected IaSArmorMaterial mat;
	protected String armorTexString;

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		// TODO Auto-generated method stub
		super.onArmorTick(world, player, itemStack);
	}


	public IaSItemArmor(IaSArmorMaterial arm, int renderIndex, int type, String body) {
		super(arm.getArmorStats(), renderIndex, type);
		mat = arm;
		this.armorTexString = body;
		setUnlocalizedName("iasArmor" + arm.getArmorStats().name() + this.armorType);
		setTextureName("IceAndShadow2:armor/iasArmor" + arm.getArmorStats().name() + this.armorType);
	}
	
	public IaSArmorMaterial getIaSArmorMaterial() {
		return mat;
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
		return !mat.isBreakable() ? 0 : super.getDamage(stack);
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
	public EnumRarity getRarity(ItemStack is) {
		return mat.getRarity(is);
	}

	@Override
	@Deprecated
	public String getTexName() {
		return "IceAndShadow2:armor/" + getModName();
	}

	@Override
	public boolean isDamageable() {
		return mat.isBreakable();
	}

	@Override
	public boolean isDamaged(ItemStack stack) {
		return !mat.isBreakable() ? false : super.isDamaged(stack);
	}
	
	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int i, boolean isHeld) {
		if (w.isRemote || !(e instanceof EntityLivingBase))
			return;
		final EntityLivingBase elb = (EntityLivingBase)e;
		final ItemStack armor = elb.getEquipmentInSlot(1+this.armorType);
		if(armor == null || armor.getItem() != this)
			return;
		mat.onTick(elb, 1, false);
		super.onUpdate(is, w, e, i, isHeld);
	}
}