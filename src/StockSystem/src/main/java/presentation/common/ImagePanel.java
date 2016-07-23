package presentation.common;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = -3355783529994238882L;
	private Image bgImage;

	public ImagePanel(Image image) {
		this.bgImage = image;
		this.setPreferredSize(new Dimension(bgImage.getWidth(this), bgImage.getHeight(this)));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0, this);
	}

}
