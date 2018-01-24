package iceandshadow2.nyx.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSOnDeathRuin;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;

public class NyxItemExtractorXP extends IaSBaseItemSingle implements IIaSGlowing, IIaSOnDeathRuin {

	// TODO: RENDER PASSES AND ICONS!

	@SideOnly(Side.CLIENT)
	protected IIcon fillIcons[];

	public NyxItemExtractorXP(String texName) {
		super(EnumIaSModule.NYX, texName);
		setMaxStackSize(1);
		setMaxDamage(14);
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 2;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
	}

	@Override
	public IIcon getIconFromDamageForRenderPass(int dmg, int pass) {
		if (pass >= 1)
			return fillIcons[Math.max(6, dmg)];
		return itemIcon;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.block;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 32;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 3;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World wld, EntityPlayer pl) {
		pl.addExperienceLevel(-5);
		is.setItemDamage(is.getItemDamage() - 1);
		return is;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heap, World order, EntityPlayer pwai) {
		if (pwai.experienceLevel >= 5 && heap.getItemDamage() > 0)
			pwai.setItemInUse(heap, getMaxItemUseDuration(heap));
		return heap;
	}

	@Override
	public void registerIcons(IIconRegister r) {
		itemIcon = r.registerIcon(getTextureName());
		fillIcons = new IIcon[7];
		for (int i = 0; i < 7; ++i)
			fillIcons[i] = r.registerIcon(getTextureName() + i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}
}
