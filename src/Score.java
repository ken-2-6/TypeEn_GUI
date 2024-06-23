import java.util.List;

public class Score {

    private int myTeamScore = 0;
    private int oppTeamScore = 0;

    private int out     = 0;
    private int strike  = 0;
    private int ball    = 0;

    private int typedNum = 0;
    private int missType = 0;

    // 何回
    private int inning = 0;

    private String username = "";

    private Ranking ranking;


    public Score() {
        this.ranking = new Ranking();
    }

    public void init() {
        this.myTeamScore = 0;
        this.oppTeamScore = 0;
        this.out = 0;
        this.strike = 0;
        this.ball = 0;
    }

    public void nextPlayer() {
        this.strike = 0;
        this.ball = 0;
    }

    public void changeTeam() {
        this.out = 0;
        nextPlayer();
    }

    /**
     * ランキングに利用するポイント
     */
    public int calcPoint() {
        return this.myTeamScore - this.oppTeamScore;
    }

    public boolean doRankIn() {
        return this.ranking.doRankedScore(this);
    }

    public void registerRank() {
        this.ranking.updateRank(this.username, this);
    }

    public List<Ranking.RankUser> getRankUsers() {
        return this.ranking.getRanking();
    }

    public void addMyTeamScore(int add) {
        this.myTeamScore += add;
    }

    public int getMyTeamScore() {
        return this.myTeamScore;
    }

    public void addOppTeamScore(int add) {
        this.oppTeamScore += add;
    }

    public int getOppTeamScore() {
        return this.oppTeamScore;
    }

    public void addStrike() {
        this.strike++;
    }

    public int getStrike() {
        return this.strike;
    }

    public void addOut() {
        this.out++;
    }

    public int getOut() {
        return this.out;
    }

    public void addBall() {
        this.ball++;
    }

    public int getBall() {
        return this.ball;
    }

    public int getMissType() {
        return this.missType;
    }

    public void addMissType() {
        this.missType++;
    }

    public int getTypedNum() {
        return this.typedNum;
    }

    public void addTypedNum() {
        this.typedNum++;
    }
    
    public void setInning(int i) {
        this.inning = i;
    }

    public int getInning() {
        return this.inning;
    }

    public void setUserName(String name) {
        this.username = name;
    }

    public String getUserName() {
        return this.username;
    }
}
