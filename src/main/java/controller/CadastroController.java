package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroController {
	@FXML
    Button btn_cancelar;
	@FXML
	Button btn_limpar;
	@FXML
	Button btn_ok;
	@FXML
	TextField nomeUsuario;
	@FXML
	TextField enderecoUsuario;
	@FXML
	TextField TelUsuario;
	@FXML
	TextField emailUsuario;
	@FXML
	TextField loginUsuario;


	
	@FXML
	protected void cadastrarUsuario() {
		System.out.println("cadastrou");
	}
	
	@FXML
	protected void liparFormulario() {
		System.out.println("limpou");
	}
	
	@FXML
	protected void cancelarCadastro(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaInicial.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}
}
