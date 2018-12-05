package display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class LoadDisplay {
	
	double backgroundOpacity,textOpacity;		//背景和文本透明度
	double rate=.01;							//速度
	boolean done;
	boolean halfDone;
	final int xMid=507,yMid=392;
	int floor;
	public void reset(){
		done = false;
		halfDone = false;
		backgroundOpacity = 0;
		textOpacity = 0;
		rate=Math.abs(rate);					
	}
	public void forceEnd(){			//两个标记对象
		done = true;
		halfDone = true;
	}
	public boolean isDone(){
		return done;
	}
	public boolean halfDone(){
		return halfDone;
	}
	public void setFloor(int x){
		floor = x;
	}
	
	//指定加载文字和背景透明度    背景=1  文字透明度=1.5
	public void tick(){
		if(backgroundOpacity>=1){
			backgroundOpacity=1;
			textOpacity+=rate;
			if(textOpacity>=1.5){
				//textOpacity=1;
				rate*=-1;
				halfDone = true;
			}
			if(textOpacity<=0){
				textOpacity=0;
				done = true;
				halfDone = false;
			}
		}else{
			backgroundOpacity+=.01;
		}
	}
	public void render(Graphics g){
		double textopac = textOpacity;
		if(textopac>=1){
			textopac = 1;
		}
		
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;
		Composite c;
		if(rate<0)
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float)textopac);
		else
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float)backgroundOpacity);
		g2.setComposite(c);
		g.fillRect(0, 0, 1100, 900);
		
		g.setColor(Color.WHITE);
		c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float)textopac);
		g2.setComposite(c);
		String display = "地下城 " + floor;
		g.setFont(new Font("Helvetica",Font.BOLD,150));
		int width = g.getFontMetrics().stringWidth(display);
		g.drawString(display, xMid-width/2, yMid);
		
		c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		g2.setComposite(c);
	}
}
