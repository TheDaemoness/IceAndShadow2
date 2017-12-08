package iceandshadow2.nyx.items;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IIaSOnDeathDrop;
import iceandshadow2.ias.ai.IIaSMobGetters;
import iceandshadow2.ias.items.IaSBaseItemMultiGlow;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.render.fx.IaSFxManager;

public class NyxItemAlabaster extends IaSBaseItemMultiGlow implements IIaSApiTransmute, IIaSOnDeathDrop {
	
	public NyxItemAlabaster(String texName) {
		super(EnumIaSModule.NYX, texName, 1);
		setMaxStackSize(4);
		GameRegistry.addSmelting(NyxItems.alabaster, new ItemStack(Items.nether_star), 1);
	}
	
	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.PURE;
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
		if (catalyst.getItemDamage() == 0) {
			do {
				if (target.getItem() == Item.getItemFromBlock(Blocks.coal_block))
					break;
				else if (target.getItem() == Item.getItemFromBlock(Blocks.gold_ore))
					break;
				else if (target.getItem() == Item.getItemFromBlock(Blocks.redstone_block))
					break;
				else if (target.getItem() == Item.getItemFromBlock(Blocks.obsidian))
					break;
				else
					return 0;
			} while (false);
			return 240;
		} else {} //No-op for now.
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final ArrayList<ItemStack> retval = new ArrayList<ItemStack>();
		catalyst.stackSize -= 1;
		if (catalyst.getItem() == this && catalyst.getItemDamage() == 0) {
			final Item tem = target.getItem(); // No Undertale.
			if (tem == Item.getItemFromBlock(Blocks.coal_block)) {
				target.stackSize -= 1;
				retval.add(new ItemStack(NyxItems.devora, 27));
			} else if (tem == Item.getItemFromBlock(Blocks.gold_block)) {
				target.stackSize -= 1;
				retval.add(new ItemStack(NyxItems.echirIngot, 27, 1));
			} else if (tem == Item.getItemFromBlock(Blocks.redstone_block)) {
				target.stackSize -= 1;
				retval.add(new ItemStack(NyxItems.cortra, 27, 1));
			} if (tem == Item.getItemFromBlock(Blocks.obsidian)) {
				target.stackSize -= 1;
				retval.add(new ItemStack(NyxBlocks.sanctifiedObsidian, 1));
			}
		}
		return retval;
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
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		IaSFxManager.spawnParticle(world, "cloudSmall", ent.posX, ent.posY-world.rand.nextFloat()+0.25, ent.posZ,
				0.0, 0.0, 0.0, false, false);
		return true;
	}

}
