import java.awt.Graphics;
import java.awt.*;

public class StartScreen implements Screen {

    private Score score;

    private Image bgImg;
    private Image ballImg;

    private Font titleFont = new Font("Arial", Font.BOLD, 80);
    private Font selectFont = new Font("Arial", Font.BOLD, 40);
    private Font manualFont = new Font("Arial", Font.BOLD, 20);

    private int index = 0;
    

    public StartScreen(Score score) {
        this.score = score;
        this.bgImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("start.png"));
        this.ballImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ball.png"));
        this.init();
    }
    
    @Override
    public void init() {
        this.index = 0;
    }

    @Override
    public Screen processTimeElapsed(int msec) {
    	return this;
    }

    @Override
    public Screen processKeyTyped(String typed) {
    	if (typed.equals("ESC")) 
        	return new BossComeScreen(this);
        // 矢印操作
        if (typed.equals("UP")) 
            this.index = (this.index - 1 + 3)%3;
        if (typed.equals("DOWN"))
            this.index = (this.index + 1 + 3)%3;

        // 次の画面決定
        if (typed.equals("SPACE")) {
            return changeScreen();
        }
        
    	return this;
    }

    @Override
    public void paint(Graphics g) {
    	// 背景表示
        g.drawImage(this.bgImg, 0, 0, Game.WIN_WIDTH, Game.WIN_HEIGHT, 0, 0, this.bgImg.getWidth(null), this.bgImg.getHeight(null), null);
        
        // タイトル表示
        g.setFont(this.titleFont);
        g.setColor(Color.GREEN);
        View.drawStringCenter(g, "タイプ園", Game.WIN_WIDTH / 2, 100);
        
        // セレクタ表示
        g.drawImage(this.ballImg, 250, 290+this.index*60, null);
        g.setFont(this.selectFont);
        g.setColor(Color.WHITE);
        g.drawString("START GAME", 300, 320);
        g.drawString("MANUAL", 300, 380);
        g.drawString("QUIT GAME", 300, 440);
        g.setFont(this.manualFont);
        g.drawString("↑↓ SPACE : 矢印操作", 450, 520);
    }
    
    private Screen changeScreen() {
    	if (this.index == 0) 
            return new GameScreen(this.score);
        else if (this.index == 1)
            return new ManualScreen(this.score);
    	return this;
    }
}