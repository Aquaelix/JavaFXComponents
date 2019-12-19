package dad.javafx.mvc.components;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class DateChooser extends HBox implements Initializable {

	private int month = 0;

	// model

	private ObjectProperty<LocalDate> date = new SimpleObjectProperty<LocalDate>();

	private StringProperty dia = new SimpleStringProperty();
	private StringProperty mes = new SimpleStringProperty();
	private StringProperty anio = new SimpleStringProperty();

	private ListProperty<String> dias = new SimpleListProperty<String>(FXCollections.observableArrayList());
	private ListProperty<String> anios = new SimpleListProperty<String>(FXCollections.observableArrayList());
	private ListProperty<String> meses = new SimpleListProperty<String>(FXCollections.observableArrayList());

	// view
	@FXML
	private HBox view;

	@FXML
	private ComboBox<String> diaChooser;

	@FXML
	private ComboBox<String> mesChooser;

	@FXML
	private ComboBox<String> yearChooser;

	public DateChooser() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DateChooserView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		date.setValue(LocalDate.now());

		mesChooser.itemsProperty().bind(meses);
		yearChooser.itemsProperty().bind(anios);
		diaChooser.itemsProperty().bind(dias);

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		Calendar.getInstance();
		for (int i = year; i >= 1900; i--)
			anios.add("" + i);

		for (int i = 1; i <= 31; i++)
			dias.add("" + i);

		meses.add("Enero");
		meses.add("Febrero");
		meses.add("Marzo");
		meses.add("Abril");
		meses.add("Mayo");
		meses.add("Junio");
		meses.add("Julio");
		meses.add("Agosto");
		meses.add("Septiembre");
		meses.add("Octubre");
		meses.add("Noviembre");
		meses.add("Diciembre");

		dia.bindBidirectional(diaChooser.valueProperty());
		mes.bindBidirectional(mesChooser.valueProperty());
		anio.bindBidirectional(yearChooser.valueProperty());

		dia.addListener((o, ov, nv) -> onChangedDayAction(nv));
		mes.addListener((o, ov, nv) -> onChangedMonthAction(nv));
		anio.addListener((o, ov, nv) -> onChangedYearAction(nv));

		diaChooser.setValue(diaChooser.getItems().get(0));
		mesChooser.setValue(mesChooser.getItems().get(0));
		yearChooser.setValue(yearChooser.getItems().get(0));

	}

	private void onChangedYearAction(String nv) {

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		String expr = "^\\d*$";

		if (nv.matches(expr)) {
			if (nv.equals("")) {
				yearChooser.setValue(yearChooser.getItems().get(0));
				date.set(LocalDate.of(Integer.valueOf(yearChooser.getItems().get(0)), date.get().getMonth(),
						date.get().getDayOfMonth()));
			} else if (1900 <= Integer.valueOf(nv) && Integer.valueOf(nv) <= year) {
				date.set(LocalDate.of(Integer.valueOf(nv), date.get().getMonth(), date.get().getDayOfMonth()));

				cambioDias();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Año inválido");
				alert.setHeaderText("Solo años entre 1900 hasta ahora.");
				alert.showAndWait();
			}

			// System.out.println(date.toString());

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("¿Año?");
			alert.setHeaderText("Solo puedes insertar números.");
			alert.showAndWait();

		}

	}

	private void onChangedMonthAction(String nv) {

		int mes = 0;

		for (int i = 0; i < meses.size(); i++) {
			if (meses.get(i).equals(nv)) {
				mes = i + 1;
				month = i + 1;
			}
		}

		cambioDias();

		date.set(LocalDate.of(date.get().getYear(), mes, date.get().getDayOfMonth()));

		// System.out.println(date.toString());
	}

	private void cambioDias() {

		Boolean bisiesto = Year.of(date.get().getYear()).isLeap(); // devuelve "true" si es bisiesto, y "false" en caso
																	// contrario
		int numeroDias = -1;

		String day = dia.getValue();

		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			numeroDias = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			numeroDias = 30;
			break;
		case 2:

			if (bisiesto) {
				numeroDias = 29;
			} else {
				numeroDias = 28;
			}
			break;

		}

		dias.clear();
		for (int i = 1; i <= numeroDias; i++)
			dias.add("" + i);

		if (Integer.valueOf(day) > numeroDias) {
			diaChooser.setValue(diaChooser.getItems().get(diaChooser.getItems().size() - 1));
		} else
			diaChooser.setValue(day);

	}

	private void onChangedDayAction(String nv) {

		// System.out.println(nv);
		if (nv != null)
			date.set(LocalDate.of(date.get().getYear(), date.get().getMonth(), Integer.valueOf(nv)));
		// System.out.println(date.toString());
	}

	public void inicializar() {
		String str=LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
		
		str = str.substring(0, 1).toUpperCase() + str.substring(1);
		
		setDia(String.valueOf(LocalDate.now().getDayOfMonth()));	
		setMes(str);
		setAnio(String.valueOf(LocalDate.now().getYear()));
	}
	
	public final ObjectProperty<LocalDate> dateProperty() {
		return this.date;
	}

	public final LocalDate getDate() {
		return this.dateProperty().get();
	}

	public final void setDate(final LocalDate date) {
		this.dateProperty().set(date);
	}

	public final StringProperty diaProperty() {
		return this.dia;
	}

	public final String getDia() {
		return this.diaProperty().get();
	}

	public final void setDia(final String dia) {
		this.diaProperty().set(dia);
	}

	public final StringProperty mesProperty() {
		return this.mes;
	}

	public final String getMes() {
		return this.mesProperty().get();
	}

	public final void setMes(final String mes) {
		this.mesProperty().set(mes);
	}

	public final StringProperty anioProperty() {
		return this.anio;
	}

	public final String getAnio() {
		return this.anioProperty().get();
	}

	public final void setAnio(final String anio) {
		this.anioProperty().set(anio);
	}
	
	/**
	 * Permite retornar la fecha en formato dd/MM/YYYY
	 * 
	 */
	public final String getDateFormated() {
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		
		return getDate().format(formater);
	}

}
