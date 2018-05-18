package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import model.Carona;

/**
 * Classe responsável por gerenciar as operações da tabela interesse do banco de dados.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class InteresseDAO {
	
	private static final Logger logger = LogManager.getLogger(InteresseDAO.class);
	
	private static InteresseDAO instanciaUnica = null;
	
	/**
	 * Construtor padrão.
	 */
	private InteresseDAO(){
		
	}
	
	/**
	 * Abre uma instância única da base de dados.
	 * 
	 * @return InteresseDAO
	 */
	public static InteresseDAO getInstance(){
		logger.info("Abrindo conexão com a base de dados de interesse");
		
		if(instanciaUnica == null){
			instanciaUnica = new InteresseDAO();			
		}		
		return instanciaUnica;
	}
	
	/**
	 * Recebe o id da sessão do usuário, a origem, o destino e a data da carona, e a hora final e inicial,
	 * retorna o id do interesse.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param origem origem da carona
	 * @param destino destino da carona
	 * @param data data da carona
	 * @param horaInicio hora inicial para encontrar a carona
	 * @param horaFim hora final para encontrar a carona
	 * @return id do interesse
	 * @throws SQLException
	 */
	public String cadastrarInteresse(String idSessao, String origem, String destino, String data, String horaInicio, String horaFim) throws SQLException{
		String idInteresse = "";
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "INSERT INTO interesse " + "(origem,destino,data,horaInicio,horaFim, idUsuario) "
				+ "values (?,?,?,?,?,?)";
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, origem);
		stmt.setString(2, destino);
		if(!data.equals("")){
			stmt.setString(3, LocalDate.parse(data, formato).toString());
		}
		else{
			stmt.setString(3, null);
		}
		stmt.setString(4, horaInicio);
		stmt.setString(5, horaFim);
		stmt.setString(6, idSessao);
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();		
		while(rs.next()){
			idInteresse = rs.getString(1);
		}
		stmt.close();		
		conexao.close();
		
		return idInteresse;
	}
	
	/**
	 * Recebe uma carona e verifica se existe interesses que coincide com a carona.
	 * 
	 * @param carona objeto carona
	 * @return lista de interesses que coincide com a carona
	 * @throws SQLException
	 */
	public List<String> verificaInteresse(Carona carona) throws SQLException{
		List<String> idUsuario = new ArrayList<String>();
		Connection conexao = new ConnectionFactory().getConnection();
		DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("uuuu-MM-dd");		
		DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		String sql = "SELECT data, idUsuario FROM interesse WHERE origem = '" + carona.getOrigem() +
				     "' AND destino = '" + carona.getDestino() + "' AND '" + carona.getHora() +
				     "' BETWEEN horaInicio AND horaFim";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			String dataInteresse = rs.getString("data");
			if(dataInteresse == null){
				idUsuario.add(rs.getString("idUsuario"));
			}
			else{
				LocalDate data = LocalDate.parse(dataInteresse, formato1);
				if(data.format(formato2).equals(carona.getData())){
					idUsuario.add(rs.getString("idUsuario"));
				}
			}			
		}
		stmt.execute();
		stmt.close();
		conexao.close();
		
		return idUsuario;
	}
	
	/**
	 * Apaga todos os interesses do banco de dados.
	 * 
	 * @throws SQLException
	 */
	public void apagarInteresses() throws SQLException{
		Connection conexao1 = new ConnectionFactory().getConnection();
		String sql1 = "DELETE FROM interesse WHERE id > 0";
		PreparedStatement stmt1 = conexao1.prepareStatement(sql1);
		stmt1.execute();
		stmt1.close();		
		conexao1.close();
		
		Connection conexao2 = new ConnectionFactory().getConnection();
		String sql2 = "ALTER TABLE interesse AUTO_INCREMENT = 1";
		PreparedStatement stmt2 = conexao2.prepareStatement(sql2);
		stmt2.execute();
		stmt2.close();		
		conexao2.close();
	}

}
