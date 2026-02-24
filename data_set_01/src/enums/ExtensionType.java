/**
 * 
 */
package enums;

import ta172.Var;

/**
 * @author ilia
 *
 */
public class ExtensionType {
	public static final ExtensionType DURATION   = new ExtensionType(Var.TYPE_DURATION);
	public static final ExtensionType ABSOLUTE   = new ExtensionType(Var.TYPE_ABSOLUTE);
	public static final ExtensionType COMPLEMENT = new ExtensionType(Var.TYPE_COMPLEMENT);
	
	private final int id;
	
	ExtensionType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
}
