//Moez B. 2/16/2020

import java.io.*;
import java.util.StringTokenizer;
import net.sourceforge.tess4j.*;

public class interp {
	private Tesseract tess = new Tesseract();
	private File img;
	public String eqtn;
	public interp(File img) {
		this.img = img;
		this.eqtn = this.scan();
	}
	
	public String scan() {
		String scanRes = "";
		try {
			this.tess.setDatapath("C:/Users/Moez/Desktop/java dependencies/Tess4J/tessdata");
			scanRes = this.tess.doOCR(this.img);
			System.out.println("image has been read.");
			System.out.println("Scan retrieved:" + scanRes);
		}
		catch(TesseractException e) {
			e.printStackTrace();
		}
		return scanRes;
	}

	public int getDeg() {
		int deg = 0;
		for(int i = 0;i < this.eqtn.length();++i) {
			if(this.eqtn.charAt(i) == 'x') {
				if(this.isBehindOperator(i)) {
					deg = 49;
				}
				else {
					deg = this.eqtn.charAt(i+1);
				}
				break;
			}
		}
		return deg-48;
	}
	
	public int[] getCoes() {
		int deg = this.getDeg();
		int[] coes = new int[deg+1];
		int[] degs = new int[deg+1];
		StringBuffer eqtn = new StringBuffer(this.eqtn);
		StringTokenizer coesTokens;
		String delim = "x";
		int c = 0;
		for(int j = 0;j < degs.length;++j) {
			degs[j] = 0;
			coes[j] = 0;
		}
		degs[0] = deg;
		degs[deg] = 0;
		
		for(int i = 0; i < this.eqtn.length();++i) {
			if(Character.toString(this.eqtn.charAt(i)).equals("x")) {
				++c;
				if(c > 1) {
					try {
						if(this.isBehindOperator(i)) {
							degs[deg-1] = 1;
						}
						else {
							degs[deg-((int)this.eqtn.charAt(i+1)-48)] = this.eqtn.charAt(i+1);
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		for(int i = 0;i < eqtn.length();++i) {
			if(((int)eqtn.charAt(i) < 48 || (int)eqtn.charAt(i) > 57) && (int)eqtn.charAt(i) != 120 && (int)eqtn.charAt(i) != 88 
					&& (int)eqtn.charAt(i) != 45) {
				eqtn.deleteCharAt(i);
				--i;
			}
			else if((int)eqtn.charAt(i) == 120 || (int)eqtn.charAt(i) == 88) {
				delim = Character.toString(eqtn.charAt(i));
				try {
					if((int)eqtn.charAt(i+1) < 57 && (int)eqtn.charAt(i+1) > 48) {
						eqtn.deleteCharAt(i+1);
					}
				}
				catch(Exception e) {}
			}
			else if(i == 3 && (int)eqtn.charAt(i) == 45 && (int)eqtn.charAt(i-1) == 61) {}
		}
		System.out.println(eqtn);
		coesTokens = new StringTokenizer(eqtn.toString(),delim);
		for(int i = 0;i < coes.length;++i) {
			if(degs[i] != 0 || i == degs.length-1) {
				coes[i] = Integer.parseInt(coesTokens.nextToken());
			}
			else {
				coes[i] = 0;
			}
		}
		return coes;
	}
	
	public boolean isBehindOperator(int i) {
		if(Character.toString(this.eqtn.charAt(i+1)).equals("+") || Character.toString(this.eqtn.charAt(i+1)).equals("-")) {
			return true;
		}
		else {
			return false;
		}
	}
}
