import java.net.URISyntaxException;

public class Model {

    public static final int MYTEAM = 1;
    public static final int OPPTEAM = 2;

    private View view;
    private Controller controller;

    private Screen screen;

    private Score score;

    
    public Model() throws URISyntaxException {
    	 Ball.loadWords(getClass().getResource("type_text.txt").toURI());
        view = new View(this);
        controller = new Controller(this);
        this.score = new Score();
        this.screen = new StartScreen(this.score);

    }

    public synchronized void processTimeElapsed(int msec) {
        this.screen = this.screen.processTimeElapsed(msec);
        view.repaint();
    }

    public synchronized void processKeyTyped(String typed) {
        this.screen = this.screen.processKeyTyped(typed);
    }

    public synchronized void processMousePressed(int x, int y) {
        // no use
    }

    public void start() {
        controller.start();
    }

    public View getView() {
        return view;
    }

    public Controller getController() {
        return controller;
    }

    public Screen getScreen() {
        return this.screen;
    }

}
