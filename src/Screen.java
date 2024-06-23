import java.awt.Graphics;

public interface Screen {


    public void init();

    public Screen processTimeElapsed(int msec);

    public Screen processKeyTyped(String typed);

    public void paint(Graphics g);
}