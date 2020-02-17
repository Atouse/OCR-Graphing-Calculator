//Moez B. 2/16/2020

import java.awt.*;
import javax.swing.*;
import java.io.*;

public class graph extends JFrame {
	private int length;
	private int width;
	private interp aasc; //analysis and scanner class
	private int deg;
	private int[] coes;
	public graph(File img) {
		this.aasc = new interp(img);
		System.out.println(aasc.eqtn);
		this.deg = aasc.getDeg();
		this.coes = aasc.getCoes();
		this.length = Toolkit.getDefaultToolkit().getScreenSize().width;
		this.width = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setVisible(true);
		this.setPreferredSize(new Dimension(length,width));
		this.setTitle("Graph");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		for(int i = 0;i < length;i+=10) {
			if(i == length/2) {
				g.setColor(Color.RED);
			}
			g.drawLine(i, 0, i, width);
			g.setColor(Color.BLACK);
		}
		for(int j = 0;j < width;j+=10) {
			if(j == width/2) {
				g.setColor(Color.RED);
			}
			g.drawLine(0,j,length,j);
			g.setColor(Color.BLACK);
		}
		int deg = this.deg;  //largest degree of func		
		int[] coes = this.coes; //length of this arr should never be shorter than deg+1; will cause ArrOOB exception
		int[] xpts = new int[this.length];
		int[] ypts = new int[this.length];
		g.translate(length/2,width/2);
		if(deg > 0) {
			for(int i = -this.length/2;i < this.length/2;++i) {
				//System.out.println(i);
				xpts[i+this.length/2] = i*10;
				if(i == 0) {
					ypts[this.length/2] = -coes[coes.length-1]*10;
				}
				else {
					for(int j = deg;j >= 0;--j) {
						ypts[i+this.length/2] += ((-i * Math.pow(i,j-1))*coes[coes.length-1-j])*10;
					}
				}				
			}
		}
		else if(deg < 0) {
			xpts = rFunc(deg)[0];
			ypts = rFunc(deg)[1];
		}
		g.drawPolyline(xpts,ypts,this.length);		
	}
	
	public int[][] rFunc(int negdeg) {
		int[] xpts = new int[this.length];
		int[] ypts = new int[this.length];
		int c = 0;
		for(double i = -10; i < 10;i+=0.1) {
			if(i != 0) {
				xpts[c] = (int)(i*10)+2;
				ypts[c] = (int)-Math.pow(i,negdeg)*10+2;
				++c;
			}
			else {
				
			}
		}
		int[][] ret = {xpts,ypts};
		return ret;
	}
	
	public static void main(String[] args) {
		graph thign = new graph(new File("image6.jpg")); //image4.jpg does not read prperly			
	}
}
