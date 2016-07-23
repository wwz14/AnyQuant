package presentation.common;

import java.awt.Graphics;

import javax.swing.JLabel;

import presentation.ui.Images;

public class SortJLable extends JLabel {

	private static final long serialVersionUID = 3729059070070926102L;
	public SortJLable(String string, int center) {
		super(string, center);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Images.ExitBt.getImage(), 0, 0, this);
	}

}
