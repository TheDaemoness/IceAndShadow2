package iceandshadow2.api;

import iceandshadow2.nyx.items.materials.NyxMaterialEchir;
import iceandshadow2.util.IaSPlayerHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class IaSRegistry {
	
	private static IaSToolMaterial defaultMaterial = new NyxMaterialEchir();
	private static HashMap<String,IaSToolMaterial> toolMaterials = new HashMap<String,IaSToolMaterial>();
	private static ArrayList<IIaSApiDistillable> handlersDistillable = new ArrayList<IIaSApiDistillable>();
	private static ArrayList<IIaSApiExaminable> handlersExaminable = new ArrayList<IIaSApiExaminable>();
	private static ArrayList<IIaSApiTransmutable> handlersTransmutable = new ArrayList<IIaSApiTransmutable>();
	private static ArrayList<IIaSApiSacrificeXp> handlersSacrificeXp = new ArrayList<IIaSApiSacrificeXp>();
	
	public static void addToolMaterial(IaSToolMaterial mat) {
		if(toolMaterials.containsKey(mat.getMaterialName()))
			throw new IllegalArgumentException("Material '" + mat.getMaterialName() + "' already exists!");
		toolMaterials.put(mat.getMaterialName(), mat);
	}
	
	public static IaSToolMaterial getToolMaterial(String key) {
		if(key == null)
			return getDefaultMaterial();
		if(toolMaterials.containsKey(key))
			return toolMaterials.get(key);
		return getDefaultMaterial();
	}
	
	public static Collection<IaSToolMaterial> getToolMaterials() {
		return toolMaterials.values();
	}

	public static IaSToolMaterial getDefaultMaterial() {
		return defaultMaterial;
	}
	
	public static void addHandler(IIaSApiDistillable handler) {
		handlersDistillable.add(handler);
	}
	public static void addHandler(IIaSApiExaminable handler) {
		handlersExaminable.add(handler);
	}
	public static void addHandler(IIaSApiTransmutable handler) {
		handlersTransmutable.add(handler);
	}
	public static void addHandler(IIaSApiSacrificeXp handler) {
		handlersSacrificeXp.add(handler);
	}
	
	public static IIaSApiTransmutable getHandlerTransmutation(ItemStack target, ItemStack catalyst) {
		if(target == null || catalyst == null)
			return null;
		Object obj;
		IIaSApiTransmutable trans;
		
		obj = target.getItem();
		if(obj instanceof ItemBlock)
			obj = ((ItemBlock)obj).field_150939_a;
		if(obj instanceof IIaSApiTransmutable) {
			trans = (IIaSApiTransmutable)obj;
			if(trans.getTransmutationTime(target, catalyst) > 0)
				return trans;
		}
		
		obj = catalyst.getItem();
		if(obj instanceof ItemBlock)
			obj = ((ItemBlock)obj).field_150939_a;
		if(obj instanceof IIaSApiTransmutable) {
			trans = (IIaSApiTransmutable)obj;
			if(trans.getTransmutationTime(target, catalyst) > 0)
				return trans;
		}
		
		for(int i = 0; i < handlersTransmutable.size(); ++i) {
			if(handlersTransmutable.get(i).getTransmutationTime(target, catalyst) > 0)
				return handlersTransmutable.get(i);
		}
		return null;
	}
	
	public static IIaSApiDistillable getHandlerDistillation(ItemStack target) {
		if(target == null)
			return null;
		Object obj;
		IIaSApiDistillable dist;
		
		obj = target.getItem();
		if(obj instanceof ItemBlock)
			obj = ((ItemBlock)obj).field_150939_a;
		if(obj instanceof IIaSApiDistillable) {
			dist = (IIaSApiDistillable)obj;
			if(dist.getBaseRate(target) > 0)
				return dist;
		}
		
		for(int i = 0; i < handlersDistillable.size(); ++i) {
			if(handlersDistillable.get(i).getBaseRate(target) > 0)
				return handlersDistillable.get(i);
		}
		return null;
	}
	
	public static int getSacrificeXpYield(ItemStack target) {
		if(target == null)
			return 0;
		Object obj;
		IIaSApiSacrificeXp sac;
		Random r = new Random();
		
		obj = target.getItem();
		if(obj instanceof ItemBlock)
			obj = ((ItemBlock)obj).field_150939_a;
		if(obj instanceof IIaSApiSacrificeXp) {
			sac = (IIaSApiSacrificeXp)obj;
			return Math.max(0,sac.getXpValue(target, r));
		}
		
		int sum = 0;
		for(IIaSApiSacrificeXp xp : handlersSacrificeXp) {
			sum = xp.getXpValue(target, r);
			if(sum > 0)
				return sum;
			else if (sum < 0)
				sum = 0;
		}
		return 0;
	}
	
	public static Map<String,Integer> handleExamination(EntityPlayer checker, Map<String,Integer> knowledge) {
		if(checker.getEquipmentInSlot(0) == null)
			return null;
		
		ItemStack is = checker.getEquipmentInSlot(0);
		Object obj = is.getItem();
		if(obj instanceof ItemBlock)
			obj = ((ItemBlock)obj).field_150939_a;
		Map<String,Integer> tempKno = null, changeKno = new TreeMap<String,Integer>();
		List<String> strs;
		
		if(obj instanceof IIaSApiExaminable) {
			IIaSApiExaminable ex = (IIaSApiExaminable)obj;
			strs = ex.getExamineMessages(is, knowledge);
			tempKno = ex.getChangedKnowledge(is, knowledge);
			if(strs != null) {
				for(String str : strs)
					IaSPlayerHelper.messagePlayer(checker, str);
			}
			if(tempKno != null) {
				for(String key : tempKno.keySet())
					changeKno.put(key, tempKno.get(key));
			}
		}
		
		for(int i = 0; i < handlersExaminable.size(); ++i) {
			strs = handlersExaminable.get(i).getExamineMessages(is, knowledge);
			tempKno = handlersExaminable.get(i).getChangedKnowledge(is, knowledge);
			if(strs != null) {
				for(String str : strs)
					IaSPlayerHelper.messagePlayer(checker, str);
			}
			if(tempKno != null) {
				for(String key : tempKno.keySet()) {
					if(!changeKno.containsKey(key))
						changeKno.put(key, tempKno.get(key));
				}
			}
		}
		return changeKno;
	}
	public static Map<String,Integer> handleExaminationBook(EntityPlayer checker, int x, int y, int z, Map<String,Integer> knowledge) {
		if(checker.getEquipmentInSlot(0) == null)
			return null;
		
		ItemStack is = checker.getEquipmentInSlot(0);
		Object obj = is.getItem();
		if(obj instanceof ItemBlock)
			obj = ((ItemBlock)obj).field_150939_a;
		IIaSApiExaminable ex = null;
		NBTTagCompound nbt = null;
		Map<String,Integer> changeKno = null;
		
		if(obj instanceof IIaSApiExaminable) {
			ex = (IIaSApiExaminable)obj;
			nbt = ex.getBookInfo(is, knowledge);
		}
		
		for(int i = 0; (nbt == null || nbt.hasNoTags()) && i < handlersExaminable.size(); ++i) {
			ex = handlersExaminable.get(i);
			nbt = ex.getBookInfo(is, knowledge);
		}
		
		if(nbt != null && !nbt.hasNoTags()) {
			if(checker.inventory.consumeInventoryItem(Items.book)) {
				ItemStack booq = new ItemStack(Items.written_book);
				booq.setTagCompound(nbt);
				if(!checker.worldObj.isRemote) {
					EntityItem ite = new EntityItem(checker.worldObj, 0.5+x, 1.2+y, 0.5+z, booq);
					checker.worldObj.spawnEntityInWorld(ite);
				}
				return ex.getChangedKnowledgeOnBook(is, knowledge);
			} else
				IaSPlayerHelper.alertPlayer(checker, "There's more information, but you'll need a plain book to write it down.");
		}
		return null;
	}
}
