package iceandshadow2.boilerplate;

public interface IRegistry<T extends IRegisterable> {
	public T get(int id);

	public int register(T obj);
}
