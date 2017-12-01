package iceandshadow2.nyx.items;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IIaSOnDeathDrop;
import iceandshadow2.ias.items.IaSBaseItemMultiGlow;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.mobs.IIaSMobGetters;

public class NyxItemAlabaster extends IaSBaseItemMultiGlow implements IIaSApiTransmute, IIaSOnDeathDrop {

	@SideOnly(Side.CLIENT)
	protected IIcon burned;

	public NyxItemAlabaster(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		setMaxStackSize(16);
	}

	@Override
	public void addInformation(ItemStack s, EntityPlayer p, List l, boolean b) {
		if (s.getItemDamage() == 0) {
			l.add(EnumChatFormatting.DARK_RED.toString() + EnumChatFormatting.ITALIC.toString() + "They are coming.");
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 1)
			return this.burned;
		return this.itemIcon;
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.uncommon;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (catalyst.getItem() != this)
			return 0;
		if (catalyst.getItemDamage() == 1) {
			if (target.getItem() == Item.getItemFromBlock(Blocks.coal_block))
				;
			return 240;
		}
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final ArrayList<ItemStack> retval = new ArrayList<ItemStack>();
		catalyst.stackSize -= 1;
		if (catalyst.getItemDamage() == 1) {
			if (target.getItem() == Item.getItemFromBlock(Blocks.coal_block)) {
				target.stackSize -= 1;
				retval.add(new ItemStack(NyxItems.devora, 27));
			}
		}
		return retval;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity e) {
		if (player.worldObj.isRemote)
			return true;
		if (stack.getItemDamage() == 0 && e instanceof EntityMagmaCube) {
			((EntityMagmaCube) e).setDead();
			player.worldObj.createExplosion(player, e.posX, e.posY, e.posZ, 0.5f, true);
			stack.setItemDamage(1);
			return true;
		}
		return false;
	}

	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int i, boolean isHeld) {
		if (w.isRemote || is.getItemDamage() > 0)
			return;
		if (e instanceof EntityLivingBase) {
			final EntityLivingBase sucker = (EntityLivingBase) e;
			if ((sucker.getAge() & 127) == 0) {
				if (sucker.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
					final List li = w.getEntitiesWithinAABBExcludingEntity(sucker,
							AxisAlignedBB.getBoundingBox(sucker.posX - 32, sucker.posY - 8, sucker.posZ - 32,
									sucker.posX + 32, sucker.posY + 24, sucker.posZ + 32));
					for (final Object ent : li) {
						if (ent instanceof EntityMob) {
							final EntityMob joker = (EntityMob) ent;
							if (joker.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
								if (ent instanceof IIaSMobGetters)
									((IIaSMobGetters) ent).setSearchTarget(sucker);
								else if (!joker.isInvisible() && joker.getAttackTarget() == null)
									joker.setTarget(sucker);
							}
						}
					}
				}
			}
		}
		super.onUpdate(is, w, e, i, isHeld);
	}

	@Override
	public void registerIcons(IIconRegister ir) {
		this.itemIcon = ir.registerIcon(getTexName() + '0');
		this.burned = ir.registerIcon(getTexName() + '1');
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}

}
