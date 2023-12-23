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
import org.jfree.data.general.DefaultPieDataset;

import java.awt.BorderLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.util.Collections;
import com.whut.client.GUI.MainFrame;
import com.whut.client.model.Doc;
import com.whut.client.service.DataProcessing;

import lombok.extern.slf4j.Slf4j;
import java.awt.Font;
import java.net.URL;
import java.util.Comparator;
import javax.swing.JOptionPane;

@Slf4j
public class DownloadCount extends JFrame {
    private ChartPanel barChartPanel;
    private ChartPanel pieChartPanel;
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

    public ChartPanel getBarChartPanel() {
        return this.barChartPanel;
    }

    private static void configFont(JFreeChart chart){
        Font kfont = new Font("宋体",Font.BOLD,14) ;// 底部
	    Font titleFont = new Font("宋体", Font.BOLD,20) ; // 图片标题
	     
	    // 图片标题
	    chart.setTitle(new TextTitle(chart.getTitle().getText(),titleFont));
	     
	    // 底部
	    chart.getLegend().setItemFont(kfont);
    }

    private static void configBarFont(JFreeChart chart){

        configFont(chart);
	    // 配置字体
	    Font xfont = new Font("宋体",Font.BOLD,14) ;// X轴
	    Font yfont = new Font("宋体",Font.BOLD,14) ;// Y轴
        CategoryPlot plot = chart.getCategoryPlot();// 图形的绘制结构对象
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

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 
        setSize(500,610);
        setLocation(500,200);
        setLayout(null);
        setVisible(true);
        setResizable(false);  
        //内容面板
        JPanel content = new JPanel();
        content.setBorder(new EmptyBorder(5, 5, 5, 5));
        content.setLayout(new BorderLayout(0, 0));
        setContentPane(content);

        JPanel panel = new JPanel();
        content.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            docList = DataProcessing.getAllDocs();
            Collections.sort(docList, new Comparator<Doc>() {
                @Override
                public int compare(Doc a, Doc b) {
                    if(a.getDownloadCount() < b.getDownloadCount())
                        return -1;
                    else if(a.getDownloadCount() > b.getDownloadCount())
                        return 1;
                    else return 0;
                }
            });

            for(Doc doc : docList){
                pieDataset.setValue(doc.getFilename(), Double.valueOf(doc.getDownloadCount()));
                dataset.addValue(doc.getDownloadCount(), doc.getCreator(), doc.getFilename());
            }
            
            JFreeChart chart = ChartFactory.createBarChart3D(
				"下载排行榜", // 图标题
				"文件", 
				"下载量", 
				dataset, 
				PlotOrientation.VERTICAL, 
				true, true, false);
                
            configBarFont(chart);
            this.barChartPanel = new ChartPanel(chart);
            barChartPanel.setBounds(0, 0, 500, 300);
            panel.add(barChartPanel);    

            JFreeChart pieChart = ChartFactory.createPieChart(
                    "下载量统计",
                    pieDataset,
                    true,
                    true,
                    false
            );
            configFont(pieChart);
            this.pieChartPanel = new ChartPanel(pieChart);
            pieChartPanel.setBounds(0, 310, 500, 300);
            panel.add(pieChartPanel);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static DownloadCount getInstance(){
        instance.init();
        return instance;
    }
}


