package application;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;

public class Kolmogorov {
	
	private ArrayList<Double> randoms, dp,dm;
	private double n, maxp, maxm, d;
	
	public Kolmogorov(ArrayList<Row> randoms) {
		
		this.randoms = new ArrayList<Double>();
		dp =new ArrayList<Double>();
		dm = new ArrayList<Double>();
		n = randoms.size();
		
		for(int i=0;i<randoms.size();i++) {
			this.randoms.add(randoms.get(i).getRi());
		}
		Collections.sort(this.randoms);
		calculateDs();
		calculateMaxValues();
		
		if(maxp>maxm) {
			d = maxp;
		}
		else {
			d = maxm;
		}
	}
	
	public void calculateMaxValues() {
		
		maxp = randoms.get(0);
		maxm = randoms.get(0);

		for (int i = 1; i < n; i++) {
			
			if (dp.get(i) > maxp) {
				maxp = dp.get(i);
			}
			if(dm.get(i)>maxm) {
				maxm = dm.get(i);
			}
		}
	}

	public void calculateDs() {
		
		double in = 0;
		double inRi =0;
		
		for (int i = 0; i < n; i++) {
			in = (i+1)/n;
			inRi = in - randoms.get(i);
			dp.add(Math.abs(inRi));
			dm.add(Math.abs(randoms.get(i)-(i/n)));
		}
	}
	
	public boolean valueH0(double alfa) {
		
		
		UniformRealDistribution realDistribution = new UniformRealDistribution();
		
		double[] obs = new double[randoms.size()];
		for(int i=0;i<obs.length;i++) {
			obs[i]=randoms.get(i);
		}
		
		KolmogorovSmirnovTest kolmogorov = new KolmogorovSmirnovTest();
		return kolmogorov.kolmogorovSmirnovTest(realDistribution, obs,alfa);
			
		
	}

}
