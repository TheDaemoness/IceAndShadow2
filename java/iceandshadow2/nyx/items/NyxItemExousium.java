package iceandshadow2.nyx.items;

import java.util.ArrayList;
import java.util.List;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.blocks.NyxBlockStone;
import iceandshadow2.util.IaSPlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemExousium extends IaSBaseItemMulti implements IIaSGlowing, IIaSApiTransmute {

	@SideOnly(Side.CLIENT)
	protected IIcon dustIconGlow, rockIcon[], crystalIcon[];

	public NyxItemExousium(String texName) {
		super(EnumIaSModule.NYX, texName, 3);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 1), new ItemStack(this, 1, 0), new ItemStack(this, 1, 0),
				new ItemStack(this, 1, 0), new ItemStack(this, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 2), new ItemStack(this, 1, 1), new ItemStack(this, 1, 1),
				new ItemStack(this, 1, 1), new ItemStack(this, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(this, 16, 0), new ItemStack(this, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(this, 4, 0), new ItemStack(this, 1, 1));
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int dmg, int pass) {
		if (dmg == 2)
			return this.crystalIcon[pass];
		if (dmg == 1)
			return this.rockIcon[pass];
		if (pass == 0)
			return this.dustIconGlow;
		return this.itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (target.getItem() != NyxItems.echirIngot || target.getItemDamage() != 0 || catalyst.getItem() != this
				|| catalyst.getItemDamage() != 0)
			return 0;
		return 120;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final List<ItemStack> it = new ArrayList<ItemStack>();
		catalyst.stackSize -= 1;
		it.add(new ItemStack(Items.iron_ingot, Math.min(2, target.stackSize), 1));
		target.stackSize -= Math.min(2, target.stackSize);
		return it;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer p, World w, int x, int y, int z, int dmg, float a, float b,
			float c) {
		final Block bl = w.getBlock(x, y, z);
		if (is.getItemDamage() >= 1) {
			boolean converted = false;
			if (bl.getMaterial() == Material.ice) {
				w.setBlockToAir(x, y, z);
				w.setBlock(x, y, z, NyxBlocks.exousicWater, 14, 3);
				converted = true;
			} else if (bl instanceof BlockSnow) {
				w.setBlockToAir(x, y, z);
				w.setBlock(x, y, z, NyxBlocks.exousicWater, w.getBlockMetadata(x, y, z) / 2, 3);
				converted = true;
			} else if (bl.getMaterial() == Material.snow || bl.getMaterial() == Material.craftedSnow) {
				w.setBlockToAir(x, y, z);
				w.setBlock(x, y, z, NyxBlocks.exousicWater, 8, 3);
				converted = true;
			}
			if (converted) {
				is.stackSize -= 1;
				if (is.getItemDamage() >= 2)
					IaSPlayerHelper.giveItem(p, new ItemStack(NyxItems.exousium, 3, 1));
			}
			return converted;
		} else if (is.getItemDamage() >= 2 && w.getBlock(x, y, z) instanceof NyxBlockStone) {
			w.setBlock(x, y, z, NyxBlocks.oreExousium, 1, 0);
			is.stackSize -= 1;
			return true;
		}
		return super.onItemUse(is, p, w, x, y, z, dmg, a, b, c);
	}

	@Override
	public void onUpdate(ItemStack is, World w, Entity ent, int time, boolean holding) {
		if (ent instanceof EntityLivingBase) {
			final EntityLivingBase el = (EntityLivingBase) ent;
			if (el.getEquipmentInSlot(0) == null)
				return;
			if (el.getEquipmentInSlot(0).getItem() != this)
				return;
			else if (el.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
				return;
			else if (!el.isPotionActive(Potion.wither.id))
				el.addPotionEffect(new PotionEffect(Potion.wither.id, 41, 0));
		}
		super.onUpdate(is, w, ent, time, holding);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		this.rockIcon = new IIcon[2];
		this.crystalIcon = new IIcon[2];

		this.itemIcon = reg.registerIcon(getTexName() + "Dust");
		this.rockIcon[1] = reg.registerIcon(getTexName() + "Rock");
		this.crystalIcon[1] = reg.registerIcon(getTexName() + "Crystal");

		this.dustIconGlow = reg.registerIcon(getTexName() + "DustGlow");
		this.rockIcon[0] = reg.registerIcon(getTexName() + "RockGlow");
		this.crystalIcon[0] = reg.registerIcon(getTexName() + "CrystalGlow");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}
	
	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.EXOUSIUM;
	}

}
