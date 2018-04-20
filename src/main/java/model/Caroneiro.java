package model;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import dao.CaronaDAO;

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
			logger.error("Origem inválido - origem: " + origem);
			throw new Exception("Origem inválida");
		}
		if(destino.equals(".") || destino.equals("()") || destino.equals("!?")){
			logger.error("Destino inválido - destino: " + destino);
			throw new Exception("Destino inválido");
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
			logger.error("Identificador do carona é inválido - id carona: " + idCarona);
			throw new Exception("Identificador do carona é inválido");
		}
		else if(atributo == null || atributo.equals("")){
			logger.error("Atributo inválido - atributo: " + atributo);
			throw new Exception("Atributo inválido");
		}
		else if(!dao.verificaCarona(idCarona)){
			logger.error("Item inexistente - id carona: " + idCarona);
			throw new Exception("Item inexistente");
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
			logger.error("Atributo inexistente - atributo: " + atributo);
			throw new Exception("Atributo inexistente");
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
			logger.error("Trajeto Inválida - id carona: " + idCarona);
			throw new Exception("Trajeto Inválida");
		}
		if(idCarona.equals("") || !dao.verificaCarona(idCarona)){
			logger.error("Trajeto inexistente - id carona: " + idCarona);
			throw new Exception("Trajeto Inexistente");
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
			logger.error("Carona Inválida - id carona: " + idCarona);
			throw new Exception("Carona Inválida");
		}
		if(idCarona.equals("") || !dao.verificaCarona(idCarona)){
			logger.error("Carona inexistente - id carona: " + idCarona);
			throw new Exception("Carona Inexistente");
		}		
		return dao.informacoesCarona(idCarona);
	}
}
