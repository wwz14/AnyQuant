package presentation.showPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;

import presentation.common.DragToMove;
import presentation.common.ImagePanel;
import presentation.common.ImgButton;
import presentation.common.Toast;
import presentation.main.MainController;
import presentation.panel.FilterAndSort.SortDialog;
import presentation.ui.Images;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI.NormalColor;

import enums.SortType;
import exception.StatusNotOKException;
import logic.stockShowInfobl.AddorRemoveStockLogic;
import logicservice.showInfoblservice.AddorRemoveLogicservice;

import javax.swing.JButton;

public class AddStockDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5763987114367036280L;
	private JTextField textField;
	AddorRemoveLogicservice addorRemoveLogicservice = new AddorRemoveStockLogic();

	public AddStockDialog(Frame parent) {
		super(parent, true);
		setSize(400, 200);
		Point point = parent.getLocation();// 获得主窗体在屏幕的坐标
		setLocation(point.x + parent.getWidth() / 2 - this.getWidth() / 2,
				point.y + parent.getHeight() / 2 - this.getHeight() / 2);

		// GUI

		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		// drag to move
		JComponent content = (JComponent) getContentPane();
		DragToMove.apply(new Component[] { content });

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel label = new JLabel("添加股票：");
		label.setBounds(86, 52, 68, 18);
		contentPanel.add(label);

		textField = new JTextField();
		textField.setBounds(164, 47, 156, 30);
		contentPanel.add(textField);
		textField.setColumns(10);

		JButton btnDone = new ImgButton(Images.doneBt_normal, Images.doneBt_on);
		btnDone.setForeground(Color.white);
		btnDone.setUI(new BEButtonUI().setNormalColor(NormalColor.red));
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("add stock to database");
				try {
					addorRemoveLogicservice.addStock(textField.getText().trim());
				} catch (Exception e1) {
					new Toast(MainController.frame, 2000, "添加股票失败...", Toast.ERROR);
					e1.printStackTrace();
				}
				new Toast(MainController.frame, 2000, "添加股票成功...", Toast.MESSEGE);
			}
		});
		btnDone.setBounds(102, 118, 100, 29);
		contentPanel.add(btnDone);

		JButton btnCancel = new ImgButton(Images.cancelBt_normal, Images.cancelBt_on);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddStockDialog.this.dispose();
			}
		});
		btnCancel.setBounds(209, 118, 100, 29);
		contentPanel.add(btnCancel);

		setVisible(true);
	}
}
