package com.whut.utils;

import java.net.URL;
import java.awt.Point;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.ImageIcon;

public class ImageUtil {
        
    public static ImageIcon createAutoAdjustIcon(Image image, boolean constrained) {
        ImageIcon icon = new ImageIcon(image) {
            @Override
            public synchronized void paintIcon(java.awt.Component cmp, Graphics g, int x, int y) {
                //初始化参数
                Point startPoint = new Point(0, 0);//默认绘制起点
                Dimension cmpSize = cmp.getSize();//获取组件大小
                Dimension imgSize = new Dimension(getIconWidth(), getIconHeight());//获取图像大小
                
                //计算绘制起点和区域
                if(constrained) {//等比例缩放
                    //计算图像宽高比例
                    double ratio = 1.0*imgSize.width/imgSize.height;
                    //计算等比例缩放后的区域大小
                    imgSize.width = (int) Math.min(cmpSize.width, ratio*cmpSize.height);
                    imgSize.height = (int) (imgSize.width/ratio);
                    //计算绘制起点
                    startPoint.x = (int) 
                            (cmp.getAlignmentX()*(cmpSize.width - imgSize.width));
                    startPoint.y = (int) 
                            (cmp.getAlignmentY()*(cmpSize.height - imgSize.height));
                } else {//完全填充
                    imgSize = cmpSize;
                }
                
                //根据起点和区域大小进行绘制
                if(getImageObserver() == null) {
                    g.drawImage(getImage(), startPoint.x, startPoint.y,
                            imgSize.width, imgSize.height, cmp);
                 } else {
                    g.drawImage(getImage(), startPoint.x, startPoint.y,
                            imgSize.width, imgSize.height, getImageObserver());
                 }
            };
        };
        return icon;
    }
    
    public static ImageIcon createAutoAdjustIcon(String filename, boolean constrained) {
        return createAutoAdjustIcon(new ImageIcon(filename).getImage(), constrained);
    }
    
    public static ImageIcon createAutoAdjustIcon(URL url, boolean constrained) {
        return createAutoAdjustIcon(new ImageIcon(url).getImage(), constrained);
    }
    
}

