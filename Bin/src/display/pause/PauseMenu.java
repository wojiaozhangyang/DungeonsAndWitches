package display.pause;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PauseMenu {
	
	Rectangle pauseBox;
	
	public static final int WIDTH = 1014;
	public static final int HEIGHT = 758; 
	
	public PauseMenu(){
		pauseBox = new Rectangle(WIDTH/2,HEIGHT/2,0,0);
	}
	
	public boolean animate(){
		//if(pauseBox)
		return true;
	}
	
	public void tick(){
		
	}
	public void render(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.5f);
		g2.setComposite(c);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH+50, HEIGHT+50);
		c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		g2.setComposite(c);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Helvetica",Font.BOLD,15));
//		g.drawString("地图",34,40);
//		g.drawString("攻击力",180,28);
//		g.drawString("攻击速度",180,60);
//		g.drawString("移动速度",330,28);
//		g.drawString("爆击率",330,60);//Critical Chance
//		g.drawString("防御力",480,28);
//		g.drawString("魔法力",480,60);
//		g.drawString("当前地下城",620,30);
		g.drawString("当前武器",795,50);//Current Weapon
		g.drawString("当前咒书",920,50);
		g.drawString("生命值",680,98);
		g.drawString("法术值",680,125);
		g.drawString("经验值",680,110);
		g.drawString("当前等级",680,80);
		g.drawString("红色的门通往大BOSS的房间 ",80,400);
		g.drawString("黄色的门通往宝箱房间 ",780,400);
		g.setFont(new Font("Helvetica",Font.BOLD,50));
		g.drawString("暂停",410,450);
		
		g.setColor(Color.RED);
		g.fillRect(1, 385, 64, 128);
		g.setColor(Color.YELLOW);
		g.fillRect(960, 385, 64, 128);
		
		//g.setColor(Color.WHITE);
		//g.fillRect(160, 120, 700, 523);
	}
}
