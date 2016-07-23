package data.utils;

import java.util.regex.Pattern;

import po.ResultListPO;

public class SearchUtils {
	public static ResultListPO search(ResultListPO resultListPO,String searchstr){

		String trueExpression = ".*"+searchstr+".*";
		for(int i = 0;i<resultListPO.getResultPOs().size();i++){
			String name = resultListPO.getData().get(i).getName();
		if(!Pattern.matches(trueExpression, name)){
			//如果不包含关键字，就把此条信息从list中删除
			resultListPO.getResultPOs().remove(i);
			resultListPO.getData().remove(i);
		}
		}
		return resultListPO;
	}
	
}

