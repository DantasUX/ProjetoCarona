package model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import dao.CaronaDAO;
import dao.InteresseDAO;
import dao.MensagemDAO;
import dao.PresencaDAO;
import dao.SolicitacaoDAO;
import dao.UsuarioDAO;

/**
 * Classe responsável por gerenciar as funções do sistema.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class Sistema {
	
	private static final Logger logger = LogManager.getLogger(Sistema.class);
	
	/**
	 * Recebe o id da sessão do usuário, o email de destino e a mensagem e envia um email.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param destino email de destino
	 * @param mensagem texto da mensagem
	 * @return true = mensagem enviada
	 */
	public boolean enviarEmail(String idSessao, String destino, String mensagem){
		logger.info("Enviando email para " + destino);
		
		return true;
	}
	
	/**
	 * Recebe o id da carona e verifica se a carona está expirada ou não.
	 * 
	 * @param idCarona id da carona
	 * @return id da carona = carona expirada, "" = carona não expirada
	 * @throws SQLException
	 */
	public String setCaronaRelampagoExpired(String idCarona) throws SQLException{
		logger.info("Executando método setCaronaRelampagoExpired");
		
		CaronaDAO c = CaronaDAO.getInstance();		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		String dataAgora = LocalDate.parse(LocalDate.now().toString()).format(formato);
		LocalDate data = LocalDate.parse(dataAgora, formato);
		LocalDate dataCarona = LocalDate.parse(c.dataCarona(idCarona), formato);
		
		if(data.isAfter(dataCarona)){
			return idCarona;
		}
		return "";
	}
	
	/**
	 * Recebe o id da carona expirada e o atributo emailTo e retorna lista de usuários que tem vaga na carona expirada.
	 * 
	 * @param idExpired id da carona expirada
	 * @param atributo emailTo
	 * @return lista contendo os usuários que tem vaga na carona expirada.
	 * @throws SQLException
	 */
	public List<String> getAtributoExpired(String idExpired, String atributo) throws SQLException{
		logger.info("Executando método getAtributoExpired");
		
		SolicitacaoDAO dao = SolicitacaoDAO.getInstance();
		return dao.usuariosCarona(idExpired);
	}
	
	/**
	 * Reinicia o sistema.
	 */
	public void reiniciarSistema(){
		logger.info("Reiniciando sistema");
	}
	
	/**
	 * Encerra a sessão do usuário.
	 * 
	 * @param login login do usuário
	 */
	public void encerrarSessao(String login){
		logger.info("Encerrando sessão");
	}
	
	/**
	 * Encerra o sistema.
	 */
	public void encerrarSistema(){
		logger.info("Encerrando sistema");
	}

	/**
	 * Zera o sitema.
	 * 
	 * @throws SQLException
	 */
	public void zerarSistema() throws SQLException{
		logger.info("Executando método zerarSistema");
		MensagemDAO m = MensagemDAO.getInstance();
		m.apagarMensagens();
		InteresseDAO i = InteresseDAO.getInstance();
		i.apagarInteresses();
		PresencaDAO p = PresencaDAO.getInstance();
		p.apagarPresencas();
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		s.apagarSolicitacoes();
		CaronaDAO c = CaronaDAO.getInstance();
		c.apagarCaronas();
		UsuarioDAO u = UsuarioDAO.getInstance();
		u.apagarUsuarios();
	}
}
