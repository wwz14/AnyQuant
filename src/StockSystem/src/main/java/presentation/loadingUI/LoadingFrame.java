package presentation.loadingUI;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;


@SuppressWarnings("serial")
public class LoadingFrame extends JFrame{
	
	private static LoadingFrame instance;
	
	public static void hideLoadingFrame() {
		instance.setVisible(false);
	}
	
	public static void showLoadingFrame() {
		instance = new LoadingFrame();
		instance.setVisible(true);
	}
	
    
    public LoadingFrame(){
        super();
        
        String name = "images/loading.gif";
        ImageIcon icon = new ImageIcon(name);
        File gifFile = new File(name);
        
        this.setSize(icon.getIconWidth(),icon.getIconHeight());
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth())/2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight())/2);
        this.setUndecorated(true);
        this.getContentPane().add(new Gif(gifFile, 5));
        this.addMouseListener(new MouseEventListener(this));
        this.addMouseMotionListener(new MouseEventListener(this));
    }

    class MouseEventListener implements MouseInputListener {
        
        Point origin;
        //鼠标拖拽想要移动的目标组件
        JFrame frame;
        
        public MouseEventListener(JFrame frame) {
          this.frame = frame;
          origin = new Point();
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {        }
    
        /**
        * 记录鼠标按下时的点
        */
        @Override
        public void mousePressed(MouseEvent e) {
            origin.x = e.getX(); 
            origin.y = e.getY();
        }
    
        @Override
        public void mouseReleased(MouseEvent e) {}
    
        /**
        * 鼠标移进标题栏时，设置鼠标图标为移动图标
        */
        @Override
        public void mouseEntered(MouseEvent e) {
          this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
        
        /**
        * 鼠标移出标题栏时，设置鼠标图标为默认指针
        */
        @Override
        public void mouseExited(MouseEvent e) {
          this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    
        /**
        * 鼠标在标题栏拖拽时，设置窗口的坐标位置
        * 窗口新的坐标位置 = 移动前坐标位置+（鼠标指针当前坐标-鼠标按下时指针的位置）
        */
        @Override
        public void mouseDragged(MouseEvent e) {
          Point p = this.frame.getLocation();
          this.frame.setLocation(
            p.x + (e.getX() - origin.x), 
            p.y + (e.getY() - origin.y)); 
        }
    
        @Override
        public void mouseMoved(MouseEvent e) {}
        
      }
    
}
