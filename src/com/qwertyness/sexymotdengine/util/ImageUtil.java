package com.qwertyness.sexymotdengine.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.qwertyness.sexymotdengine.MotdState;
import com.qwertyness.sexymotdengine.response.Mode;

public class ImageUtil {
	
	public static BufferedImage getFavicon() {
		BufferedImage output = null;
		Mode mode = MotdState.getActiveMode();
		File image = new File(mode.IMAGE_PATH);
		if (image.exists()) {
			if (mode.IMAGE_PATH.contains("http://")) {
				try {
					output = resizeImage(ImageIO.read(new URL(mode.IMAGE_PATH)));
				} catch (IOException e) {mode.ENABLE_OVERLAY_IMAGE = false;}
			} 
			else {
				try {
					output = resizeImage(ImageIO.read(image));
				} catch (IOException e) {e.printStackTrace();}
			}
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
