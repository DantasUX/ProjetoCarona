package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import model.Carona;

/**
 * Classe responsável por gerenciar as operações da tabela carona do banco de dados.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class CaronaDAO {
	
	private static final Logger logger = LogManager.getLogger(CaronaDAO.class);
	
	private static CaronaDAO instanciaUnica = null;	
	
	/**
	 * Contrutor padrão.
	 */
	private CaronaDAO(){
		
	}
	
	/**
	 * Abre uma instância única da base de dados.
	 * 
	 * @return CaronaDAO
	 */
	public static CaronaDAO getInstance(){
		logger.info("Abrindo conexão com a base de dados de carona");
		
		if(instanciaUnica == null){
			instanciaUnica = new CaronaDAO();			
		}		
		return instanciaUnica;
	}
	
	/**
	 * Recebe uma carona e a armazena no banco de dados do sistema.
	 * 
	 * @param carona objeto carona
	 * @return id da carona
	 * @throws SQLException 
	 */
	public String cadastrarCarona(Carona carona) throws SQLException{
		logger.info("Executando método cadastrarCarona");
		
		String idCarona = "";		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "INSERT INTO carona " + "(origem,destino,cidade,data,dataVolta,hora,vagas,municipal,minimoCaroneiros,idUsuario) "
				+ "values (?,?,?,?,?,?,?,?,?,?)";		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu");		
		PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, carona.getOrigem());
		stmt.setString(2, carona.getDestino());
		stmt.setString(3, carona.getCidade());
		stmt.setString(4, LocalDate.parse(carona.getData(), formato).toString());
		if(carona.getDataVolta() == null){
			stmt.setString(5, null);
		}
		else{
			stmt.setString(5, carona.getDataVolta().toString());
		}
		stmt.setString(6, carona.getHora());
		stmt.setInt(7, carona.getVagas());
		stmt.setBoolean(8, carona.getMunicipal());
		stmt.setInt(9, carona.getMinimoCaroneiros());
		stmt.setString(10, carona.getIdUsuario());		
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();		
		while(rs.next()){
			idCarona = rs.getString(1);
		}
		stmt.close();		
		conexao.close();
		
		InteresseDAO i = InteresseDAO.getInstance();
		MensagemDAO m = MensagemDAO.getInstance();
		List<String> idUsuario = i.verificaInteresse(carona);
		for(String id: idUsuario){
			m.enviaMensagemParaUsuario(carona, id);
		}
		
		return idCarona;
	}	
	
	/**
	 * Recebe a origem e o destino. Retorna a carona e sua id. Tanto a origem quanto o destino podem ser
	 * deixados em branco. origem = "" retorna todas as caronas com o destino não em branco. destino = ""
	 * retorna todas as caronas com a origem não em branco. origem = "" e destino = "" retorna todas as caronas
	 * do sistema.
	 * 
	 * @param origem origem da carona
	 * @param destino destino da carona
	 * @return um map contendo o id e a carona
	 * @throws SQLException 
	 */
	public Map<String, Carona> localizarCarona(String origem, String destino) throws SQLException{
		logger.info("Executando método localizarCarona");
		
		Map<String, Carona> caronasLocalizadas = new LinkedHashMap<String, Carona>();		
		String sql = "";		
		if(!origem.equals("")){
			if(!destino.equals("")){
				sql = "SELECT * FROM carona WHERE origem = '" + origem +"' AND destino = '" + destino + "'";
			}
			else{
				sql = "SELECT * FROM carona WHERE origem = '" + origem +"'";
			}
		}
		else{
			if(!destino.equals("")){
				sql = "SELECT * FROM carona WHERE destino = '" + destino + "'";
			}
			else{
				sql = "SELECT * FROM carona";
			}
		}		
		Connection conexao = new ConnectionFactory().getConnection();
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			LocalDate data = LocalDate.parse(rs.getString("data"));
			LocalTime hora = LocalTime.parse(rs.getString("hora"));
			Carona carona = new Carona(rs.getString("origem"), rs.getString("destino"), data, hora, rs.getInt("vagas"));
			carona.setIdUsuario(rs.getString("idUsuario"));
			caronasLocalizadas.put(rs.getString("id"), carona);
		}
		stmt.execute();
		stmt.close();
		conexao.close();								
		return caronasLocalizadas;
	}
	
	/**
	 * Recebe a cidade, a origem e o destino da carona municipal e localiza as caronas.
	 * 
	 * @param cidade cidade da carona
	 * @param origem origem da carona
	 * @param destino destino da carona
	 * @return um map contendo o id e a carona
	 * @throws Exception
	 */
	public Map<String, Carona> localizarCaronaMunicipal(String cidade, String origem, String destino) throws SQLException{
		logger.info("Executando método localizarCaronaMunicipal");
		
		Map<String, Carona> caronasMunicipaisLocalizadas = new LinkedHashMap<String, Carona>();		
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT * FROM carona WHERE municipal = true "
				+ "AND cidade = '" + cidade + "' AND origem = '" + origem + "' AND destino = '" + destino + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			LocalDate data = LocalDate.parse(rs.getString("data"));
			LocalTime hora = LocalTime.parse(rs.getString("hora"));
			Carona carona = new Carona(rs.getString("origem"), rs.getString("destino"), rs.getString("cidade"), data, hora, rs.getInt("vagas"));
			carona.setIdUsuario(rs.getString("idUsuario"));
			caronasMunicipaisLocalizadas.put(rs.getString("id"), carona);
		}
		stmt.execute();
		stmt.close();
		conexao.close();
		
		return caronasMunicipaisLocalizadas;
	}
	
	/**
	 * Recebe a cidade da carona municipal e localiza as caronas.
	 * 
	 * @param cidade cidade da carona
	 * @return um map contendo o id e a carona
	 * @throws Exception
	 */
	public Map<String, Carona> localizarCaronaMunicipal(String cidade) throws SQLException{
		logger.info("Executando método localizarCaronaMunicipal");
		
		Map<String, Carona> caronasMunicipaisLocalizadas = new LinkedHashMap<String, Carona>();		
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT * FROM carona WHERE municipal = true "
				+ "AND cidade = '" + cidade + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			LocalDate data = LocalDate.parse(rs.getString("data"));
			LocalTime hora = LocalTime.parse(rs.getString("hora"));
			Carona carona = new Carona(rs.getString("origem"), rs.getString("destino"), rs.getString("cidade"), data, hora, rs.getInt("vagas"));
			carona.setIdUsuario(rs.getString("idUsuario"));
			caronasMunicipaisLocalizadas.put(rs.getString("id"), carona);
		}
		stmt.execute();
		stmt.close();
		conexao.close();
		
		return caronasMunicipaisLocalizadas;
	}
	
	/**
	 * Recebe o id da carona e retorna a sua origem.
	 * 
	 * @param idCarona id da carona
	 * @return origem da carona
	 * @throws SQLException 
	 */
	public String origemCarona(String idCarona) throws SQLException{
		logger.info("Executando método origemCarona");
		
		return retornaInformacaoCarona(idCarona, "origem");
	}	
	
	/**
	 * Recebe o id da carona e retorna o seu destino.
	 * 
	 * @param idCarona id da carona
	 * @return destino da carona
	 * @throws SQLException 
	 */
	public String destinoCarona(String idCarona) throws SQLException{
		logger.info("Executando método destinoCarona");
		
		return retornaInformacaoCarona(idCarona, "destino");
	}
	
	/**
	 * Recebe o id da carona e retorna a sua data.
	 * 
	 * @param idCarona id da carona
	 * @return data da carona
	 * @throws SQLException 
	 */
	public String dataCarona(String idCarona) throws SQLException{
		logger.info("Executando método dataCarona");
		
		String data = retornaInformacaoCarona(idCarona, "data");		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu");		
		return LocalDate.parse(data).format(formato);
	}
	
	/**
	 * Recebe o id da carona e retorna a sua hora.
	 * 
	 * @param idCarona id da carona
	 * @return hora da carona
	 * @throws SQLException
	 */
	public String horaCarona(String idCarona) throws SQLException{
		logger.info("Executando método horaCarona");
		
		String hora = retornaInformacaoCarona(idCarona, "hora");		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");	
		return LocalTime.parse(hora).format(formato);
	}
	
	/**
	 * Recebe o id da carona e nome da coluna da tabela carona onde a informação está armazenada no banco de
	 * dados. Por fim, retorna a informação dessa coluna.
	 * 
	 * @param idCarona id da carona
	 * @param coluna uma coluna equivalente da tabela carona no banco de dados. Exemplo: origem, destino, data, hora.
	 * @return a informação armazenada da coluna.
	 * @throws SQLException
	 */
	private String retornaInformacaoCarona(String idCarona, String coluna) throws SQLException{
		logger.info("Executando método retornaInformacaoCarona");
		
		String informacao = "";		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT " + coluna + " FROM carona WHERE id = '" + idCarona + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			informacao = rs.getString(coluna);
		}
		stmt.execute();
		stmt.close();
		conexao.close();		
		return informacao;
	}
	
	/**
	 * Recebe o id da carona e retorna a quantidade de vagas.
	 * 
	 * @param idCarona id da carona
	 * @return quantidade de vagas da carona
	 * @throws SQLException 
	 */
	public int vagasCarona(String idCarona) throws SQLException{
		logger.info("Executando método vagasCarona");
		
		int vagas = 0;		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT vagas FROM carona WHERE id = '" + idCarona + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			vagas = rs.getInt("vagas");
		}
		stmt.execute();
		stmt.close();
		conexao.close();		
		return vagas;
	}
	
	/**
	 * Recebe o id da carona relâmpago e retorna a quantidade de mínimo de caroneiros necessários para a carona.
	 * 
	 * @param idCarona id da carona
	 * @return mínimo de caroneiros necessários para a carona relâmpago
	 * @throws SQLException
	 */
	public int minimoCaroneiro(String idCarona) throws SQLException{
		logger.info("Executando método minimoCaroneiro");
		
		int minimoCaroneiros = 0;		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT minimoCaroneiros FROM carona WHERE id = '" + idCarona + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			minimoCaroneiros = rs.getInt("minimoCaroneiros");
		}
		stmt.execute();
		stmt.close();
		conexao.close();		
		return minimoCaroneiros;
	}
	
	/**
	 * Recebe o id da carona e retorna o seu trajeto, ou seja, a origem e o destino.
	 * 
	 * @param idCarona id da carona
	 * @return trajeto da carona
	 * @throws SQLException 
	 */
	public String trajetoCarona(String idCarona) throws SQLException{
		logger.info("Executando método trajetoCarona");
		
		return origemCarona(idCarona) + " - " + destinoCarona(idCarona);
	}
	
	/**
	 * Recebe o id da carona e retorna informações como origem, destino, data e hora.
	 * 
	 * @param idCarona id da carona
	 * @return informações da carona
	 * @throws SQLException 
	 */
	public String informacoesCarona(String idCarona) throws SQLException{
		logger.info("Executando método informacoesCarona");
		
		String origem = origemCarona(idCarona);
		String destino = destinoCarona(idCarona);
		String data = dataCarona(idCarona);
		String hora = horaCarona(idCarona);
		return  origem + " para " + destino + ", no dia " + data + ", as " + hora;
	}
	
	/**
	 * Recebe o id da carona e verifica se ele é válido, ou seja, se o id da carona existe no banco de dados.
	 * 
	 * @param idCarona id da carona
	 * @return true = id da carona válido, false = id da carona inválido
	 * @throws SQLException 
	 */
	public boolean verificaCarona(String idCarona) throws SQLException{
		logger.info("Executando método verificaCarona");
		
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT id FROM carona WHERE id = '" + idCarona +"'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		boolean resultadoId = rs.next();		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return resultadoId;
	}
	
	/**
	 * Recebe o login do usuário e retorna todo o histórico de caronas desse usuário.
	 * 
	 * @param login login do usuário
	 * @return histórico de caronas do usuário
	 * @throws SQLException
	 */
	public List<String> historicoCaronas(String login) throws SQLException{
		logger.info("Executando método historicoCaronas");
		
		List<String> id = new ArrayList<String>();		
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT carona.id FROM carona, usuario WHERE carona.idUsuario = usuario.id"
				+ " AND usuario.login = '" + login + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			id.add(rs.getString("id"));
		}		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return id;
	}
	
	/**
	 * Recebe o id da sessão do usuário e o índice da posição da carona e retorna o id da carona.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param indexCarona índice da posição da carona que define a ordem com que a carona foi cadastrada
	 * @return id da carona
	 * @throws SQLException
	 */
	public String getCaronaUsuario(String idSessao, int indexCarona) throws SQLException{
		logger.info("Executando método getCaronaUsuario");
		
		List<String> id  = new ArrayList<String>();
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT id FROM carona WHERE idUsuario = '" + idSessao +"'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while(rs.next()){
			id.add(rs.getString("id"));
		}		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return id.get(indexCarona-1);
	}
	
	/**
	 * Recebe o id da sessão do usuário e retorna todas as caronas desse usuário.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @return todas as caronas do usuário
	 * @throws SQLException
	 */
	public Map<String, Carona> getTodasCaronasUsuario(String idSessao) throws SQLException{
		logger.info("Executando método getTodasCaronasUsuario");
		
		Map<String, Carona> caronasLocalizadas = new LinkedHashMap<String, Carona>();		
		Connection conexao = new ConnectionFactory().getConnection();	
		String sql = "SELECT * FROM carona WHERE idUsuario = '" + idSessao +"'";

		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			LocalDate data = LocalDate.parse(rs.getString("data"));
			LocalTime hora = LocalTime.parse(rs.getString("hora"));
			Carona carona = new Carona(rs.getString("origem"), rs.getString("destino"), data, hora, rs.getInt("vagas"));
			carona.setIdUsuario(rs.getString("idUsuario"));
			caronasLocalizadas.put(rs.getString("id"), carona);
		}

		stmt.execute();
		stmt.close();

		conexao.close();		
								
		return caronasLocalizadas;
	}
	
	/**
	 * Recebe o id da carona e verifica se a carona é municipal.
	 * 
	 * @param idCarona id da carona
	 * @return true = municipal, false = não municipal
	 * @throws SQLException
	 */
	public boolean caronaMunicipal(String idCarona) throws SQLException{
		logger.info("Executando método caronaMunicipal");
		
		boolean municipal = false;		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT municipal FROM carona WHERE id = '" + idCarona + "'";		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			municipal = rs.getBoolean("municipal");
		}
		stmt.execute();
		stmt.close();		
		conexao.close();
		
		return municipal;
	}
	
	/**
	 * Recebe o id da carona e marca a carona como preferencial.
	 * 
	 * @param idCarona id da carona
	 * @throws SQLException
	 */
	public void definirCaronaPreferencial(String idCarona) throws SQLException{
		logger.info("Executando método definirCaronaPreferencial");
		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "UPDATE carona SET preferencial = true WHERE id = '" + idCarona + "'";	
		PreparedStatement stmt = conexao.prepareStatement(sql);
		stmt.execute();
		stmt.close();		
		conexao.close();
	}
	
	/**
	 * Recebe o id da carona e verifica se a carona é preferencial.
	 * 
	 * @param idCarona id da carona
	 * @return true = carona preferencial, false = carona não preferencial
	 * @throws SQLException
	 */
	public boolean isCaronaPreferencial(String idCarona) throws SQLException{
		logger.info("Executando método isCaronaPreferencial");
		
		boolean preferencial = false;		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT preferencial FROM carona WHERE id = '" + idCarona + "'";		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			preferencial = rs.getBoolean("preferencial");
		}
		stmt.execute();
		stmt.close();		
		conexao.close();
		
		return preferencial;
	}
	
	/**
	 * Apaga todas as caronas que estão armazenadas no banco de dados.
	 * 
	 * @throws SQLException 
	 */
	public void apagarCaronas() throws SQLException{
		logger.info("Executando método apagarCaronas");
		
		Connection conexao1 = new ConnectionFactory().getConnection();
		String sql1 = "DELETE FROM carona WHERE id > 0";
		PreparedStatement stmt1 = conexao1.prepareStatement(sql1);
		stmt1.execute();
		stmt1.close();		
		conexao1.close();
		
		Connection conexao2 = new ConnectionFactory().getConnection();
		String sql2 = "ALTER TABLE carona AUTO_INCREMENT = 1";
		PreparedStatement stmt2 = conexao2.prepareStatement(sql2);
		stmt2.execute();
		stmt2.close();		
		conexao2.close();
	}

}
