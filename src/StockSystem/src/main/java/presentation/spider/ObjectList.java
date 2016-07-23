package presentation.spider;

import java.io.Serializable;

public class ObjectList  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8155103972397481469L;
	SecurityRisk securityRisk;
	
	
	@Override
	public String toString() {
		return securityRisk.toString();
	}


	public SecurityRisk getSecurityRisk() {
		return securityRisk;
	}


	public void setSecurityRisk(SecurityRisk securityRisk) {
		this.securityRisk = securityRisk;
	}
}
