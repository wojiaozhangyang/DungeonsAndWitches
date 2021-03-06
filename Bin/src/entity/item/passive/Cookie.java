package entity.item.passive;

import java.awt.Graphics;

import display.NotificationIndicator;
import entity.item.ItemEntity;
import entity.player.Player;


/**
 * @包名： entity.item.passive
 * @类名： Cookie
 * @创建人： 阳阳
 * @类描述： 饼干->加暴击和攻速
 */
public class Cookie extends ItemEntity{
	NotificationIndicator notifInd = new NotificationIndicator();
	
	public Cookie(int entityWidth, int entityHeight) {
		super(32, 32);
		this.setLocation(entityWidth, entityHeight);
		this.setName("饼干");
		this.setSubText("香脆的小饼干，入口香甜，emmmmmmm");
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
		win.drawImage(sprite.getCookie(), (int)getEntity().getX(),(int)getEntity().getY()-(int)hoverCurrentDistance, null);
		
	}

	@Override
	public boolean pickup(Player p) {
		if(pickupCooldown>0){
			return false;
		}else{
		p.addCriticalChance(4);
		p.addAttackSpeed(.1);
		this.notifInd.ItemNotif(this.getName(), this.getSubText());
		return true;
		}
	}
}
