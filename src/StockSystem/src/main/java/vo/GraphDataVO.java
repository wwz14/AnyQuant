package vo;

import java.io.Serializable;
import java.util.Iterator;

public abstract class GraphDataVO<Var> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1269062895336631243L;

	public abstract Iterator<DataItem> getDataItemIterator();
	
	public abstract Var getVar();
	
}
