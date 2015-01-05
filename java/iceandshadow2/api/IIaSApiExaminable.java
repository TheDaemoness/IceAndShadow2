package iceandshadow2.api;

import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.items.materials.NyxMaterialEchir;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * An interface for handlers of IaS's examination system.
 * This can either be implemented by Item or Block derivatives, or implemented by a handler and registered with IaSRegistry.
 * If an Item or Block has a handler, it will always be run instead of any in IaSRegistry.
 */
public interface IIaSApiExaminable {
	/**
	 * Returns a list of messages to be printed to the player after examining an item.
	 * @param toExam The item stack being examined.
	 * @param knowledge The knowledge currently recorded in the examination table.
	 * @return A list of messages to tell the player. Null is acceptable.
	 */
	public List<String> getExamineMessages(ItemStack toExam, List<AssocPair<String, Integer>> knowledge);
	
	/**
	 * Returns a list of any knowledge that should be changed after examining the item.
	 * @param toExam The item stack being examined.
	 * @param knowledge The knowledge currently recorded in the examination table.
	 * @return A list of any CHANGED knowledge, including new knowledge. Null is acceptable if no new knowledge was learned.
	 */
	public List<AssocPair<String, Integer>> getChangedKnowledge(ItemStack toExam, List<AssocPair<String, Integer>> knowledge);
}