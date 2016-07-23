package utils;

import java.io.InputStream;
import java.net.URL;

import presentation.common.Toast;
import presentation.main.MainController;
import presentation.ui.Images;

public class NetTester extends Thread {
	boolean isFirstShowNotConnected = true;

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while (true) {
			NetStatus.isConnected = isConnected();
			
			try {
				this.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isConnected() {
		URL url = null;
		boolean result = false;
		try {
			url = new URL("http://baidu.com");

			InputStream in = url.openStream();
			in.close();
			isFirstShowNotConnected = true;
			if (null != MainController.frame) {
				MainController.changeLeftPanelBg(Images.LeftBg_Online);
			}
			result = true;

		} catch (Exception e) {
			System.out.println("网络连接失败！");
			if (null != MainController.frame && isFirstShowNotConnected) {
				new Toast(MainController.frame, 1000, "网络未连接...", Toast.ERROR);
				isFirstShowNotConnected = false;
				MainController.changeLeftPanelBg(Images.LeftBg_Offline);
			}
		}
		return result;
	}

}

// @SuppressWarnings("serial")
// class Dialog extends JDialog{
//
// String message;
//
// public Dialog(String message) {
// super();
// this.message = message;
// this.setBounds(300, 200, 200, 150);
// initComponents(message);
// this.setVisible(true);
// }
//
// private void initComponents(String text) {
// JPanel container = new JPanel();
// container.setBounds(0, 0, 200, 100);
// container.setLayout(null);
//
// JLabel warning = new JLabel(text, JLabel.CENTER);
// warning.setBounds(0, 0, 200, 100);
// container.add(warning);
//
// this.setContentPane(container);
// }
//
// }
