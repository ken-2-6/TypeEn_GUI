import java.awt.*;

public class BossComeScreen implements Screen{

	private Screen screen;
	
	private Image backImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("boss.png"));;

	
	public BossComeScreen(Screen screen) {
		this.screen = screen;
	}
	
	@Override
	public void init() {
		// no use
	}
	
	@Override
    public Screen processTimeElapsed(int msec) {
		return this;
	}
	
	@Override
    public Screen processKeyTyped(String typed) {
		// 元の画面を表示
		if (typed.equals("ESC")) {
			return this.screen;
		}
		return this;
	}
	
	@Override
	public void paint(Graphics g) {
		// 背景の表示
        g.drawImage(this.backImage, 0, 0, Game.WIN_WIDTH, Game.WIN_HEIGHT,
    				0, 0, this.backImage.getWidth(null), this.backImage.getHeight(null), null);
	}
}
