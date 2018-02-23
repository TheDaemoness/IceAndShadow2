package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSFlags;
import iceandshadow2.IaSRegistry;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.entities.projectile.EntityShadowBall;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class NyxBaseItemBone extends IaSBaseItemSingle implements IIaSGlowing {
	
	@SideOnly(Side.CLIENT)
	protected IIcon glow;

	public NyxBaseItemBone(String texName) {
		super(EnumIaSModule.NYX, texName);
		setMaxStackSize(1);
		setMaxDamage(300);
		setNoRepair();
		setFull3D();
		IaSRegistry.blacklistUncraft(this);
	}
	
	public abstract void onBoneDone(ItemStack is);
	public abstract void doEffect(Entity ent);

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NATIVE;
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.uncommon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() > 0;
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		if (entityItem.getEntityItem().isItemDamaged()) {
			entityItem.setDead();
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack isk, World par2World, EntityPlayer plai) {
		if (plai.capabilities.isCreativeMode) {
			IaSPlayerHelper.messagePlayer(plai,
					"badPlayerNoBone");
			return isk;
		}
		if (!isk.isItemDamaged()) {
			if (plai.dimension != IaSFlags.dim_nyx_id) {
				IaSPlayerHelper.messagePlayer(plai,
						"offNyxNoBone");
				return isk;
			}
			plai.attackEntityFrom(DamageSource.magic,
					Math.max(0, plai.worldObj.difficultySetting.getDifficultyId() - 1));
			isk.setItemDamage(1);
		}
		return isk;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}

	@Override
	public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
		super.onUpdate(stack, par2World, par3Entity, par4, par5);
		if (par2World.isRemote)
			return;
		if (!(par3Entity instanceof EntityPlayer)) {
			stack.stackSize = 0;
			return;
		}
		final int dmg = stack.getItemDamage();
		if (stack.isItemDamaged()) {
			if (((EntityPlayer) par3Entity).capabilities.isCreativeMode) {
				stack.setItemDamage(0);
			} else if (dmg > 0) {
				if (stack.attemptDamageItem(1, par2World.rand))
					onBoneDone(stack);
				else
					doEffect(par3Entity);
			}
		}
	}
	
	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return getIconFromDamageForRenderPass(0, pass);
	}

	@Override
	public IIcon getIconFromDamageForRenderPass(int dmg, int pass) {
		if (pass == 1)
			return glow;
		return itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getTextureName());
		glow = reg.registerIcon(getTextureName() + "Glow");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
}
