package main.src;

/**
 * @(#)CGameMain.java
 *
 *
 * @author 
 * @version 1.00
 */


import FunCode.JSystem;
import FunCode.JSprite;
import FunCode.JStaticSprite;
import FunCode.JAnimateSprite;
import FunCode.JTextSprite;
import FunCode.JEffect;
import FunCode.JSound;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

import main.src.Game;
import main.util.Audio;
import main.util.BufferedImageLoader;
import main.util.EntityHolder;
import main.util.KeyInput;
import display.DeadDisplay;
import display.LoadDisplay;
import display.MenuDisplay;
import display.NotificationIndicator;
import display.pause.PauseMenu;
import display.player.HealthDisplay;
import display.player.ManaDisplay;
import display.player.MapDisplay;
import display.player.StatDisplay;
import display.player.XpDisplay;
import entity.hostile.Skeletons.Skeleton3;
import entity.hostile.Skeletons.Skeleton8;
import entity.item.consumable.Chest;
import entity.item.consumable.Ladder;
import entity.particle.OnFire;
import entity.player.LevelUp;
import entity.player.Player;
import entity.util.DirectionalMovementHelper;
import entity.util.EntityCollision;
import entity.util.EntityShake;
import entity.util.ProjectileKeyHelper;
import environment.block.Floor;
import environment.room.Map;
import environment.util.CheckBounds;
import environment.util.RoomCoordinate;


@SuppressWarnings("unused")
public class CGameMain extends Canvas implements Runnable
{
	public static CGameMain g_GameMain;				// 本类的唯一实例
	public int				m_iGameState =	0;		// 游戏状态，0 -- 游戏结束等待开始状态；1 -- 按下空格键开始，初始化游戏；2 -- 游戏进行中
	public static final int WIDTH = 950+64;
	public static final int HEIGHT = 694+64;
	public final String TITLE = "地下城与巫师";
	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;		//精灵表图
	private Floor background;      					
	
	Player p = new Player();
	EntityHolder EH = new EntityHolder();
	EntityShake ES = new EntityShake();
	Audio a;
	
	LevelUp levelUp;
	
	
	EntityCollision entityC= new EntityCollision();
	Map m;
	
	OnFire onFire = new OnFire();
	
	boolean debug;
	
	
	int time = 0;
	private boolean Loading,Pause,Transition,Dead,Menu,Next,bossFinished;
	
	
	
	String fps = "";
	HealthDisplay healthDisplay;	//健康
	DeadDisplay deadDisplay;		//死亡
	ManaDisplay manaDisplay;		//法力
	XpDisplay xpDisplay;
	MapDisplay mapDisplay;			//地图
	LoadDisplay loadDisplay;		//负载
	StatDisplay statDisplay;		//统计
	NotificationIndicator notifInd;//通知指示器
	ProjectileKeyHelper PKH;		
	DirectionalMovementHelper DMH;	//定向运动帮助DMH
	CheckBounds CB;					//检查边界
	MenuDisplay menuDisplay;		//菜单显示
	PauseMenu pauseMenu;			//暂停菜单
	
	public void init(){
		
		
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			spriteSheet = loader.loadImage("/sprite_sheet.png");	//精灵图加载
		}catch(IOException e){
			e.printStackTrace();
		}
		
		addKeyListener(new KeyInput(this));
		Loading = false;
		Menu = true;
		Pause = false;
		Transition = false;
		background = new Floor();
		p.setLocation((new RoomCoordinate()).getX(8)-32, (new RoomCoordinate()).getY(5)-32);		//房间坐标
		m = new Map();
		levelUp = new LevelUp();
		healthDisplay = new HealthDisplay();
		manaDisplay = new ManaDisplay();
		mapDisplay = new MapDisplay();
		xpDisplay = new XpDisplay();
		loadDisplay = new LoadDisplay();
		statDisplay = new StatDisplay();
		pauseMenu = new PauseMenu();
		notifInd = new NotificationIndicator();
		PKH = new ProjectileKeyHelper();
		DMH = new DirectionalMovementHelper();
		CB = new CheckBounds();
		a = new Audio();
		menuDisplay = new MenuDisplay();
		deadDisplay = new DeadDisplay();
		
	}
	
	private synchronized void start(){
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop(){
		if(!running)
			return;
		
		running = false;
		try{
			thread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		System.exit(1);
	}
	double amountOfTicks = 60.0;
	double ns = 1000000000/amountOfTicks;
	public void run(){
		init();
		long lastTime = System.nanoTime();
		
		
		double delta = 1;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta>=1){
				if(Menu){
					menuTick();
				}else if(Loading){																					//加载
					loadTick();
				}else if(Dead){
					dead();
				}else if(Transition){
					transition();
				}else if(Pause){																				//暂停
					pause();
				}else{																							//Tick
					tick();
					
				}
				setDisplay();
				
				updates++;
				delta=0;
				frames++;
			}
				if((Menu||Loading)&&!Next){
					menuRender();
				}else{
					render();
				}
				
																									//Render
			
			if(System.currentTimeMillis()-timer>1000){
				timer += 1000;
				
				//System.out.println(updates+" ticks / "+frames+" fps");
				fps = ""+frames;
				updates=0;
				frames=0;
			}
			
		}
		stop();
	} 
	private void menuTick(){
		menuDisplay.tick();
		menuDisplay.startBgm();
		if(menuDisplay.start()){
			Loading = true;
			Menu = false;
			p.setDisplay(statDisplay);
			p.reset();
			(new Chest(0, 0)).initChestItems();
			a.stopTitlebgm();
			a.playBmg1();
			m.resetFloor();
		}
	}
	private void menuRender(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		g.fillRect(0, 0, WIDTH+50, HEIGHT+50);
		/////////////////////////////////////////////////////////////////////
		menuDisplay.render(g);
		
		loadDisplay.render(g);
		/////////////////////////////////////////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	private void loadTick(){
		if(time==0){
			m.nextFloor();
			loadDisplay.setFloor(m.getFloor());
			loadDisplay.reset();
		}
		if(time==-1){
			a.playBmg1();;
			a.stopBossbgm();
			Loading = false;
			Next = false;
			m.create(3+m.getFloor()*3);
			p.setLocation((new RoomCoordinate()).getX(8)-32, (new RoomCoordinate()).getY(5)-32);
			EH.setE(m.getE());
			EH.setB(m.getB());
			EH.setS(m.getS());
			mapDisplay.setRoomLayout(m.getRoomLayout());
			mapDisplay.currentPosition(m.getCurrentX(), m.getCurrentY(),m.getRoom().isTreasure(),m.getRoom().isBoss());
			time= -1;
		}else{
			loadDisplay.tick();
			if(loadDisplay.halfDone()){
				time=-2;
			}	
		}
		time++;
		
	}
	private void dead(){
		deadDisplay.tick();
	}
	
	private void transition(){
		if(time==0){
			if(p.checkPosition(WIDTH, HEIGHT)==1)
				m.moveUp();
			else if(p.checkPosition(WIDTH, HEIGHT)==2)
				m.moveRight();
			else if(p.checkPosition(WIDTH, HEIGHT)==3)
				m.moveDown();
			else if(p.checkPosition(WIDTH, HEIGHT)==4)
				m.moveLeft();
			p.setTransitionLocation(p.checkPosition(WIDTH, HEIGHT));
			EH.setE(m.getE());
			EH.setB(m.getB());
			EH.setS(m.getS());
			EH.getP().clear();
			
			mapDisplay.currentPosition(m.getCurrentX(), m.getCurrentY(),m.getRoom().isTreasure(),m.getRoom().isBoss());
			
		}else if(time==10){
			Transition = false;
			time = -1;
		}
		time++;
	}
	
	private void pause(){
		
	}
	
	private void tick(){
		
		//////////////////////////////////////////////////////////////////////////////
		
		p.tick();                                                                   //player tick
		
		
		
		for(int i =0; i<EH.getE().size();i++){ 												//每个敌人实体
			EH.getE().get(i).tick();                                                        //enemy[i] tick
			EH.getE().get(i).follow(p);                                                     //enemy[i] movements
			entityC.collide(p, EH.getE().get(i));                                           //enemy[i]/碰撞
			for(int j=0;j<EH.getE().size();j++){
				if(j!=i){
					entityC.collide(EH.getE().get(i), EH.getE().get(j));                            //enemy[i]/enemy[j] collision
				}
			}
			if(EH.getE().get(i).dead()){
				EH.getE().get(i).drop();
				a.playDeath();
				p.giveXP(EH.getE().remove(i).getXpWorth());                                                        //remove enemy[i]
				i--;
			}
		}
		
		for(int i=0;i<EH.getB().size();i++){
			EH.getB().get(i).tick();                                                        //block[i] tick
			entityC.collide(p, EH.getB().get(i));                                           //block[i]/player collision
			entityC.collide(EH.getP(),EH.getB().get(i));                        //playerprojectile[i]/block[i] collision
			entityC.collide(EH.getEP(),EH.getB().get(i));
			for(int j=0;j<EH.getE().size();j++){
				entityC.collide(EH.getE().get(j), EH.getB().get(i));                                //block[i]/enemy[i] collision
			}
			for(int j=0;j<EH.getS().size();j++){
				entityC.collide(EH.getS().get(j), EH.getB().get(i));
			}
		}
		
		for(int i=0;i<m.getDoors().size();i++){
			if(m.getDoors().get(i).getCollision()){
				entityC.collide(p, m.getDoors().get(i));
				entityC.collide(EH.getP(),m.getDoors().get(i));
				entityC.collide(EH.getEP(),m.getDoors().get(i));
				for(int j=0;j<EH.getE().size();j++){
					entityC.collide(EH.getE().get(j), m.getDoors().get(i));                                //block[i]/enemy[i] collision
				}
				for(int j=0;j<EH.getS().size();j++){
					entityC.collide(EH.getS().get(j), m.getDoors().get(i));
				}
			}
			
			
		}
		
		for(int i=0;i<EH.getS().size();i++){
			EH.getS().get(i).tick();
			for(int j=0;j<EH.getS().size();j++){
				if(j!=i){
					entityC.collide(EH.getS().get(i), EH.getS().get(j));                            //enemy[i]/enemy[j] collision
				}
			}
		}
		
		for(int i=0;i<EH.getP().size();i++){
			EH.getP().get(i).tick();
			if(EH.getP().get(i).getX()<-300||EH.getP().get(i).getX()>1200){
				if(EH.getP().get(i).getY()<-300||EH.getP().get(i).getX()>1000){
					EH.getP().remove(i);
					i--;
				}
			}
		}
		for(int i=0;i<EH.getEP().size();i++){
			EH.getEP().get(i).tick();
			if(EH.getEP().get(i).getX()<-300||EH.getEP().get(i).getX()>1200){
				if(EH.getEP().get(i).getY()<-300||EH.getEP().get(i).getX()>1000){
					EH.getEP().remove(i);
					i--;
				}
			}
		}
		
		entityC.collide(p, EH.getS());
		
		entityC.collide(EH.getEP(), p);
		
		entityC.collide(EH.getP(), EH.getE(), EH.getS());                                  //playerprojectile[i]/enemy[] collision
		
		if(p.checkPosition(WIDTH, HEIGHT)!=-1){
			Transition = true;
		}
		
		for(int i=0;i<EH.getPA().size();i++){
			EH.getPA().get(i).tick();
			if(EH.getPA().get(i).isDone()){
				EH.getPA().remove(i);
				i--;
			}
		}
		
		CB.tick();
		ES.tick();
		m.setDoors(EH.getE().size(),this.p);
		onFire.tick();
		healthDisplay.tick(p.getMaxHealth(), p.getCurrentHealth());					//HealthDisplay tick
		manaDisplay.tick(p.getMaxMana(), p.getCurrentMana());
		mapDisplay.tick();
		notifInd.tick();
		
		
		
		if(p.newFloor()){
			p.nextFloor(false);
			Menu = false;
			Pause = false;
			Loading = true;
			Next = true;
			loadDisplay.reset();
		}
		if(!loadDisplay.isDone())
			loadDisplay.tick();
		if(p.isDead()){
			Dead=true;
			a.stopBmg1();
			a.stopBossbgm();
			a.playGameOverbgm();
			a.stopTitlebgm();
			System.out.println("dead");
		}
		
		////////////////////////////////////////////////////////////////////////////////
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		g.fillRect(0, 0, WIDTH+50, HEIGHT+50);
		///////////////////////////////////////////////////////////////////////////////
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH+50, 128);
		background.render(g);
		
		for(int i =0; i<EH.getE().size();i++){
			EH.getE().get(i).renderShadow(g);
		}
		p.renderShadow(g);
		for(int i=0;i<EH.getS().size();i++){
			EH.getS().get(i).renderShadow(g);
		}
		
		for(int i=0;i<EH.getB().size();i++){
			EH.getB().get(i).render(g);
		}
		
		for(int i=0;i<m.getDoors().size();i++){
			m.getDoors().get(i).render(g);
		}
		
		for(int i =0; i<EH.getE().size();i++){
			EH.getE().get(i).render(g);
		}
		
		for(int i=0;i<EH.getS().size();i++){
			EH.getS().get(i).render(g);
		}
		
		p.render(g);
		for(int i = 0;i<EH.getP().size();i++){
			EH.getP().get(i).render(g);
		}
		for(int i=0;i<EH.getEP().size();i++){
			EH.getEP().get(i).render(g);
		}
		for(int i=0;i<EH.getPA().size();i++){
			EH.getPA().get(i).render(g);
		}
		
		onFire.render(g);
		notifInd.render(g);
		healthDisplay.render(g, p.getMaxHealth(), p.getCurrentHealth());
		manaDisplay.render(g, p.getMaxMana(), p.getCurrentMana());
		mapDisplay.render(g);
		xpDisplay.render(g, p.getMaxXp(), p.getCurrentXp());
		statDisplay.render(g);
		
		if(debug){
			for(int i =0; i<EH.getE().size();i++){
				EH.getE().get(i).renderHitbox(g);
			}
			p.renderHitbox(g);
			for(int i=0;i<EH.getS().size();i++){
				EH.getS().get(i).renderHitbox(g);
			}
			for(int i=0;i<EH.getP().size();i++){
				EH.getP().get(i).renderHitbox(g);
			}
			g.setColor(Color.WHITE);
			g.setFont(new Font("TEST", Font.PLAIN,10));
			g.drawString("FPS: "+fps, 5, 10);
			g.drawString("XY: "+m.currentCoordinates(), 5, 20);
			g.drawString("E: "+EH.getE().size(), 5, 30);
		}
		
		if(m.bossFinished()){
			bossFinished=true;
			g.setColor(Color.WHITE);
			g.setFont(new Font("Helvetica",Font.BOLD,30));
			g.drawString("按下Enter继续",420-g.getFontMetrics().stringWidth("按下Enter继续"),240);
		}else{
			bossFinished=false;
		}
		if(this.debug){
			g.setColor(Color.WHITE);
			g.setFont(new Font("Helvetica",Font.PLAIN,15));
			g.drawString("Debug Mode: ON",880,750);
		}
		if(this.Pause){
			pauseMenu.render(g);
		}
		if(Dead){
			deadDisplay.render(g,m.getFloor(),p.getLevel());
			//p.render(g);
		}
		if(!loadDisplay.isDone()){
			loadDisplay.render(g);
		}
		//////////////////////////////////////////////////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	public void setDisplay(){
		statDisplay.setArmor(p.getArmor());
		statDisplay.setAttackDamage(p.getDamage());
		statDisplay.setAttackSpeed(p.getAttackSpeed());
		statDisplay.setCriticalChance(p.getCriticalChance());
		statDisplay.setMagicPower(p.getMagicPower());
		statDisplay.setMovementSpeed((p.getMaxY()+p.getMaxX())*10/2);
		statDisplay.setLevel(p.getLevel());
		statDisplay.setFloor(m.getFloor());
	}
	
	public void summon(){
		//EH.addE(new KittyKannon(500,300,1000));
		//EH.addE(new Skeleton3(500,300,1000));
		//EH.addE(new Slime(500,300,100));
		//EH.addS(new Chest(500,300));
		//EH.addE(new Skeleton8(500,300,1000));
	}
	
	public void keyPressed(KeyEvent k){
		//WSAD移动人物
		if(k.getKeyCode()==87){
			p.moveUp(true);
			DMH.up(true);
			if(Menu){
				menuDisplay.moveUp();
			}
		}
		if(k.getKeyCode()==83){
			p.moveDown(true);
			DMH.down(true);
			if(Menu){
				menuDisplay.moveDown();
			}
		}
		if(k.getKeyCode()==68){
			p.moveRight(true);
			DMH.right(true);
		}	
		if(k.getKeyCode()==65){
			p.moveLeft(true);
			DMH.left(true);
		}
		//小键盘上下左右键发射子弹
		if(k.getKeyCode() == 39)
			PKH.right(true);
		if(k.getKeyCode() == 38){
			PKH.up(true);
			if(Menu){
				menuDisplay.moveUp();
			}
		}
		if(k.getKeyCode() == 40){
			PKH.down(true);
			if(Menu){
				menuDisplay.moveDown();
			}
		}
		if(k.getKeyCode() == 37)
			PKH.left(true);
		//Skill Upgrades
		/*
		if(k.getKeyCode()==49) //1 DMG
			p.addDamage(levelUp.getATT());
		if(k.getKeyCode()==50) //2 MS
			p.addMovementSpeed(levelUp.getMS());
		if(k.getKeyCode()==51) //3 DEF
			p.addArmor(levelUp.getDEF());
		if(k.getKeyCode()==52) //4 AS
			p.addAttackSpeed(levelUp.getAS());
		if(k.getKeyCode()==53) //5 CC
			p.addCriticalChance(levelUp.getCC());
		if(k.getKeyCode()==54) //6 MAG
			p.addMAG(levelUp.getMAG());
			*/
		
		
		//法术
		if(k.getKeyCode()==32){
			if(debug){
				//p.giveMana(100);
				//p.giveXP(10000000);
				//p.resetHealth();
			}
			
			p.getSpell().activate(p);
			//loading=true;
			//damageInd.addHeal((int)(p.getEntity().getX()+p.getEntity().getWidth()/2), (int)(p.getEntity().getY()),p.resetHealth());
		}
		if(k.getKeyCode()==69){
			summon();
		}
		if(k.getKeyCode()==27){			//ESC
			if(Pause)
				Pause=false;
			else if(!Menu&&!Loading&&!Dead)
				Pause=true;
		}
		if(k.getKeyCode()==16){
			this.amountOfTicks=30;
			ns = 1000000000/amountOfTicks;
			a.playBmg1();
		}
		if(k.getKeyCode()==92){			// |debug模式
			if(debug)
				debug=false;
			else
				debug = true;
		}
		if(k.getKeyChar()==10){
			if(Menu){
				menuDisplay.select();
			}
			if(false&&Pause){
				Menu = true;
				Pause = false;
				Dead = false;
				menuDisplay.reset();
				loadDisplay.reset();
			}
			if(!loadDisplay.isDone()){
				loadDisplay.forceEnd();
			}
			if(Dead){
				Menu = true;
				Pause = false;
				Dead = false;
				menuDisplay.reset();
				loadDisplay.reset();
			}
			if(bossFinished){
				EH.addS(new Ladder(p.getX(),p.getY()));
			}
		}
		//System.out.println(k.getKeyCode());
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode()==87){
			p.moveUp(false);
			DMH.up(false);
		}
		if(e.getKeyCode()==83){
			p.moveDown(false);
			DMH.down(false);
		}
		if(e.getKeyCode()==68){
			p.moveRight(false);
			DMH.right(false);
		}
		if(e.getKeyCode()==65){
			p.moveLeft(false);
			DMH.left(false);
		}
		//发射子弹
		if (e.getKeyCode() == 39)		//右
			PKH.right(false);
		if (e.getKeyCode() == 38)		//上
			PKH.up(false);
		if (e.getKeyCode() == 40)		//下
			PKH.down(false);
		if (e.getKeyCode() == 37)		//左
			PKH.left(false);
		
		if(e.getKeyCode()==16){			//shift
			this.amountOfTicks=60;
			ns = 1000000000/amountOfTicks;
		}
	}
	
	
	
	//=============================================================================
	//
	// 构造方法
    public CGameMain() 
    {
    }
    
    //=============================================================================
    // 游戏主循环，此函数将被不停的调用，引擎每刷新一次屏幕，此函数即被调用一次
	// 用以处理游戏的开始、进行中、结束等各种状态. 
	// 函数参数fDeltaTime : 上次调用本函数到此次调用本函数的时间间隔，单位：秒
    public void GameMainLoop( float fDeltaTime )
    {
    	switch( m_iGameState )
		{
			// 初始化游戏，清空上一局相关数据
		case 1:
			{
				GameInit();
				m_iGameState	=	2; // 初始化之后，将游戏状态设置为进行中
			}
			break;
	
			// 游戏进行中，处理各种游戏逻辑
		case 2:
			{
				// 修改此处游戏循环条件，完成正确游戏逻辑
				if( true )
				{
					GameRun( fDeltaTime );
				}
				else
				{
					// 游戏结束。调用游戏结算函数，并把游戏状态修改为结束状态
					m_iGameState	=	0;
					GameEnd();
				}
			}
			break;
	
			// 游戏结束/等待按空格键开始
		case 0:
		default:
			break;
		};
    }
    
	//==============================================================================
	//
	// 每局开始前进行初始化，清空上一局相关数据
	public void	GameInit()
	{
	}
	
	//==============================================================================
	//
	// 每局游戏进行中
	public void	GameRun( float fDeltaTime )
	{
	}
	
	//==============================================================================
	//
	// 本局游戏结束
	public void	GameEnd()
	{
	}
    
    // dOnMouseMove：鼠标移动后将被调用的方法
	// 参数 fMouseX, fMouseY：为鼠标当前坐标
	//
    public void	OnMouseMove( float fMouseX, float fMouseY )
	{
	}
	
	// dOnMouseClick：鼠标按下后将被调用的方法
	// 参数 iMouseType：鼠标按键值，见 enum MouseTypes 定义
	// 参数 fMouseX, fMouseY：为鼠标当前坐标
	//	
	public void	OnMouseClick( int iMouseType, float fMouseX, float fMouseY )
	{		
	}

	// dOnMouseUp：鼠标弹起后将被调用的方法
	// 参数 iMouseType：鼠标按键值，见 enum MouseTypes 定义
	// 参数 fMouseX, fMouseY：为鼠标当前坐标
	//	
	public void	OnMouseUp( int iMouseType, float fMouseX, float fMouseY )
	{		
	}
    
	// dOnKeyDown：键盘被按下后将被调用的方法
	// 参数 iKey：被按下的键，值见 enum KeyCodes 宏定义
	// 参数 bAltPress, bShiftPress，bCtrlPress：键盘上的功能键Alt，Ctrl，Shift当前是否也处于按下状态
	//    
    public void	OnKeyDown( int iKey, boolean bAltPress, boolean bShiftPress, boolean bCtrlPress )
    {
    }
    
	// dOnKeyUp：键盘按键弹起后将被调用的方法
	// 参数 iKey：弹起的键，值见 enum KeyCodes 宏定义
	//    
	public void	OnKeyUp( int iKey )
	{
	}
	
	// dOnSpriteColSprite：精灵与精灵碰撞后将被调用的方法
	// 精灵之间要产生碰撞，必须在编辑器或者代码里设置精灵发送及接受碰撞
	// 参数 szSrcName：发起碰撞的精灵名字
	// 参数 szTarName：被碰撞的精灵名字
	//	
	public void	OnSpriteColSprite( String szSrcName, String szTarName )
	{
	}
	
	// dOnSpriteColWorldLimit：精灵与世界边界碰撞后将被调用的方法
	// 精灵之间要产生碰撞，必须在编辑器或者代码里设置精灵的世界边界限制
	// 参数 szName：碰撞到边界的精灵名字
	// 参数 iColSide：碰撞到的边界 0 左边，1 右边，2 上边，3 下边
	//	
	public void	OnSpriteColWorldLimit( String szName, int iColSide )
	{
	}
	
	
	public static void main(String args[]){
		CGameMain game = new CGameMain();
		
		game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		game.setMaximumSize(new Dimension(WIDTH,HEIGHT));
		game.setMinimumSize(new Dimension(WIDTH,HEIGHT));
		
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}
	
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
}

