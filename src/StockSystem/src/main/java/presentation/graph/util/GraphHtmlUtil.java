package presentation.graph.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.json.GsonUtil;

/**  
* @ClassName: GraphHtmlUtil    
* @Description: 包含echarts绘图产生html文件所需要的方法
* @author zhuding    
*        
*/
public class GraphHtmlUtil {

	public static final String HTML_PATH = "html";
	
	private GraphHtmlUtil() {}
	
	/**
     * 读取模板并写入数据
     *
     * @param option
     * @return
     */
    private static List<String> replaceData(Option option, int width, int height) {
        String optionStr = GsonUtil.format(option);
        BufferedReader bufferedReader = null;
        List<String> lines = new ArrayList<String>();
        String line;
        try {
            bufferedReader = new BufferedReader(new FileReader("html/template"));
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("##option##")) {
                    line = line.replace("##option##", optionStr);
                }
                if (line.contains("##width##")) {
                    line = line.replace("##width##", width+"px");
                }
                if (line.contains("##height##")) {
                    line = line.replace("##height##", height+"px");
                }
                lines.add(line);
            }
        } catch (Exception e) {} 
        return lines;
    }

    /**
     * 导出到指定文件
     *
     * @param option
     * @return 返回html路径
     */
    public static String exportToHtml(Option option, String path, int width, int height) {
        
        Writer writer = null;
        
        List<String> data = replaceData(option, width, height);
        // 写入文件
        File html = new File(path);
        
        if(!html.exists())
			try {
				html.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        
        try {
            writer = new OutputStreamWriter(new FileOutputStream(html),
                    "UTF-8");
            for (String l : data) {
                writer.write(l + "\n");
            }
        } catch (Exception e) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
        // 处理
        try {
            return html.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 删除临时的html文件
     * @return
     */
    public static boolean deleteHtml() {
    	File file = new File(HTML_PATH);
    	if(!file.exists())
    		return false;
    	File[] files = file.listFiles();
    	for (File f : files) {
			if(f.getName().endsWith(".html")){
				f.delete();
			}
		}
		return true;
	}
	
}
