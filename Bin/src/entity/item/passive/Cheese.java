package entity.item.passive;

import java.awt.Graphics;

import display.NotificationIndicator;
import entity.item.ItemEntity;
import entity.player.Player;

/**
 * @包名： entity.item.passive
 * @类名： Cheese
 * @创建人： 阳阳
 * @类描述： 面包->加攻击力和血量
 */

public class Cheese extends ItemEntity{
	NotificationIndicator notifInd = new NotificationIndicator();
	
	public Cheese(int entityWidth, int entityHeight) {
		super(32, 32);
		this.setLocation(entityWidth, entityHeight);
		this.setName("面包");
		this.setSubText("生活不止苟且，还有诗和远方!");
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
	public void render(Graphics win) {
		win.drawImage(sprite.getCheese(), (int)getEntity().getX(),(int)getEntity().getY()-(int)hoverCurrentDistance, null);
		
	}

	@Override
	public boolean pickup(Player p) {
		if(pickupCooldown>0){
			return false;
		}else{
		p.addDamage(2);
		p.addDamage(1);
		p.addHP(20);
		this.notifInd.ItemNotif(this.getName(), this.getSubText());
		return true;
		}
	}
}
