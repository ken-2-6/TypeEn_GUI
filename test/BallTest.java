import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;

public class BallTest {
	
	@Test
	public void ボールを生成すると自動でランダムな文字列を持つ() throws URISyntaxException {
		Ball.loadWords(getClass().getResource("type_text.txt").toURI());
		Score score = new Score();
		Ball ball = new Ball(score);
		assertEquals(true, ball.getText() != null);
	}

	@Test
	public void 文字列を打ち終わると次の文字列が始まる() throws URISyntaxException {
		Ball.loadWords(getClass().getResource("type_text.txt").toURI());
		Score score = new Score();
		Ball ball = new Ball(score);
		ball.pitch(GameScreen.Team.MY_TEAM);
		String before_text = ball.getText();

		for (int i = 0; i < before_text.length(); i++){
			ball.addTypedChar(before_text.charAt(i), GameScreen.Team.MY_TEAM);
		}
		ball.update();
		assertEquals(false, before_text.equals(ball.getText()));
	}

	@Test
	public void ミスタイピングするとScoreのミスタイプが増える() throws URISyntaxException {
		Ball.loadWords(getClass().getResource("type_text.txt").toURI());
		Score score = new Score();
		Ball ball = new Ball(score);
		ball.pitch(GameScreen.Team.MY_TEAM);

		ball.addTypedChar('!', GameScreen.Team.MY_TEAM);
		ball.addTypedChar('!', GameScreen.Team.MY_TEAM);
		ball.addTypedChar('!', GameScreen.Team.MY_TEAM);
		
		assertEquals(3, score.getMissType());
	}

	
}
