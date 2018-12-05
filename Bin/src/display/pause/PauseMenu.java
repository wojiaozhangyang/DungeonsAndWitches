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
//		g.drawString("��ͼ",34,40);
//		g.drawString("������",180,28);
//		g.drawString("�����ٶ�",180,60);
//		g.drawString("�ƶ��ٶ�",330,28);
//		g.drawString("������",330,60);//Critical Chance
//		g.drawString("������",480,28);
//		g.drawString("ħ����",480,60);
//		g.drawString("��ǰ���³�",620,30);
		g.drawString("��ǰ����",795,50);//Current Weapon
		g.drawString("��ǰ����",920,50);
		g.drawString("����ֵ",680,98);
		g.drawString("����ֵ",680,125);
		g.drawString("����ֵ",680,110);
		g.drawString("��ǰ�ȼ�",680,80);
		g.drawString("��ɫ����ͨ����BOSS�ķ��� ",80,400);
		g.drawString("��ɫ����ͨ�����䷿�� ",780,400);
		g.setFont(new Font("Helvetica",Font.BOLD,50));
		g.drawString("��ͣ",410,450);
		
		g.setColor(Color.RED);
		g.fillRect(1, 385, 64, 128);
		g.setColor(Color.YELLOW);
		g.fillRect(960, 385, 64, 128);
		
		//g.setColor(Color.WHITE);
		//g.fillRect(160, 120, 700, 523);
	}
}
