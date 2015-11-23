package iceandshadow2.nyx.entities.ai.senses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class IaSSetSenses extends IaSSense implements Set<IaSSense> {

	private final ArrayList<IaSSense> senses;

	public IaSSetSenses(EntityLivingBase elb) {
		super(elb, 0.0);
		this.senses = new ArrayList<IaSSense>();
	}

	@Override
	public boolean add(IaSSense sense) {
		this.dist = Math.max(this.dist, sense.getRange());
		return this.senses.add(sense);
	}

	@Override
	public boolean addAll(Collection<? extends IaSSense> c) {
		return this.senses.addAll(c);
	}

	@Override
	public boolean canSense(Entity ent) {
		for (final IaSSense s : this.senses) {
			if (s.canSense(ent))
				return true;
		}
		return false;
	}

	@Override
	public void clear() {
		this.senses.clear();
	}

	@Override
	public boolean contains(Object o) {
		return this.senses.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.senses.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<IaSSense> iterator() {
		return this.senses.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return this.senses.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.senses.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.senses.retainAll(c);
	}

	@Override
	public int size() {
		return this.senses.size();
	}

	@Override
	public Object[] toArray() {
		return this.senses.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.senses.toArray(a);
	}
}
