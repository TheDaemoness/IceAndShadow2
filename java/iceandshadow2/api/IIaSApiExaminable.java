package iceandshadow2.api;

import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * An interface for handlers of IaS's examination system. This can either be
 * implemented by Item or Block derivatives, or implemented by a handler and
 * registered with IaSRegistry. If an Item or Block has a handler, it will
 * always be run before any in IaSRegistry. Multiple handlers may be called for
 * one examination. Should certain changed knowledge conflict, the first change
 * is the one that will be recorded.
 */
public interface IIaSApiExaminable {

	/**
	 * Indicates whether the examination of a certain item or block is possible
	 * by this handler.
	 *
	 * @param toExam
	 *            The item stack being examined.
	 * @param knowledge
	 *            The knowledge currently recorded in the book or examination
	 *            table.
	 */
	public NBTTagCompound canExamine(ItemStack toExam, Set<String> knowledge);

	/**
	 * Returns a list of any knowledge that should be changed after examining
	 * the item, in addition to the knowledge gained from simply examining the
	 * item or block.
	 *
	 * @param toExam
	 *            The item stack being examined.
	 * @param knowledge
	 *            The knowledge currently recorded in the book or examination
	 *            table.
	 * @return A list of any CHANGED knowledge, including new knowledge. Null is
	 *         acceptable if no new knowledge was learned.
	 */
	public Set<String> getChangedKnowledge(ItemStack toExam, Set<String> knowledge);

	/**
	 * Gets information about this item to put into a book. If multiple handlers
	 * have book information, the first one registered is the only one called.
	 *
	 * @param toExam
	 *            The item stack being examined.
	 * @param knowledge
	 *            The knowledge currently recorded in the book or examination
	 *            table.
	 * @return An NBTTagCompound to be applied to a book, or null otherwise.
	 */
	public NBTTagCompound getExamineInfo(ItemStack toExam, Set<String> knowledge);

	/**
	 * Stops the execution of further relevant handlers. This should generally
	 * be false.
	 *
	 * @param toExam
	 *            The item stack being examined.
	 * @param knowledge
	 *            The knowledge currently recorded in the book or examination
	 *            table.
	 * @return A list of any CHANGED knowledge, including new knowledge. Null is
	 *         acceptable if no new knowledge was learned.
	 */
	public boolean stopFurtherExam(ItemStack toExam, Set<String> knowledge);
}