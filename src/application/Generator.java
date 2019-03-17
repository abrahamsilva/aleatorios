package application;

import java.util.LinkedList;

import javax.swing.text.AbstractDocument.LeafElement;

public class Generator {
	
	private LinkedList<Row> randomNumbers;
	private int seed, c, a, m, it;
	private LinkedList<Integer> control;
	
	public Generator(int seed, int c, int a, int m, int it) {
			
		this.seed = seed;
		this.c = c;
		this.a = a;
		this.m = m;
		this.it = it;
		randomNumbers = new LinkedList<Row>();
		control = new LinkedList<Integer>();
	}
	
	public LinkedList<Row> getList(){
		
		return randomNumbers;
	}

	public void middleSquare() {
		
		int xi;
		Row row = null;
		String middle;
		boolean flag = true;
		
		for(int i=0; flag && i<it ;i++ ) {
			
			xi = seed;
			if(i>0)control.add(xi);
			seed = (seed*seed);
			middle = String.valueOf(seed);
			if(middle.length()<8) {
	
				middle = String.format("%08d", seed);
					
			}
			seed = middle(middle);	
			if(control.contains(seed)) {
				flag=false;
			}
			else {
				row= new Row(i, xi, seed, (seed/10000d));
				randomNumbers.add(row);
			}
		}
	}
	public int middle(String number) {
		
		return Integer.parseInt(number.substring(2, 6));
	}
	
	public void congruential() {
		
		int xi;
		Row row = null;
		boolean flag = true;
		
		for(int i=0; flag && i<it ;i++ ) {
			
			xi = seed;
			if(i>0)control.add(xi);
			seed = (a*xi+c)%m;
		
			if(control.contains(seed)) {
				flag=false;
			}
			else {
				row= new Row(i, xi, seed, ((double)seed/(double)m));
				randomNumbers.add(row);
			}
		}
	}
	
	public void multiplier() {
		
		int xi;
		Row row = null;
		boolean flag = true;
		
		for(int i=0; flag && i<it ;i++ ) {
			
			xi = seed;
			if(i>0)control.add(xi);
			seed = (a*xi)%m;
		
			if(control.contains(seed)) {
				flag=false;
			}
			else {
				row= new Row(i, xi, seed, ((double)seed/(double)m));
				randomNumbers.add(row);
			}
		}
	}
}
