import java.awt.Graphics;

public class GameScreen implements Screen {

    private Score score;

    private Ball ball;
    private Ground ground;
    private Pitcher pitcher;
    private Batter batter;
    private GameMessage gm;

    private Team attackerTeam;

    public GameScreen(Score score) {
        this.score = score;
        this.ball = new Ball(this.score);
        this.ground = new Ground(this.score);
        this.pitcher = new Pitcher(this);
        this.batter = new Batter();
        this.gm = new GameMessage();
        init();
    }

    @Override
    public void init() {
        this.score.init();
        this.score.setInning(9); // 試合開始直後は9回表
        this.gm.startMessage(GameMessage.Status.START_GAME);

        this.attackerTeam = Team.OPP_TEAM;

        // Debug
        //this.attackerTeam = Team.MY_TEAM;
        this.score.addOut();
        this.score.addOut();
        //this.score.addStrike();
        //this.score.addBall();
    }

    @Override
    public Screen processTimeElapsed(int msec) {

        // 選手交代
        if (this.score.getStrike() >= 3) {
            this.score.addOut();
            this.gm.startMessage(GameMessage.Status.OUT);
            this.score.nextPlayer();
        }
        if (this.score.getBall() >= 4) {
            this.ground.hit(Ball.Result.ONE_BASE_HIT, this.attackerTeam);
            this.gm.startMessage(GameMessage.Status.FOUR_BALL);
            this.score.nextPlayer();
        }
          
        // チーム交代 
        if (this.score.getOut() >= 3) {
            this.score.changeTeam();
            this.ground.resetBase();
            this.changeTeam();
            this.gm.startMessage(GameMessage.Status.CHANGE_TEAM_MESSAGE);
        }

        // 試合終了か判定
        if (this.score.getInning() >= 10) {
            // ニックネームを入力必要な場合は入力画面を表示する
            return changeScreen();
        }


        // ボールを投げる
        if (this.ball.getStatus() == Ball.Status.WAIT &&
            this.pitcher.getStatus() == Pitcher.Status.WAIT &&
            this.batter.getStatus() == Batter.Status.WAIT &&
            this.gm.getStatus() == GameMessage.Status.WAIT) {
                this.pitcher.pitch(this.ball);
        }

        // ボールを打つ
        if (this.ball.getStatus() == Ball.Status.LEAVE_FOR_BATTER &&
        	  this.ground.canHitBall(this.ball) &&
        	  this.batter.getStatus() == Batter.Status.WAIT) {
				this.batter.swing(this.ball);
				
        }
        
        if (this.ball.getStatus() == Ball.Status.LEAVE_FOR_GROUND)
        	this.ground.setFallingBallAnimation(true);
        
        
        // 打たれたボールが地面に落下
        if (this.ball.getStatus() == Ball.Status.ON_GROUND) {
        	this.ground.setFallingBallAnimation(false);
            // 得点の加算
        	this.ground.hit(ball.getResult(), this.attackerTeam);
        	this.gm.showBallResult(this.ball);
      
            // 次のボール投げへの準備
        	this.ball.init(0, 0);
        	this.pitcher.init();
        	this.batter.init();
        	
        }
        

        this.ball.update();
        this.pitcher.update();
        this.batter.update();
        this.gm.update();

    	return this;
    }

    @Override
    public Screen processKeyTyped(String typed) {
    	if (typed.equals("ESC")) 
        	return new BossComeScreen(this);
        
        // キーボード入力をボールに送信
        for (int i = 0; i < typed.length(); i++) 
            this.ball.addTypedChar(typed.charAt(i), this.attackerTeam);

    	return this;
    }
    
    public GameScreen.Team getAttackerTeam() {
    	return this.attackerTeam;
    }

    @Override
    public void paint(Graphics g) {
        this.ground.paint(g);
        
        
        if (!ground.isFallingBallAnimation()) {
			this.pitcher.paint(g);
			this.batter.paint(g);
		}
        this.ball.paint(g);
        this.gm.paint(g);
    }

    public enum Team {
        MY_TEAM,
        OPP_TEAM
    }

    /**
     * チーム交代
     */
    private void changeTeam() {
        if (this.attackerTeam == Team.OPP_TEAM)
                this.attackerTeam = Team.MY_TEAM;
        else {
            this.score.setInning(this.score.getInning() + 1);
            this.attackerTeam = Team.OPP_TEAM;
        }
    }
    
    private Screen changeScreen() {
    	if (this.score.doRankIn() && this.score.getUserName().equals(""))
            return new NameTypeScreen(this.score);
        return new ResultScreen(this.score);
    }
}