package po;

import java.io.Serializable;

import vo.LinkVO;

public class LinkPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7014406224172699553L;
	private String link;
	private String name;
	
	
	
	
	public LinkPO(String link, String name) {
		super();
		this.link = link;
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public LinkVO toVO() {
		return new LinkVO(this.link, this.name);
	}
	
}
