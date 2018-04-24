package model;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import dao.CaronaDAO;
import dao.SolicitacaoDAO;

/**
 * Classe responsável por cuidar das funções que são de um caroneiro.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class Caroneiro {
	
	private static final Logger logger = LogManager.getLogger(Caroneiro.class);

	/**
	 * Recebe a origem e o destino da carona, faz a verificação necessária de erros e localiza as caronas.
	 * Tanto a origem quanto o destino podem ser deixados em branco. origem = "" retorna todas as caronas
	 * com o destino não em branco. destino = "" retorna todas as caronas com a origem não em branco.
	 * origem = "" e destino = "" retorna todas as caronas do sistema.
	 * 
	 * @param origem origem da carona
	 * @param destino destino da carona
	 * @return um map contendo o id e a carona
	 * @throws Exception
	 */
	public Map<String, Carona> localizarCarona(String origem, String destino) throws Exception{
		logger.info("Executando método localizarCarona");
		
		CaronaDAO dao = CaronaDAO.getInstance();
		if(origem.equals("-") || origem.equals("()") || origem.equals("!") || origem.equals("!?")){
			Exception e = new Exception("Origem inválida");
			logger.error("Origem inválido - origem: " + origem, e);
			throw e;
		}
		if(destino.equals(".") || destino.equals("()") || destino.equals("!?")){
			Exception e = new Exception("Destino inválido");
			logger.error("Destino inválido - destino: " + destino, e);
			throw e;
		}
		return dao.localizarCarona(origem, destino);
	}
	
	/**
	 * Recebe o id da carona e o atributo, faz a verificação necessária de erros e retorna a origem, o destino,
	 * a data ou quantidade de vagas, dependendo do valor do atributo.
	 * 
	 * @param idCarona id da carona
	 * @param atributo origem, destino, data ou vagas
	 * @return origem, destino, data ou vagas, dependendo do valor do atributo
	 * @throws Exception
	 */
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{
		logger.info("Executando método getAtributoCarona");
		
		CaronaDAO dao = CaronaDAO.getInstance();
		if(idCarona == null || idCarona.equals("")){
			Exception e = new Exception("Identificador do carona é inválido");
			logger.error("Identificador do carona é inválido - id carona: " + idCarona, e);
			throw e;
		}
		else if(atributo == null || atributo.equals("")){
			Exception e = new Exception("Atributo inválido");
			logger.error("Atributo inválido - atributo: " + atributo, e);
			throw e;
		}
		else if(!dao.verificaCarona(idCarona)){
			Exception e = new Exception("Item inexistente");
			logger.error("Item inexistente - id carona: " + idCarona, e);
			throw e;
		}		
		else if(atributo.equals("origem")){
			return dao.origemCarona(idCarona);
		}
		else if(atributo.equals("destino")){
			return dao.destinoCarona(idCarona);
		}
		else if(atributo.equals("data")){
			return dao.dataCarona(idCarona);
		}
		else if(atributo.equals("vagas")){
			return dao.vagasCarona(idCarona)+"";
		}
		else{
			Exception e = new Exception("Atributo inexistente");
			logger.error("Atributo inexistente - atributo: " + atributo, e);
			throw e;
		}
	}	
	
	/**
	 * Recebe o id da carona, faz a verificação necessária de erros e retorna o seu trajeto, ou seja,
	 * a origem e o destino.
	 * 
	 * @param idCarona id da carona
	 * @return trajeto da carona
	 * @throws Exception
	 */
	public String getTrajeto(String idCarona) throws Exception{
		logger.info("Executando método getTrajeto");
		
		CaronaDAO dao = CaronaDAO.getInstance();
		if(idCarona == null){
			Exception e = new Exception("Trajeto Inválida");
			logger.error("Trajeto Inválida - id carona: " + idCarona, e);
			throw e;
		}
		if(idCarona.equals("") || !dao.verificaCarona(idCarona)){
			Exception e = new Exception("Trajeto Inexistente");
			logger.error("Trajeto inexistente - id carona: " + idCarona, e);
			throw e;
		}		
		return dao.trajetoCarona(idCarona);
	}
	
	/**
	 * Recebe o id da carona, faz a verificação necessária de erros e retorna informações como origem, destino,
	 * data e hora.
	 * 
	 * @param idCarona id da carona
	 * @return informações da carona
	 * @throws Exception
	 */
	public String getCarona(String idCarona) throws Exception{
		logger.info("Executando método getCarona");
		
		CaronaDAO dao = CaronaDAO.getInstance();
		if(idCarona == null){
			Exception e = new Exception("Carona Inválida");
			logger.error("Carona Inválida - id carona: " + idCarona, e);
			throw e;
		}
		if(idCarona.equals("") || !dao.verificaCarona(idCarona)){
			Exception e = new Exception("Carona Inexistente");
			logger.error("Carona inexistente - id carona: " + idCarona, e);
			throw e;
		}		
		return dao.informacoesCarona(idCarona);
	}
	
	/**
	 * Recebe o id da sessão do usuário, o id da carona e os pontos de encontro sugeridos, faz a verificação
	 * necessária, cria uma solicitação e retorna o id dessa solicitação.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @param pontos pontos de encontro sugeridos pelo usuário
	 * @return id da solicitação
	 * @throws Exception
	 */
	public String sugerirPontoEncontro(String idSessao, String idCarona, String pontos) throws Exception{
		if(pontos.equals("")){
			Exception e = new Exception("Ponto Inválido");
			logger.error("Ponto Inválido - pontos: " + pontos, e);
			throw e;
		}
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		return s.sugerirPontoEncontro(idSessao, idCarona, pontos);
	}
	
	/**
	 * Recebe o id da sessão do usuário, o id da carona e o ponto sugerido pelo usuário. Se algum ponto de
	 * encontro da resposta do motorista for o mesmo do caroneiro, e se o caroneiro ainda querer a carona ele
	 * pode solicitar uma vaga dizendo o ponto para encontro. Por fim, retorna o id da solicitação.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @param ponto ponto de encontro sugerido pelo usuário
	 * @return id da solicitação
	 * @throws SQLException
	 */
	public String solicitarVagaPontoEncontro(String idSessao, String idCarona, String ponto) throws SQLException{
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		return s.solicitarVagaPontoEncontro(idSessao, idCarona, ponto);
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
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		return s.solicitarVaga(idSessao, idCarona);
	}	
}
