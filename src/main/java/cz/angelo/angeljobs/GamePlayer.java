package cz.angelo.angeljobs;

import org.bukkit.entity.Player;

public class GamePlayer {

	private int level;
	private Player player;
	private Jobs jobs;
	private double exp;
	private double mexp;
	private double mmoney;

	public GamePlayer(Player player, int level, double exp, Jobs jobs, double mexp, double mmoney){
		this.player = player;
		this.level = level;
		this.jobs = jobs;
		this.exp = exp;
		this.mexp = mexp;
		this.mmoney = mmoney;
	}


	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Jobs getJobs() {
		return jobs;
	}

	public void setJobs(Jobs jobs) {
		this.jobs = jobs;
	}

	public double getExp() {
		return exp;
	}

	public void setExp(double exp) {
		this.exp = exp;
	}

	public double getMexp() {
		return mexp;
	}

	public void setMexp(double mexp) {
		this.mexp = mexp;
	}

	public double getMmoney() {
		return mmoney;
	}

	public void setMmoney(double mmoney) {
		this.mmoney = mmoney;
	}
}
