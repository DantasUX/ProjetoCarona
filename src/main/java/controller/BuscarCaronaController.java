package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
	
	Map<String, Carona> idCaronas = new LinkedHashMap<String, Carona>();
	
	public void buscarCaronas(){
		String destino = textDestino.getText();
		String origem = textOrigem.getText();
		caroneiro = new Caroneiro();
		try {
			idCaronas = caroneiro.localizarCarona(origem, destino);
			Collection<Carona> caronas = idCaronas.values();
			clData.setCellValueFactory(new PropertyValueFactory("data"));
			clSaida.setCellValueFactory(new PropertyValueFactory("hora"));
			clVagas.setCellValueFactory(new PropertyValueFactory("vagas"));
			clOrigem.setCellValueFactory(new PropertyValueFactory("origem"));
			clDestino.setCellValueFactory(new PropertyValueFactory("destino"));
			tblCaronas.getItems().setAll(caronas);			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void voltar(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaPerfil.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}
	
	public void selecionarCarona(MouseEvent event) throws IOException{
		Sessao sessao = Sessao.getInstance();
		int linha = tblCaronas.getSelectionModel().getSelectedIndex();
		Set<String> keys = idCaronas.keySet();
		List<String> id = new ArrayList<>();
		for(String i: keys){
			id.add(i);
		}
		sessao.setIdCarona(id.get(linha));
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaCaronaViewPassageiro.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		CaronaViewPassageiroController contrl = fxmlLoader.<CaronaViewPassageiroController>getController();
		contrl.detalhes();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}
	
	public void interesseCarona(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaInteresse.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	

}
