package entity.item.passive;

import java.awt.Graphics;

import display.NotificationIndicator;
import entity.item.ItemEntity;
import entity.player.Player;

/**
 * @包名： entity.item.passive
 * @类名： Bow
 * @创建人： 阳阳
 * @类描述： 弓箭->加攻击力攻击速度
 */

public class Bow extends ItemEntity{
	NotificationIndicator notifInd = new NotificationIndicator();
	
	public Bow(int entityWidth, int entityHeight) {
		super(32, 32);
		this.setLocation(entityWidth, entityHeight);
		this.setName("弓");
		this.setSubText("温侯神射世间稀,曾向辕门独解危!");
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
		win.drawImage(sprite.getBow(), (int)getEntity().getX(),(int)getEntity().getY()-(int)hoverCurrentDistance, null);
		
	}

	@Override
	public boolean pickup(Player p) {
		if(pickupCooldown>0){
			return false;
		}else{
			p.addDamage(3);
		p.addAttackSpeed(1);
		this.notifInd.ItemNotif(this.getName(), this.getSubText());
		return true;
		}
		
	}
}
