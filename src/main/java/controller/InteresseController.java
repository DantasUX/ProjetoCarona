package controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

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
import model.Caroneiro;

public class InteresseController {
	
	@FXML
	private Button btnCadastrarInteresse;
	@FXML
	private Button btnVoltar;
	@FXML
	private TextField textHoraInicio;
	@FXML
	private TextField textHoraFim;
	@FXML
	private DatePicker textData;
	@FXML
	private TextField textDestino;
	@FXML
	private TextField textOrigem;
	
	private Caroneiro caroneiro;
	
	public void cadastrarInteresse(){
		Sessao sessao = Sessao.getInstance();
		caroneiro = new Caroneiro();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		String horaInicio = textHoraInicio.getText();
		String horaFim = textHoraFim.getText();		
		String destino = textDestino.getText();
		String origem = textOrigem.getText();
		System.out.println(horaInicio);
		try {
			String data = textData.getValue().format(formato);
			caroneiro.cadastrarInteresse(sessao.getIdSessao(), origem, destino, data, horaInicio, horaFim);
			JOptionPane.showMessageDialog(null, "Interesse Cadastrado com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void voltar(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaBuscarCarona.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}

}
