package entity.item.passive;

import java.awt.Graphics;

import display.NotificationIndicator;
import entity.item.ItemEntity;
import entity.player.Player;


/**
 * @包名： entity.item.passive
 * @类名： Gem
 * @创建人： 阳阳
 * @类描述： 宝石->加法力值和法术
 */
public class Gem extends ItemEntity{
	NotificationIndicator notifInd = new NotificationIndicator();
	
	public Gem(int entityWidth, int entityHeight) {
		super(32, 32);
		this.setLocation(entityWidth, entityHeight);
		this.setName("命运蓝宝石");
		this.setSubText("你知道他们是怎么说大宝石的，对么");
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
		win.drawImage(sprite.getGem(), (int)getEntity().getX(),(int)getEntity().getY()-(int)hoverCurrentDistance, null);
		
	}

	@Override
	public boolean pickup(Player p) {
		if(pickupCooldown>0){
			return false;
		}else{
		p.addMAG(10);
		p.addMP(30);
		this.notifInd.ItemNotif(this.getName(), this.getSubText());
		return true;
		}
	}
}
