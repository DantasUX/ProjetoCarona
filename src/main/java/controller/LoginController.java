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
import model.UsuarioControl;

public class LoginController {
	
	@FXML
	private TextField textLogin;
	@FXML
	private TextField textSenha;
	@FXML
	private Button btnVoltar;
	@FXML
	private Button btnEntrar;
	
	private UsuarioControl usuario;
	
	public void voltar(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaInicial.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}
	
	public void entrar(ActionEvent event){
		String login = textLogin.getText();
		String senha = textSenha.getText();
		Sessao sessao = Sessao.getInstance();
		usuario = new UsuarioControl();
		try {
			String id = usuario.abrirSessao(login, senha);
			sessao.setIdSessao(id);
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaPerfil.fxml"));		
			Parent atualizanovo = fxmlLoader.load();
			Scene atualizarCena = new Scene(atualizanovo);
			Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
			atualizaTela.setScene(atualizarCena);
			atualizaTela.show();
		} catch (Exception e) {
			
		}
	}

}
