package presentation.graph.util;

import java.util.ArrayList;
import java.util.Iterator;

import vo.DataItem;
import vo.GraphDataVO;

/**  
* @ClassName: StandardGraphDataVO    
* @Description: 画图所用的标准数据单元
* @author zhuding    
*    
* @param <Var>    
*/
@SuppressWarnings("serial")
public 	class StandardGraphDataVO<Var> extends GraphDataVO<Var> {

	private ArrayList<DataItem> dataItems = new ArrayList<>();

	private Var var;

	public StandardGraphDataVO(Var var) {
		this.var = var;
	}

	public StandardGraphDataVO<Var> add(DataItem dataItem) {
		dataItems.add(dataItem);
		return this;
	}

	@Override
	public Iterator<DataItem> getDataItemIterator() {
		return dataItems.iterator();
	}

	@Override
	public Var getVar() {
		return var;
	}

}