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
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import model.Caroneiro;

public class AvaliarCaronaController {
	
	@FXML
	private CheckBox seguraTranquila;
	@FXML
	private CheckBox naoFuncionou;
	@FXML
	private Button btnVoltar;
	@FXML
	private Button btnAvaliar;
	
	private Caroneiro caroneiro;
	
	public void avaliar(){
		Sessao sessao = Sessao.getInstance();
		caroneiro = new Caroneiro();
		String review = "";
		if(seguraTranquila.isSelected()){
			review = "segura e tranquila";
		}
		if(naoFuncionou.isSelected()){
			review = "não funcionou";
		}
		try {
			caroneiro.reviewCarona(sessao.getIdSessao(), sessao.getIdCarona(), review);
			JOptionPane.showMessageDialog(null, "Avaliação concluída.", "Informação", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void voltar(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaCaronaViewPassageiro.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		CaronaViewPassageiroController contrl = fxmlLoader.<CaronaViewPassageiroController>getController();
		contrl.detalhes();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}
	
	public void marcaSeguraTranquila(){
		seguraTranquila.setSelected(true);
		naoFuncionou.setSelected(false);
	}
	
	public void marcaNaoFuncionou(){
		naoFuncionou.setSelected(true);
		seguraTranquila.setSelected(false);
	}

}
