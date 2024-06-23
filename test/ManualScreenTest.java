import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;

public class ManualScreenTest {

    @Test
    public void スペースを押すとスタート画面に戻る() throws URISyntaxException {
        
    	 Ball.loadWords(getClass().getResource("type_text.txt").toURI());
        Score score = new Score();
        Screen s = new ManualScreen(score);

        s = s.processKeyTyped("SPACE");
        assertEquals(true, s instanceof StartScreen);
    }
}