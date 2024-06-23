import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

public class Ranking {

	// ランキング
	private List<RankUser> rankUsers = new ArrayList<>();
	
	public Ranking() {
		// ランキング入力
		loadRanking();
	}
	
	/**
	 * 指定したスコアがランキングに入るかどうか
	 * @param score Score
	 * @return boolean ランキングに入るならtrue
	 */
	public boolean doRankedScore(Score score) {
		for (RankUser u : this.rankUsers) {
			if (u.getPoint() < score.calcPoint())
				return true;
		}
		return false;
	}
	
	/**
	 * 指定したスコアをランキングに入れる
	 * ランキング外のスコアは除外される
	 * @param name ランキングに登録される名前
	 * @param score ランキングに登録するスコア
	 */
	public void updateRank(String name, Score score) {
		this.rankUsers.add(new RankUser(name, score.calcPoint()));
		Collections.sort(this.rankUsers, new RankUserComparator());
		this.rankUsers.remove(3);
		saveRanking();
	}
	
	public List<RankUser> getRanking() {
		return this.rankUsers;
	}
	
	private void loadRanking() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResource("ranking.txt").openStream()));){ 
			String t;
			while ((t = br.readLine()) != null) {
				String data[] = t.split(" ");
				this.rankUsers.add(new RankUser(data[0], Integer.parseInt(data[1])));
			} 
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void saveRanking() {
		try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(getClass().getResource("ranking.txt").toURI()))));){
			for (RankUser u : this.rankUsers) {
				pw.println(u.toString());
			}
			pw.flush();
			pw.close();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * ランキング入りしているユーザーを表すクラス
	 */
	public class RankUser {
		
		private String name;
		private int point;
		
		public RankUser(String name, int point) {
			this.name = name;
			this.point = point;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getPoint() {
			return this.point;
		}
		
		@Override
		public String toString() {
			return name + " " + point;
		}
	}
	
	public class RankUserComparator implements Comparator<RankUser> {
		@Override
		public int compare(RankUser u1, RankUser u2) {
			return Integer.valueOf(u2.getPoint()).compareTo(u1.getPoint());
		}
	}
}
