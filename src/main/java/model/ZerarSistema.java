package model;

import java.sql.SQLException;

import dao.CaronaDAO;
import dao.PresencaDAO;
import dao.SolicitacaoDAO;
import dao.UsuarioDAO;

/**
 * Classe responsável por zerar o sistema.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class ZerarSistema {

	/**
	 * Zera o sitema.
	 * 
	 * @throws SQLException
	 */
	public void zerarSistema() throws SQLException{
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
