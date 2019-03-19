package application;
import java.util.ArrayList;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;

public class ChiSquare {

	private ArrayList<Row> randoms;
	private double min, max, n, range, size;
	private int k;
	private double[] classes, absolutes, relatives;

	public ChiSquare(ArrayList<Row> randoms) {

		this.randoms = randoms;
		n = randoms.size();
		findMinValue();
		findMaxValue();
		range = max - min;
		k = (int) Math.floor((1 + (3.322 * Math.log10(n))));
		size = range / k;
		createClasses();
		frequencies();
	}

	public double calculateCV() {

		double sum = 0;

		for (int i = 0; i < k; i++) {

			sum+=absolutes[i];
	
			//System.out.println("Observed "+ i + ":" + absolutes[i]);

		}
		//System.out.println("sum: " + sum);
	
		double ei = sum / (double) k;
		//System.out.println("ei "+ei);
		double res = 0;

		for (int i = 0; i < k; i++) {

			res += (Math.pow(absolutes[i] - ei, 2)) / ei;

		}
		//System.out.println("res "+res);
		return res;
	}

	public void createClasses() {

		classes = new double[k];

		int factor = 1;

		for (int i = 0; i < k; i++) {

			classes[i] = size * factor;
			factor++;
		}

	}

	public void frequencies() {

		absolutes = new double[k];
		relatives = new double[k];

		for (int i = 0; i < k; i++) {

			for (int j = 0; j < n; j++) {

				if (i == 0) {
					if (randoms.get(j).getRi() >= 0 && randoms.get(j).getRi() <= classes[i]) {
						absolutes[i]++;
					}
				} else {

					if (i == k - 1) {
						if (randoms.get(j).getRi() > classes[i - 1] && randoms.get(j).getRi() <= classes[i]
								|| randoms.get(j).getRi() > classes[i]) {
							absolutes[i]++;
						}
					} else {
						if (randoms.get(j).getRi() > classes[i - 1] && randoms.get(j).getRi() <= classes[i]) {
							absolutes[i]++;
						}
					}
				}
			}
		}
		for (int i = 0; i < k; i++) {
			relatives[i] = absolutes[i] / n;

		}

	}

	public void findMinValue() {

		min = randoms.get(0).getRi();

		for (int i = 1; i < n; i++) {
			if (randoms.get(i).getRi() < min) {
				min = randoms.get(i).getRi();
			}
		}
	}

	public void findMaxValue() {

		max = randoms.get(0).getRi();

		for (int i = 1; i < n; i++) {
			if (randoms.get(i).getRi() > max) {
				max = randoms.get(i).getRi();
			}
		}

	}

	public double checkTable(double alfa, int parametros) {

		ChiSquaredDistribution chi = new ChiSquaredDistribution(k - 1 - parametros);

		return chi.inverseCumulativeProbability(1 - alfa);
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public int getK() {
		return k;
	}

	public double getSize() {
		return size;
	}

}
