import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class Ball extends Enemy {
	
	public enum Status {
		WAIT,
		LEAVE_FOR_BATTER,
		LEAVE_FOR_GROUND,
		ON_GROUND;
	
	}
	
	public enum Result {
		NONE("何もないよ"),
		OUT("アウト"),
		STRIKE("ストライク"),
		FOUL("ファール"),
		BALL("ボール"),
		ONE_BASE_HIT("ヒット"),
		TWO_BASE_HIT("ツーベースヒット"),
		THREE_BASE_HIT("スリーベースヒット"),
		HOME_RUN("ホームラン");
		
		private String name;
		
		private Result(String name) {
			this.name = name;
		}
	}
	
	private Status status;
	private Result result;
	
	// アニメーションの時間軸フレーム
	private int animeNum;
	private static final int FALLING_ANIMETION_TIME = 1000 / Controller.DELAY * 1;
	private static final int SX_ANIME = Game.WIN_WIDTH / 2;
	private static final int SY_ANIME = Game.WIN_HEIGHT - 50;

	private int toXAnime = 0;
	private int toYAnime = 0;
	
	private String text;
	private int index = 0;
	
	// タイピングミスなどを管理するスコア
	private Score score;
	
	
	// 結果のレア度
	// 攻撃時のレア度の推移
	private static final List<Ball.Result> batterRate = Arrays.asList(Result.OUT, Result.STRIKE, Result.FOUL, Result.BALL, Result.ONE_BASE_HIT, Result.TWO_BASE_HIT, Result.THREE_BASE_HIT, Result.HOME_RUN);
	// 守備時のレア度の推移
	private static final List<Ball.Result> pitcherRate = Arrays.asList(Result.HOME_RUN, Result.THREE_BASE_HIT, Result.TWO_BASE_HIT, Result.ONE_BASE_HIT, Result.BALL, Result.FOUL, Result.STRIKE, Result.OUT);
	// 現在のレア度のリスト
	private ArrayList<Ball.Result> ballRate;
	
	
	// 乱数生成機
	private Random rnd;
	
	// View
	private Font font = new Font("Arial", Font.BOLD, 20);
	private Image image;
	private double imgRate = 1; // 0~1

	// 入力に利用される文字列たち
	private static LinkedList<String> typeWords = new LinkedList<>();


	/**
	 * 入力する英単語の文字列を取得する. ゲーム起動時に呼び出す必要あり
	 */
	public static void loadWords(URI url) {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(url)));){
			String t;
			while ((t = br.readLine()) != null) 
				typeWords.add(t);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Ball(Score score) {
		super(0, 0);
		this.score = score;
		this.rnd = new Random();
		init(100, 100);
		this.image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ball.png"));
		this.ballRate = new ArrayList<>(Arrays.asList(Ball.Result.STRIKE,Ball.Result.STRIKE,Ball.Result.STRIKE,Ball.Result.STRIKE,
				Ball.Result.STRIKE,Ball.Result.STRIKE,Ball.Result.STRIKE));
		
	}
	
	
	@Override
	public void init(int x, int y) {
		super.init(x, y);
		this.text = randomText();
		this.index = 0;
		this.status = Status.WAIT;
		this.animeNum = 0;
		this.toXAnime = 0;
		this.toYAnime = 0;
		this.result = Result.NONE;
		this.imgRate = 1;
	}

	public String getText() {
		return this.text;
	}
	
	/**
	 * ボールを投げる
	 */
	public void pitch(GameScreen.Team attackerTeam) {
		this.status = Status.LEAVE_FOR_BATTER;
		
		// ボールのレア度を初期化
		List<Ball.Result> rateList = pitcherRate;
		if (attackerTeam == GameScreen.Team.MY_TEAM)
			rateList = batterRate;
		for (int i = 0; i < this.ballRate.size(); i++)
			this.ballRate.set(i, rateList.get(0));
	}
	
	/**
	 * ボールを打つ
	 * レア度を参照してボールの飛ぶ方向を決定する
	 */
	public void hit() {
		this.status = Status.LEAVE_FOR_GROUND;
		// 打たれた結果をランダムで選ぶ
		this.result = this.ballRate.get(rnd.nextInt(this.ballRate.size()));
		
		// 結果をもとにアニメーションさせる
		switch (this.result) {
			case OUT:
				this.toXAnime = 240 * (rnd.nextInt(3) + 1);
				this.toYAnime = 350;
				break;

			case FOUL:
				this.toXAnime = 10 + 650* rnd.nextInt(1);
				this.toYAnime = 500;
				break;
			
			case ONE_BASE_HIT:
				this.toXAnime = 240 * (rnd.nextInt(3) + 1);
				this.toYAnime = 450;
				break;
			
			case TWO_BASE_HIT:
				this.toXAnime = 240 * (rnd.nextInt(3) + 1);
				this.toYAnime = 250;
				break;
			
			case THREE_BASE_HIT:
				this.toXAnime = 240 * (rnd.nextInt(3) + 1);
				this.toYAnime = 150;
				break;

			case
				HOME_RUN:
				this.toXAnime = 100 + 500* rnd.nextInt(1);
				this.toYAnime = 50;
				break;
			
			default:
				// それ以外はアニメーションさせない
				this.status = Status.ON_GROUND;
				break;
		}

	}
	

	@Override
	public void update() {
		// ボールの移動
		if (this.status == Status.LEAVE_FOR_BATTER)
			this.y += 1;

		// 文字列更新
		if (this.index >= this.text.length()) {
			this.text = randomText();
			this.index = 0;
		}
		
		// 落下アニメーションの更新
		if (this.status == Status.LEAVE_FOR_GROUND) {
			this.animeNum++;
			this.x = SX_ANIME + (this.toXAnime - SX_ANIME) / FALLING_ANIMETION_TIME * this.animeNum;
			this.y = SY_ANIME + (this.toYAnime - SY_ANIME) / FALLING_ANIMETION_TIME * this.animeNum;
			this.imgRate = (1 - Math.abs(1 - 1 * (double)this.animeNum / ((double)FALLING_ANIMETION_TIME / 2))) + 0.5;
		}

		// アニメーション終了
		if (this.animeNum > FALLING_ANIMETION_TIME) 
				this.status = Status.ON_GROUND;
	}

	
	/**
	 * ボールのレア度を更新するためのタイピング
	 * @param c 入力された文字
	 */
	public void addTypedChar(char c, GameScreen.Team team) {
		if (!this.isLiving)
			return;
		
		if (this.status != Status.LEAVE_FOR_BATTER)
			return;
		
		this.score.addTypedNum();
		// タイピング失敗したか
		if (this.text.charAt(this.index) != c) {
			this.score.addMissType();
			return;
		}
		// タイピング成功
		this.index++;
		this.improveBallRate(team);
		this.improveBallRate(team);
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public Result getResult() {
		return this.result;
	}
	
	
	@Override
	public void paint(Graphics g) {
		if (!this.isLiving || this.status == Status.WAIT)
			return;

		// ボールの画像を表示
		g.drawImage(this.image, 
			(int)(this.x - this.image.getWidth(null)/2.0*this.imgRate), 
			(int)(this.y - this.image.getHeight(null)/2.0*this.imgRate), 
			(int)(this.x + this.image.getWidth(null)/2.0*this.imgRate), 
			(int)(this.y + this.image.getHeight(null)/2.0*this.imgRate), 
			0,
			0,
			(int)(this.image.getWidth(null)),
			(int)(this.image.getHeight(null)),
			null
		);
		
		
		if (this.status == Status.LEAVE_FOR_BATTER) {
			// タイピング文字の表示
			g.setFont(this.font);
			g.setColor(Color.BLACK);
			g.drawString(this.text.substring(this.index), this.x - this.text.substring(this.index).length()/2 * 13, this.y+10);

			this.drawRarity(g);

		}
	}
	
	private void drawRarity(Graphics g) {
		// ボールのレア度を表示
		g.setColor(Color.WHITE);
		g.setFont(this.font);
		for (int i = 0; i < this.ballRate.size(); i++) {
			g.drawString(this.ballRate.get(i).name, 10, 500 + 20*i);
		}
	}


	private String randomText() {
		int size = typeWords.size();
		return typeWords.get(rnd.nextInt(size));
	}
	
	/**
	 * ボールのレア度を向上させる
	 */
	private void improveBallRate(GameScreen.Team attackerTeam) {
		int rndIndex = this.rnd.nextInt(this.ballRate.size());
		switch (attackerTeam) {
			case MY_TEAM:
				int nowRate = batterRate.indexOf(this.ballRate.get(rndIndex));
				if (nowRate < batterRate.size()-1) {
					this.ballRate.set(rndIndex, batterRate.get(nowRate+1));
				}
				break;
			case OPP_TEAM:
				nowRate = pitcherRate.indexOf(this.ballRate.get(rndIndex));
				if (nowRate < pitcherRate.size()-1) {
					this.ballRate.set(rndIndex, pitcherRate.get(nowRate+1));
				}
				break;
		}
	}
}
