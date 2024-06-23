import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;


import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JPanel {

    private Model model;

    private AudioClip sound;

    public View(Model model) {
        this.model = model;

        // 画像を読み込む．画像ファイルは src においておくと bin に自動コピーされる
        //image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("robot.png"));
        // サウンドを読み込む
        //sound = Applet.newAudioClip(getClass().getResource("bomb.wav"));
    }

    /**
     * 画面を描画する
     * @param g  描画用のグラフィックスオブジェクト
     */
    @Override
    public void paintComponent(Graphics g) {
        // 画面をいったんクリア
		clear(g);
		
		
		
        
        // スクリーンの描画
        this.model.getScreen().paint(g);
        

		// 画像の表示例
		// g.drawImage(image, model.getMX(), model.getMY(), this);
		
		// Linux でアニメーションをスムーズに描画するため（描画結果が勝手に間引かれることを防ぐ）
		getToolkit().sync();
    }

    /**
     * 画面を黒色でクリア
     * @param g  描画用のグラフィックスオブジェクト
     */
    public void clear(Graphics g) {
        Dimension size = getSize();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, size.width, size.height);
    }

    public void playBombSound() {
        sound.stop(); // まず音を停めてから
        sound.play(); // 再生する
    }

    
    /**
     * 指定した座標を中心に文字を表示するUtil Method
     * @param g
     * @param text 表示する文字
     * @param x 表示する文字の中央座標x
     * @param y 表示する文字の中央座標y
     */
    public static void drawStringCenter(Graphics g,String text, int x, int y){
		FontMetrics fm = g.getFontMetrics();
		Rectangle rectText = fm.getStringBounds(text, g).getBounds();
		x=x-rectText.width/2;
		y=y-rectText.height/2+fm.getMaxAscent();
        g.drawString(text, x, y);
	}
}
