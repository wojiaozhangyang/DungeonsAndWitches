package display.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.util.Sprite;


/**
 * @包名： display.player
 * @类名： StatDisplay
 * @创建人： 阳阳
 * @类描述：对游戏上属性绘制 
 */
public class StatDisplay {
	
	private double attackDamage,attackSpeed,movementSpeed,criticalChance,armor,magicPower;
	private int floor,level,spellCost;
	private static Sprite sprite = new Sprite();
	private BufferedImage spell,weapon;
	private String spellName,weaponName;
	public StatDisplay(){
		attackDamage = 0;
		attackSpeed = 0;
		movementSpeed = 0;
		criticalChance = 0;
		armor = 0;
		magicPower = 0;
		floor = 0;
		spellName="没有法术";
		weaponName="没有武器";
	}
	public void setAttackDamage(double input){
		attackDamage = input;
	}
	public void setAttackSpeed(double input){
		attackSpeed = input;
	}
	public void setMovementSpeed(double input){
		movementSpeed = input;
	}
	public void setCriticalChance(double input){
		criticalChance = input;
	}
	public void setArmor(double input){
		armor = input;
	}
	public void setMagicPower(double input){
		magicPower = input;
	}
	public void setFloor(int x){
		floor = x;
	}
	@SuppressWarnings("static-access")
	public void setWand(BufferedImage input,String name){
		weapon = sprite.resize(input, 64, 64);
		weaponName = name;
	}
	public void setSpell(BufferedImage input,String name,int manaCost){
		spell = input;
		spellName = name;
		spellCost = manaCost;
	}
	public void setLevel(int x){
		level = x;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("Helvetica",Font.BOLD,20));
		g.drawString("攻击力:",180,30);
		g.drawString("攻速: ",180,60);
		g.drawString("移速: ",330,30);
		g.drawString("暴击: ",330,60);
		g.drawString("防御力: ",480,30);
		g.drawString("魔法力: ",480,60);
		g.drawString(""+(int)attackDamage,260,30);
		g.drawString(""+(int)(attackSpeed*100)/100.0,240,60);
		g.drawString(""+(int)movementSpeed,390,30);
		g.drawString(""+(int)((int)(criticalChance*10))/10.0+"%",390,60);
		g.drawString(""+(int)(armor)+"%",560,30);
		g.drawString(""+(int)magicPower,560,60);
		
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Helvetica",Font.BOLD,30));
		g.drawString("地下城   "+(int)floor,620,34);
		
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Helvetica",Font.BOLD,20));
		g.drawString("当前等级: "+(int)level,743-g.getFontMetrics().stringWidth("Level: "+level),80);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Helvetica",Font.BOLD,20));
		g.drawString("武器",800,30);
		g.drawString("法书",930,30);
		g.setColor(Color.YELLOW);
		g.drawRect(790, 10, 100, 108);
		g.drawRect(905, 10, 100, 108);
		g.drawImage(weapon,808,45,null);
		g.drawImage(spell, 923, 45, null);
		g.setFont(new Font("Helvetica",Font.PLAIN,10));
		g.drawString(weaponName,886-g.getFontMetrics().stringWidth(weaponName),113);
		g.drawString(spellName,1001-g.getFontMetrics().stringWidth(spellName),113);
		g.setColor(new Color(150,150,255));
		g.drawString("消耗: "+spellCost,1001-g.getFontMetrics().stringWidth("Cost: "+spellCost),100);
	}
}
