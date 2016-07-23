package presentation.common;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

/**
 * 可以下拉加载数据的滚动条
 */
public class NTScrollPane extends JScrollPane {

	private static final long serialVersionUID = -574830850091806938L;
	private BaseDataLoader dataLoader;
	private boolean enable = true;

	public NTScrollPane() {
		
		this.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if (enable) {
					if (dataLoader != null) {
						JScrollBar bar = (JScrollBar) e.getSource();
						int max = bar.getMaximum();
						int extent = bar.getModel().getExtent();
						int value = bar.getValue();
						// 判断滚动条是否拉到底部
						if ((max - (value + extent)) < 1) {
							// 设置鼠标在滚动面板上的光标为等待状态
							NTScrollPane.this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
							new Task().execute();
						}
					}
				}
			}
		});
	}


	/**
	 * 设置一个数据的加载器
	 * 
	 * @param dataLoader
	 */
	public void setDataLoader(BaseDataLoader dataLoader) {
		this.dataLoader = dataLoader;
	}

	/**
	 * 任务类，用来加载数据
	 */
	private class Task extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			enable = false;
			dataLoader.nextBatch();
			return null;
		}

		@Override
		protected void done() {
			// 设置滚动面板设置上鼠标设置为默认光标
			NTScrollPane.this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			enable = true;
		}
		
	}
}