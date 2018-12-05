package display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.util.Audio;
import main.util.Sprite;

public class MenuDisplay {
	private int location;
	final int selectionAmount = 3;
	final int xMid=507,yMid=392;
	static Audio a;
	
	boolean started = false;
	private double r,g=(Math.PI)/3,b=(2*Math.PI)/3;
	
	private double hover,hoverSpeed,rotate;
	
	private BufferedImage background,title,titleShadow,titleOutline,titleStart,titleAbout,titleHelp;
	
	private Sprite sprite = new Sprite();
	
	private int page; //0=about, 1=menu, 2=help1, 3=help2
	private double page0Offset,page1Offset,page2Offset,page3Offset,offset;
	private int transition;
	
	private boolean start,help,about;
	Random random = new Random();
	
	public MenuDisplay(){
		page0Offset = -xMid*2;
		page1Offset=0;
		page2Offset=xMid*2;
		page3Offset=xMid*2*2;
		a = new Audio();
		hoverSpeed = 1;
		rotate = .01;
		page = 1;
		titleStart = sprite.getTitleStart();
		titleAbout = sprite.getTitleAbout();
		titleHelp = sprite.getTitleHelp();
		background = sprite.getTitleBackground();
		title = sprite.getTitle();
		titleShadow = sprite.resize(sprite.getTitleBlur(),(int)(title.getWidth()*1.5),(int)(title.getHeight()*1.5));	
		titleOutline = sprite.recolor(sprite.getTitleOutline(), new Color((int)(Math.abs(Math.sin(r))*255),(int)(Math.abs(Math.sin(g))*255),(int)(Math.abs(Math.sin(b))*255)));
	}
	
	public void tick(){
		tickP0();
		tickP1();
		tickP2();
		tickP3();
		if(transition>0){
			if(offset>-xMid*2){
				offset-=60;
				page0Offset-=60;
				page1Offset-=60;
				page2Offset-=60;
				page3Offset-=60;
			}else{
				transition--;
				offset+=xMid*2;
			}
		}
		if(transition<0){
			if(offset<xMid*2){
				offset+=60;
				page0Offset+=60;
				page1Offset+=60;
				page2Offset+=60;
				page3Offset+=60;
			}else{
				transition++;
				offset-=xMid*2;
			}
		}
	}
	public void render(Graphics g){
		g.drawImage(background,
				(int)(xMid-background.getWidth()/2),
				(int)(yMid-background.getHeight()/2),
				null);
		
		
		renderP0(g);
		renderP1(g);
		renderP2(g);
		renderP3(g);		
	}
	public void startBgm(){
		if(!started){
			started = true;
			a.playTitlebgm();
			a.stopBmg1();
			a.stopBossbgm();
			a.stopGameOverbgm();
		}
	}
	public void moveUp(){
		if(page==1){
			location--;
			a.playSelect();
			if(location<0)
				location += selectionAmount;
		}
	}
	public void moveDown(){
		if(page==1){
			location++;
			a.playSelect();
			location%=selectionAmount;
		}
	}
	public void select(){
		if(page==1&&transition==0){
			if(location==0){	//开始游戏菜单
				start = true;
				help = false;
				about = false;
			}
			if(location==1){	
				help = true;	//帮助菜单
				start = false;
				about = false;
				page=2;
				transition = 1;
			}
			if(location==2){	//关于菜单
				about = true;
				start = false;
				help = false;
				page=0;
				transition = -1;
			}
		}
		if(page==0&&transition==0){
			page++;
			transition=1;
		}
		if(page==2&&transition==0){
			page++;
			transition=1;
		}
		if(page==3&&transition==0){
			page=1;
			transition=-2;
		}
		
	}
	public boolean start(){
		return start;
	}
	public void reset(){
		start = false;
		help = false;
		about = false;
		location = 0;
		page = 1;
		page0Offset = -xMid*2;
		page1Offset=0;
		page2Offset=xMid*2;
		page3Offset=xMid*2*2;
		transition=0;
		started = true;
		a.playTitlebgm();
		a.stopBmg1();
		a.stopBossbgm();
		a.stopGameOverbgm();
	}
	
	private void tickP0(){
		titleOutline = sprite.recolor(titleOutline, new Color((int)(Math.abs(Math.sin(r))*255),(int)(Math.abs(Math.sin(g))*255),(int)(Math.abs(Math.sin(b))*255)));
		titleShadow = sprite.recolor(titleShadow, new Color((int)(Math.abs(Math.sin(r))*50),(int)(Math.abs(Math.sin(g))*50),(int)(Math.abs(Math.sin(b))*50)));
		r+=0.01;
		g+=0.01;
		b+=0.01;
		hover+=hoverSpeed*(Math.abs(Math.abs((int)hover)-10.5)/10);
		if(Math.abs(hover)>10){
			hover = 10*(Math.abs(hoverSpeed)/hoverSpeed);
			hoverSpeed*=-1;
		}
	}
	private void tickP1(){
		
	}
	private void tickP2(){
	
	}
	private void tickP3(){
	
	}
	
	
	private void renderP0(Graphics g){
			g.setColor(Color.WHITE);
			g.setFont(new Font("Helvetica",Font.BOLD,20));
			g.drawString("尽可能地获得更多的宝物。每层都有一个BOSS，你必须打败它才能进入下一个地下城。",(int)(100+page0Offset),70);
			g.drawString("随着你在地下城关卡的增加，难度也会增加。",(int)(100+page0Offset),120);
			g.drawString("有几种类型的敌人和BOSS要小心！他们都有寻路算法来绕过房间障碍物追逐你！",(int)(100+page0Offset),170);
			g.drawString("所有房间和地图都是随机生成的。您可以查看屏幕的左上角以查看地图！",(int)(100+page0Offset),220);
			g.drawString("它每次都是随机的，每层地下城都会变大！",(int)(100+page0Offset),270);
			g.drawString("您可以通过打败BOSS和通过钥匙打开宝箱，获得一些物品。",(int)(100+page0Offset),320);
			g.drawString("有些物品有助于提高你的整体数据，而有些则会为你提供新的武器和能力！",(int)(100+page0Offset),370);
			g.drawString("特别致谢：----------------------------------------------------------------------------------------------------",(int)(100+page0Offset),420);
			g.drawString("本游戏从设计初到完成离不开团队成员的共同协作，及以下人员对本游戏的支持",(int)(100+page0Offset),470);
			g.drawString("来自Ms.雨给到的游戏灵感",(int)(100+page0Offset),520);
			g.drawString("来自Ms.淑君做的游戏素材",(int)(100+page0Offset),570);
			g.drawString("来自Mr.杰针对游戏BUG测试报告",(int)(100+page0Offset),620);
			g.drawString("来自Mr.瑞格工程师技术支持",(int)(100+page0Offset),670);
			g.drawString("来自Mr.所春老师各方面协助",(int)(100+page0Offset),720);
			
			g.drawString("|-------------------------------------------------- |",(int)(500+page0Offset),520);
			g.drawString("|                                                           |",(int)(500+page0Offset),570);
			g.drawString("|                    照片占位符                      |  ",(int)(500+page0Offset),620);
			g.drawString("|                                                           |",(int)(500+page0Offset),670);
			g.drawString("|-------------------------------------------------- |",(int)(500+page0Offset),720);
			
	}
	private void renderP1(Graphics g){
		g.drawImage(titleShadow,
				(int)(xMid-titleShadow.getWidth()/2-20+page1Offset),
				(int)(yMid-titleShadow.getHeight()/2-230),
				null);
		g.drawImage(title,
				(int)(xMid-title.getWidth()/2+page1Offset),
				(int)(yMid-title.getHeight()/2-250+hover),
				null);
		
		g.drawImage(titleOutline,
				(int)(xMid-titleOutline.getWidth()/2+page1Offset),
				(int)(yMid-titleOutline.getHeight()/2-250+hover),
				null);
		
		
		Graphics2D g2 = (Graphics2D) g;
		Composite c;
		if(location==0)
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		else
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.5f);
		g2.setComposite(c);
		
		g.drawImage(titleStart,
				(int)(xMid-titleStart.getWidth()/2+page1Offset),
				(int)(320),
				null);
		if(location==1)
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		else
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.5f);
		g2.setComposite(c);
		
		g.drawImage(titleHelp,
				(int)(xMid-titleHelp.getWidth()/2+page1Offset),
				(int)(450),
				null);
		if(location==2)
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		else
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.5f);
		g2.setComposite(c);
		
		g.drawImage(titleAbout,
				(int)(xMid-titleAbout.getWidth()/2+page1Offset),
				(int)(580),
				null);
		c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		g2.setComposite(c);
	}
	
	BufferedImage player = sprite.resize(sprite.getPlayer(), sprite.getPlayer().getWidth()*2, sprite.getPlayer().getHeight()*2);
	
	private void renderP2(Graphics g){
		g.drawImage(sprite.getTutorial()[0], (int)(100+page2Offset), 250,null);
		g.drawImage(sprite.getFireballSpellbook(), (int)(110+page2Offset), 100,null);
		g.drawImage(sprite.getLaserSpellbook(), (int)(210+page2Offset), 100,null);
		g.drawImage(sprite.getBlackholeSpellbook(), (int)(310+page2Offset), 100,null);
		
		g.drawImage(sprite.getTutorial()[1], (int)(670+page2Offset), 250,null);
		g.drawImage(player, (int)(735+page2Offset), 70,null);
		
		g.drawImage(sprite.getTutorial()[2], (int)(150+page2Offset), 600,null);
		
		g.drawImage(sprite.getTutorial()[3], (int)(610+page2Offset), 600,null);
	}
	
	BufferedImage key = sprite.resize(sprite.getKey(), 110, 110);
	BufferedImage skeleton = sprite.resize(sprite.getSkeleton()[0][0], sprite.getSkeleton()[0][0].getWidth()*2, sprite.getSkeleton()[0][0].getHeight()*2);
	BufferedImage chest = sprite.resize(sprite.getChest(), sprite.getChest().getWidth()*2, sprite.getChest().getHeight()*2);
	BufferedImage slime = sprite.resize(sprite.getSlime()[0], sprite.getSlime()[0].getWidth()*5, sprite.getSlime()[0].getHeight()*5);
	BufferedImage heart = sprite.resize(sprite.getHealth(), sprite.getHealth().getWidth()*5, sprite.getHealth().getHeight()*5);
	
	private void renderP3(Graphics g){
		g.drawImage(sprite.getTutorial()[4], (int)(30+page3Offset), 210,null);
		g.drawImage(heart, (int)(200+page3Offset), 50,null);
		
		g.drawImage(sprite.getTutorial()[5], (int)(600+page3Offset), 210,null);
		g.drawImage(skeleton, (int)(780+page3Offset), 50,null);
		
		g.drawImage(sprite.getTutorial()[6], (int)(100+page3Offset), 560,null);
		g.drawImage(slime, (int)(210+page3Offset), 400,null);
		
		g.drawImage(sprite.getTutorial()[7], (int)(630+page3Offset), 560,null);
		g.drawImage(key, (int)(645+page3Offset), 405,null);
		g.drawImage(chest, (int)(800+page3Offset), 400,null);
	}
}
