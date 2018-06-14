package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PerfilController {
	
	@FXML
	private Button btnCadastrarCaronas;
	@FXML
	private Button btnProcurarCaronas;
	
	public void cadastrarCarona(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaCadastrarCarona.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}
	
	public void procurarCaronas(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaBuscarCarona.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}

}
