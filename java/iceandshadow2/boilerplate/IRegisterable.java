package iceandshadow2.boilerplate;

public interface IRegisterable {
	public int getID();

	public IRegistry getRegistry();

	public void setRegister(IRegistry reg, int id);
}
