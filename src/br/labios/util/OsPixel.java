package br.labios.util;

import java.awt.Point;

public abstract class OsPixel extends Point {

	public static final int[] pixPreto = { 0, 0, 0, 255 };
	public static final int[] pixBrco = { 255, 255, 255, 255 };
	
	public abstract void setRgb( int[] rgb );
	
	public int compareTo(int limite) {
		int vlr = getHistogramValue();
		if (vlr < limite)
			return -1;
		if (vlr > limite)
			return 1;
		return 0;
	}	

	public abstract int getHistogramValue();
}
