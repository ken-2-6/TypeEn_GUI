import java.awt.Graphics;

public abstract class Enemy {

	protected int x;
	protected int y;

	protected boolean isLiving;
	
	public Enemy() {
		this(0, 0);
	}
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		isLiving = true;
	}
	
	public void init(int x, int y) {
		this.x = x;
		this.y = y;
		isLiving = true;
	}
	
	public void update() {
		
	}
	
	public void paint(Graphics g) {
		
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	public boolean isLiving() {
		return this.isLiving;
	}

	public void setIsLiving(boolean is) {
		this.isLiving = is;
	}
}
