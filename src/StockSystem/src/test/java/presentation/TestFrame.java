package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicToolTipUI;

import presentation.common.Toast;
import presentation.ui.Images;

@SuppressWarnings("serial")
public class TestFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnToast = new JButton("Toast");
		btnToast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Toast(TestFrame.this, 3000, "toast", Toast.MESSEGE);
			}
		});
		btnToast.setBounds(151, 81, 93, 23);
		contentPane.add(btnToast);

		JButton btnTip = new JButton("Tip") {
			@Override
			public JToolTip createToolTip() {
				MyToolTip tip = new MyToolTip();
				return tip;
			}
		};
		btnTip.setToolTipText("aaa");
		btnTip.setBounds(151, 147, 93, 23);
		contentPane.add(btnTip);
	}

	public class MyToolTip extends JToolTip {
		public MyToolTip() {
			setSize(1030, 70);
			setBorder(null);
			setUI(new MyToolTipUI());
		};

		private class MyToolTipUI extends BasicToolTipUI {

			@Override
			public void paint(Graphics g, JComponent c) {
				g.drawImage(Images.TOPBAR1, 0, 0, 1030, 70, Color.gray, null);
			}

			@Override
			public Dimension getPreferredSize(JComponent c) {
				return new Dimension(1030, 70);
			}

		}

	}
}
