package com.whut.client.GUI.Panel.FileGUI;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.util.Vector;

import com.whut.client.GUI.MainFrame;
import com.whut.client.model.Doc;
import com.whut.client.service.DataProcessing;

import lombok.extern.slf4j.Slf4j;
import java.awt.Font;
import java.net.URL;

import javax.swing.JOptionPane;

@Slf4j
public class DownloadCount extends JFrame {
    private ChartPanel chartPanel;
    private static DownloadCount instance = new DownloadCount();
        private Vector<Doc> docList;

    public DownloadCount(){
        super("下载统计");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
           log.error("无法设置主题", ex);
        }
        URL url  = MainFrame.class.getResource("Image/icon.png");
        ImageIcon icon = new ImageIcon(url);
        setIconImage(icon.getImage());

    }

    public ChartPanel getChartPanel() {
        return this.chartPanel;
    }

    private static void configFont(JFreeChart chart){
	     // 配置字体
	     Font xfont = new Font("楷体",Font.BOLD,14) ;// X轴
	     Font yfont = new Font("楷体",Font.BOLD,14) ;// Y轴
	     Font kfont = new Font("楷体",Font.BOLD,14) ;// 底部
	     Font titleFont = new Font("楷体", Font.BOLD,20) ; // 图片标题
	     CategoryPlot plot = chart.getCategoryPlot();// 图形的绘制结构对象
	     
	     // 图片标题
	     chart.setTitle(new TextTitle(chart.getTitle().getText(),titleFont));
	     
	     // 底部
	     chart.getLegend().setItemFont(kfont);

	     // X 轴
	     CategoryAxis domainAxis = plot.getDomainAxis();   
	        domainAxis.setLabelFont(xfont);// 轴标题
	        domainAxis.setTickLabelFont(xfont);// 轴数值 

	     // Y 轴
	     ValueAxis rangeAxis = plot.getRangeAxis();   
	        rangeAxis.setLabelFont(yfont); 
	        rangeAxis.setTickLabelFont(yfont);  
            rangeAxis.setRange(0, 100);
	        
	}

    public void init(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            docList = DataProcessing.getAllDocs();
            for(Doc doc : docList){
                dataset.addValue(doc.getDownloadCount(), doc.getCreator(), doc.getFilename());
            }
            JFreeChart chart = ChartFactory.createBarChart3D(
				"下载排行榜", // 图标题
				"文件", 
				"下载量", 
				dataset, 
				PlotOrientation.VERTICAL, 
				true, true, false);
                
            configFont(chart);
            
        
            this.chartPanel = new ChartPanel(chart);



            this.setContentPane(this.chartPanel);
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 
            setSize(500,400);
            setLocation(500,200);
            setLayout(null);
            setVisible(true);
            setResizable(false);
            
	   
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static DownloadCount getInstance(){
        instance.init();
        return instance;
    }
}
