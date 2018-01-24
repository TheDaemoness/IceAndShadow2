package iceandshadow2.nyx.items.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSLenses;
import iceandshadow2.api.IIaSApiTransmuteLens;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.NyxItems;

public class NyxItemPotion extends IaSBaseItemMulti {

	public static final ArrayList<Potion> validPotions = new ArrayList<Potion>(Potion.potionTypes.length);

	@SideOnly(Side.CLIENT)
	protected IIcon liquidIcons[];

	static {
		validPotions.add(Potion.heal);
		validPotions.add(Potion.regeneration);
		validPotions.add(Potion.harm);
		for (Potion p : Potion.potionTypes) {
			if (p == null)
				continue;
			if (p.getEffectiveness() > 0.5 && !p.isInstant())
				validPotions.add(p);
		}
	}

	public static int convertVanilla(int damage) {
		List l = PotionHelper.getPotionEffects(damage, false);
		if (l != null && !l.isEmpty()) {
			PotionEffect p = (PotionEffect) l.get(0);
			Potion what = Potion.potionTypes[p.getPotionID()];
			if ((what.isInstant() && p.getAmplifier() >= 1)
					|| (p.getDuration() >= (int) (9600 * what.getEffectiveness()))) {
				for (int i = 0; i < validPotions.size(); ++i) {
					if (validPotions.get(i).id == what.id)
						return freshEffect(i);
				}
			}

		}
		return -1;
	}
	
	public static int getEffect(int damage) {
		return damage >> 2;
	}
	public static int freshEffect(int effect) {
		return effect << 2;
	}

	public NyxItemPotion(String id) {
		super(EnumIaSModule.NYX, id, 4);
		this.setMaxStackSize(1);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return (stack.getItemDamage() & 3) > 0;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return (stack.getItemDamage() & 3)/4.0;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World order, EntityPlayer p) {
		final int damage = is.getItemDamage();
		final int used = damage & 3;
		final int fx = getEffect(damage);
		if (!order.isRemote && fx < validPotions.size()) {
			(new Exception()).printStackTrace();
			if(!p.capabilities.isCreativeMode)
				p.getFoodStats().setFoodLevel(Math.max(p.getFoodStats().getFoodLevel() - 3, 0));
			final Potion pot = validPotions.get(fx);
			if (pot.isInstant())
				pot.affectEntity(null, p, 0, pot.isBadEffect()?1.0:0.5);
			else {
				int duration = (int) (225 * pot.getEffectiveness());
				if (p.isPotionActive(pot)) {
					if(!p.capabilities.isCreativeMode)
						p.getFoodStats().setFoodLevel(Math.max(p.getFoodStats().getFoodLevel() - 2, 0));
					final PotionEffect existingPot = p.getActivePotionEffect(pot);
					duration += existingPot.getDuration() / (1 << Math.max(0, 2 - existingPot.getAmplifier()));
				}
				p.addPotionEffect(new PotionEffect(pot.id, duration, 2));
			}
			order.playSoundAtEntity(p, "random.drink", 0.4F, order.rand.nextFloat() * 0.1F + 1);
			if(used == 3) {
				return new ItemStack(NyxItems.salt, 1, 1);
			} else
				is.setItemDamage(damage + 1);
		}
		return is;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.drink;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 16;
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		String original = super.getItemStackDisplayName(is);
		final int fx = getEffect(is.getItemDamage());
		if (fx >= validPotions.size())
			return original;
		return  original + " ("+LanguageRegistry.instance().getStringLocalization(validPotions.get(fx).getName())+")";
	}

	@Override
	public int getSubtypeCount() {
		return validPotions.size() * 4;
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int meta = 0; meta < validPotions.size(); ++meta)
			par3List.add(new ItemStack(par1, 1, freshEffect(meta)));
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return this.getUnlocalizedName();
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return pass == 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int dmg, int pass) {
		if (pass == 0)
			return liquidIcons[dmg & 3];
		return NyxItems.flask.getIconFromDamage(0);
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromDamage(int dmg) {
		final int fx = getEffect(dmg);
		if (fx >= validPotions.size())
			return 0;
		return validPotions.get(fx).getLiquidColor();
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int pass) {
		return pass > 0 ? super.getColorFromItemStack(is, pass) : this.getColorFromDamage(is.getItemDamage());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		liquidIcons = new IIcon[4];
		for (int i = 0; i < liquidIcons.length; ++i)
			liquidIcons[i] = reg.registerIcon(getTextureName() + i);
	}

}
