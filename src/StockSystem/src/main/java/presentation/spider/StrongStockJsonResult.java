package presentation.spider;

import java.io.Serializable;
import java.util.ArrayList;

public class StrongStockJsonResult implements Serializable{
	
	private static final long serialVersionUID = -4229362583711123829L;
	ArrayList<StrongStockItem> arraylist;

	public ArrayList<StrongStockItem> getArraylist() {
		return arraylist;
	}

	public void setArraylist(ArrayList<StrongStockItem> arraylist) {
		this.arraylist = arraylist;
	}
	
	
	
	
} 
