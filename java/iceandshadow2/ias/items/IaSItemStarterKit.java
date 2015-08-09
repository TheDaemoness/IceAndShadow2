package iceandshadow2.ias.items;

import java.util.List;
import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.IIaSApiSacrificeXp;
import iceandshadow2.api.IIaSOnDeathDestroy;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSPlayerHelper;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class IaSItemStarterKit extends IaSBaseItemSingle implements IIaSOnDeathDestroy {

	public static IaSItemStarterKit instance;
	
	public static void init() {
		instance = new IaSItemStarterKit("StarterKit");
	}
	
	public IaSItemStarterKit(String texName) {
		super(EnumIaSModule.IAS, texName);
		this.setMaxStackSize(1);
		this.register();
		this.setCreativeTab(IaSCreativeTabs.misc);
	}
	
	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_,
			List l, boolean p_77624_4_) {
		l.add(EnumChatFormatting.GRAY.toString()
				+ EnumChatFormatting.ITALIC.toString()
				+ "Creative Only");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w,
			EntityPlayer pwai) {
		if(w.isRemote || !pwai.capabilities.isCreativeMode)
			return is;
		is.stackSize = 1;
		IaSPlayerHelper.giveItem(pwai, new ItemStack(Items.diamond_helmet));
		IaSPlayerHelper.giveItem(pwai, new ItemStack(Items.diamond_chestplate));
		IaSPlayerHelper.giveItem(pwai, new ItemStack(Items.diamond_leggings));
		ItemStack it = new ItemStack(Items.diamond_boots);
		it.addEnchantment(Enchantment.featherFalling, 4);
		it.addEnchantment(Enchantment.unbreaking, 3);
		IaSPlayerHelper.giveItem(pwai, it);
		IaSPlayerHelper.giveItem(pwai, new ItemStack(Items.iron_axe));
		IaSPlayerHelper.giveItem(pwai, new ItemStack(Items.iron_shovel));
		it = new ItemStack(Items.diamond_pickaxe);
		it.addEnchantment(Enchantment.efficiency, 3);
		IaSPlayerHelper.giveItem(pwai, it);
		it = new ItemStack(Items.diamond_sword);
		it.addEnchantment(Enchantment.smite, 3);
		it.addEnchantment(Enchantment.knockback, 1);
		IaSPlayerHelper.giveItem(pwai, it);
		it = new ItemStack(Items.bow);
		it.addEnchantment(Enchantment.power, 3);
		it.addEnchantment(Enchantment.punch, 1);
		it.addEnchantment(Enchantment.infinity, 1);
		IaSPlayerHelper.giveItem(pwai, it);
		IaSPlayerHelper.giveItem(pwai, new ItemStack(Items.cooked_porkchop,64));
		IaSPlayerHelper.giveItem(pwai, new ItemStack(Items.shears));
		IaSPlayerHelper.giveItem(pwai, new ItemStack(Blocks.cobblestone, 64));
		IaSPlayerHelper.giveItem(pwai, new ItemStack(Items.arrow,16));
		is.setItemDamage(0);
		is.func_150996_a(NyxItems.teleportCrystal);
		return is;
	}

	

}
