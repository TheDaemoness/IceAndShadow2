package iceandshadow2.nyx.items.tools;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSLenses;
import iceandshadow2.api.IIaSApiTransmuteLens;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;

public abstract class NyxItemLens extends IaSBaseItemSingle implements IIaSGlowing, IIaSApiTransmuteLens {

	@SideOnly(Side.CLIENT)
	protected IIcon lensicon, altaricon;

	boolean overrideAltar;

	public NyxItemLens(String id, boolean altar) {
		super(EnumIaSModule.NYX, id);
		overrideAltar = altar;
	}

	@Override
	public IIcon getAltarTopTexture(ItemStack lens) {
		return altaricon;
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (pass == 1)
			return lensicon;
		return itemIcon;
	}

	public abstract EnumIaSLenses getLensType();

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public abstract int getTransmuteLensRate(EnumIaSLenses lenstype, ItemStack lens, ItemStack target);

	@Override
	public abstract List<ItemStack> getTransmuteLensYield(ItemStack lens, ItemStack target);

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getTexName());
		lensicon = reg.registerIcon(getTexName() + "Glow");
		if (overrideAltar)
			altaricon = reg.registerIcon(getTexName() + "Altar");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean spawnParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}

}
