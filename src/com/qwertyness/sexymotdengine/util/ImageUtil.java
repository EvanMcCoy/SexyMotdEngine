package com.qwertyness.sexymotdengine.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.qwertyness.sexymotdengine.response.Info;

public class ImageUtil {
	
	public static List<BufferedImage> getGIFFrames() {
		List<BufferedImage> output = new ArrayList<BufferedImage>();
		Info info = Info.getActiveInfo();
		File image = new File(info.IMAGE_PATH);
		if (image.exists()) {
			if (info.IMAGE_PATH.endsWith(".gif")) {
				ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
				ImageInputStream in = null;
				try {
					in = ImageIO.createImageInputStream(image);
					reader.setInput(in);
					for (int i = 0, count = reader.getNumImages(true); i < count; i++)
					{
					    output.add(resizeImage(reader.read(i)));
					}
				} catch (IOException exception) {}
			}
			else {
				if (info.IMAGE_PATH.contains("http://")) {
					try {
						output.add(resizeImage(ImageIO.read(new URL(info.IMAGE_PATH))));
					} catch (IOException e) {info.ENABLE_OVERLAY_IMAGE = false;}
				} 
				else {
					try {
						output.add(resizeImage(ImageIO.read(image)));
					} catch (IOException e) {e.printStackTrace();}
				}
			}
			
		}
		return output;
	}
	
	public static List<BufferedImage> getOptimizedGIFFrames() {
		List<BufferedImage> output = new ArrayList<BufferedImage>();
		try {
	        String[] imageatt = new String[]{
	                "imageLeftPosition",
	                "imageTopPosition",
	                "imageWidth",
	                "imageHeight"
	            };
	        ImageReader reader = (ImageReader)ImageIO.getImageReadersByFormatName("gif").next();
	        ImageInputStream ciis = ImageIO.createImageInputStream(
	        		new File(Info.getActiveInfo().IMAGE_PATH));
	        reader.setInput(ciis, false);
	        int noi = reader.getNumImages(true);
	        BufferedImage master = null;
	        for (int i = 0; i < noi; i++) {
	            BufferedImage image = reader.read(i);
	            IIOMetadata metadata = reader.getImageMetadata(i);
	            Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
	            NodeList children = tree.getChildNodes();
	            for (int j = 0; j < children.getLength(); j++) {
	                Node nodeItem = children.item(j);
	                if(nodeItem.getNodeName().equals("ImageDescriptor")){
	                    Map<String, Integer> imageAttr = new HashMap<String, Integer>();
	                    for (int k = 0; k < imageatt.length; k++) {
	                        NamedNodeMap attr = nodeItem.getAttributes();
	                        Node attnode = attr.getNamedItem(imageatt[k]);
	                        imageAttr.put(imageatt[k], Integer.valueOf(attnode.getNodeValue()));
	                    }
	                    if(i==0){
	                        master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
	                    }
	                    master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);
	                }
	            }
	            output.add(resizeImage(master));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return output;
	}
	
	public static BufferedImage resizeImage(BufferedImage image) {
		BufferedImage resizedImage = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, 64, 64, null);
		g.dispose();
		return resizedImage;
	}
}
