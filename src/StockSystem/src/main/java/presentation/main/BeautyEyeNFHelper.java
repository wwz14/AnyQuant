package presentation.main;

import java.awt.Font;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

/**
 * @Description: 苹果风格
 * 
 * @version V1.0
 */
public class BeautyEyeNFHelper {

	/**
	 * 更改外观
	 */
	public static void changeLook() {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
			// 关闭右上角设置
			UIManager.put("RootPane.setupButtonVisible", false);
			// 设置jtabbedpanel左缩进更小
			UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(3, 2, 2, 2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更改默认字体
	 */
	public static void changeFont() {
		Font font = new Font("微软雅黑", Font.PLAIN, 12);
		@SuppressWarnings("rawtypes")
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}

	}
}
