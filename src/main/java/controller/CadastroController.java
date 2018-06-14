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

public class CadastroController {
	@FXML
    private Button btn_cancelar;
	@FXML
	private Button btn_limpar;
	@FXML
	private Button btn_ok;
	@FXML
	private TextField nomeUsuario;
	@FXML
	private TextField enderecoUsuario;
	@FXML
	private TextField emailUsuario;
	@FXML
	private TextField loginUsuario;
	@FXML
	private TextField senhaUsuario;
	
	private UsuarioControl usuario;


	
	@FXML
	protected void cadastrarUsuario() throws Exception {
		String nome = nomeUsuario.getText();
		String endereco = enderecoUsuario.getText();
		String email = emailUsuario.getText();
		String login = loginUsuario.getText();
		String senha = senhaUsuario.getText();
		usuario = new UsuarioControl();
		usuario.criarUsuario(login, senha, nome, endereco, email);
		limparFormulario();
	}
	
	@FXML
	protected void limparFormulario() {
		nomeUsuario.setText("");
		enderecoUsuario.setText("");
		emailUsuario.setText("");
		loginUsuario.setText("");
		senhaUsuario.setText("");
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
