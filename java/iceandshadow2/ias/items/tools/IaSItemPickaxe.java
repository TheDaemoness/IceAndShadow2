package iceandshadow2.ias.items.tools;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Multimap;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.interfaces.IIaSModName;
import iceandshadow2.util.EnumIaSModule;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class IaSItemPickaxe extends ItemPickaxe implements IIaSModName {
	
	public IaSItemPickaxe() {
		super(ToolMaterial.IRON);
		this.setUnlocalizedName("iasToolPickaxe");
	}
	
	@Override
	public boolean getHasSubtypes() {
		return true;
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
	public boolean hitEntity(ItemStack is, EntityLivingBase user,
			EntityLivingBase target) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return false;
		if(user instanceof EntityPlayer)
			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)user), m.getToolDamage(is, user, target, false));
		else
			target.attackEntityFrom(DamageSource.causeMobDamage(user), m.getToolDamage(is, user, target, false));
		return true;
	}
	
	/**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
	@Override
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        //multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double)this.damageVsEntity, 0));
        return multimap;
    }

	@Override
	public boolean onBlockDestroyed(ItemStack is, World w,
			Block bl, int x, int y,
			int z, EntityLivingBase user) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return false;
		m.onHarvest(is, user, w, x, y, z);
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
	public int getHarvestLevel(ItemStack is, String toolClass) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return 0;
		return m.getHarvestLevel(is, toolClass);
	}



	@Override
	public float getDigSpeed(ItemStack is, Block block, int meta) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return 0;
		return m.getHarvestSpeed(is, block, meta, false);
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
		return m.getDurability(is, false);
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return EnumIaSModule.IAS;
	}

}