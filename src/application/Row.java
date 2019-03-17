package application;

public class Row {
	
	private int i;
	private double xi, xii, Ri;
	
	
	public Row(int i, double xi, double xii, double Ri) {
		
		this.i = i;
		this.xi = xi;
		this.xii = xii;
		this.Ri = Ri;
	}


	public int getI() {
		return i;
	}


	public void setI(int i) {
		this.i = i;
	}


	public double getXi() {
		return xi;
	}


	public void setXi(double xi) {
		this.xi = xi;
	}


	public double getXii() {
		return xii;
	}


	public void setXii(double xii) {
		this.xii = xii;
	}


	public double getRi() {
		return Ri;
	}


	public void setRi(double ri) {
		Ri = ri;
	}
	
	
}
