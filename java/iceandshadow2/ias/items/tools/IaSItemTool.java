package iceandshadow2.ias.items.tools;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Multimap;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IIaSTool;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.interfaces.IIaSModName;
import iceandshadow2.util.EnumIaSModule;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class IaSItemTool extends ItemTool implements IIaSModName, IIaSTool {

	private EnumIaSToolClass classe;
	
	public IaSItemTool(EnumIaSToolClass cl) {
		super(cl.getBaseDamage(), ToolMaterial.EMERALD, new HashSet<Material>());
		this.setUnlocalizedName("iasTool");
		classe = cl;
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
		//See IaSRegistry.
	}

	@Override
	public boolean getHasSubtypes() {
		return true;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(stack);
		return m.onSwing(stack, entityLiving);
	}
	
	@Override
	public float func_150893_a(ItemStack is, Block block) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		return m.getHarvestSpeed(is, block);
	}

	@Override
	public boolean canHarvestBlock(Block bl, ItemStack is) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		Set<String> s = this.getToolClasses(is);
		if(!s.contains(bl.getHarvestTool(0)))
			return false;
		return bl.getHarvestLevel(0) <= m.getHarvestLevel(is, bl.getHarvestTool(0));
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		return ((IaSItemTool)(stack.getItem())).getIaSToolClass().getToolClassSet();
    }
	
	@Override
	public int getHarvestLevel(ItemStack is, String toolClass) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		return m.getHarvestLevel(is, toolClass);
	}

	@Override
	public void getSubItems(Item it, CreativeTabs ct,
			List l) {
		Collection<IaSToolMaterial> mats = IaSRegistry.getToolMaterials();
		l.add(new ItemStack(this));
		for(IaSToolMaterial m : mats) {
			ItemStack is;
			is = new ItemStack(this);
			is.setTagCompound(new NBTTagCompound());
			is.getTagCompound().setString("iasMaterial", m.getMaterialName());
			l.add(is.copy());
		}
	}

	@Override
	public boolean onLeftClickEntity(ItemStack is, EntityPlayer user,
			Entity target) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		is.damageItem(m.onAttack(is, user, target), user);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack is, World w,
			Block bl, int x, int y,
			int z, EntityLivingBase user) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return false;
		is.damageItem(m.onHarvest(is, user, w, x, y, z), user);
		return true;
	}

	@Override
	public int getItemEnchantability() {
		return 16;
	}

	@Override
	public String getToolMaterialName() {
		return "Wabbagoogies";
	}

	@Override
	public boolean getIsRepairable(ItemStack is, ItemStack two) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return false;
		return m.isRepairable(is, two);
	}

	@Override
	public float getDigSpeed(ItemStack is, Block block, int meta) {
		return func_150893_a(is, block); //meta sensitivity is pointless ATM, I wish it wasn't.
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	@Deprecated
	public String getTexName() {
		return null;
	}


	@Override
	public String getUnlocalizedName(ItemStack is) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return null;
		return m.getUnlocalizedName(is);
	}

	@Override
	public IIcon getIconIndex(ItemStack is) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return null;
		return m.getIcon(is);
	}

	@Override
	public IIcon getIcon(ItemStack is, int renderPass, EntityPlayer player,
			ItemStack usingItem, int useRemaining) {
		return getIconIndex(is);
	}

	@Override
	public int getMaxDamage(ItemStack is) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return 0;
		return m.getDurability(is);
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return EnumIaSModule.IAS;
	}
	
	@Override
	public EnumIaSToolClass getIaSToolClass() {
		return classe;
	}

}