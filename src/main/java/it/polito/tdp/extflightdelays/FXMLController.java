package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="compagnieMinimo"
	private TextField compagnieMinimo; // Value injected by FXMLLoader

	@FXML // fx:id="cmbBoxAeroportoPartenza"
	private ComboBox<String> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

	@FXML // fx:id="cmbBoxAeroportoDestinazione"
	private ComboBox<String> cmbBoxAeroportoDestinazione; // Value injected by FXMLLoader

	@FXML // fx:id="btnAnalizza"
	private Button btnAnalizza; // Value injected by FXMLLoader

	@FXML // fx:id="btnConnessione"
	private Button btnConnessione; // Value injected by FXMLLoader

	@FXML
	void doAnalizzaAeroporti(ActionEvent event) {
		try {

			int minimo = Integer.parseInt(compagnieMinimo.getText());
			popolaTendina(model.getGraphVertices(minimo));
			model.creaGrafo(minimo);

		} catch (Exception e) {
			txtResult.setText("Inserire Numero!");
		}
	}

	@FXML
	void doTestConnessione(ActionEvent event) {

		try {
			Airport d = model.sToAir(cmbBoxAeroportoDestinazione.getValue());
			Airport p = model.sToAir(cmbBoxAeroportoPartenza.getValue());

			txtResult.appendText("Si parte da: " + p);
			txtResult.appendText("\n   ...e si arriva a: " + d);
			if (model.trovaPercorso(d, p) != null) {
				for (Airport a : model.trovaPercorso(p, d))
					txtResult.appendText("\n" + a.toString());
			} else
				txtResult.appendText("\nNon esiste questa tratta");

		} catch (NumberFormatException e) {
			txtResult.setText("Errore test connessione");
		}
	}

	void popolaTendina(Map<Integer, Airport> dati) {
		cmbBoxAeroportoPartenza.getItems().clear();
		cmbBoxAeroportoDestinazione.getItems().clear();
		for (Airport a : dati.values()) {
			cmbBoxAeroportoPartenza.getItems().add(a.getAirportName());
			cmbBoxAeroportoDestinazione.getItems().add(a.getAirportName());
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
		assert compagnieMinimo != null : "fx:id=\"compagnieMinimo\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbBoxAeroportoDestinazione != null : "fx:id=\"cmbBoxAeroportoDestinazione\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnConnessione != null : "fx:id=\"btnConnessione\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
