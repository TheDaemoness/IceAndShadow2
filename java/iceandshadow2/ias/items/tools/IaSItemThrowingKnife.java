package iceandshadow2.ias.items.tools;

//import iceandshadow2.nyx.entity.projectile.EntityThrowingKnife;

import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.entities.projectile.EntityThrowingKnife;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class IaSItemThrowingKnife extends IaSItemWeapon {

	public static final String PLAYER_NBT_ID = "iasThrowingKnifeDelay";

	public IaSItemThrowingKnife() {
		super(EnumIaSToolClass.KNIFE);
		setNoRepair();
		setMaxDamage(0);
		setUnlocalizedName("iasThrowingKnife");
		setMaxStackSize(32);
	}

	@Override
	public boolean canRepair() {
		return false;
	}

	@Override
	public int getMaxDamage(ItemStack is) {
		return 0;
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase,
			EntityLivingBase par3EntityLivingBase) {
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_,
			int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World aroundThe, EntityPlayer plai) {
		final int delai = plai.getEntityData().getInteger(IaSItemThrowingKnife.PLAYER_NBT_ID);
		if (delai > 0)
			return is;

		EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, is);
		final EntityThrowingKnife var8 = new EntityThrowingKnife(aroundThe, plai, 1.0F, is);

		final IaSToolMaterial mat = IaSToolMaterial.extractMaterial(is);
		mat.onKnifeThrow(is, plai, var8);

		aroundThe.playSoundAtEntity(plai, "random.bow", 0.5F, 0.75F);

		if (!plai.capabilities.isCreativeMode) {
			is.stackSize -= 1;
		}

		if (!aroundThe.isRemote) {
			plai.getEntityData().setInteger(IaSItemThrowingKnife.PLAYER_NBT_ID,
					mat.getKnifeCooldown(is, aroundThe, plai));
			aroundThe.spawnEntityInWorld(var8);
		}

		return is;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack is, EntityPlayer user, Entity target) {
		final IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		m.onAttack(is, user, target);
		return true;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World world, Entity player, int par4, boolean par5) {
		final int tom = player.getEntityData().getInteger(IaSItemThrowingKnife.PLAYER_NBT_ID); // Political
																								// joke,
																								// pay
																								// no
																								// attention.
		if (par5 && tom > 0 && !world.isRemote) {
			player.getEntityData().setInteger(IaSItemThrowingKnife.PLAYER_NBT_ID, tom - 1);
		}
	}
}
