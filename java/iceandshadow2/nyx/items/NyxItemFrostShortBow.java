package iceandshadow2.nyx.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.nyx.entities.projectile.EntityIceArrow;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.interfaces.IIaSModName;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class NyxItemFrostShortBow extends ItemBow implements IIaSModName, IIaSGlowing {

	@SideOnly(Side.CLIENT)
	private IIcon[][] iconArray;
	
	@SideOnly(Side.CLIENT)
	private IIcon glow;

	public boolean inuse;

	public NyxItemFrostShortBow(String par1) {
		super();
		this.setUnlocalizedName("nyx"+par1);
		this.setMaxDamage(384);
		this.bFull3D = false;
		inuse = false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(getTexName());
		this.glow = reg.registerIcon(getTexName()+"Glow");
		this.iconArray = new IIcon[3][2];

		for (int i = 0; i < this.iconArray.length; ++i) {
			this.iconArray[i][0] = reg.registerIcon(getTexName()+"Anim"
					+ (i + 1));
			this.iconArray[i][1] = reg.registerIcon(getTexName()+"GlowAnim"
					+ (i + 1));
		}
	}
	
	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		this.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, 0);
		return par1ItemStack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;
		inuse = false;

		float var7 = (float) var6 / 20.0F;
		var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

		if ((double) var7 < 0.4D) {
			return;
		}

		if (var7 > 0.95F) {
			var7 = 1.0F;
		}

		int var9 = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.power.effectId, par1ItemStack) + 1;
		int var10 = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.punch.effectId, par1ItemStack);
		EntityIceArrow var8 = new EntityIceArrow(par2World,
				par3EntityPlayer, var7 * 2.0F, var10 + 1, var9 * 30 + 70);

		var8.setDamage(var8.getDamage() + (double) var9 * 0.5D + 0.5D);

		if (var10 > 0) {
			var8.setKnockbackStrength(var10);
		}

		if(!par3EntityPlayer.capabilities.isCreativeMode)
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage()+1);

		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F,
				1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

		if (!par2World.isRemote) {
			par2World.spawnEntityInWorld(var8);
		}

	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 20;
	}

	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer plai) {
		((NyxItemFrostShortBow)item.getItem()).inuse = false;
		return true;
	}

	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player,
			ItemStack usingItem, int useRemaining) {

		if(usingItem != stack)
			this.inuse = false;

		if (!(((NyxItemFrostShortBow)stack.getItem()).inuse))
			return renderPass>0?this.glow:this.itemIcon;

		int j = getMaxItemUseDuration(stack) - useRemaining;

		if (j >= 15)
			return this.iconArray[2][renderPass];
		if (j >= 10)
			return this.iconArray[1][renderPass];
		return this.iconArray[0][renderPass];

	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {

		if (this.getDamage(par1ItemStack) < this.getMaxDamage()-1) {
			this.inuse = true;
			par3EntityPlayer.setItemInUse(par1ItemStack,
					this.getMaxItemUseDuration(par1ItemStack));
		}

		return par1ItemStack;
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public String getTexName() {
		return "IceAndShadow2:"+this.getModName();
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return EnumIaSModule.NYX;
	}
	
	public final Item register() {
		IaSRegistration.register(this);
		return this;
	}
	
	@Override
	public boolean usesDefaultGlowRenderer() {
		return false;
	}

}
