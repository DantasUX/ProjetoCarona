package controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Motorista;

public class CadastrarCaronaController {
	
	@FXML
	private Button btn_cancelar;
	@FXML
	private Button btn_limpar;
	@FXML
	private Button btn_ok;
	@FXML
	private TextField origem;
	@FXML
	private TextField destino;
	@FXML
	private DatePicker data;
	@FXML
	private TextField hora;
	@FXML
	private TextField vagas;
	
	private Motorista motorista;
	
	public void cancelarCadastro(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaPerfil.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}
	
	public void limparFormulario(){
		origem.setText("");
		destino.setText("");
		hora.setText("");
		vagas.setText("");
		data.setValue(null);
	}
	
	public void cadastrarCarona(){
		Sessao sessao = Sessao.getInstance();
		String origem = this.origem.getText();
		String destino = this.destino.getText();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		String data = this.data.getValue().format(formato);
		String hora = this.hora.getText();
		String vagas = this.vagas.getText();
		motorista = new Motorista();
		System.out.println(data);
		try {
			motorista.cadastrarCarona(sessao.getIdSessao(), origem, destino, data, hora, vagas);
		} catch (Exception e) {
			
		}
	}

}
