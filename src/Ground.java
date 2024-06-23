import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Ground extends Enemy {

    // 全塁にプレイヤーがいるか
    private boolean[] baseInPlayer = new boolean[4];

    private Score score;
    
    private Image groundImg;
    private Image runnerImg;
    private Image bsoImg;
    
    private Font scoreFont = new Font("Arial", Font.BOLD, 15);

    
    private boolean fallingBall = false;
    

    public Ground(Score score) {
        this.score = score;
        this.groundImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ground.png"));
        this.runnerImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("runner.png"));
        this.bsoImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("bso.png"));
        resetBase();
    }

    public void init() {
        resetBase();
    }

    public void resetBase() {
        for (int i = 0; i < this.baseInPlayer.length; i++) 
            baseInPlayer[i] = false;
    }

    /**
     * 指定したボールがバッターが打てる距離にいるか
     * @param ball 投げられたボール
     * @return 打てるならtrue
     */
    public boolean canHitBall(Ball ball) {
        return ball.getY() > Game.WIN_HEIGHT * 3/4;
    }

    /**
     * バッターがボールが撃つまたは見逃すとスコアを更新する
     * @param ball 撃たれたボール
     * @param team ボールを打ったチーム
     */
    public void hit(Ball.Result status, GameScreen.Team team) {
        int moveOnNum = -1;
        switch(status) {

            case OUT:
            	System.out.println("アウト");
                this.score.addOut();
                System.out.println(this.score.getOut());
                return;
            
            case STRIKE:
            	System.out.println("ストライク");
                this.score.addStrike();
                System.out.println(this.score.getStrike());
                return;

            case BALL:
            	System.out.println("ボール");
                this.score.addBall();
                System.out.println(this.score.getBall());
                return;
            
            case FOUL:
                if (this.score.getStrike() <= 1)
                    this.score.addStrike();
                return;
            case ONE_BASE_HIT:
                moveOnNum = 1;
                break;
            case TWO_BASE_HIT:
                moveOnNum = 2;
                break;
            case THREE_BASE_HIT:
                moveOnNum = 3;
                break;
            case HOME_RUN:
                moveOnNum = 4;
                break;
            default:
                return;
        }
        // 打者を0塁にいると仮定して打ったスコアによって移動開始
        this.baseInPlayer[0] = true;

        // 塁に出ている人たちの動かす
        for (int i = 3; i >= 0; i--) {
            if (!this.baseInPlayer[i]) 
                continue;

            // ホームベースに戻った
            this.baseInPlayer[i] = false;
            if (i+moveOnNum >= 4) 
                this.addTeamScore(score, team);
            else
                this.baseInPlayer[i+moveOnNum] = true;
        }
        this.score.nextPlayer();
    }

    @Override
    public void update() {
    	
        throw new UnsupportedOperationException();
    }
    
    
    public void setFallingBallAnimation(boolean is) {
    	this.fallingBall = is;
    }
    
    public boolean isFallingBallAnimation() {
    	return this.fallingBall;
    }

    @Override
    public void paint(Graphics g) {
    	//ボールが落下中の時は背景しか表示しないようにする
    	if (this.fallingBall) {
    		g.drawImage(this.groundImg, 0, 0, Game.WIN_WIDTH, Game.WIN_HEIGHT,
    				0, this.groundImg.getHeight(null)/2, this.groundImg.getWidth(null), this.groundImg.getHeight(null), null);
    		return;
    	}
    	// グランド背景画像の表示
    	g.drawImage(this.groundImg, 0, 0, Game.WIN_WIDTH, Game.WIN_HEIGHT, 0, 0, this.groundImg.getWidth(null), this.groundImg.getHeight(null)/2, null);

        // ストライク・ボール・アウト数を表示
        for (int i = 0; i < this.score.getBall(); i++)
            g.drawImage(this.bsoImg, 520+i*32, 530, 520+i*32+32, 530+32, 0, 0, 32, 32, null);
        for (int i = 0; i < this.score.getStrike(); i++) 
            g.drawImage(this.bsoImg, 520+i*32, 560, 520+i*32+32, 560+32, 0, 32, 32, 64, null);
        for (int i = 0; i < this.score.getOut(); i++)
            g.drawImage(this.bsoImg, 520+i*32, 590, 520+i*32+32, 590+32, 0, 64, 32, 96, null);

        
        // 得点の表示
        g.setColor(Color.WHITE);
        g.setFont(this.scoreFont);
        g.drawString(String.format("相: %d点  自: %d点", this.score.getOppTeamScore(), this.score.getMyTeamScore()), 520, 500);
    	
    	// 走者の表示
    	if (this.baseInPlayer[1])
    		g.drawImage(this.runnerImg, 580, 250, 580+100, 250+100, 0, 0, 0+32, 0+32, null);
    	
    	if (this.baseInPlayer[2])
    		g.drawImage(this.runnerImg, 250, 50, 250+100, 50+100, 0, 64, 0+32, 64+32, null);
    
    	if (this.baseInPlayer[3])
    		g.drawImage(this.runnerImg, 30, 250, 30+100, 250+100, 0, 32, 0+32, 32+32, null);
    }


    private void addTeamScore(Score score, GameScreen.Team team) {
         if (team == GameScreen.Team.MY_TEAM)
            this.score.addMyTeamScore(1);
        else if (team == GameScreen.Team.OPP_TEAM)
            this.score.addOppTeamScore(1);
    }   
}