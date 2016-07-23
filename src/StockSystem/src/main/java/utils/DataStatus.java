package utils;

public class DataStatus {
	private static boolean needRefresh = false;

	public static boolean isNeedRefresh() {
		return needRefresh;
	}

	public static void setNeedRefresh(boolean needRefresh) {
		DataStatus.needRefresh = needRefresh;
	}
	
}
