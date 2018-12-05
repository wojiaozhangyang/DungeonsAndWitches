package environment.block;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @包名： environment.block
 * @类名： TreasureDoor
 * @创建人： 阳阳
 * @类描述：绘制含有宝箱的房间门样式 
 */
public class TreasureDoor extends Door{
	//绘制宝箱的房间门
	public TreasureDoor(int x, int y, int width, int height, int pos) {
		super(x, y, width, height, pos);
		// TODO Auto-generated constructor stub
	}
	public void render(Graphics win) {
		win.setColor(Color.YELLOW);
		if(!opened){
			win.fillRect((int)this.getBlock().getX(), (int)this.getBlock().getY(), (int)this.getBlock().getWidth(), (int)this.getBlock().getHeight());
		//win.drawImage(sprite.getDoorBlock(),(int)block.getX(),(int)block.getY(),null);
		}else{
			win.drawRect((int)this.getBlock().getX(), (int)this.getBlock().getY(), (int)this.getBlock().getWidth(), (int)this.getBlock().getHeight());
		}
		
	}
}
