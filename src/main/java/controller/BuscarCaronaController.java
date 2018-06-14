package controller;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Carona;
import model.Caroneiro;

public class BuscarCaronaController implements Initializable {
	
	@FXML
	private TableView<Carona> tblCaronas;
	@FXML
	private TableColumn<Carona, String> clData;
	@FXML
	private TableColumn<Carona, String> clSaida;
	@FXML
	private TableColumn<Carona, String> clVagas;
	@FXML
	private TableColumn<Carona, String> clOrigem;
	@FXML
	private TableColumn<Carona, String> clDestino;
	@FXML
	private TextField textDestino;
	@FXML
	private TextField textOrigem;
	@FXML
	private Button btnFiltrar;
	@FXML
	private Button btnVoltar;
	@FXML
	private Button btnInteresseCarona;
	
	private Caroneiro caroneiro;
	
	public void buscarCaronas(){
		String destino = textDestino.getText();
		String origem = textOrigem.getText();
		caroneiro = new Caroneiro();
		try {
			Map<String, Carona> idCaronas = caroneiro.localizarCarona(origem, destino);
			Collection<Carona> caronas = idCaronas.values();
			clData.setCellValueFactory(new PropertyValueFactory("data"));
			clSaida.setCellValueFactory(new PropertyValueFactory("hora"));
			clVagas.setCellValueFactory(new PropertyValueFactory("vagas"));
			clOrigem.setCellValueFactory(new PropertyValueFactory("origem"));
			clDestino.setCellValueFactory(new PropertyValueFactory("destino"));
			tblCaronas.getItems().setAll(caronas);
			
		} catch (Exception e) {
			
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	

}
