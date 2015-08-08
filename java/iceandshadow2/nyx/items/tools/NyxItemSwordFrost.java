package iceandshadow2.nyx.items.tools;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemSwordFrost extends IaSBaseItemSingle implements IIaSGlowing, IIaSApiTransmute {

	public static final String nbtTierID = "nyxTierNifelhium";
	private static final String[] numerals = {"II", "III", "IV", "V", "VI"};

	@SideOnly(Side.CLIENT)
	private IIcon glow;

	public NyxItemSwordFrost(String par1) {
		super(EnumIaSModule.NYX,par1);
		this.bFull3D = true;
		this.setMaxDamage(512);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep,
			List li, boolean boo) {
		final int mod = this.getUpgradeLevel(is);
		if(mod > 0) {
			String tier = numerals[mod-1];
			li.add(EnumChatFormatting.DARK_AQUA.toString()
					+ EnumChatFormatting.ITALIC.toString()
					+ "Tier " + tier);
		}
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}
	
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		Multimap mm = HashMultimap.create();
		mm.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), 
				new AttributeModifier(field_111210_e, "Weapon modifier", 5, 0));
		return mm;
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(getTexName());
		this.glow = reg.registerIcon(getTexName() + "Glow");
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player,
			ItemStack usingItem, int useRemaining) {
		return renderPass > 0 ? this.glow : this.itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is) {
		return EnumAction.block;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World w,
			EntityPlayer ep) {
		ep.setItemInUse(is, this.getMaxItemUseDuration(is));
		return is;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if(target.getItem() != this)
			return 0;
		if(this.getUpgradeLevel(target) >= 5)
			return 0;
		if(catalyst.stackSize < this.getUpgradeCost(this.getUpgradeLevel(target)))
			return 0;
		if(catalyst.getItem() == NyxItems.nifelhiumPowder)
			return 160;
		return 0;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
			Entity entity) {
		super.onLeftClickEntity(stack, player, entity);
		if(entity instanceof EntityLivingBase)
			((EntityLivingBase)entity).addPotionEffect(
				new PotionEffect(Potion.moveSlowdown.id,15+4*getUpgradeLevel(stack),4));
		return false;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target,
			ItemStack catalyst, World world) {
		if(!target.hasTagCompound()) {
			target.setTagCompound(new NBTTagCompound());
			target.getTagCompound().setInteger(nbtTierID,1);
		} else
			target.getTagCompound().setInteger(nbtTierID,this.getUpgradeLevel(target)+1);
		catalyst.stackSize -= this.getUpgradeCost(this.getUpgradeLevel(target));
		return null;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst,
			World world, Entity ent) {
		return false;
	}
	
	public int getUpgradeLevel(ItemStack is) {
		if(!is.hasTagCompound())
			return 0;
		if(!is.getTagCompound().hasKey(nbtTierID))
			return 0;
		return is.getTagCompound().getInteger(nbtTierID);
	}

	public int getUpgradeCost(int mod) {
		return 9+(mod)/2;
	}
}
