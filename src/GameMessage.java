import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameMessage extends Enemy {

    private Status status;
    
    private int num;
    
    private static final int MESSAGE_SHOW_TIME = 1000 / Controller.DELAY * 2;
    
    // View
    Font logFont = new Font("Arial", Font.BOLD, 60);
    

    public GameMessage() {
        num = 0;
        this.status = Status.WAIT;
        this.x = Game.WIN_WIDTH / 2;
        this.y = Game.WIN_HEIGHT / 2;
    }

    public void showBallResult(Ball ball) {
        Status _status = Status.WAIT;
        switch (ball.getResult()) {
            case STRIKE:
                _status = Status.STRIKE;
                break;
            case FOUL:
                _status = Status.FOUL;
                break;
            case BALL:
                _status = Status.BALL;
                break;
            case ONE_BASE_HIT:
                _status = Status.ONE_BASE_HIT_MESSAGE;
                break;
            case TWO_BASE_HIT:
                _status = Status.TWO_BASE_HIT_MESSAGE;
                break;
            case THREE_BASE_HIT:
                _status = Status.THREE_BASE_HIT_MESSAGE;
                break;
            case HOME_RUN:
                _status = Status.HOMERUN_MESSAGE;
                break;
            default:
            		return;
            	  
        }
        startMessage(_status);
    }


    public void startMessage(Status status) {
        this.status = status;
        this.num = 0;
    }


    public Status getStatus() {
        return this.status;
    }


    @Override
    public void update() {
        if (this.status != Status.WAIT) {
            num++;
            
            // メッセージ終了条件
            if (num >= MESSAGE_SHOW_TIME)
                this.status = Status.WAIT;
            
        }
    }


    @Override
    public void paint(Graphics g) {
    	if (this.status == Status.WAIT)
    		return;
    	
    	String text = "";
    
    	switch(this.status) {
    		case START_GAME:
    			text = "PLAY BALL!";
    			g.setColor(Color.RED);
    			break;
    		
    		case STRIKE:
    			text = "ストライク";
    			g.setColor(Color.ORANGE);
    			break;
            
            case BALL:
                text = "ボール";
    			g.setColor(Color.GREEN);
    			break;
            
            case FOUL:
                text = "ファール";
                g.setColor(Color.ORANGE);
                break;
            
            case OUT:
                text = "アウト";
                g.setColor(Color.RED);
                break;
            
            case ONE_BASE_HIT_MESSAGE:
                text = "ヒット";
                g.setColor(Color.BLUE);
                break;
            
            case TWO_BASE_HIT_MESSAGE:
                text = "ツーベースヒット";
                g.setColor(Color.GREEN);
                break;

            case THREE_BASE_HIT_MESSAGE:
                text = "スリーベースヒット";
                g.setColor(Color.ORANGE);
                break;

            case HOMERUN_MESSAGE:
                text = "ホームラン";
    			g.setColor(Color.YELLOW);
    			break;

            
            case CHANGE_TEAM_MESSAGE:
                text = "先攻後攻交代";
    			g.setColor(Color.RED);
    			break;

            case FOUR_BALL:
                text = "フォアボール";
    			g.setColor(Color.BLUE);
    			break;

            default:
    			break;
    		
    	}
    	if (!text.equals("")) {
            g.setFont(this.logFont);
    		View.drawStringCenter(g, text, this.x, this.y);
        }
    }


    public enum Status {
        WAIT,
        START_GAME,
        STRIKE,
        FOUL,
        BALL,
        OUT,
        ONE_BASE_HIT_MESSAGE,
        TWO_BASE_HIT_MESSAGE,
        THREE_BASE_HIT_MESSAGE,
        HOMERUN_MESSAGE,
        CHANGE_TEAM_MESSAGE,
        FOUR_BALL
        
    }
}