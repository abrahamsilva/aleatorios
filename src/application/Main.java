package application;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Main extends Application {

	private TableView<Row> table;
	private TableColumn<Row, Integer> idColumn, xiColumn, xiiColumn, RiColumn;
	private RadioButton msRButton, cRButton, mRButton, cmRButton, ccRButton, chiRadioButton, kRadioButton;
	private TextField seedTextField, aTextField, cTextField, iterationsTextField, mTextField, alfaTextField;
	private ToggleGroup radioButtonsGroup;
	private Label testLabel;
	private int a, c, m, seed, it;
	private int[] seeds, as, ms;

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Generación de números aleatorios");

		// label

		testLabel = new Label("---- Pruebas de bondad ----");
		testLabel.setTextFill(Color.web("#808080"));

		// table columns

		idColumn = new TableColumn<>("i");
		idColumn.setMaxWidth(100);
		idColumn.setSortable(false);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("i"));

		xiColumn = new TableColumn<>("Xi");
		xiColumn.setMinWidth(130);
		xiColumn.setSortable(false);
		xiColumn.setCellValueFactory(new PropertyValueFactory<>("xi"));

		xiiColumn = new TableColumn<>("Xi+1");
		xiiColumn.setMinWidth(130);
		xiiColumn.setSortable(false);
		xiiColumn.setCellValueFactory(new PropertyValueFactory<>("xii"));

		RiColumn = new TableColumn<>("Random");
		RiColumn.setMinWidth(130);
		RiColumn.setSortable(false);
		RiColumn.setCellValueFactory(new PropertyValueFactory<>("Ri"));

		// text fields
		seedTextField = new TextField();
		seedTextField.setPromptText("Semilla");

		aTextField = new TextField();
		aTextField.setPromptText("a");

		cTextField = new TextField();
		cTextField.setPromptText("c");

		mTextField = new TextField();
		mTextField.setPromptText("m");

		iterationsTextField = new TextField();
		iterationsTextField.setPromptText("iteraciones");

		alfaTextField = new TextField();
		alfaTextField.setPromptText("alfa");
		alfaTextField.setMaxWidth(100);

		// radio buttons

		msRButton = new RadioButton();
		msRButton.setText("Cuadrados Medios");

		cRButton = new RadioButton();
		cRButton.setText("Congruencial");

		cmRButton = new RadioButton();
		cmRButton.setText("Congruencial Mixto");

		mRButton = new RadioButton();
		mRButton.setText("Multiplicativo");

		ccRButton = new RadioButton();
		ccRButton.setText("Congruencial Combinado");

		chiRadioButton = new RadioButton();
		chiRadioButton.setText("Chi cuadrada");

		kRadioButton = new RadioButton();
		kRadioButton.setText("Kolmogorov");

		radioButtonsGroup = new ToggleGroup();
		radioButtonsGroup.getToggles().addAll(msRButton, cRButton, cmRButton, mRButton, ccRButton);

		// buttons

		Button generateButton = new Button("Generar");
		generateButton.setOnAction(e -> generateButtonAction());

		table = new TableView<>();
		table.getColumns().addAll(idColumn, xiColumn, xiiColumn, RiColumn);

		// layouts

		HBox textFieldsLayout = new HBox();
		textFieldsLayout.setPadding(new Insets(15, 15, 1, 15));
		textFieldsLayout.setSpacing(15);
		textFieldsLayout.setAlignment(Pos.CENTER);
		textFieldsLayout.getChildren().addAll(seedTextField, aTextField, cTextField, mTextField, iterationsTextField);

		HBox radioButtonsLayout = new HBox();
		radioButtonsLayout.setPadding(new Insets(15, 15, 1, 15));
		radioButtonsLayout.setSpacing(15);
		radioButtonsLayout.setAlignment(Pos.CENTER);
		radioButtonsLayout.getChildren().addAll(msRButton, cRButton, cmRButton);

		HBox radioButtonsLayout2 = new HBox();
		radioButtonsLayout2.setPadding(new Insets(15, 15, 30, 15));
		radioButtonsLayout2.setSpacing(15);
		radioButtonsLayout2.setAlignment(Pos.CENTER);
		radioButtonsLayout2.getChildren().addAll(mRButton, ccRButton);

		HBox testLayout = new HBox();
		testLayout.setAlignment(Pos.CENTER);
		testLayout.getChildren().addAll(testLabel);

		HBox alfaLayout = new HBox();
		alfaLayout.setPadding(new Insets(25, 15, 10, 15));
		alfaLayout.setAlignment(Pos.CENTER);
		alfaLayout.getChildren().addAll(alfaTextField);

		HBox testButtonsLayout = new HBox();
		testButtonsLayout.setPadding(new Insets(10, 15, 25, 15));
		testButtonsLayout.setSpacing(20);
		testButtonsLayout.setAlignment(Pos.CENTER);
		testButtonsLayout.getChildren().addAll(chiRadioButton, kRadioButton);

		HBox buttonsLayout = new HBox();
		buttonsLayout.setPadding(new Insets(3, 3, 20, 3));
		buttonsLayout.setSpacing(15);
		buttonsLayout.setAlignment(Pos.CENTER);
		buttonsLayout.getChildren().addAll(generateButton);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(table, textFieldsLayout, radioButtonsLayout, radioButtonsLayout2, testLayout,
				alfaLayout, testButtonsLayout, buttonsLayout);

		Scene scene = new Scene(vBox, 500, 700);
		primaryStage.setScene(scene);
		primaryStage.show();

		// binding properties

		aTextField.disableProperty().bind(msRButton.selectedProperty());
		cTextField.disableProperty().bind(Bindings.or(msRButton.selectedProperty(), mRButton.selectedProperty()));
		mTextField.disableProperty().bind(msRButton.selectedProperty());

		kRadioButton.disableProperty().bind(Bindings.or(msRButton.selectedProperty(), ccRButton.selectedProperty()));
		chiRadioButton.disableProperty().bind(Bindings.or(msRButton.selectedProperty(), ccRButton.selectedProperty()));
		alfaTextField.disableProperty().bind(ccRButton.selectedProperty());

		ccRButton.setOnMouseClicked(t -> {

			String n = askNGenerators();

			try {

				if (n != null) {
					int num = Integer.parseInt(n);
					if (num > 0) {
						combinedData(num);
					} else {
						Alert error = new Alert(AlertType.ERROR, "Ingresa un número mayor a cero");
						error.setTitle("Error");
						error.setHeaderText("Datos incorrectos");
						error.showAndWait();
					}
				}

			} catch (NumberFormatException e) {
				Alert error = new Alert(AlertType.ERROR, "Ingresa un número mayor a cero");
				error.setTitle("Error");
				error.setHeaderText("Datos incorrectos");
				error.showAndWait();
			}

		});

	}

	public void combinedData(int num) {

		Generator generator;
		ArrayList<Row> randomNumbers;
		table.getItems().clear();

		seeds = new int[num];
		as = new int[num];
		ms = new int[num];

		int[] aux;
		boolean flag = true;
		for (int i = 0; i < num; i++) {

			aux = createDialog();
			if (aux == null) {
				flag = false;
				break;
			} else {
				seeds[i] = aux[0];
				as[i] = aux[1];
				ms[i] = aux[2];

			}
		}
		if (flag) {

			generator = new Generator(seeds, as, ms, it);
			generator.combined();
			randomNumbers = generator.getList();
			for (Row r : randomNumbers) {
				table.getItems().add(r);
			}
			System.out.println("total:" + randomNumbers.size());

		}

	}

	public String askNGenerators() {

		try {
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setTitle("Número de generadores a combinar");

			ButtonType acceptType = new ButtonType("Aceptar", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().addAll(acceptType, ButtonType.CANCEL);

			dialog.getDialogPane().lookupButton(acceptType).setDisable(true);

			GridPane grid = new GridPane();

			TextField n = new TextField();
			n.setPromptText("Número de generadores");

			TextField i = new TextField();
			i.setPromptText("iteraciones");

			HBox hBox = new HBox();
			hBox.setPadding(new Insets(15, 15, 0, 15));
			hBox.setSpacing(15);
			hBox.setAlignment(Pos.CENTER);
			hBox.getChildren().addAll(n, i);

			n.setOnKeyReleased(t -> {

				String afString = n.getText();
				String seedString = i.getText();

				boolean isDisable = (afString.isEmpty() || afString.trim().isEmpty())
						|| (seedString.isEmpty() || seedString.trim().isEmpty());

				dialog.getDialogPane().lookupButton(acceptType).setDisable(isDisable);
			});
			i.setOnKeyReleased(t -> {

				String afString = n.getText();
				String seedString = i.getText();

				boolean isDisable = (afString.isEmpty() || afString.trim().isEmpty())
						|| (seedString.isEmpty() || seedString.trim().isEmpty());

				dialog.getDialogPane().lookupButton(acceptType).setDisable(isDisable);
			});

			grid.add(hBox, 1, 0);

			dialog.getDialogPane().setContent(grid);

			Optional<ButtonType> result = dialog.showAndWait();

			if (result.get() == acceptType) {
				ccRButton.setSelected(false);
				it = Integer.parseInt(i.getText());
				return n.getText();
			}
			ccRButton.setSelected(false);
			return null;

		} catch (NumberFormatException e) {

			Alert error = new Alert(AlertType.ERROR, "Ingresa sólo números");
			error.setTitle("Error");
			error.setHeaderText("Datos incorrectos");
			error.showAndWait();
			return null;
		}

	}

	private void generateButtonAction() {

		try {

			Generator generator;
			ArrayList<Row> randomNumbers;
			table.getItems().clear();

			if (msRButton.isSelected()) {

				seed = Integer.parseInt(seedTextField.getText());
				it = Integer.parseInt(iterationsTextField.getText());

				cTextField.setText("");
				aTextField.setText("");
				mTextField.setText("");

				generator = new Generator(seed, 0, 0, 0, it);
				generator.middleSquare();
				randomNumbers = generator.getList();

				for (Row r : randomNumbers) {
					table.getItems().add(r);
				}
				System.out.println("total:" + randomNumbers.size());

			} else if (cRButton.isSelected()) {

				seed = Integer.parseInt(seedTextField.getText());
				it = Integer.parseInt(iterationsTextField.getText());
				a = Integer.parseInt(aTextField.getText());
				c = Integer.parseInt(cTextField.getText());
				m = Integer.parseInt(mTextField.getText());

				generator = new Generator(seed, c, a, m, it);
				generator.congruential();
				randomNumbers = generator.getList();

				for (Row r : randomNumbers) {
					table.getItems().add(r);
				}
				System.out.println("total:" + randomNumbers.size());

				if (chiRadioButton.isSelected()) {

					ChiSquare chiSquare = new ChiSquare(randomNumbers);
					double cv = chiSquare.calculateCV();
					double valueT = chiSquare.checkTable(Double.parseDouble(alfaTextField.getText()), 0);
					System.out.println("K: " + chiSquare.getK());
					if (cv < valueT) {
						Alert res = new Alert(AlertType.INFORMATION,
								"La prueba Chi cuadrada acepta la H0 ya que: " + cv + " < " + valueT);
						res.setTitle("Chi Cuadrada");
						res.setHeaderText("H0 aceptada!");
						res.showAndWait();
					} else {
						Alert res = new Alert(AlertType.INFORMATION,
								"La prueba Chi cuadrada rechaza la H0 ya que: " + cv + " >= " + valueT);
						res.setTitle("Chi Cuadrada");
						res.setHeaderText("H0 rechazada!");
						res.showAndWait();
					}
				}
				if (kRadioButton.isSelected()) {
					Kolmogorov kolmogorov = new Kolmogorov(randomNumbers);
					boolean rejected = kolmogorov.valueH0(Double.parseDouble(alfaTextField.getText()));

					if (rejected == false) {
						Alert res = new Alert(AlertType.INFORMATION,
								"La prueba Kolmogorov acepta la H0\n D = " + kolmogorov.getD() + "\nValor p = " + kolmogorov.getP());
						res.setTitle("Kolmogorov");
						res.setHeaderText("H0 aceptada!");
						res.showAndWait();
					} else {
						Alert res = new Alert(AlertType.INFORMATION,
								"La prueba Kolmogorov rechaza la H0\n D = " + kolmogorov.getD()+ "\nValor p = " + kolmogorov.getP());
						res.setTitle("Kolmogorov");
						res.setHeaderText("H0 rechazada!");
						res.showAndWait();
					}
				}

			} else if (mRButton.isSelected()) {

				cTextField.setText("");
				seed = Integer.parseInt(seedTextField.getText());
				it = Integer.parseInt(iterationsTextField.getText());
				a = Integer.parseInt(aTextField.getText());
				m = Integer.parseInt(mTextField.getText());

				generator = new Generator(seed, 0, a, m, it);
				generator.multiplier();
				randomNumbers = generator.getList();

				for (Row r : randomNumbers) {
					table.getItems().add(r);
				}
				System.out.println("total:" + randomNumbers.size());

			} else if (cmRButton.isSelected()) {

				seed = Integer.parseInt(seedTextField.getText());
				it = Integer.parseInt(iterationsTextField.getText());
				a = Integer.parseInt(aTextField.getText());
				c = Integer.parseInt(cTextField.getText());
				m = Integer.parseInt(mTextField.getText());

				if (checkHullDobell(m, c, a - 1)) {

					Alert info = new Alert(AlertType.INFORMATION, "Si cumple con Hull-Dobell");
					info.setTitle("Info");
					info.setHeaderText("Datos válidos");
					info.showAndWait();
					generator = new Generator(seed, c, a, m, it);
					generator.congruential();
					randomNumbers = generator.getList();

					for (Row r : randomNumbers) {
						table.getItems().add(r);
					}
					System.out.println("total:" + randomNumbers.size());
				} else {
					
					Alert info = new Alert(AlertType.INFORMATION, "No cumple con Hull-Dobell");
					info.setTitle("Info");
					info.setHeaderText("Datos no válidos");
					info.showAndWait();
				}
			}

		} catch (NumberFormatException e) {
			
			Alert error = new Alert(AlertType.ERROR, "Ingresa sólo números y que sean mayores a cero");
			error.setTitle("Error");
			error.setHeaderText("Datos incorrectos");
			error.showAndWait();
		}

	}

	public int[] createDialog() {

		Dialog<ButtonType> dialog = new Dialog<>();
		int[] result = new int[3];
		dialog.setTitle("Generador combinado");

		ButtonType addLButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addLButtonType, ButtonType.CANCEL);

		dialog.getDialogPane().lookupButton(addLButtonType).setDisable(true);

		GridPane grid = new GridPane();

		TextField aField = new TextField();
		aField.setPromptText("a");

		TextField seedField = new TextField();
		seedField.setPromptText("semilla");

		TextField mField = new TextField();
		mField.setPromptText("m");

		aField.setOnKeyReleased(t -> {

			String afString = aField.getText();
			String seedString = seedField.getText();
			String mfString = mField.getText();
			boolean isDisable = (afString.isEmpty() || afString.trim().isEmpty())
					|| (seedString.isEmpty() || seedString.trim().isEmpty())
					|| (mfString.isEmpty() || mfString.trim().isEmpty());

			dialog.getDialogPane().lookupButton(addLButtonType).setDisable(isDisable);
		});
		seedField.setOnKeyReleased(t -> {
			String afString = aField.getText();
			String seedString = seedField.getText();
			String mfString = mField.getText();
			boolean isDisable = (afString.isEmpty() || afString.trim().isEmpty())
					|| (seedString.isEmpty() || seedString.trim().isEmpty())
					|| (mfString.isEmpty() || mfString.trim().isEmpty());
			dialog.getDialogPane().lookupButton(addLButtonType).setDisable(isDisable);
		});

		mField.setOnKeyReleased(t -> {
			String afString = aField.getText();
			String seedString = seedField.getText();
			String mfString = mField.getText();
			boolean isDisable = (afString.isEmpty() || afString.trim().isEmpty())
					|| (seedString.isEmpty() || seedString.trim().isEmpty())
					|| (mfString.isEmpty() || mfString.trim().isEmpty());
			dialog.getDialogPane().lookupButton(addLButtonType).setDisable(isDisable);
		});

		HBox hBox = new HBox();
		hBox.setPadding(new Insets(15, 15, 0, 15));
		hBox.setSpacing(15);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(seedField, aField, mField);

		grid.add(hBox, 1, 0);

		dialog.getDialogPane().setContent(grid);

		try {
			if (dialog.showAndWait().get() == addLButtonType) {

				result[0] = Integer.parseInt(seedField.getText());
				System.out.println("seed " + result[0]);
				result[1] = Integer.parseInt(aField.getText());
				System.out.println("a " + result[1]);
				result[2] = Integer.parseInt(mField.getText());
				System.out.println("m " + result[2]);
			} else {
				return null;
			}

		} catch (NumberFormatException e) {
			Alert error = new Alert(AlertType.ERROR, "Ingresa sólo números");
			error.setTitle("Error");
			error.setHeaderText("Datos incorrectos");
			error.showAndWait();
			return null;
		}

		return result;
	}

	public boolean checkHullDobell(int m, int c, int a) {

		if (areCoprime(m, c) && isDivisible(a, m) && hd3(a, m)) {
			return true;
		} else {
			return false;
		}
	}

	public LinkedList<Integer> primeFactors(int n) {

		LinkedList<Integer> primeFactorsList = new LinkedList<Integer>();

		for (int i = 2; i <= n; i++) {

			if (isPrime(i)) {

				while (n % i == 0) {
					primeFactorsList.add(i);
					n = n / i;
				}
			}
		}

		return primeFactorsList;
	}

	public boolean areCoprime(int a, int b) {

		if (Euclides(a, b) == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDivisible(int n, int m) {

		LinkedList<Integer> factors = primeFactors(m);

		for (int factor : factors) {
			if (n % factor != 0)
				return false;
		}

		return true;

	}

	public boolean hd3(int a, int m) {
		if (a % 4 == 0 && m % 4 == 0) {
			return true;
		} else {
			return false;
		}
	}

	public long Euclides(long a, long b) {

		if (b == 0) {
			return a;
		} else {
			return Euclides(b, a % b);
		}
	}

	public boolean isPrime(int n) {

		for (int i = 2; i < n; i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		launch(args);

	}
}
