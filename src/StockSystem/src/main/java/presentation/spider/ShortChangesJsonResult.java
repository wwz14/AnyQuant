package presentation.spider;

import java.io.Serializable;
import java.util.ArrayList;

public class ShortChangesJsonResult  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4664630353729098932L;
	ArrayList<ShortChangesItem> arraylist;

	public ArrayList<ShortChangesItem> getArraylist() {
		return arraylist;
	}

	public void setArraylist(ArrayList<ShortChangesItem> arraylist) {
		this.arraylist = arraylist;
	}
	
}
