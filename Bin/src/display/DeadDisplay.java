package display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.util.Sprite;

public class DeadDisplay {
	Rectangle screen;			
	float opacity;				//死亡透明度
	String dead;				
	final int xMid=507,yMid=392;
	Sprite sprite = new Sprite();
	BufferedImage background;
	public DeadDisplay(){
		screen = new Rectangle(0,0,1200,1000);
		opacity = 0;
		background = sprite.getTitleBackground();
		dead = "你死了";
	}
	public void tick(){
		if(opacity<1)
			opacity += 0.01;
		if(opacity>=1){
			opacity = 1;
		}
	}
	public void render(Graphics g,int floor,int level){
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;
		Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,opacity);
		g2.setComposite(c);
		g.drawImage(background,
				(int)(xMid-background.getWidth()/2),
				(int)(yMid-background.getHeight()/2),
				null);
		c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		g2.setComposite(c);
		g.setFont(new Font("test", Font.BOLD,(int)50));
		
		g.setColor(Color.WHITE);
		
		g.drawString("你死了", (int)500-g.getFontMetrics().stringWidth(dead)/2,200);
		g.drawString("你通过的地下城为: "+ floor, (int)500-g.getFontMetrics().stringWidth("你通过的地下城为: "+ floor)/2,300);
		g.drawString("你的等级为: "+level, (int)500-g.getFontMetrics().stringWidth("你的等级为: "+level)/2,400);
		g.drawString("键入回车键重新开始", (int)500-g.getFontMetrics().stringWidth("键入回车键重新开始")/2,500);
	}
}
