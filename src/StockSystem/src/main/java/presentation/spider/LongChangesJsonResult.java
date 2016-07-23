package presentation.spider;

import java.io.Serializable;
import java.util.ArrayList;

public class LongChangesJsonResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5123884727205058484L;
	ArrayList<LongChangesItem> arraylist;

	public ArrayList<LongChangesItem> getArraylist() {
		return arraylist;
	}

	public void setArraylist(ArrayList<LongChangesItem> arraylist) {
		this.arraylist = arraylist;
	}
}
