package utils;
/**
 * @date2016/05/08
 * @author wwz
 *返回信息类
 */
public class ResultMsg {
	/**
	 * 状态是否正确，true，object为返回对象，false，object为null，存取object前应该线设置或判断isok状态
	 */
	boolean isok;
	/**
	 * 纪录状态具体信息
	 */
	String message;
	/**
	 * 返回的对象
	 */
	Object result;
	
	public ResultMsg(boolean isok, String message, Object result) {
		super();
		this.isok = isok;
		this.message = message;
		this.result = result;
	}
	public ResultMsg() {
		super();
	}
	public boolean isIsok() {
		return isok;
	}

	public void setIsok(boolean isok) {
		this.isok = isok;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
		

}
