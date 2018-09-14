package iceandshadow2.nyx.items.tools;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.api.IIaSApiTransmute;
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
import net.minecraft.item.Item;
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
	private static final String[] numerals = { "II", "III", "IV", "V", "VI" };

	@SideOnly(Side.CLIENT)
	private IIcon glow;

	public NyxItemSwordFrost(String par1) {
		super(EnumIaSModule.NYX, par1);
		bFull3D = true;
		setMaxStackSize(1);
		setMaxDamage(512);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean boo) {
		final int mod = getUpgradeLevel(is);
		if (mod > 0) {
			final String tier = NyxItemSwordFrost.numerals[mod - 1];
			li.add(EnumChatFormatting.DARK_AQUA.toString() + EnumChatFormatting.ITALIC.toString() + "Tier " + tier);
		}
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		final Multimap mm = HashMultimap.create();
		mm.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(Item.field_111210_e, "Weapon modifier", 5, 0));
		return mm;
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		return renderPass > 0 ? glow : itemIcon;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is) {
		return EnumAction.block;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (target.getItem() != this)
			return 0;
		if (getUpgradeLevel(target) >= 5)
			return 0;
		if (catalyst.stackSize < getUpgradeCost(getUpgradeLevel(target)))
			return 0;
		if (catalyst.getItem() == NyxItems.nifelhiumPowder)
			return 160;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		if (!target.hasTagCompound()) {
			target.setTagCompound(new NBTTagCompound());
			target.getTagCompound().setInteger(NyxItemSwordFrost.nbtTierID, 1);
		} else
			target.getTagCompound().setInteger(NyxItemSwordFrost.nbtTierID, getUpgradeLevel(target) + 1);
		catalyst.stackSize -= getUpgradeCost(getUpgradeLevel(target));
		return null;
	}

	public int getUpgradeCost(int mod) {
		return 9 + (mod) / 2;
	}

	public int getUpgradeLevel(ItemStack is) {
		if (!is.hasTagCompound())
			return 0;
		if (!is.getTagCompound().hasKey(NyxItemSwordFrost.nbtTierID))
			return 0;
		return is.getTagCompound().getInteger(NyxItemSwordFrost.nbtTierID);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep) {
		ep.setItemInUse(is, getMaxItemUseDuration(is));
		return is;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		super.onLeftClickEntity(stack, player, entity);
		if (entity instanceof EntityLivingBase)
			((EntityLivingBase) entity)
					.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 15 + 4 * getUpgradeLevel(stack), 4));
		return false;
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getTextureName());
		glow = reg.registerIcon(getTextureName() + "Glow");
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}
}
