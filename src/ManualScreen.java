import java.awt.Graphics;
import java.awt.*;

public class ManualScreen implements Screen {

    private Score score;

    private Image backGroundImg;

    private Font titleFont = new Font("Arial", Font.BOLD, 80);
    private Font manualFont = new Font("Arial", Font.BOLD, 20);

    private String[] manualText = {
            "-------------------------------",
            "基本的にボール上に表示される文字を",
            "タイピングするゲームです",
            "ゲーム開始時は守備から始まり、タイピングを",
            "成功させていくことで攻撃や守備の",
            "クオリティが上がります",
            "-------------------------------",
            "SPACEを押すとスタート画面に戻ります"
        };


    public ManualScreen(Score score) {
        this.score = score;
        // 背景画像を取得
        this.backGroundImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("start.png"));
    }
    
    @Override
    public void init() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Screen processTimeElapsed(int msec) {
    	return this;
    }

    @Override
    public Screen processKeyTyped(String typed) {
        if (typed.equals("SPACE") || typed.equals("DEL")) {
            return new StartScreen(this.score);
        }
        if (typed.equals("ESC")) 
        	return new BossComeScreen(this);
    	return this;
    }

    @Override
    public void paint(Graphics g) {
        // 背景表示
        g.drawImage(this.backGroundImg, 0, 0, Game.WIN_WIDTH, Game.WIN_HEIGHT, 0, 0, this.backGroundImg.getWidth(null), this.backGroundImg.getHeight(null), null);
        
    	// タイトル表示
        g.setFont(this.titleFont);
        g.setColor(Color.GREEN);
        View.drawStringCenter(g, "ゲーム説明", Game.WIN_WIDTH / 2, 100);

        // 説明表示
        g.setFont(this.manualFont);
        g.setColor(Color.WHITE);
        for (int i = 0; i < this.manualText.length; i++)
            g.drawString(this.manualText[i], 250, 250 + i*40);
    }
}