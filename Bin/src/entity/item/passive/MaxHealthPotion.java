package entity.item.passive;

import java.awt.Graphics;

import display.NotificationIndicator;
import entity.item.ItemEntity;
import entity.player.Player;

/**
 * @包名： entity.item.passive
 * @类名： MaxHealthPotion
 * @创建人： 阳阳
 * @类描述： 生命药水+加血
 */
public class MaxHealthPotion extends ItemEntity{
	NotificationIndicator notifInd = new NotificationIndicator();
	
	public MaxHealthPotion(int entityWidth, int entityHeight) {
		super(32, 32);
		this.setLocation(entityWidth, entityHeight);
		this.setName("生命药水");
		this.setSubText("这点小伤不算什么！");
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
		win.drawImage(sprite.getHealthPot(), (int)getEntity().getX(),(int)getEntity().getY()-(int)hoverCurrentDistance, null);
		
	}

	@Override
	public boolean pickup(Player p) {
		if(pickupCooldown>0){
			return false;
		}else{
		p.addHP(30);
		this.notifInd.ItemNotif(this.getName(), this.getSubText());
		return true;
		}
	}

}
