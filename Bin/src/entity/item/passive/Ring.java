package entity.item.passive;

import java.awt.Graphics;

import display.NotificationIndicator;
import entity.item.ItemEntity;
import entity.player.Player;

/**
 * @包名： entity.item.passive
 * @类名： Ring
 * @创建人： 阳阳
 * @类描述：手镯+法力+法术值+攻击力 
 */

public class Ring extends ItemEntity{
	NotificationIndicator notifInd = new NotificationIndicator();
	
	public Ring(int entityWidth, int entityHeight) {
		super(32, 32);
		this.setLocation(entityWidth, entityHeight);
		this.setName("手镯");
		this.setSubText("逢凶化吉、遇难呈祥!");
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
		win.drawImage(sprite.getRing(), (int)getEntity().getX(),(int)getEntity().getY()-(int)hoverCurrentDistance, null);
		
	}

	@Override
	public boolean pickup(Player p) {
		if(pickupCooldown>0){
			return false;
		}else{
		p.addMAG(10);
		p.addDamage(1);
		p.addMP(10);
		this.notifInd.ItemNotif(this.getName(), this.getSubText());
		return true;
		}
	}
}
