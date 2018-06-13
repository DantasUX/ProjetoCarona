package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Classe responsável por gerenciar as operações da tabela presenca do banco de dados.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class PresencaDAO {

	private static final Logger logger = LogManager.getLogger(PresencaDAO.class);
	
	private static PresencaDAO instanciaUnica = null;
	
	private PresencaDAO(){
		 
	}
	
	/**
	 * Abre uma instância única da base de dados.
	 * 
	 * @return PresencaDAO
	 */
	public static PresencaDAO getInstance(){
		logger.info("Abrindo conexão com a base de dados de presença");
		
		if(instanciaUnica == null){
			instanciaUnica = new PresencaDAO();			
		}		
		return instanciaUnica;
	}
	
	/**
	 * Atribui a um usuário se ele faltou ou não em uma determinada carona.
	 * 
	 * @param motorista id da sessão do usuário motorista
	 * @param idCarona id da carona
	 * @param loginCaroneiro login do caroneiro
	 * @param review true = faltou, false = não faltou
	 * @throws SQLException
	 */
	public void reviewVagaEmCarona(String motorista, String idCarona, String loginCaroneiro, boolean review) throws SQLException{
		logger.info("Executando método reviewVagaEmCarona");
		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "INSERT INTO presenca " + "(carona,motorista,loginCaroneiro,caroneiroFaltou) "
				+ "values (?,?,?,?)";		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		stmt.setString(1, idCarona);
		stmt.setString(2, motorista);
		stmt.setString(3, loginCaroneiro);
		stmt.setBoolean(4, review);
		stmt.execute();
		stmt.close();		
		conexao.close();
	}
	
	/**
	 * Recebe o login do usuário e retorna a quantidade de faltas dele em caronas.
	 * 
	 * @param login login do usuários
	 * @return quantidade de faltas do usuário em caronas
	 * @throws SQLException
	 */
	public int faltasEmCaronas(String login) throws SQLException{
		logger.info("Executando método faltasEmCaronas");
		
		int resultado = 0;		
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT count(*) FROM presenca WHERE loginCaroneiro = '" + login + "'"
				+ " AND caroneiroFaltou = true";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while(rs.next()){
			resultado = rs.getInt("count(*)");
		}		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return resultado;
	}
	
	/**
	 * Recebe o login do usuário e retorna a quantidade de presença dele em caronas.
	 * 
	 * @param login login do usuário
	 * @return quantidade de presença do usuário em caronas
	 * @throws SQLException
	 */
	public int presencasEmCaronas(String login) throws SQLException{
		logger.info("Executando método presencasEmCaronas");
		
		int resultado = 0;		
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT count(*) FROM presenca WHERE loginCaroneiro = '" + login + "'"
				+ " AND caroneiroFaltou = false";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while(rs.next()){
			resultado = rs.getInt("count(*)");
		}		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return resultado;
	}
	
	/**
	 * Apaga todas as informações de presença do banco de dados.
	 * 
	 * @throws SQLException
	 */
	public void apagarPresencas() throws SQLException{
		logger.info("Executando método apagarPresencas");
		
		Connection conexao1 = new ConnectionFactory().getConnection();
		String sql1 = "DELETE FROM presenca WHERE id > 0";
		PreparedStatement stmt1 = conexao1.prepareStatement(sql1);
		stmt1.execute();
		stmt1.close();		
		conexao1.close();
		
		Connection conexao2 = new ConnectionFactory().getConnection();
		String sql2 = "ALTER TABLE presenca AUTO_INCREMENT = 1";
		PreparedStatement stmt2 = conexao2.prepareStatement(sql2);
		stmt2.execute();
		stmt2.close();		
		conexao2.close();
	}	
}
