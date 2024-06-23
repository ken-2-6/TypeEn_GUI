import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;

public class GameScreenTest {

	@Test
	public void アウトが3で攻撃チームがMYTEAMならば試合終了する() throws URISyntaxException {

		Ball.loadWords(getClass().getResource("type_text.txt").toURI());
		Score score = new Score();
		Screen s = new GameScreen(score);


		for (int i = 0; i < 3; i++)
			score.addOut();
		s = s.processTimeElapsed(1);
		for (int i = 0; i < 3; i++)
			score.addOut();
		s = s.processTimeElapsed(1);
		assertEquals(true, s instanceof ResultScreen);
	}
}