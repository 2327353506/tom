package org.tom.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * GZIP压缩
 * @author wmt
 *
 */
public class GzipUtil {
	
	private final static String suffix= ".gzjs";
	
	/**
	 * 数据压缩
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void compress(InputStream in, OutputStream os)  
	        throws Exception {  
	  
	    GZIPOutputStream gos = new GZIPOutputStream(os);  
	  
	    int count;  
	    byte data[] = new byte[1024];  
	    while ((count = in.read(data, 0, 1024)) != -1) {  
	        gos.write(data, 0, count);  
	    }  
	  
	    gos.finish();  
	  
	    gos.flush();  
	    gos.close();  
	} 
	
	/** 
	 * 数据解压缩 
	 *  
	 * @param is 
	 * @param os 
	 * @throws Exception 
	 */  
	public static void decompress(InputStream in, OutputStream os)  
	        throws Exception {  
	  
	    GZIPInputStream gis = new GZIPInputStream(in);  
	  
	    int count;  
	    byte data[] = new byte[1024];  
	    while ((count = gis.read(data, 0, 1024)) != -1) {  
	        os.write(data, 0, count);  
	    }  
	    gis.close();  
	}
	
	
	public static void main(String[] args) throws Exception {
		File in = new File("D:/gzjs/JdArea_utf-8.js");
		File out = new File("D:/gzjs/JdArea_"+System.currentTimeMillis() +suffix);
		out.createNewFile();
		try(InputStream fileIn = new FileInputStream(in);
			OutputStream fileOut = new FileOutputStream(out)){
			GzipUtil.compress(fileIn, fileOut);
			fileOut.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
