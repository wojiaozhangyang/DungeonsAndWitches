package main.util;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image){			//����spriteSheet��ͼ��
		this.image = image;
	}
	
	public BufferedImage grabImage(int col, int row, int width, int height){
		
		BufferedImage img = image.getSubimage((col*64)-64, (row*64)-64, width, height); //��ͼ����вü�
		return img;
	}
}
