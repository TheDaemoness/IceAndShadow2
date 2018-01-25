package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.items.IaSBaseItem;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemIngot extends IaSBaseItemSingleGlow {

	@SideOnly(Side.CLIENT)
	protected IIcon active, invisible;

	protected IaSBaseItem association;

	public NyxItemIngot(String texName, IaSBaseItem associated) {
		super(EnumIaSModule.NYX, texName);
		setHasSubtypes(true);
		association = associated;
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1));
		GameRegistry.addSmelting(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1), 0);
	}

	@Override
	public void addInformation(ItemStack s, EntityPlayer p, List l, boolean b) {
		if (s.getItemDamage() == 1) {
			l.add(EnumChatFormatting.GRAY.toString() + EnumChatFormatting.ITALIC.toString()
					+ "Sneak and Use Item to finalize.");
		} else {
			l.add(EnumChatFormatting.GRAY.toString() + EnumChatFormatting.ITALIC.toString()
					+ "This needs to be heated up...");
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int dmg, int pass) {
		if (dmg > 0)
			return active;
		if (pass == 1)
			return invisible;
		return itemIcon;
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		if (stack.getItemDamage() == 1)
			return 16;
		return 64;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return association.getAspect();
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return association.getRarity(new ItemStack(association));
	}

	@Override
	public void getSubItems(Item i, CreativeTabs t, List l) {
		super.getSubItems(i, t, l);
		l.add(new ItemStack(this, 1, 1));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItemDamage() == 1)
			return super.getUnlocalizedName() + "Active";
		return super.getUnlocalizedName();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heap, World order, EntityPlayer pwai) {
		if (pwai.isSneaking()) {
			heap.setItemDamage(0);
		}
		return heap;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		active = reg.registerIcon(getTextureName() + "Active");
		invisible = reg.registerIcon("IceAndShadow2:iasInvisible");
	}
}
