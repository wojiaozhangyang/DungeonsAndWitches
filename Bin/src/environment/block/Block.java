package environment.block;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.util.Sprite;
import main.util.SpriteLoader;

/**
 * @包名： environment.block
 * @类名： Block
 * @创建人： 阳阳
 * @类描述：绘制墙块 
 */
public abstract class  Block {
	
	Rectangle block;
	
	Sprite sprite = new Sprite();
	
	boolean collision;
	
	public Block(int x, int y, int width, int height){
		block = new Rectangle(x,y,width,height);
	}
	
	public boolean setCollision(boolean input){
		collision = input;
		return collision;
	}
	public boolean getCollision(){
		return collision;
	}
	
	public Rectangle getBlock(){
		return block;
	}
	public void move(int x, int y){
		block.translate(x, y);
	}
	public abstract void render(Graphics win);
	
	public abstract void tick();
}
