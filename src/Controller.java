import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

public class Controller implements ActionListener, KeyListener, MouseListener {

    public static final int DELAY = 16; // msec
    private Model model;
    private Timer timer;

    public Controller(Model model) {
        // モデルを保持（イベントの通知先）
        this.model = model;
        timer = new Timer(DELAY, this);
    }

    public void start() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.processTimeElapsed(DELAY);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        model.processKeyTyped(Character.toString(e.getKeyChar()));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String s = "";
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                s = "UP";
                break;
            case KeyEvent.VK_DOWN:
                s = "DOWN";
                break;
            case KeyEvent.VK_SPACE:
                s = "SPACE";
                break;
            case KeyEvent.VK_DELETE:
                s = "DEL";
                break;
            case KeyEvent.VK_ENTER :
                s = "ENTER";
                break;
            case KeyEvent.VK_ESCAPE:
            	  s = "ESC";
            	  break;
            default:
                break;
        }
        model.processKeyTyped(s);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // do nothing
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // do nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        model.processMousePressed(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // do nothing
    }
}