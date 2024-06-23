import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;

public class GroundTest {
	
	@Test
	public void ヒット四回打つと一点はいる() {
		Score score = new Score();
		Ground ground = new Ground(score);

		ground.hit(Ball.Result.ONE_BASE_HIT, GameScreen.Team.MY_TEAM);
		//assertEquals(0, score.getMyTeamScore());
		ground.hit(Ball.Result.TWO_BASE_HIT, GameScreen.Team.MY_TEAM);
		//assertEquals(0, score.getMyTeamScore());
		ground.hit(Ball.Result.THREE_BASE_HIT, GameScreen.Team.MY_TEAM);
		//assertEquals(0, score.getMyTeamScore());
		ground.hit(Ball.Result.HOME_RUN, GameScreen.Team.MY_TEAM);
		assertEquals(4, score.getMyTeamScore());
	}

	@Test
	public void ストライクされるとスコアのストライクが増える() {
		Score score = new Score();
		Ground ground = new Ground(score);
		ground.hit(Ball.Result.STRIKE, GameScreen.Team.MY_TEAM);
		ground.hit(Ball.Result.STRIKE, GameScreen.Team.MY_TEAM);
		assertEquals(2, score.getStrike());
	}

	@Test
	public void ボールを更新し続けるといつかはボールが打てる() throws URISyntaxException {
		Ball.loadWords(getClass().getResource("type_text.txt").toURI());
		Score score = new Score();
		Ball ball = new Ball(score);
		ball.pitch(GameScreen.Team.MY_TEAM);
		Ground ground = new Ground(score);
		

		while (!ground.canHitBall(ball)) {
			ball.update();
		}

		assertEquals(true, ground.canHitBall(ball));
	}

	
}
