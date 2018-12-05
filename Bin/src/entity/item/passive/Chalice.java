package entity.item.passive;

import java.awt.Graphics;

import display.NotificationIndicator;
import entity.item.ItemEntity;
import entity.player.Player;

/**
 * @包名： entity.item.passive
 * @类名： Chalice
 * @创建人： 阳阳
 * @类描述：酒杯->加魔法力 魔法值 
 */
public class Chalice extends ItemEntity{
NotificationIndicator notifInd = new NotificationIndicator();
	
	public Chalice(int entityWidth, int entityHeight) {
		super(32, 32);
		this.setLocation(entityWidth, entityHeight);
		this.setName("高杯圣酒");
		this.setSubText("对酒当歌，人生几何!");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		this.move(0,0);
		this.move();
		this.hover();
		if(pickupCooldown>0)
			pickupCooldown--;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(sprite.getChalice(), (int)getEntity().getX(),(int)getEntity().getY()-(int)hoverCurrentDistance, null);
		
	}

	@Override
	public boolean pickup(Player p) {
		if(pickupCooldown>0){
			return false;
		}else{
		p.addMAG(20);
		p.addMP(20);
		this.notifInd.ItemNotif(this.getName(), this.getSubText());
		return true;
		}
	}
}
