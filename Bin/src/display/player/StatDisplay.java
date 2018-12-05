package display.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.util.Sprite;


/**
 * @������ display.player
 * @������ StatDisplay
 * @�����ˣ� ����
 * @������������Ϸ�����Ի��� 
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
		spellName="û�з���";
		weaponName="û������";
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
		g.drawString("������:",180,30);
		g.drawString("����: ",180,60);
		g.drawString("����: ",330,30);
		g.drawString("����: ",330,60);
		g.drawString("������: ",480,30);
		g.drawString("ħ����: ",480,60);
		g.drawString(""+(int)attackDamage,260,30);
		g.drawString(""+(int)(attackSpeed*100)/100.0,240,60);
		g.drawString(""+(int)movementSpeed,390,30);
		g.drawString(""+(int)((int)(criticalChance*10))/10.0+"%",390,60);
		g.drawString(""+(int)(armor)+"%",560,30);
		g.drawString(""+(int)magicPower,560,60);
		
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Helvetica",Font.BOLD,30));
		g.drawString("���³�   "+(int)floor,620,34);
		
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Helvetica",Font.BOLD,20));
		g.drawString("��ǰ�ȼ�: "+(int)level,743-g.getFontMetrics().stringWidth("Level: "+level),80);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Helvetica",Font.BOLD,20));
		g.drawString("����",800,30);
		g.drawString("����",930,30);
		g.setColor(Color.YELLOW);
		g.drawRect(790, 10, 100, 108);
		g.drawRect(905, 10, 100, 108);
		g.drawImage(weapon,808,45,null);
		g.drawImage(spell, 923, 45, null);
		g.setFont(new Font("Helvetica",Font.PLAIN,10));
		g.drawString(weaponName,886-g.getFontMetrics().stringWidth(weaponName),113);
		g.drawString(spellName,1001-g.getFontMetrics().stringWidth(spellName),113);
		g.setColor(new Color(150,150,255));
		g.drawString("����: "+spellCost,1001-g.getFontMetrics().stringWidth("Cost: "+spellCost),100);
	}
}
