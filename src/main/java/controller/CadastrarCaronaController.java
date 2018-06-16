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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Motorista;

public class CadastrarCaronaController {
	
	@FXML
	private Pane panel01;
	@FXML
	private Pane panel02;
	@FXML
	private Pane panel03;
	@FXML
	private Button btn_Carona_Municipal;
	@FXML
	private Button btn_Carona_Intermunicipal;
	@FXML
	private Button btn_Carona_Relampago;
	@FXML
	private Button btn_cancelar;
	@FXML
	private Button btn_limpar;
	@FXML
	private Button btn_ok;
	@FXML
	private TextField origemM;
	@FXML
	private TextField destinoM;
	@FXML
	private DatePicker dataM;
	@FXML
	private TextField horaM;
	@FXML
	private TextField vagasM;
	@FXML
	private TextField cidadeM;
	@FXML
	private TextField origemI;
	@FXML
	private TextField destinoI;
	@FXML
	private DatePicker dataI;
	@FXML
	private TextField horaI;
	@FXML
	private TextField vagasI;
	@FXML
	private TextField origemR;
	@FXML
	private TextField destinoR;
	@FXML
	private TextField horaR;
	@FXML
	private DatePicker dataIdaR;
	@FXML
	private DatePicker dataVoltaR;
	@FXML
	private TextField minimoCaroneirosR;
	
	private Motorista motorista;

	public void ativaCaronaMunicipal(ActionEvent event) throws IOException{
		panel01.setVisible(true);
		panel02.setVisible(false);
		panel03.setVisible(false);
		
	}
	public void ativaCaronaIntermunicipal(ActionEvent event) throws IOException{
		panel01.setVisible(false);
		panel02.setVisible(true);
		panel03.setVisible(false);
		
	}
	public void ativaCaronaRelampago(ActionEvent event) throws IOException{
		panel01.setVisible(false);
		panel02.setVisible(false);
		panel03.setVisible(true);
		
	}
	
	public void cancelarCadastro(ActionEvent event) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/telaPerfil.fxml"));		
		Parent atualizanovo = fxmlLoader.load();
		Scene atualizarCena = new Scene(atualizanovo);
		Stage atualizaTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
		atualizaTela.setScene(atualizarCena);
		atualizaTela.show();
	}
	
	public void limparFormulario(){
		if(panel01.isVisible()){
			origemM.setText("");
			destinoM.setText("");
			horaM.setText("");
			vagasM.setText("");
			dataM.setValue(null);
			cidadeM.setText("");
		}
		if(panel02.isVisible()){
			origemI.setText("");
			destinoI.setText("");
			horaI.setText("");
			vagasI.setText("");
			dataI.setValue(null);
		}
		if(panel03.isVisible()){
			origemR.setText("");
			destinoR.setText("");
			horaR.setText("");
			dataIdaR.setValue(null);
			dataVoltaR.setValue(null);
			minimoCaroneirosR.setText("");
		}
		
	}
	
	public void cadastrarCarona(){
		Sessao sessao = Sessao.getInstance();
		motorista = new Motorista();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu");		
		
		if(panel01.isVisible()){
			String origem = this.origemM.getText();
			String destino = this.destinoM.getText();			
			String hora = this.horaM.getText();
			String vagas = this.vagasM.getText();
			String cidade = this.cidadeM.getText();
			try {
				String data = this.dataM.getValue().format(formato);
				motorista.cadastrarCaronaMunicipal(sessao.getIdSessao(), origem, destino, cidade, data, hora, vagas);
				JOptionPane.showMessageDialog(null, "Carona cadastrada com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
				limparFormulario();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(panel02.isVisible()){
			String origem = this.origemI.getText();
			String destino = this.destinoI.getText();			
			String hora = this.horaI.getText();
			String vagas = this.vagasI.getText();
			try {
				String data = this.dataI.getValue().format(formato);
				motorista.cadastrarCarona(sessao.getIdSessao(), origem, destino, data, hora, vagas);
				JOptionPane.showMessageDialog(null, "Carona cadastrada com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
				limparFormulario();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(panel03.isVisible()){
			String origem = this.origemR.getText();
			String destino = this.destinoR.getText();
			String hora = this.horaR.getText();			
			String minimoCaroneiros = this.minimoCaroneirosR.getText();
			try {
				String dataIda = this.dataIdaR.getValue().format(formato);
				String dataVolta = this.dataVoltaR.getValue().format(formato);
				motorista.cadastrarCaronaRelampago(sessao.getIdSessao(), origem, destino, dataIda, dataVolta, hora, minimoCaroneiros);
				JOptionPane.showMessageDialog(null, "Carona cadastrada com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
				limparFormulario();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
