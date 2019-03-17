package application;

import javafx.scene.control.TextField;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application {

	private TableView<Row> table;
	private TableColumn<Row, Integer> idColumn, xiColumn, xiiColumn, RiColumn;
	private RadioButton msRButton, cRButton, mRButton, cmRButton, ccRButton;
	private TextField seedTextField, aTextField, cTextField, iterationsTextField, mTextField;
	private ToggleGroup radioButtonsGroup;
	private int a, c, m, seed, it;

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Generación de números aleatorios");

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
		radioButtonsLayout2.setPadding(new Insets(15, 15, 15, 15));
		radioButtonsLayout2.setSpacing(15);
		radioButtonsLayout2.setAlignment(Pos.CENTER);
		radioButtonsLayout2.getChildren().addAll(mRButton, ccRButton);

		HBox buttonsLayout = new HBox();
		buttonsLayout.setPadding(new Insets(3, 3, 20, 3));
		buttonsLayout.setSpacing(15);
		buttonsLayout.setAlignment(Pos.CENTER);
		buttonsLayout.getChildren().addAll(generateButton);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(table, textFieldsLayout, radioButtonsLayout, radioButtonsLayout2, buttonsLayout);

		Scene scene = new Scene(vBox, 500, 550);
		primaryStage.setScene(scene);
		primaryStage.show();

		// binding properties

		aTextField.disableProperty().bind(msRButton.selectedProperty());
		cTextField.disableProperty().bind(Bindings.or(msRButton.selectedProperty(), mRButton.selectedProperty()));
		mTextField.disableProperty().bind(msRButton.selectedProperty());

	}

	private void generateButtonAction() {

		Generator generator;
		LinkedList<Row> randomNumbers;
		seed = Integer.parseInt(seedTextField.getText());
		it = Integer.parseInt(iterationsTextField.getText());
		table.getItems().clear();

		if (msRButton.isSelected()) {

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
		} else if (mRButton.isSelected()) {

			cTextField.setText("");
			a = Integer.parseInt(aTextField.getText());
			m = Integer.parseInt(mTextField.getText());

			generator = new Generator(seed, 0, a, m, it);
			generator.multiplier();
			randomNumbers = generator.getList();

			for (Row r : randomNumbers) {
				table.getItems().add(r);
			}
			System.out.println("total:" + randomNumbers.size());
			
		}else if (cmRButton.isSelected()) {
			
			
			a = Integer.parseInt(aTextField.getText());
			c = Integer.parseInt(cTextField.getText());
			m = Integer.parseInt(mTextField.getText());
			
			if(checkHullDobell(m, c, a-1)) {
				
				System.out.println("si cumple");
				generator = new Generator(seed, c, a, m, it);
				generator.congruential();
				randomNumbers = generator.getList();

				for (Row r : randomNumbers) {
					table.getItems().add(r);
				}
				System.out.println("total:" + randomNumbers.size());
			}
			else {
				System.out.println("no cumple");
			}		
		}
	}
	
	public boolean checkHullDobell(int m, int c, int a) {
		
		if(areCoprime(m, c) && isDivisible(a, m) && hd3(a, m)) {
			return true;
		}
		else {
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
		}
		else {
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
