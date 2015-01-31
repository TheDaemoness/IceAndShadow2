package iceandshadow2.nyx.items.tools;

import java.util.List;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class NyxItemBow extends IaSBaseItemSingle implements IIaSGlowing, IIaSApiTransmute {

	public static final String nbtTierID = "nyxBowDrawModifier";
	private static final String[] numerals = {"II", "III", "IV", "V", "VI"};

	@SideOnly(Side.CLIENT)
	private IIcon[][] iconArray;

	@SideOnly(Side.CLIENT)
	private IIcon glow;

	public boolean inuse;

	public NyxItemBow(String par1) {
		super(EnumIaSModule.NYX,par1);
		this.bFull3D = false;
		inuse = false;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep,
			List li, boolean boo) {
		final int mod = this.getSpeedModifier(is);
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
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(getTexName());
		this.glow = reg.registerIcon(getTexName() + "Glow");
		this.iconArray = new IIcon[3][2];
		for (int i = 0; i < this.iconArray.length; ++i) {
			this.iconArray[i][0] = reg.registerIcon(getTexName() + "Anim"
					+ (i + 1));
			this.iconArray[i][1] = reg.registerIcon(getTexName() + "GlowAnim"
					+ (i + 1));
		}
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player,
			ItemStack usingItem, int useRemaining) {

		if (usingItem != stack)
			this.inuse = false;

		if (!((NyxItemBow) stack.getItem()).inuse)
			return renderPass > 0 ? this.glow : this.itemIcon;

			final int j = getMaxItemUseDuration(stack) - useRemaining;

			if (j >= getTimeForIcon(this.getSpeedModifier(stack), 2))
				return this.iconArray[2][renderPass];
			if (j >= getTimeForIcon(this.getSpeedModifier(stack), 1))
				return this.iconArray[1][renderPass];
			return this.iconArray[0][renderPass];
	}

	public abstract int getTimeForIcon(int mod, int index);
	public abstract int getUpgradeCost(int mod);

	public int getSpeedModifier(ItemStack is) {
		if(!is.hasTagCompound())
			return 0;
		if(!is.getTagCompound().hasKey(nbtTierID))
			return 0;
		return is.getTagCompound().getInteger(nbtTierID);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer plai) {
		((NyxItemBow) item.getItem()).inuse = false;
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {

		if (this.getDamage(par1ItemStack) < this.getMaxDamage() - 1) {
			this.inuse = true;
			par3EntityPlayer.setItemInUse(par1ItemStack,
					this.getMaxItemUseDuration(par1ItemStack));
		}

		return par1ItemStack;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is) {
		return EnumAction.bow;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return false;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if(target.getItem() != this)
			return 0;
		if(this.getSpeedModifier(target) >= 5)
			return 0;
		if(catalyst.stackSize < this.getUpgradeCost(this.getSpeedModifier(target)))
			return 0;
		if(catalyst.getItem() == NyxItems.nifelhiumPowder)
			return 160;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target,
			ItemStack catalyst, World world) {
		if(!target.hasTagCompound()) {
			target.setTagCompound(new NBTTagCompound());
			target.getTagCompound().setInteger(nbtTierID,1);
		} else
			target.getTagCompound().setInteger(nbtTierID,this.getSpeedModifier(target)+1);
		catalyst.stackSize -= this.getUpgradeCost(this.getSpeedModifier(target));
		return null;
	}

	@Override
	public boolean spawnParticles(ItemStack target, ItemStack catalyst,
			World world, Entity ent) {
		return false;
	}

}
