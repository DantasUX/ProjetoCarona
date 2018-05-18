package model;

import java.sql.SQLException;

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
