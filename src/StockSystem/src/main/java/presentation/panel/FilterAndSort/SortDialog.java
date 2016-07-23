package presentation.panel.FilterAndSort;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI.NormalColor;

import enums.ShowPanelType;
import enums.SortType;
import presentation.common.DragToMove;
import presentation.common.ImagePanel;
import presentation.common.ImgButton;
import presentation.main.MainController;
import presentation.ui.Images;

public class SortDialog extends JDialog {

	private static final long serialVersionUID = -6929879866972163399L;
	private SortType[] sortTypes;

	/**
	 * Create the panel.
	 */
	public SortDialog(Frame parent, ShowPanelType showPanelType) {
		// setModal(true);
		super(parent, true);
		setSize(400, 200);
		Point point = parent.getLocation();// 获得主窗体在屏幕的坐标
		setLocation(point.x + parent.getWidth() / 2 - this.getWidth() / 2,
				point.y + parent.getHeight() / 2 - this.getHeight() / 2);

		initComponent(showPanelType);

		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		// drag to move
		JComponent content = (JComponent) getContentPane();
		DragToMove.apply(new Component[] { content });

		// setOpacity(0.9f);
		setVisible(true);

	}

	private void initComponent(ShowPanelType showPanelType) {
		JPanel contentPanel = new ImagePanel(Images.sortBg);
		contentPanel.setLayout(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		// sortTypes = SortType.values();
		if (showPanelType == ShowPanelType.StockListPanel || showPanelType == ShowPanelType.MarketPanel) {
			sortTypes = new SortType[] { SortType.openAscend, SortType.openDown, SortType.closeAscend,
					SortType.closeDown, SortType.highAscend, SortType.highDown, SortType.lowAscend, SortType.lowDown,
					SortType.adj_priceAscent, SortType.adj_priceDown, SortType.volumeAscent, SortType.volumeDown,
					SortType.turnoverAscent, SortType.turnoverDown, SortType.peAscent, SortType.peDown,
					SortType.pbAscent, SortType.pbDown };
		} else if (showPanelType == ShowPanelType.StockPanel) {
			sortTypes = new SortType[] { SortType.dateAscent, SortType.dateDown, SortType.openAscend, SortType.openDown,
					SortType.closeAscend, SortType.closeDown, SortType.highAscend, SortType.highDown,
					SortType.lowAscend, SortType.lowDown, SortType.adj_priceAscent, SortType.adj_priceDown,
					SortType.volumeAscent, SortType.volumeDown, SortType.turnoverAscent, SortType.turnoverDown,
					SortType.peAscent, SortType.peDown, SortType.pbAscent, SortType.pbDown };
		} else if (showPanelType == ShowPanelType.BenchmarkPanel) {
			sortTypes = new SortType[] { SortType.dateAscent, SortType.dateDown, SortType.openAscend, SortType.openDown,
					SortType.closeAscend, SortType.closeDown, SortType.highAscend, SortType.highDown,
					SortType.lowAscend, SortType.lowDown, SortType.adj_priceAscent, SortType.adj_priceDown,
					SortType.volumeAscent, SortType.volumeDown };
		}
		JComboBox<?> comboBox = new JComboBox<Object>(sortTypes);
		// 皮肤包下选中后的JComboBox 为蓝色，以下方法更改为白色
		Component component = comboBox.getEditor().getEditorComponent();
		if (component instanceof JTextField) {
			JTextField field = (JTextField) component;

			field.setEditable(false);

			field.setSelectionColor(
					field.getBackground()/* java.awt.Color.WHITE */);

			comboBox.setEditable(true);
		}
		comboBox.setBounds(103, 93, 196, 31);
		contentPanel.add(comboBox);

		JButton btnCancel = new ImgButton(Images.cancelBt_normal, Images.cancelBt_on);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SortDialog.this.dispose();
			}
		});
		btnCancel.setBounds(92, 145, 100, 29);
		contentPanel.add(btnCancel);

		JButton btnDone = new ImgButton(Images.doneBt_normal, Images.doneBt_on);
		btnDone.setForeground(Color.white);
		btnDone.setUI(new BEButtonUI().setNormalColor(NormalColor.red));
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SortType sortType = (SortType) comboBox.getSelectedItem();
				MainController.sortDatas(sortType);
				SortDialog.this.dispose();
			}
		});
		btnDone.setBounds(214, 145, 100, 29);
		contentPanel.add(btnDone);

	}

}
