package environment.block;

import java.awt.Graphics;

/**
 * @包名： environment.block
 * @类名： SolidBlock
 * @创建人： 阳阳
 * @类描述： 绘制墙块
 */
public class SolidBlock extends Block{

	public SolidBlock(int x, int y, int width, int height) {
		super(x, y, width, height);
		setCollision(true);
	}
	
	public void render(Graphics win){
		win.drawImage(sprite.getFloor(),(int)block.getX(),(int)block.getY(),null);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
