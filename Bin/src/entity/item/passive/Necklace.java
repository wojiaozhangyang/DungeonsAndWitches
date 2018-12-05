package entity.item.passive;

import java.awt.Graphics;

import display.NotificationIndicator;
import entity.item.ItemEntity;
import entity.player.Player;

/**
 * @包名： entity.item.passive
 * @类名： Necklace
 * @创建人： 阳阳
 * @类描述：项链+攻击+法术+防御+攻击速度 
 */
public class Necklace extends ItemEntity{
	NotificationIndicator notifInd = new NotificationIndicator();
	
	public Necklace(int entityWidth, int entityHeight) {
		super(32, 32);
		this.setLocation(entityWidth, entityHeight);
		this.setName("项链");
		this.setSubText("阳光下闪闪发光,戴在脖子上显的更加美丽");
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
		win.drawImage(sprite.getNecklace(), (int)getEntity().getX(),(int)getEntity().getY()-(int)hoverCurrentDistance, null);
		
	}

	@Override
	public boolean pickup(Player p) {
		if(pickupCooldown>0){
			return false;
		}else{
		p.addDamage(1);
		p.addMAG(8);
		p.addArmor(5);
		p.addAttackSpeed(.3);
		this.notifInd.ItemNotif(this.getName(), this.getSubText());
		return true;
		}
	}
}
