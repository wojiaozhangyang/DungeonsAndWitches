package environment.block;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @包名： environment.block
 * @类名： BossDoor
 * @创建人： 阳阳
 * @类描述：绘制BOSS关卡的门=红色
 */
public class BossDoor extends Door{

	public BossDoor(int x, int y, int width, int height, int pos) {
		super(x, y, width, height, pos);
		// TODO Auto-generated constructor stub
	}
	public void render(Graphics win) {
		win.setColor(Color.RED);
		//如果  门！=开    //实心框绘制     else 空心框绘制
		if(!opened){
			win.fillRect((int)this.getBlock().getX(), (int)this.getBlock().getY(), (int)this.getBlock().getWidth(), (int)this.getBlock().getHeight());
		//win.drawImage(sprite.getDoorBlock(),(int)block.getX(),(int)block.getY(),null);
		}else{
			win.drawRect((int)this.getBlock().getX(), (int)this.getBlock().getY(), (int)this.getBlock().getWidth(), (int)this.getBlock().getHeight());
		}
		
	}
}
