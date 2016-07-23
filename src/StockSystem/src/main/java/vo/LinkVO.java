package vo;

import java.io.Serializable;

public class LinkVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1174539855143596233L;
	private String link;
	private String name;
	
	public LinkVO(String link, String name) {
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
}
