import java.awt.*;

import java.util.List;

public class ResultScreen implements Screen {

    private Score score;

    // 背景の画像
    private Image groundImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ground.png"));
    
    // text font 
    private Font titleFont = new Font("Arial", Font.BOLD, 80);
    private Font resultFont = new Font("Arial", Font.BOLD, 30);


    public ResultScreen(Score score) {
        this.score = score;

        // ランキング登録
        if (this.score.doRankIn())
            this.score.registerRank();
    }

    @Override
    public void init() {
        
    }

    @Override
    public Screen processTimeElapsed(int msec) {
    	return this;
    }

    @Override
    public Screen processKeyTyped(String typed) {
        if (typed.equals("SPACE")) {
            return new StartScreen(this.score);
        }
        if (typed.equals("ESC")) 
        	return new BossComeScreen(this);
    	return this;
    }

    @Override
    public void paint(Graphics g) {
        // 背景の表示
        g.drawImage(this.groundImg, 0, 0, Game.WIN_WIDTH, Game.WIN_HEIGHT,
    				0, this.groundImg.getHeight(null)/2, this.groundImg.getWidth(null), this.groundImg.getHeight(null), null);
    	
        // タイトル表示
        g.setFont(this.titleFont);
        g.setColor(Color.GREEN);
        View.drawStringCenter(g, "結果画面", Game.WIN_WIDTH / 2, 100);

        // 結果表示
        g.setFont(this.resultFont);
        g.setColor(Color.WHITE);
        View.drawStringCenter(g, String.format("%s 得点", this.score.getUserName()),  Game.WIN_WIDTH / 2, 200);
        View.drawStringCenter(g, String.format("スコア: %d", this.score.calcPoint()),  Game.WIN_WIDTH / 2, 240);
        View.drawStringCenter(g, String.format("ミスタイプ: %d", this.score.getMissType()), Game.WIN_WIDTH / 2, 280);
        View.drawStringCenter(g, String.format("合計タイプ数: %d", this.score.getTypedNum()), Game.WIN_WIDTH / 2, 320);

        // ランキング表示
        List<Ranking.RankUser> users = this.score.getRankUsers();
        View.drawStringCenter(g, "ランキング", Game.WIN_WIDTH / 2, 400);
        View.drawStringCenter(g, String.format("%s : %d", users.get(0).getName(), users.get(0).getPoint()), Game.WIN_WIDTH / 2, 450);
        View.drawStringCenter(g, String.format("%s : %d", users.get(1).getName(), users.get(1).getPoint()), Game.WIN_WIDTH / 2, 500);
        View.drawStringCenter(g, String.format("%s : %d", users.get(2).getName(), users.get(2).getPoint()), Game.WIN_WIDTH / 2, 550);
    }
}