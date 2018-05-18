package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Classe responsável por gerenciar as operações da tabela solicitacao do banco de dados.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class SolicitacaoDAO {
	
	private static final Logger logger = LogManager.getLogger(UsuarioDAO.class);
	
	private static SolicitacaoDAO instanciaUnica = null;
	
	private SolicitacaoDAO(){
		
	}
	
	/**
	 * Abre uma instância única da base de dados.
	 * 
	 * @return SolicitacaoDAO
	 */
	public static SolicitacaoDAO getInstance(){
		logger.info("Abrindo conexão com a base de dados de solicitacao");
		
		if(instanciaUnica == null){
			instanciaUnica = new SolicitacaoDAO();			
		}		
		return instanciaUnica;
	}
	
	/**
	 * A partir de uma carona, o caroneiro pode sugerir pontos de encontro para essa carona criando uma solicitação.
	 * Por fim, retorna o id da solicitação.
	 * 
	 * @param caroneiro id do caroneiro
	 * @param idCarona id da carona
	 * @param pontos pontos que o caroneiro sugeriu
	 * @return id da solicitação
	 * @throws SQLException
	 */
	public String sugerirPontoEncontro(String caroneiro, String idCarona, String pontos) throws SQLException{
		logger.info("Sugerindo pontos para uma carona e retornando o id da solicitação - id do caroneiro: " + caroneiro);
		
		String idSolicitacao = "";		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "INSERT INTO solicitacao " + "(caroneiro,carona,pontosSugeridos) "
				+ "values (?,?,?)";		
		PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, caroneiro);
		stmt.setString(2, idCarona);
		stmt.setString(3, pontos);		
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();		
		while(rs.next()){
			idSolicitacao = rs.getString(1);
		}		
		stmt.close();		
		conexao.close();		
		return idSolicitacao;
	}
	
	/**
	 * Após o caroneiro sugerir pontos de encontro, o motorista pode responder dizendo os pontos de encontro
	 * que ele quer.
	 * 
	 * @param motorista id do motorista
	 * @param idCarona id da carona
	 * @param idSugestao id da sugestão ou solicitação
	 * @param pontos pontos de resposta do motorista
	 * @throws SQLException
	 */
	public void responderSugestaoPontoEncontro(String motorista, String idCarona, String idSugestao, String pontos) throws SQLException{
		logger.info("Respondendo a sugestão de pontos de encontro do caroneiro - id do motorista" + motorista);
		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "UPDATE solicitacao SET motorista = '" + motorista + "', respostaPontosSugeridos = '" + pontos + "'"
				+ " WHERE id = '" + idSugestao + "' AND carona = '" + idCarona + "'";		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		stmt.execute();
		stmt.close();		
		conexao.close();
	}
	
	/**
	 * Se algum ponto de encontro da resposta do motorista for o mesmo do caroneiro, e se o caroneiro ainda
	 * querer a carona ele pode solicitar uma vaga dizendo o ponto para encontro.
	 * 
	 * @param caroneiro id do caroneiro
	 * @param idCarona id da carona
	 * @param ponto ponto de encontro para a carona
	 * @return id da solicitação
	 * @throws SQLException
	 */
	public String solicitarVagaPontoEncontro(String caroneiro, String idCarona, String ponto) throws SQLException{
		logger.info("Solicitando vaga - id do caroneiro: " + caroneiro);
		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "UPDATE solicitacao SET pontoEncontro = '" + ponto
				+ "' WHERE caroneiro = '" + caroneiro + "' AND carona = '" + idCarona + "'";		
		PreparedStatement stmt = conexao.prepareStatement(sql);		
		stmt.execute();
		stmt.close();		
		conexao.close();		
		return idSolicitacao(caroneiro, idCarona);
	}
	
	/**
	 * Recebe o id do caroneiro e a id da carona e retorna o id da solicitação.
	 * 
	 * @param caroneiro id do caroneiro
	 * @param idCarona id da carona
	 * @return id da solicitação
	 * @throws SQLException
	 */
	private String idSolicitacao(String caroneiro, String idCarona) throws SQLException{
		String idSolicitacao = "";		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT id FROM solicitacao"
				+ " WHERE caroneiro = '" + caroneiro + "' AND carona = '" + idCarona + "'";		
		PreparedStatement stmt = conexao.prepareStatement(sql);		
		ResultSet rs = stmt.executeQuery();		
		while(rs.next()){
			idSolicitacao = rs.getString("id");
		}		
		stmt.execute();
		stmt.close();		
		conexao.close();		
		return idSolicitacao;
	}
	
	/**
	 * Recebe o id da solicitação e retorna a origem da carona.
	 * 
	 * @param idSolicitacao id da solicitação
	 * @return origem da carona
	 * @throws SQLException
	 */
	public String origemSolicitacao(String idSolicitacao) throws SQLException{
		logger.info("Retornando origem da carona a partir de solicitação");
		
		String sql = "SELECT origem FROM solicitacao, carona WHERE solicitacao.id = '" + idSolicitacao + "' AND solicitacao.carona = carona.id";
		return retornaInformacaoSolicitacao(idSolicitacao, sql, "origem");
	}	
	
	/**
	 * Recebe o id da solicitação e retorna o destino da carona.
	 * 
	 * @param idSolicitacao id da solicitação
	 * @return destino da carona
	 * @throws SQLException
	 */
	public String destinoSolicitacao(String idSolicitacao) throws SQLException{
		logger.info("Retornando destino da carona a partir de solicitação");
		
		String sql = "SELECT destino FROM solicitacao, carona WHERE solicitacao.id = '" + idSolicitacao + "' AND solicitacao.carona = carona.id";
		return retornaInformacaoSolicitacao(idSolicitacao, sql, "destino");
	}
	
	/**
	 * Recebe o id da solicitação e retorna o dono ou motorista da carona.
	 * 
	 * @param idSolicitacao id da solicitação
	 * @return dono ou motorista da carona
	 * @throws SQLException
	 */
	public String donoCarona(String idSolicitacao) throws SQLException{
		logger.info("Retornando motorista da carona a partir de solicitação");
		
		String sql = "SELECT nome FROM solicitacao, carona, usuario WHERE solicitacao.id = '" + idSolicitacao +
				"' AND solicitacao.carona = carona.id AND carona.idUsuario = usuario.id";
		return retornaInformacaoSolicitacao(idSolicitacao, sql, "nome");
	}
	
	/**
	 * Recebe o id da solicitação e retorna o dono da solicitação ou caroneiro.
	 * 
	 * @param idSolicitacao id da solicitação
	 * @return dono da solicitação ou caroneiro
	 * @throws SQLException
	 */
	public String donoSolicitacao(String idSolicitacao) throws SQLException{
		logger.info("Retornando caroneiro da carona a partir de solicitação");
		
		String sql = "SELECT nome FROM solicitacao, usuario WHERE solicitacao.id = '" + idSolicitacao + "' AND solicitacao.caroneiro = usuario.id";
		return retornaInformacaoSolicitacao(idSolicitacao, sql, "nome");
	}
	
	/**
	 * Recebe o id da solicitação e retorna o ponto de encontro da carona.
	 * 
	 * @param idSolicitacao id da solicitação
	 * @return ponto de encontro da carona
	 * @throws SQLException
	 */
	public String pontoEncontro(String idSolicitacao) throws SQLException{
		logger.info("Retornando ponto de encontro da carona");
		
		String sql = "SELECT pontoEncontro FROM solicitacao WHERE solicitacao.id = '" + idSolicitacao + "'";
		return retornaInformacaoSolicitacao(idSolicitacao, sql, "pontoEncontro");
	}
	
	/**
	 * Recebe o id da solicitação, uma string sql de busca no banco de dados e a coluna para recuperar a informação.
	 * Por fim, retorna a informação.
	 * 
	 * @param idSolicitacao od da solicitação
	 * @param sql string sql de busca no banco de dados
	 * @param coluna de uma tabela do banco de dados para recuperar a informação
	 * @return informação da coluna
	 * @throws SQLException
	 */
	private String retornaInformacaoSolicitacao(String idSolicitacao, String sql, String coluna) throws SQLException{
		String informacao = "";		
		Connection conexao = new ConnectionFactory().getConnection();
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
	 * O motorista pode aceitar ou não a solicitação do caroneiro para uma vaga na carona.
	 * 
	 * @param motorista id do motorista
	 * @param idSolicitacao id da solicitação
	 * @throws Exception
	 */
	public void aceitarSolicitacaoPontoEncontro(String motorista, String idSolicitacao) throws Exception{
		logger.info("Aceitar solicitação do caroneiro para uma carona - id do motorista: " + motorista);
		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "UPDATE solicitacao SET solicitacaoAceita = true, motorista = '" + motorista
				+ "' WHERE id = '" + idSolicitacao + "'";		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		stmt.execute();
		stmt.close();		
		conexao.close();		
		int vagas = recuperaVagasCarona(idSolicitacao);
		vagas--;
		atualizaVagasCarona(idSolicitacao, vagas);		
	}	
	
	/**
	 * Recebe o id da solicitação e retorna a quantidade de vagas da carona.
	 * 
	 * @param idSolicitacao id da solicitação
	 * @return quantidade de vagas da carona
	 * @throws SQLException
	 */
	private int recuperaVagasCarona(String idSolicitacao) throws SQLException{
		int vagas = 0;		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT vagas FROM carona, solicitacao"
				+ " WHERE solicitacao.id = '" + idSolicitacao + "' AND carona.id = solicitacao.carona";		
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
	 * Recebe o id da solicitação e uma nova quantidade de vagas da carona e atualiza a quantidade de vagas que
	 * a carona tem atualmente.
	 * 
	 * @param idSolicitacao id da solicitação
	 * @param vagas nova quantidade de vagas da carona
	 * @throws SQLException
	 */
	private void atualizaVagasCarona(String idSolicitacao, int vagas) throws SQLException{				
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "UPDATE carona, solicitacao SET vagas = '" + vagas
				+ "' WHERE solicitacao.id = '" + idSolicitacao + "' AND carona.id = solicitacao.carona";		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		stmt.execute();
		stmt.close();		
		conexao.close();
	}
	
	/**
	 * Recebe o id da solicitação e verifica se a solicitação já foi aceita.
	 * 
	 * @param idSolicitacao id da solicitação
	 * @return true = solicitação aceita, false = solicitação não aceita
	 * @throws SQLException
	 */
	public boolean verificaSolicitacaoAceita(String idSolicitacao) throws SQLException{
		logger.info("Verificando se a solicitação já foi aceita");
		
		boolean solicitacaoAceita = false;		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT solicitacaoAceita FROM solicitacao"
				+ " WHERE id = '" + idSolicitacao + "'";		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();				
		while (rs.next()) {
			solicitacaoAceita = rs.getBoolean("solicitacaoAceita");
		}		
		stmt.execute();
		stmt.close();		
		conexao.close();		
		return solicitacaoAceita;
	}
	
	/**
	 * O usuário pode desistir da carona a qualquer momento. Esse método recebe o id da sessão do usuário,
	 * o id da carona e o id da solicitação e marca a solicitação como desistida.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @param idSolicitacao id da solicitação
	 * @throws Exception
	 */
	public void desistirRequisicao(String idSessao, String idCarona, String idSolicitacao) throws Exception{
		logger.info("Desistindo da solicitação");
		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "UPDATE solicitacao SET solicitacaoDesistida = true"
				+ " WHERE id = '" + idSolicitacao + "'";		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		stmt.execute();
		stmt.close();		
		conexao.close();		
		int vagas = recuperaVagasCarona(idSolicitacao);
		vagas++;
		atualizaVagasCarona(idSolicitacao, vagas);
		
		
	}
	
	/**
	 * Recebe o id da sessão do usuário e o id da carona, solicita uma vaga na carona e retorna o id da solicitação.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @return id da solicitação
	 * @throws SQLException
	 */
	public String solicitarVaga(String idSessao, String idCarona) throws SQLException{
		logger.info("Solicitando vaga na carona - id da sessão: " + idSessao);
		
		String idSolicitacao = "";
		if(!solicitacaoExiste(idSessao, idCarona)){
			Connection conexao = new ConnectionFactory().getConnection();			
			String sql = "INSERT INTO solicitacao " + "(caroneiro,carona) "
					+ "values (?,?)";			
			PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, idSessao);
			stmt.setString(2, idCarona);			
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();			
			while(rs.next()){
				idSolicitacao = rs.getString(1);
			}			
			stmt.close();			
			conexao.close();
		}
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT id FROM solicitacao WHERE caroneiro = '" + idSessao + "'"
				+ " AND carona = '" + idCarona + "'";		
		PreparedStatement stmt = conexao.prepareStatement(sql);		
		ResultSet rs = stmt.executeQuery();		
		while(rs.next()){
			idSolicitacao = rs.getString("id");
		}		
		stmt.execute();
		stmt.close();		
		conexao.close();		
		return idSolicitacao;
	}
	
	/**
	 * Recebe o id da sessão do usuário e o id da carona e verifica se a solicitação existe.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @return true = solicitação existe, false = solicitação não existe
	 * @throws SQLException
	 */
	public boolean solicitacaoExiste(String idSessao, String idCarona) throws SQLException{
		logger.info("Verificando se solicitação existe");
		
		boolean solicitacaoExiste = false;		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT id FROM solicitacao WHERE caroneiro = '" + idSessao + "'"
				+ " AND carona = '" + idCarona + "'";		
		PreparedStatement stmt = conexao.prepareStatement(sql);		
		ResultSet rs = stmt.executeQuery();		
		solicitacaoExiste = rs.next();		
		stmt.execute();
		stmt.close();		
		conexao.close();		
		return solicitacaoExiste;
	}
	
	/**
	 * Recebe o id da sessão do usuário e o id da solicitação e rejeita a solicitação.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idSolicitacao id da solicitação
	 * @throws Exception
	 */
	public void rejeitarSolicitacao(String idSessao, String idSolicitacao) throws Exception{
		logger.info("Rejeitando solicitação - id da sessão: " + idSessao);
		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "UPDATE solicitacao SET solicitacaoRejeitada = true, motorista = '" + idSessao
				+ "' WHERE id = '" + idSolicitacao + "'";		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		stmt.execute();
		stmt.close();		
		conexao.close();
	}
	
	/**
	 * Recebe o id da solicitação e verifica se ela já foi rejeitada.
	 * 
	 * @param idSolicitacao id da solicitação
	 * @return true = solicitação rejeitada, false = solicitação não rejeitada
	 * @throws SQLException
	 */
	public boolean verificaSolicitacaoRejeitada(String idSolicitacao) throws SQLException{
		logger.info("Verificando se a solicitação já está rejeitada");
		
		boolean solicitacaoRejeitada = false;		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT solicitacaoRejeitada FROM solicitacao"
				+ " WHERE id = '" + idSolicitacao + "'";		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			solicitacaoRejeitada = rs.getBoolean("solicitacaoRejeitada");
		}
		stmt.execute();
		stmt.close();		
		conexao.close();		
		return solicitacaoRejeitada;
	}
	
	/**
	 * Recebe o id da sessão do usuário e o id da carona e retorna as solicitações confirmadas.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @return lista contendo o id de cada solicitação confirmada
	 * @throws SQLException
	 */
	public List<String> getSolicitacoesConfirmadas(String idSessao, String idCarona) throws SQLException{
		List<String> solicitacoesConfirmadas = new ArrayList<String>();		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT id FROM solicitacao WHERE motorista = '" + idSessao + "' AND carona = '" + idCarona
				+ "' AND solicitacaoAceita = true AND solicitacaoRejeitada = false AND solicitacaoDesistida = false";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {			
			solicitacoesConfirmadas.add(rs.getString("id"));
		}
		stmt.execute();
		stmt.close();
		conexao.close();								
		return solicitacoesConfirmadas;
	}
	
	/**
	 * Recebe o id da sessão do usuário e o id da carona e retorna as solicitações pendentes.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @return lista contendo o id de cada solicitação pendente
	 * @throws SQLException
	 */
	public List<String> getSolicitacoesPendentes(String idSessao, String idCarona) throws SQLException{
		List<String> solicitacoesPendentes = new ArrayList<String>();		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT id FROM solicitacao WHERE motorista = '" + idSessao + "' AND carona = '" + idCarona
				+ "' AND solicitacaoAceita = false AND solicitacaoRejeitada = false";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {			
			solicitacoesPendentes.add(rs.getString("id"));
		}
		stmt.execute();
		stmt.close();
		conexao.close();								
		return solicitacoesPendentes;
	}
	
	/**
	 * Recebe o id da sessão do usuário e o id da carona e retorna os pontos de encontro de cada caroneiro diferente da carona.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @return lista contendo os pontos de encontro de cada caroneiro da carona
	 * @throws SQLException
	 */
	public List<String> getPontosEncontro(String idSessao, String idCarona) throws SQLException{
		List<String> pontosEncontro = new ArrayList<String>();		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT pontoEncontro FROM solicitacao WHERE motorista = '" + idSessao + "' AND carona = '" + idCarona + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			if(rs.getString("pontoEncontro") != null){
				pontosEncontro.add(rs.getString("pontoEncontro"));
			}			
		}
		stmt.execute();
		stmt.close();
		conexao.close();								
		return pontosEncontro;
	}
	
	/**
	 * Recebe o id da sessão do usuário e o id da carona e retorna os pontos sugeridos de cada caroneiro da carona.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @return lista contendo os pontos sugeridos de cada caroneiro da carona
	 * @throws SQLException
	 */
	public List<String> getPontosSugeridos(String idSessao, String idCarona) throws SQLException{
		List<String> pontosSugeridos = new ArrayList<String>();		
		Connection conexao = new ConnectionFactory().getConnection();	
		String sql = "SELECT pontosSugeridos FROM solicitacao WHERE motorista = '" + idSessao + "' AND carona = '" + idCarona + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {
			if(rs.getString("pontosSugeridos") != null){
				pontosSugeridos.add(rs.getString("pontosSugeridos"));
			}			
		}
		stmt.execute();
		stmt.close();
		conexao.close();								
		return pontosSugeridos;
	}
	
	/**
	 * Recebe o id da carona e o login do caroneiro e verifica se o caroneiro possui vaga na carona.
	 * 
	 * @param idCarona id da carona
	 * @param loginCaroneiro login do caroneiro
	 * @return true = possui vaga, false = não possui vaga
	 * @throws SQLException
	 */
	public boolean vagaUsuarioCaronaPorLogin(String idCarona, String loginCaroneiro) throws SQLException{
		boolean possuiVaga = false;		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT solicitacao.id FROM solicitacao, usuario WHERE carona = '" + idCarona + "'"
				+ " AND usuario.login = '" + loginCaroneiro + "'"
				+ " AND solicitacao.caroneiro = usuario.id AND solicitacaoAceita = true AND solicitacaoRejeitada = false"
				+ " AND solicitacaoDesistida = false";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		possuiVaga = rs.next();		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return possuiVaga;
	}
	
	/**
	 * Recebe o id da carona e o id da sessão do usuário e verifica se o usuário possui vaga na carona.
	 * 
	 * @param idCarona id da carona
	 * @param idSessao id da sessão do usuário
	 * @return true = possui vaga, false = não possui vaga
	 * @throws SQLException
	 */
	public boolean vagaUsuarioCaronaPorSessao(String idCarona, String idSessao) throws SQLException{
		boolean possuiVaga = false;		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT solicitacao.id FROM solicitacao, usuario WHERE carona = '" + idCarona + "'"
				+ " AND usuario.id = '" + idSessao + "'"
				+ " AND solicitacao.caroneiro = usuario.id AND solicitacaoAceita = true AND solicitacaoRejeitada = false"
				+ " AND solicitacaoDesistida = false";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		possuiVaga = rs.next();		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return possuiVaga;
	}
	
	/**
	 * Recebe o login do usuário e retorna o seu histórico de vagas em caronas.
	 * 
	 * @param login login do usuário
	 * @return lista contendo o id de cada carona que o usuário possui uma vaga
	 * @throws SQLException
	 */
	public List<String> historicoVagasCaronas(String login) throws SQLException{
		List<String> caronas = new ArrayList<String>();		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "SELECT solicitacao.carona FROM solicitacao, usuario WHERE usuario.login = '" + login + "'"
				+ " AND solicitacao.caroneiro = usuario.id AND solicitacaoAceita = true";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {			
			caronas.add(rs.getString("carona"));
		}
		stmt.execute();
		stmt.close();
		conexao.close();								
		return caronas;
	}
	
	/**
	 * Apaga todas as solicitações do banco de dados.
	 * 
	 * @throws SQLException
	 */
	public void apagarSolicitacoes() throws SQLException{
		Connection conexao1 = new ConnectionFactory().getConnection();
		String sql1 = "DELETE FROM solicitacao WHERE id > 0";
		PreparedStatement stmt1 = conexao1.prepareStatement(sql1);
		stmt1.execute();
		stmt1.close();		
		conexao1.close();
		
		Connection conexao2 = new ConnectionFactory().getConnection();
		String sql2 = "ALTER TABLE solicitacao AUTO_INCREMENT = 1";
		PreparedStatement stmt2 = conexao2.prepareStatement(sql2);
		stmt2.execute();
		stmt2.close();		
		conexao2.close();
	}

}
