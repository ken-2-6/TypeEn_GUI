import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class Batter extends Enemy {

    private Status status;
    
    private Image image;
    
    private Ball ball;
    
    
    // アニメーション専用変数
    private int num = 0;
    
    
    public Batter() {
    	this.image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("batter.png"));
    	init();
    }


    public void init() {
    	this.status = Status.WAIT;
    	this.ball = null;
    }

    /**
     * 指定されたボールを打つ
     * ボールを打つことでボールのステータスを更新される副作用あり
     * @param ball 打たれるボール
     */
    public void swing(Ball ball) {
        this.status = Status.SWING;
        this.ball = ball;
    }

    @Override
    public void update() {
    	
    	// animation
    	switch(this.status) {
	    	case SWING:
	    		this.num++;
	    		if ((double)num > 1000.0 / Controller.DELAY * 0.25) {
	    			this.num = 0;
	    			this.status = Status.SWUNG;
	    		}
	    		break;
	    	case SWUNG:
	    		this.num++;
	    		if ((double)num > 1000.0 / Controller.DELAY * 0.25) {
	    			ball.hit();
	    			init();
	    		}
				break;
			default:
				break;
    	}
    }

    @Override
    public void paint(Graphics g) {
    	// バッターの画像をStatusに合わせて切り抜き
    	g.drawImage(this.image, 
    			Game.WIN_WIDTH / 2 + 20, 
    			Game.WIN_HEIGHT - 200,
    			Game.WIN_WIDTH / 2 + 20 +150,
    			Game.WIN_HEIGHT - 200 + 150,
    			0,
    			32*this.status.ordinal(),
    			32,
    			32*this.status.ordinal() + 32,
    			null);
    }

    public enum Status {
        WAIT,
        SWING,
        SWUNG
    }

	public Status getStatus() {
		return this.status;
	}
}