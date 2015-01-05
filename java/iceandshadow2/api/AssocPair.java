package iceandshadow2.api;

import org.objectweb.asm.tree.analysis.Value;

/**
 * A pair class designed to associate an object (the key) with a certain value.
 * The pair is designed so that the value is mutable, but the key is not.
 */
public class AssocPair<KeyType, ValueType> {
	private KeyType key;
	private ValueType val;
	
	public AssocPair(KeyType key) {
		this(key, null);
	}
	public AssocPair(KeyType key, ValueType value) {
		this.key = key;
		this.val = value;
	}
	public AssocPair(AssocPair<KeyType, ValueType> old) {
		this.key = old.getKey();
		this.val = old.getValue();
	}
	public AssocPair(AssocPair<KeyType, Object> old, ValueType newValue) {
		this.key = old.getKey();
		this.val = newValue;
	}
	
	public KeyType getKey() {
		return key;
	}
	public ValueType getValue() {
		return val;
	}
	
	public void setValue(ValueType newValue) {
		val = newValue;
	}
	
	@Override
	public int hashCode() {
		return key.hashCode() ^ val.hashCode();
	}
	
	@Override
	public boolean equals(Object cmp) {
		if(cmp instanceof AssocPair) {
			AssocPair pear = (AssocPair)cmp;
			return key.equals(pear.getKey()) && val.equals(pear.getValue());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return key.toString() + ": " + val.toString();
	}
}
