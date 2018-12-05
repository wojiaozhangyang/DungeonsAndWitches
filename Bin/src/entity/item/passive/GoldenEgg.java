package entity.item.passive;

import java.awt.Graphics;

import display.NotificationIndicator;
import entity.item.ItemEntity;
import entity.player.Player;


/**
 * @包名： entity.item.passive
 * @类名： GoldenEgg
 * @创建人： 阳阳
 * @类描述： 金蛋->加暴击+法力+攻击+法术值
 */
public class GoldenEgg extends ItemEntity{
NotificationIndicator notifInd = new NotificationIndicator();
	
	public GoldenEgg(int entityWidth, int entityHeight) {
		super(32, 32);
		this.setLocation(entityWidth, entityHeight);
		this.setName("金蛋");
		this.setSubText("Mmm... 美味的早餐!");
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
		g.drawImage(sprite.getGoldenEgg(), (int)getEntity().getX(),(int)getEntity().getY()-(int)hoverCurrentDistance, null);
		
	}

	@Override
	public boolean pickup(Player p) {
		if(pickupCooldown>0){
			return false;
		}else{
		p.addCriticalChance(1);
		p.addMP(10);
		p.addDamage(1);
		p.addMAG(5);
		this.notifInd.ItemNotif(this.getName(), this.getSubText());
		return true;
		}
	}
}
