package org.tom.util;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class QrCode {
	
	
	
	/**
	 * 生成二维码
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage createImage(String content) throws Exception {  
        return createImage(content, null);  
    }  

	/**
	 * 生成的内容
	 * @param content 内容
	 * @param logoSrc logo 图片位置
	 * @return
	 * @throws Exception 
	 */
	public static BufferedImage createImage(String content,String logoSrc) throws Exception{
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,  
                BarcodeFormat.QR_CODE, 200, 200, hints);  
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		if(logoSrc != null && !"".equals(logoSrc)){
			insertImage(image, logoSrc, true);
		}
		return image;
	}
	
	private static void insertImage(BufferedImage source, String imgPath,  
            boolean needCompress) throws Exception {  
        File file = new File(imgPath);  
        if (!file.exists()) {  
            System.err.println(""+imgPath+"   该文件不存在！");  
            return;  
        }  
        Image src = ImageIO.read(new File(imgPath));  
        int width = src.getWidth(null);  
        int height = src.getHeight(null);  
        if (needCompress) { // 压缩LOGO  
            if (width > 50) {  
                width = 50;  
            }  
            if (height > 50) {  
                height = 50;  
            }  
            Image image = src.getScaledInstance(width, height,  
                    Image.SCALE_SMOOTH);  
            BufferedImage tag = new BufferedImage(width, height,  
                    BufferedImage.TYPE_INT_RGB);  
            Graphics g = tag.getGraphics();  
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
            g.dispose();  
            src = image;  
        }  
        // 插入LOGO  
        Graphics2D graph = source.createGraphics();  
        int x = (200 - width) / 2;  
        int y = (200 - height) / 2;  
        graph.drawImage(src, x, y, width, height, null);  
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);  
        graph.setStroke(new BasicStroke(3f));  
        graph.draw(shape);  
        graph.dispose();  
    }
	/**
	 * 条形码解码
	 * @param imgPath
	 * @return
	 */
    public static String decodeBc(String imgPath) {    
        BufferedImage image = null;    
        Result result = null;    
        try {    
            image = ImageIO.read(new File(imgPath));    
            if (image == null) {    
                System.out.println("the decode image may be not exit.");    
            }    
            LuminanceSource source = new BufferedImageLuminanceSource(image);    
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));    
    
            result = new MultiFormatReader().decode(bitmap, null);    
            return result.getText();    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return null;    
    } 
    /**
     * 二维码解码
     * @param imgPath
     * @return
     */
    public static String decodeQr(String imgPath) {    
        BufferedImage image = null;    
        Result result = null;    
        MultiFormatReader formatReader = new MultiFormatReader();
        try {    
            image = ImageIO.read(new File(imgPath));    
            if (image == null) {    
                System.out.println("the decode image may be not exit.");    
            }    
            LuminanceSource source = new BufferedImageLuminanceSource(image);    
            Binarizer  binarizer = new HybridBinarizer(source);
            BinaryBitmap bitmap = new BinaryBitmap(binarizer);    
    
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();   
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");    
            result = formatReader.decode(bitmap, hints);    
            return result.getText();    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return null;    
    } 
    /**
     * 
     * @return
     * @throws IOException 
     */
    public static BufferedImage zoom(String imgPath,int zWidth,int zHeight) throws IOException{
    	File img = new File(imgPath);
    	if(!img.exists()){
    		throw new RuntimeException("图片不存在");
    	}
    	Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);  
        int height = src.getHeight(null);   // 压缩LOGO  
            if (width > zWidth) {  
                width = zWidth;  
            }  
            if (height > zHeight) {  
                height = zHeight;  
            } 
        Image image = src.getScaledInstance(width, height,  
                Image.SCALE_SMOOTH);  
        BufferedImage tag = new BufferedImage(width, height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics g = tag.getGraphics();  
        g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
        g.dispose();  
        src = image;
		return tag;
    }
	
	public static void main(String[] args) throws Exception {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		//BufferedImage s = createImage("我是大胖子");
		BufferedImage s = zoom("D:\\logo.png",64,64);
    	//ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	String name = UUID.randomUUID().toString().replaceAll("-", "");
    	System.out.println(111+".png");
    	File f =new File("D:\\"+111+".png");
    	if(!f.exists()){
    		f.createNewFile();
    	}
    	FileOutputStream bos = new FileOutputStream(f);
    	ImageIO.write(s, "png", bos);
    	
    	//System.out.println(decodeQr("D:\\IMG_0044.JPG"));
    	//byte[] b = bos.toByteArray();
    	//String encoderStr = "data:image/jpg;base64,"+encoder.encodeBuffer(b);
    	//System.out.println("<img src='"+encoderStr+"'>");
	}
	 

}
