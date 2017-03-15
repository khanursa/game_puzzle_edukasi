package com.example.game;

import java.util.Random;

public enum Direction {

    SLIDE_ATAS, SLIDE_BAWAH, SLIDE_KIRI, SLIDE_KANAN;
    
    private static Random random = new Random();

    public static Direction acak() {
        return values()[random.nextInt(4)];
    }
    public static Direction getArahGerakan(int xOriginal, int yOriginal, int xCurrent, int yCurrent) {
        if (xCurrent > xOriginal) {
            return SLIDE_KANAN;
        } else if (xCurrent < xOriginal) {
            return SLIDE_KIRI;
        } else if (yCurrent > yOriginal) {
            return SLIDE_BAWAH;
        } else if (yCurrent < yOriginal) {
            return SLIDE_ATAS;
        }
        return null;
    }
    
    public static Direction getLawanArah(Direction arah) {
        switch (arah) {
            case SLIDE_ATAS: return SLIDE_BAWAH;
            case SLIDE_BAWAH: return SLIDE_ATAS;
            case SLIDE_KIRI: return SLIDE_KANAN;
            case SLIDE_KANAN: return SLIDE_KIRI;
        }
        return null;
    }
    
}
