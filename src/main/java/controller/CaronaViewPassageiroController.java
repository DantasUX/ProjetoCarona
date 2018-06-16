package controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Caroneiro;

public class CaronaViewPassageiroController {
	
	@FXML
	private TextArea area;
	@FXML
	private Button btnSolicitar;
	
	private Caroneiro caroneiro;
	
	public void detalhes(){
		Sessao sessao = Sessao.getInstance();
		caroneiro = new Caroneiro();
		try {
			area.setText("Tajeto: " + caroneiro.getTrajeto(sessao.getIdCarona()) + "\nCarona: " + caroneiro.getCarona(sessao.getIdCarona()));
		} catch (Exception e) {
			
		}
	}
	
	public void avaliar(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaAvaliarCarona.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}
	
	public void voltar(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaBuscarCarona.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}
	
	public void solicitar(){
		Sessao sessao = Sessao.getInstance();
		caroneiro = new Caroneiro();
		try {
			caroneiro.solicitarVaga(sessao.getIdSessao(), sessao.getIdCarona());
			JOptionPane.showMessageDialog(null, "Carona solicitada com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

}
