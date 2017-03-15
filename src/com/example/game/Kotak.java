package com.example.game;

import android.media.Image;

	public class Kotak {

	    private final int DELTA_GERAKAN = 1;
	    
	    private int koordinatX;
	    private int koordinatY;
	    private int X;
	    private int Y;
	    private Image gambar;
	    
	    /**
	     * Membuat sebuah <code>Kotak</code> baru.
	     * 
	     * @param koordinatX koordinat X untuk menggambar <code>Kotak</code> ini.
	     * @param koordinatY koordinat Y untuk menggambar <code>Kotak</code> ini.
	     * @param x posisi horizontal <code>Kotak</code> ini (mulai dari 0).
	     * @param y posisi vertikal <code>Kotak</code> ini (mulai dari 0).
	     * @param gambar sebuah <code>Image</code> yang mewakili gambar untuk <code>Kotak</code> ini.
	     */
	    public Kotak(int koordinatX, int koordinatY, int x, int y, Image gambar) {
	        this.koordinatX = koordinatX;
	        this.koordinatY = koordinatY;
	        this.X = x;
	        this.Y = y;
	        this.gambar = gambar;
	    }

	    /**
	     * Mengembalikan koordinat X untuk <code>Kotak</code> ini.
	     * 
	     * @return posisi koordinat X untuk menggambar <code>Kotak</code> ini.
	     */
	    public int getKoordinatX() {
	        return koordinatX;
	    }

	    /**
	     * Mengatur koordinat X untuk <code>Kotak</code> ini.
	     * 
	     * @param x posisi koordinat X untuk menggambar <code>Kotak</code> ini.
	     */
	    public void setKoordinatX(int x) {
	        this.koordinatX = x;
	    }

	    /**
	     * Mengembalikan koordinat Y untuk <code>Kotak</code> ini.
	     * 
	     * @return posisi koordinat Y untuk menggambar <code>Kotak</code> ini.
	     */
	    public int getKoordinatY() {
	        return koordinatY;
	    }

	    /**
	     * Mengatur koordinat Y untuk <code>Kotak</code> ini.
	     * 
	     * @param y posisi koordinat Y untuk menggambar <code>Kotak</code> ini.
	     */
	    public void setKoordinatY(int y) {
	        this.koordinatY = y;
	    }

	    /**
	     * Mengatur koordinat (X,Y) untuk <code>Kotak</code> ini.
	     * 
	     * @param x koordinat X untuk <code>Kotak</code> ini.
	     * @param y koordinat Y untuk <code>Kotak</code> ini.
	     */
	    public void setKoordinat(int x, int y) {
	        this.koordinatX = x;
	        this.koordinatY = y;
	    }
	    /**
	     * Mengembalikan lokasi <code>Kotak</code> ini secara horizontal.  Lokasi
	     * kiri atas diwakili dengan (0,0).
	     * 
	     * @return lokasi <code>Kotak</code> secara horizontal (mulai dari 0).
	     */
	    public int getX() {
	        return X;
	    }

	    /**
	     * Mengembalikan lokasi <code>Kotak</code> ini secara vertikal.  Lokasi
	     * kiri atas diwakili dengan (0,0).
	     * 
	     * @return lokasi <code>Kotak</code> secara vertikal (mulai dari 0).
	     */
	    public int getY() {
	        return Y;
	    }

	    /**
	     * Mengatur lokasi <code>Kotak</code> secara horizontal.  Lokasi kiri atas diwakili
	     * dengan (0,0).
	     * 
	     * @param X lokasi <code>Kotak</code> secara horizontal(mulai dari 0).
	     */
	    public void setX(int X) {
	        this.X = X;
	    }

	    /**
	     * Mengatur lokasi <code>Kotak</code> secara vertikal.  Lokasi kiri atas diwakili
	     * dengan (0,0).
	     * 
	     * @param Y lokasi <code>Kotak</code> secara vertikal (mulai dari 0).
	     */
	    public void setY(int Y) {
	        this.Y = Y;
	    }
	    
	    /**
	     * Mengatur lokasi horizontal dan vertikel dari <code>Kotak</code>.  Lokasi kiri atas diwakili dengan (0,0).
	     * 
	     * @param X lokasi <code>Kotak</code> secara horizontal (mulai dari 0).
	     * @param Y lokasi <code>Kotak</code> secara vertikal (mulai dari 0).
	     */
	    public void setXY(int X, int Y) {
	        this.X = X;
	        this.Y = Y;
	    }
	    
	    /**
	     * Mengembalikan <code>Image</code> yang mewakili gambar untuk <code>Kotak</code> ini.
	     * 
	     * @return sebuah <code>Image</code> untuk <code>Kotak</code> ini.
	     */
	    public Image getGambar() {
	        return gambar;
	    }
	    
	    /**
	     * Mengatur gambar untuk <code>Kotak</code> ini.
	     * 
	     * @param gambar sebuah <code>Image</code> yang mewakili gambar untuk <code>Kotak</code> ini.
	     */
	    public void setGambar(Image gambar) {
	        this.gambar = gambar;
	    }
	    
	    /**
	     * Menggerakkan kotak ini ke arah yang ditentukan.
	     * 
	     * @param arah sebuah enumerasi dari <code>ArahSlide</code> yang mewakili arah pergerakan.
	     */
	    public void move(Direction arah) {
	        if (arah==Direction.SLIDE_ATAS) {
	            koordinatY-=DELTA_GERAKAN;
	        } else if (arah==Direction.SLIDE_BAWAH) {
	            koordinatY+=DELTA_GERAKAN;
	        } else if (arah==Direction.SLIDE_KIRI) {
	            koordinatX-=DELTA_GERAKAN;
	        } else if (arah==Direction.SLIDE_KANAN) {
	            koordinatX+=DELTA_GERAKAN;
	        }
	    }
	    
	    /**
	     * Menukar posisi <code>Kotak</code>.  Method ini hanya menukar posisi secara logical
	     * yang dimulai dari (0,0) untuk posisi kiri atas, tanpa menukar posisi koordinat yang
	     * dipakai untuk penggambaran.
	     * 
	     * @param k <code>Kotak</code> ini akan ditukar dengan posisi <code>k</code>.     
	     */
	    public void swap(Kotak k) {
	        int tmpX = X;
	        int tmpY = Y;
	        this.setX(k.getX());
	        this.setY(k.getY());
	        k.setX(tmpX);
	        k.setY(tmpY);
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final Kotak other = (Kotak) obj;
	        if (this.X != other.X) {
	            return false;
	        }
	        if (this.Y != other.Y) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public int hashCode() {
	        int hash = 7;
	        hash = 73 * hash + this.DELTA_GERAKAN;
	        hash = 73 * hash + this.koordinatX;
	        hash = 73 * hash + this.koordinatY;
	        hash = 73 * hash + this.X;
	        hash = 73 * hash + this.Y;
	        return hash;
	    }
	}