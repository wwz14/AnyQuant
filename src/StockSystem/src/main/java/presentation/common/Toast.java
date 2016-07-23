package presentation.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;

import presentation.loadingUI.Gif;
import presentation.showPanel.ShowPanel;
import presentation.ui.UIConfig;

public class Toast extends JDialog {

	private static final long serialVersionUID = 6550086205928604559L;
	public static final int MESSEGE = 0;
	public static final int WARING = 1;
	public static final int ERROR = 2;

	JLabel lb;
	int delay;

	/**
	 * @param parent
	 *            所在的父frame
	 * @param delay
	 *            toast显示的时间,如果是-1则永久显示
	 * @param message
	 *            显示的消息
	 */
	public Toast(Frame parent, int delay, String message, int toastType) {

		// 设置modal为false才能跑后面的frame
		super(parent, false);
		this.delay = delay;
		setSize(400, 70);
		this.setLocationRelativeTo(parent);
		this.setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setAlwaysOnTop(true);
		
		
		
		
		
		// drag to move
		JComponent content = (JComponent) getContentPane();
		DragToMove.apply(new Component[] { content });

		JPanel toastPanel = new JPanel();
		toastPanel.setLayout(null);
		switch (toastType) {
		case MESSEGE:
			toastPanel.setBackground(UIConfig.MarketBar); // green
			break;
		case WARING:
			toastPanel.setBackground(UIConfig.LineBt); // orange
			break;
		case ERROR:
			toastPanel.setBackground(UIConfig.BenchmarkBar); // red
			break;

		default:
			break;
		}

	
		

		lb = new JLabel(message);
		lb.setForeground(Color.WHITE);
		lb.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lb.setHorizontalAlignment(SwingConstants.CENTER);
		lb.setBounds(0, 10, 400, 50);
		toastPanel.add(lb);
		
		getContentPane().add(toastPanel);
		this.setVisible(true);

		if (delay > 0) {
			disposeThread();
		}
	}

	public void disposeThread() {
		new DisposeThread().start();
	}

	protected float hyalineValue = 1f;

	public class DisposeThread extends Thread {
		@Override
		public void run() {

			try {
				if (delay > 0) {
					Thread.sleep(delay);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (true) {
				try {
					Thread.sleep(50); // 1/0.05*50 =1000ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				hyalineValue -= 0.05f;
				if (hyalineValue < 0) {
					hyalineValue = 0;
					Toast.this.dispose();
				}
				setOpacity(hyalineValue);

			}
		}
	}

	

}
