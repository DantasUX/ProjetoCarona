package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import model.Carona;

/**
 * Classe responsável por gerenciar as operações da tabela mensagem do banco de dados.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class MensagemDAO {

	private static final Logger logger = LogManager.getLogger(MensagemDAO.class);
	
	private static MensagemDAO instanciaUnica = null;
	
	/**
	 * Construtor padrão.
	 */
	private MensagemDAO(){
		
	}
	
	/**
	 * Abre uma instância única da base de dados.
	 * 
	 * @return MensagemDAO
	 */
	public static MensagemDAO getInstance(){
		logger.info("Abrindo conexão com a base de dados de mensagem");
		
		if(instanciaUnica == null){
			instanciaUnica = new MensagemDAO();			
		}		
		return instanciaUnica;
	}
	
	/**
	 * Recebe o id da sessão do usuário e retorna uma lista das mensagens que não foram lidas.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @return lista das mensagens que não foram lidas
	 * @throws SQLException
	 */
	public List<String> mensagensPerfil(String idSessao) throws SQLException{
		List<String> mensagens = new ArrayList<String>();
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT texto FROM mensagem WHERE idUsuario = '" + idSessao + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while(rs.next()){
			mensagens.add(rs.getString("texto"));
		}		
		stmt.execute();
		stmt.close();
		conexao.close();
		
		return mensagens;
	}
	
	/**
	 * Recebe uma carona e o id da sessão do usuário e envia uma mensagem que uma carona coincide com os interesses do usuário.
	 * 
	 * @param carona objeto carona
	 * @param idSessao id da sessão do usuário
	 * @throws SQLException
	 */
	public void enviaMensagemParaUsuario(Carona carona, String idSessao) throws SQLException{
		UsuarioDAO u = UsuarioDAO.getInstance();
		
		String mensagem = "Carona cadastrada no dia " + carona.getData() + ", às " + carona.getHora() +
						  " de acordo com os seus interesses registrados. Entrar em contato com " +
						  u.emailUsuarioPorSessao(carona.getIdUsuario());
		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "INSERT INTO mensagem " + "(texto,idUsuario) "
				+ "values (?,?)";		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		stmt.setString(1, mensagem);
		stmt.setString(2, idSessao);		
		stmt.execute();
		stmt.close();		
		conexao.close();
	}
	
	/**
	 * Apaga todas as mensagens do banco de dados.
	 * 
	 * @throws SQLException
	 */
	public void apagarMensagens() throws SQLException{
		Connection conexao1 = new ConnectionFactory().getConnection();
		String sql1 = "DELETE FROM mensagem WHERE id > 0";
		PreparedStatement stmt1 = conexao1.prepareStatement(sql1);
		stmt1.execute();
		stmt1.close();		
		conexao1.close();
		
		Connection conexao2 = new ConnectionFactory().getConnection();
		String sql2 = "ALTER TABLE mensagem AUTO_INCREMENT = 1";
		PreparedStatement stmt2 = conexao2.prepareStatement(sql2);
		stmt2.execute();
		stmt2.close();		
		conexao2.close();
	}
}
