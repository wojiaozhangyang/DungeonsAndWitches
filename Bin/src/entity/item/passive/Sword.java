package entity.item.passive;

import java.awt.Graphics;

import display.NotificationIndicator;
import entity.item.ItemEntity;
import entity.player.Player;

/**
 * @包名： entity.item.passive
 * @类名： Sword
 * @创建人： 阳阳
 * @类描述： 武士剑+加攻击力
 */
public class Sword extends ItemEntity{
	NotificationIndicator notifInd = new NotificationIndicator();
	
	public Sword(int entityWidth, int entityHeight) {
		super(32, 32);
		this.setLocation(entityWidth, entityHeight);
		this.setName("武士剑");
		this.setSubText("我打你，你是不是感觉更疼了呢...");
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
		win.drawImage(sprite.getSword(), (int)getEntity().getX(),(int)getEntity().getY()-(int)hoverCurrentDistance, null);
		
	}

	@Override
	public boolean pickup(Player p) {
		if(pickupCooldown>0){
			return false;
		}else{
		p.addDamage(7);
		this.notifInd.ItemNotif(this.getName(), this.getSubText());
		return true;
		}
	}
}
