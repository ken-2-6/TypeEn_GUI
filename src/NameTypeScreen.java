import java.awt.*;

public class NameTypeScreen implements Screen {

    private Score score;

    private Image bgImg;

    private Font titleFont = new Font("Arial", Font.BOLD, 80);
    private Font typeFont = new Font("Arial", Font.BOLD, 30);

    private String username = "";


    public NameTypeScreen(Score score) {
        this.score = score;
        this.bgImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ground.png"));
    
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
        if (typed.equals("SPACE") || typed.equals("UP") || typed.equals("DOWN"))
            return this;
        
        // 文字を消す処理
        if (typed.equals("DEL")) {
            this.username = this.username.substring(0, this.username.length() - 1);
            return this;
        }

        // ニックネーム入力を終了する処理
        if (typed.equals("ENTER")) {
            this.score.setUserName(this.username);
            return new ResultScreen(this.score);
        }

        // 入力された文字をニックネームに加える
        this.username = this.username + typed;

        return this;
    }

    @Override
    public void paint(Graphics g) {
        // 背景の表示
        g.drawImage(this.bgImg, 0, 0, Game.WIN_WIDTH, Game.WIN_HEIGHT,
    				0, this.bgImg.getHeight(null)/2, this.bgImg.getWidth(null), this.bgImg.getHeight(null), null);

        // タイトル表示
        g.setFont(this.titleFont);
        g.setColor(Color.GREEN);
        View.drawStringCenter(g, "ニックネームを入力", Game.WIN_WIDTH / 2, 100);

        // 説明表示
        g.setFont(this.typeFont);
        g.setColor(Color.WHITE);
        View.drawStringCenter(g, "ランキングに乗ることになりました！", Game.WIN_WIDTH / 2, 200);
        View.drawStringCenter(g, "登録するニックネームを入力してください", Game.WIN_WIDTH / 2, 230);
        View.drawStringCenter(g, "Enterを押すことで入力を終了します", Game.WIN_WIDTH / 2, 260);

        // username 表示
        View.drawStringCenter(g, this.username, Game.WIN_WIDTH / 2, 300);
    }
}