package iceandshadow2.api;

import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * An interface for handlers of IaS's examination system. This can either be
 * implemented by Item or Block derivatives, or implemented by a handler and
 * registered with IaSRegistry. If an Item or Block has a handler, it will
 * always be run before any in IaSRegistry. Multiple handlers may be called for
 * one examination if the block or item doesn't implement this interface. Should
 * certain changed knowledge conflict, the first change is the one that will be
 * recorded.
 */
public interface IIaSApiExaminable {

	/**
	 * Returns a list of messages to be printed to the player after examining an
	 * item.
	 * 
	 * @param toExam
	 *            The item stack being examined.
	 * @param knowledge
	 *            The knowledge currently recorded in the examination table.
	 * @return A list of messages to tell the player. Null is acceptable.
	 */
	public List<String> getExamineMessages(ItemStack toExam,
			Map<String, Integer> knowledge);
	

	/**
	 * Gets information about this item to put into a book. If multiple handlers
	 * have book information, the first one registered is the only one called.
	 * 
	 * @param toExam
	 *            The item stack being examined.
	 * @param knowledge
	 *            The knowledge currently recorded in the examination table.
	 * @return An NBTTagCompound to be applied to a book, or null otherwise.
	 */
	public NBTTagCompound getBookInfo(ItemStack toExam,
			Map<String, Integer> knowledge);

	/**
	 * Returns a list of any knowledge that should be changed after examining
	 * the item.
	 * 
	 * @param toExam
	 *            The item stack being examined.
	 * @param knowledge
	 *            The knowledge currently recorded in the examination table.
	 * @return A list of any CHANGED knowledge, including new knowledge. Null is
	 *         acceptable if no new knowledge was learned.
	 */
	public Map<String, Integer> getChangedKnowledge(ItemStack toExam,
			Map<String, Integer> knowledge);

	/**
	 * Returns a list of any knowledge that should be changed after writing a
	 * book on the item. Used for implementing lore documents. Applied after the
	 * normal examination changed knowledge, overriding it.
	 * 
	 * @param toExam
	 *            The item stack being examined.
	 * @param knowledge
	 *            The knowledge currently recorded in the examination table.
	 * @return True if a book needs to be written for the knowledge in the table
	 *         to change, false if the item merely needs to be examined.
	 */
	public Map<String, Integer> getChangedKnowledgeOnBook(ItemStack toExam,
			Map<String, Integer> knowledge);
}