package com.example.game;

public class PuzzleStats
{
	private int plays;
	private int best;
	private float avg;
	private boolean newBest;

	public PuzzleStats(int plays, int best, float avg, boolean newBest)
	{
		this.plays = plays;
		this.best = best;
		this.avg = avg;
		this.newBest = newBest;
	}

	public int getPlays()
	{
		return plays;
	}

	public int getBest()
	{
		return best;
	}

	public float getAvg()
	{
		return avg;
	}

	public boolean isNewBest()
	{
		return newBest;
	}
}