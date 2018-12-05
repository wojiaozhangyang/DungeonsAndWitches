package environment.block;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @包名： environment.block
 * @类名： Door
 * @创建人： 阳阳
 * @类描述： 开关门的判断
 */
public class Door extends Block{
	int pos;
	boolean opened;
	
	//定义关卡开门关门函数细节
	public Door(int x, int y, int width, int height,int pos) {
		super(x, y, width, height);
		setCollision(false);
		this.pos = pos;
		opened = true;
	}
	public void close(){
		opened = false;
		setCollision(true);
	}
	public void open(){
		opened = true;
		setCollision(false);
	}

	@Override
	public void render(Graphics win) {
		win.setColor(Color.WHITE);
		if(!opened){
			win.fillRect((int)this.getBlock().getX(), (int)this.getBlock().getY(), (int)this.getBlock().getWidth(), (int)this.getBlock().getHeight());
		//win.drawImage(sprite.getDoorBlock(),(int)block.getX(),(int)block.getY(),null);
		}else{
			win.drawRect((int)this.getBlock().getX(), (int)this.getBlock().getY(), (int)this.getBlock().getWidth(), (int)this.getBlock().getHeight());
		}
		
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
