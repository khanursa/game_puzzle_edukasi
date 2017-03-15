package com.example.game;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Puzzle
{
	public static final int DIRECTION_LEFT = 0;
	public static final int DIRECTION_UP = 1;
	public static final int DIRECTION_RIGHT = 2;
	public static final int DIRECTION_DOWN = 3;

	public static final int[] DIRECTION_X = {-1, 0, +1, 0}; 
	public static final int[] DIRECTION_Y = {0, -1, 0, +1};
	
	private int[] tiles;
	private int[] acak;
	private int handleLocation;
	private int moveCount;
	
	private Algoritma algoritma = new Algoritma();
	private int width;
	private int height;
	int BATAS_X = width +1;
	int BATAS_Y = height +1;
	
	private Puzzle parent;
	private Kotak[] kotak;
	
	
	public void init(int width, int height)
	{
		this.width = width;
		this.height = height;
		tiles = new int[width * height];
		
		for(int i = 0; i < tiles.length; i++)
		{
			tiles[i] = i;
		}
		handleLocation = tiles.length-1;
		
		moveCount = 0;
	}
	
	public boolean isSolved()
	{
		for(int i = 0; i < tiles.length; i++)
		{
			if(tiles[i] != i)
			{
				return false;
			}
		}

		return true;
	}
	
	public void setTiles(int[] tiles)
	{
		this.tiles = tiles;
		
		for(int i = 0; i < tiles.length; i++)
		{
			if(tiles[i] == tiles.length - 1)
			{
				handleLocation = i;
				break;
			}
		}
	}
	
	public int[] getTiles()
	{
		return tiles; // should not be written
	}
	
	public int getColumnAt(int location)
	{
		return location % width;
	}
	
	public int getRowAt(int location)
	{
		return location / width;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int hammingDistance()
	{
		int hamming = 0;
		
		for(int i = 0; i < tiles.length; i++)
		{
			hamming += Math.abs(i - tiles[i]);
		}
		
		return hamming;
	}
	
public void AlgoritmaAstar(){
	algoritma.prosesAlgoritma();
	ExecutorService executor = Executors.newCachedThreadPool();
    Runnable aksi = new Runnable() {

        Iterator<Puzzle> iter = algoritma.getListPuzzle().iterator();
        Puzzle curPuzzle;
		private Puzzle papan;
        @Override
        public void run() {
            while (iter.hasNext()) {
                curPuzzle = iter.next();
                if (curPuzzle.getParent() != null) {
                    papan = null;
					Direction arah = Direction.getArahGerakan(
                            papan.getKotak(0).getKoordinatX(),
                            papan.getKotak(0).getKoordinatY(),
                            curPuzzle.getKotak(0).getKoordinatX(),
                            curPuzzle.getKotak(0).getKoordinatY());
                    if (arah != null) {
                        Kotak curKotak = papan.getKotak(0);
                        Kotak kotakTujuan = papan.getKotakSekitar(curKotak, arah);
                        kotakTujuan.getKoordinatX();
                        kotakTujuan.getKoordinatY();

                        
                        
                        curKotak.swap(kotakTujuan);
                    }
                }
            }
        }
    };
    executor.submit(aksi);
    executor.shutdown();
    }
	
	public void scramble()
	{

		int[] yates = tiles;
		if(width < 2 || height < 2)
		{
			return;
		}else if (width >= 2 || height >= 2)
		{
			algoritma.fisher(yates, width, height);
			acak = yates;
			for(int n = 0; n < yates.length; n++)
			{
				if(yates[n] == yates.length-1)
				{
					handleLocation = n;
					break;
				}
			}
		}
		moveCount = 0;
	}
	
	public int salahTempat(){
		int salahtempat = 0;
		for(int n = 0;n < acak.length;n++){
			for(int k = 0;k < tiles.length;k++){
				if (!(acak[n] == tiles[k])){
					salahtempat++;
				}
			}
		}
		return salahtempat;
	}

	

	public boolean move(int direction, int count)
	{
		boolean match = false;
		
		for(int i = 0; i < count; i++)
		{
			int targetLocation = handleLocation + DIRECTION_X[direction] + DIRECTION_Y[direction] * width;
			tiles[handleLocation] = tiles[targetLocation];
			match |= tiles[handleLocation] == handleLocation;
			tiles[targetLocation] = tiles.length - 1; // handle tile
			handleLocation = targetLocation;
		}
		
		moveCount++;
		return match;
	}
	
	public void Algoritmaastar(){
	}
	
	public int getPossibleMoves()
	{
		int x = getColumnAt(handleLocation);
		int y = getRowAt(handleLocation);
		
		boolean left = x > 0;
		boolean right = x < width - 1;
		boolean up = y > 0;
		boolean down = y < height - 1;
		
		return (left ? 1 << DIRECTION_LEFT : 0) |
				(right ? 1 << DIRECTION_RIGHT : 0) |
				(up ? 1 << DIRECTION_UP : 0) |
				(down ? 1 << DIRECTION_DOWN : 0);
	}

	public int getDirection(int location)
	{
		int delta = location - handleLocation;
		
		if(delta % width == 0)
		{
			return delta < 0 ? DIRECTION_UP : DIRECTION_DOWN;
		}
		else if(handleLocation / width == (handleLocation + delta) / width)
		{
			return delta < 0 ? DIRECTION_LEFT : DIRECTION_RIGHT;
		}
		else
		{
			return -1;
		}
	}
	
	public void moveToKotakSekitar(Kotak curKotak, Direction arah) throws PosisiKotakSalah {
        Kotak kotakTujuan = getKotakSekitar(curKotak, arah);
        if (kotakTujuan == null) {
            throw new PosisiKotakSalah();
        }
        int targetX = kotakTujuan.getKoordinatX();
        int targetY = kotakTujuan.getKoordinatY();
        kotakTujuan.setKoordinat(curKotak.getKoordinatX(), curKotak.getKoordinatY());
        curKotak.setKoordinat(targetX, targetY);
        curKotak.swap(kotakTujuan);
    }
	
	public Kotak getKotakSekitar(Kotak k, Direction arah) {
        switch (arah) {
            case SLIDE_ATAS:
                if (k.getY() - 1 < 0) {
                    return null;
                }
                return getKotak(k.getX(), k.getY() - 1);
            case SLIDE_BAWAH:
                if (k.getY() + 1 >= BATAS_Y) {
                    return null;
                }
                return getKotak(k.getX(), k.getY() + 1);
            case SLIDE_KIRI:
                if (k.getX() - 1 < 0) {
                    return null;
                }
                return getKotak(k.getX() - 1, k.getY());
            case SLIDE_KANAN:
                if (k.getX() + 1 >= BATAS_X) {
                    return null;
                }
                return getKotak(k.getX() + 1, k.getY());
        }
        return null;
    }
	
	 public class PosisiKotakSalah extends Exception {
	        public PosisiKotakSalah() {
	            super("Posisi puzzle salah.");
	        }
	    }
	
	public Kotak[] getAllPuzzle() {
        return kotak;
    }
	
	public int getMoveCount()
	{
		return moveCount;
	}
	
	public void setMoveCount(int moveCount)
	{
		this.moveCount = moveCount;
	}
	
	public int getHandleLocation()
	{
		return handleLocation;
	}
	public Kotak getKotak(int nilaiKotak) {
        return kotak[nilaiKotak];
    }
	
	 public Kotak getKotak(int x, int y) {
	        for (Kotak k : kotak) {
	            if (k.getX() == x & k.getY() == y) {
	                return k;
	            }
	        }
	        return null;
	    }

    public void setParent(Puzzle parent) {
        this.parent = parent;
    }
    
    public Puzzle getParent() {
        return parent;
    }
	
}
