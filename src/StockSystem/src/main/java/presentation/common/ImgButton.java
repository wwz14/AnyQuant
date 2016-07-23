package presentation.common;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImgButton extends JButton {

	
	private static final long serialVersionUID = 7430309266794094707L;
	ImageIcon normal;
	ImageIcon on;

	public ImgButton(ImageIcon normal,ImageIcon on) {
		this.normal =normal;
		this.on=on;
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		
		this.setIcon(normal);
		this.setRolloverIcon(on);
		this.setPressedIcon(on);
		this.setSize(normal.getIconWidth(), normal.getIconHeight());
	}
	
	public void setOn(){
		this.setIcon(this.on);
	}
	
	public void setNormal(){
		this.setIcon(this.normal);
	}
	
	
}
