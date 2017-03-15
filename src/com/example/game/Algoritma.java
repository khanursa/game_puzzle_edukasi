package com.example.game;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import com.example.game.Puzzle.PosisiKotakSalah;

public class Algoritma {
	PriorityQueue<Puzzle> queue;
    private List<Puzzle> lstPuzzle;
	
	private Puzzle puzzle;
	Random random = new Random();
	public void fisher(int[] yates, int width, int height){
		int[] fisher = new int[width * height];
		int n = 0;
		int idxFinish = 0;
		for (int i = yates.length-1; i >0;i--)
		{
			int index = random.nextInt(i);
			int tmp = yates[index];
			yates[index] = yates[i];
			yates[i] = tmp;
			fisher[n] = yates[i];
			idxFinish = yates[index];
			n++;
		}
		fisher[yates.length-1] = idxFinish;
		yates = fisher;
	}
	
	public void Astar(){
		for (int i=0; i<900; i++) {      
			try {
				puzzle.moveToKotakSekitar(puzzle.getKotak(0, 0), Direction.acak());
			} catch (PosisiKotakSalah ex) {}
		}
		queue = new PriorityQueue<Puzzle>(1, new Comparator<Puzzle>(){

        @Override
	        public int compare(Puzzle p1, Puzzle p2) {
	            return hitungNilaiF(p1) - hitungNilaiF(p2);
	        }
        });
		queue.add(puzzle);
	}
	
	public void prosesAlgoritma() {
        
        while (!queue.isEmpty()) {
            puzzle = queue.poll();
            if (puzzle.salahTempat()==0) break;            
            
            // Memeriksa kemungkinan langkah yang ada
            for (Direction arah : Direction.values()) {                    
                Puzzle p = new Puzzle();
                try {                    
                    p.moveToKotakSekitar(p.getKotak(0), arah);                    
                    if (!queue.contains(p)) {
                        if (!p.equals(puzzle.getParent())) {
                            p.setParent(puzzle);                            
                            queue.add(p);
                        }
                    }
                } catch (PosisiKotakSalah ex) {}
                
            }
        }       
        lstPuzzle.add(puzzle);
        while (puzzle.getParent()!=null) {
            puzzle = puzzle.getParent();
            lstPuzzle.add(puzzle);
            
        } 
        Collections.reverse(lstPuzzle);
    }

	private int hitungNilaiG(Puzzle p) { 
	    if (p.getParent()==null) {
	        return 1;
	    } else {
	        return hitungNilaiG(p.getParent()) + 1;
	    }
	}
	public List<Puzzle> getListPuzzle() {
        return lstPuzzle;
    }

	private int hitungNilaiH(Puzzle p) {
	    return p.salahTempat();
	}
	private int hitungNilaiF(Puzzle p) {
	    return hitungNilaiG(p) + hitungNilaiH(p);
	}    

}
