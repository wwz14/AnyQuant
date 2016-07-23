package presentation.graph.echarts;

import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**  
* @ClassName: GraphPanel    
* @Description: 包含echarts图表的panel,继承JComponent
* @author zhuding    
*        
*/
@SuppressWarnings("serial")
public class GraphPanel extends JFXPanel {
	
	private WebEngine engine;

	private String url;
	
	private EChartsPainter eChartsPainter;

	public GraphPanel(String url) {
		this.url = url;
		createScene();
		loadURL();
	}
	
	public GraphPanel(EChartsPainter eChartsPainter) {
		this.eChartsPainter = eChartsPainter;
		createScene();
	}
	
	private void createScene() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				WebView view = new WebView();
				engine = view.getEngine();

				GraphPanel.this.setScene(new Scene(view));
			}
		});
	}

	public void loadURL() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String tmp = toURL(url);

				if (tmp == null) {
					tmp = toURL("http://" + url);
				}

				engine.load(url);
			}
		});
	}

	private static String toURL(String str) {
		try {
			return new URL(str).toExternalForm();
		} catch (MalformedURLException exception) {
			return null;
		}
	}
	
	@Override
	public void setSize(int width, int height) {
		// TODO Auto-generated method stub
		super.setSize(width, height);
		if (eChartsPainter == null) {
			return;
		}
		url = eChartsPainter.getURL(width, height);
		loadURL();
	}
	
	@Override
	public void setSize(Dimension d) {
		// TODO Auto-generated method stub
		super.setSize(d);
		if (eChartsPainter == null) {
			return;
		}
		url = eChartsPainter.getURL(d.width, d.height);
		loadURL();
	}
	
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		// TODO Auto-generated method stub
		super.setPreferredSize(preferredSize);
		if (eChartsPainter == null) {
			return;
		}
		url = eChartsPainter.getURL(preferredSize.width, preferredSize.height);
		loadURL();
	}


}
