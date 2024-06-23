import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class Pitcher extends Enemy {

    private Status status;

    
    private int num = 0;
    
    private Image image;
    
    private Ball ball;
    
    private GameScreen gs;
    
    
    public Pitcher(GameScreen gs) {
    	this.gs = gs;
    	this.init();
    	this.image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("pitcher.png")); 
    	this.x = Game.WIN_WIDTH / 2;
    	this.y = Game.WIN_HEIGHT / 2 - 150;
    }

    public void init() {
        this.status = Status.WAIT;
    }

    
    /**
     * 指定したボールを投げる
     * ボールは投げられることでステータスが変わる副作用あり
     * @param ball 投げられるボール
     */
    public void pitch(Ball ball) {
        this.status = Status.THROWING_1;
        this.ball = ball;
    }

    @Override
    public void update() {

    	switch (this.status) {
	    	case THROWING_1:
	    		this.num++;
	    		if ((double)num > 1000.0 / Controller.DELAY * 1) {
	    			this.status = Status.THROWING_2;
	    			this.num = 0;
	    		}
	    		break;
	    	case THROWING_2:
	    		this.num++;
	    		if ((double)num > 1000.0 / Controller.DELAY * 0.5) {
	    			this.status = Status.THREW;
	    			this.ball.init(this.x, this.y);
	    			this.ball.pitch(gs.getAttackerTeam());
	    		}
	    		break;
	    	default:
	    		break;
    	}
    }

    @Override
    public void paint(Graphics g) {
    	g.drawImage(this.image, 
    			this.x - 50, 
    			this.y - 50,
    			this.x + 50,
    			this.y + 50,
    			0,
    			32*this.status.ordinal(),
    			32,
    			32*this.status.ordinal() + 32,
    			null);
    }

    public enum Status {
        WAIT,
        THROWING_1,
        THROWING_2,
        THREW
    }

	public Status getStatus() {
		return this.status;
	}
}