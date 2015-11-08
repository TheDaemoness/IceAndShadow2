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
		senses = new ArrayList<IaSSense>();
	}

	@Override
	public boolean add(IaSSense sense) {
		dist = Math.max(dist, sense.getRange());
		return senses.add(sense);
	}

	@Override
	public boolean addAll(Collection<? extends IaSSense> c) {
		return senses.addAll(c);
	}

	@Override
	public boolean canSense(Entity ent) {
		for (final IaSSense s : senses) {
			if (s.canSense(ent))
				return true;
		}
		return false;
	}

	@Override
	public void clear() {
		senses.clear();
	}

	@Override
	public boolean contains(Object o) {
		return senses.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return senses.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<IaSSense> iterator() {
		return senses.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return senses.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return senses.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return senses.retainAll(c);
	}

	@Override
	public int size() {
		return senses.size();
	}

	@Override
	public Object[] toArray() {
		return senses.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return senses.toArray(a);
	}
}
