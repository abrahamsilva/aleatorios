package application;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import javax.swing.text.AbstractDocument.LeafElement;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Generator {

	private ArrayList<Row> randomNumbers;
	private int seed, c, a, m, it;
	private ArrayList<Integer> control;

	private int[] seeds, as, ms;

	public Generator(int seed, int c, int a, int m, int it) {

		this.seed = seed;
		this.c = c;
		this.a = a;
		this.m = m;
		this.it = it;
		randomNumbers = new ArrayList<Row>();
		control = new ArrayList<Integer>();
	}

	public Generator(int[] seeds, int[] as, int[] ms, int it) {
		this.seeds = seeds;
		this.as = as;
		this.ms = ms;
		this.it = it;
		randomNumbers = new ArrayList<Row>();
		control = new ArrayList<Integer>();
	}

	public ArrayList<Row> getList() {

		return randomNumbers;
	}

	public void middleSquare() {

		int xi;
		Row row = null;
		String middle;
		boolean flag = true;

		for (int i = 0; i < it; i++) {

			xi = seed;
			if (i > 0)
				control.add(xi);
			seed = (seed * seed);
			middle = String.valueOf(seed);
			if (middle.length() < 8) {

				middle = String.format("%08d", seed);

			}
			seed = middle(middle);

			if (control.contains(seed) && flag) {

				if (askToContinue()) {
					flag = false;
					row = new Row(i, xi, seed, (seed / 10000d));
					randomNumbers.add(row);
				} else {
					break;
				}

			} else {
				row = new Row(i, xi, seed, (seed / 10000d));
				randomNumbers.add(row);
			}
		}
	}

	public boolean askToContinue() {

		Alert warning = new Alert(AlertType.WARNING,
				"Los números random comenzarán a repetirse, ¿desea detener la generación o continuar?");
		warning.setTitle("Warning");
		warning.setHeaderText("Repetición de números random");
		ButtonType yesBtn = new ButtonType("Continuar");
		ButtonType noBtn = new ButtonType("Detener");

		warning.getButtonTypes().setAll(yesBtn, noBtn);
		Optional<ButtonType> result = warning.showAndWait();

		if (result.get() == yesBtn) {

			return true;
		} else {
			return false;
		}

	}

	public int middle(String number) {

		return Integer.parseInt(number.substring(2, 6));
	}

	public void congruential() {

		int xi;
		Row row = null;
		boolean flag = true;

		for (int i = 0; i < it; i++) {

			xi = seed;
			if (i > 0)
				control.add(xi);
			seed = (a * xi + c) % m;

			if (control.contains(seed) && flag) {

				if (askToContinue()) {
					flag = false;
					row = new Row(i, xi, seed, ((double) seed / (double) m));
					randomNumbers.add(row);
				} else {
					break;
				}

			} else {
				row = new Row(i, xi, seed, ((double) seed / (double) m));
				randomNumbers.add(row);
			}
		}
	}

	public void combined() {

		int[] xi = new int[seeds.length];
		double xij = 0;
		double sum;
		double xb;
		boolean flag = true;
		Row row = null;
		double aux = 1;

		for (int i = 0; i < it; i++) {

			sum = 0;
			xb = xij;	

			if (i > 0) {
				control.add((int) xb);
			}
			for (int j = 0; j < seeds.length; j++) {

				xi[j] = mixed(seeds[j], as[j], ms[j]);
				seeds[j] = xi[j];
				sum += xi[j] * aux;
				aux = aux * -1;
			}
			xij = sum % (ms[0] - 1);

			if (control.contains((int) xij) && flag) {

				if (askToContinue()) {
					flag = false;
					if (xij == 0) {
						row = new Row(i, xb, xij, ((double)ms[0]-1 / (double) ms[0]));
					} else {
						row = new Row(i, xb, xij, (xij / (double) ms[0]));
					}
					randomNumbers.add(row);
				} else {
					break;
				}
			} else {
				
				if (xij == 0) {
					row = new Row(i, xb, xij, ((double)ms[0]-1 / (double) ms[0]));
				} else {
					row = new Row(i, xb, xij, (xij / (double) ms[0]));
				}
				randomNumbers.add(row);
			}

		}

	}

	public int mixed(int seed, int a, int m) {

		return (a * seed) % m;
	}

	public void multiplier() {

		int xi;
		Row row = null;
		boolean flag = true;

		for (int i = 0; i < it; i++) {

			xi = seed;
			if (i > 0)
				control.add(xi);
			seed = (a * xi) % m;

			if (control.contains(seed) && flag) {

				if (askToContinue()) {
					flag = false;
					row = new Row(i, xi, seed, ((double) seed / (double) m));
					randomNumbers.add(row);
				} else {
					break;
				}
			} else {
				row = new Row(i, xi, seed, ((double) seed / (double) m));
				randomNumbers.add(row);
			}
		}
	}
}
