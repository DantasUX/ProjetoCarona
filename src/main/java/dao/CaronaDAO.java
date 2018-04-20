package dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import model.Carona;

/**
 * Classe responsável por representar um banco de dados. Ela possui um map, onde a chave vai ser o id da carona
 * e o objeto carona estará armazenado no valor. Todos os valors são armazenados apenas em tempo de
 * execução.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class CaronaDAO {
	
	private static final Logger logger = LogManager.getLogger(CaronaDAO.class);
	
	private static CaronaDAO instanciaUnica = null;
	
	private Map<String, Carona> caronas = new HashMap<String, Carona>();
	
	private CaronaDAO(){
		
	}
	
	/**
	 * Abre uma instância única da base de dados.
	 * 
	 * @return CaronaDAO
	 */
	public static CaronaDAO getInstance(){
		logger.info("Abrindo conexão com a base de dados de caronas");
		
		if(instanciaUnica == null){
			instanciaUnica = new CaronaDAO();			
		}		
		return instanciaUnica;
	}
	
	/**
	 * Recebe uma carona e a armazena na base de dados do sistema.
	 * 
	 * @param carona objeto carona
	 * @return id da carona
	 */
	public String cadastrarCarona(Carona carona){
		logger.info("Cadastrando carona");
		
		caronas.put((caronas.size()+1)+"", carona);
		return caronas.size()+"";
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
	 */
	public Map<String, Carona> localizarCarona(String origem, String destino){
		logger.info("Localizando carona - origem: " + origem + " - destino: " + destino);
		
		Map<String, Carona> caronasLocalizadas = new HashMap<String, Carona>();
		
		if(!origem.equals("")){
			if(!destino.equals("")){
				for(String key: caronas.keySet()){
					Carona carona = caronas.get(key);
					if(carona.getOrigem().equals(origem) && carona.getDestino().equals(destino)){
						caronasLocalizadas.put(key, carona);
					}
				}
			}
			else{
				for(String key: caronas.keySet()){
					Carona carona = caronas.get(key);
					if(carona.getOrigem().equals(origem)){
						caronasLocalizadas.put(key, carona);
					}
				}
			}
		}
		else{
			if(!destino.equals("")){
				for(String key: caronas.keySet()){
					Carona carona = caronas.get(key);
					if(carona.getDestino().equals(destino)){
						caronasLocalizadas.put(key, carona);
					}
				}
			}
			else{
				return caronas;
			}
		}						
		return caronasLocalizadas;
	}	
	
	/**
	 * Recebe o id da carona e retorna a sua origem.
	 * 
	 * @param idCarona id da carona
	 * @return origem da carona
	 */
	public String origemCarona(String idCarona){
		logger.info("Retornando origem da carona - id da carona: " + idCarona);
		
		return caronas.get(idCarona).getOrigem();
	}
	
	/**
	 * Recebe o id da carona e retorna o seu destino.
	 * 
	 * @param idCarona id da carona
	 * @return destino da carona
	 */
	public String destinoCarona(String idCarona){
		logger.info("Retornando destino da carona - id da carona: " + idCarona);
		
		return caronas.get(idCarona).getDestino();
	}
	
	/**
	 * Recebe o id da carona e retorna a sua data.
	 * 
	 * @param idCarona id da carona
	 * @return data da carona
	 */
	public String dataCarona(String idCarona){
		logger.info("Retornando data da carona - id da carona: " + idCarona);
		
		return caronas.get(idCarona).getData();
	}
	
	/**
	 * Recebe o id da carona e retorna a quantidade de vagas.
	 * 
	 * @param idCarona id da carona
	 * @return quantidade de vagas da carona
	 */
	public int vagasCarona(String idCarona){
		logger.info("Retornando vagas da carona - id da carona: " + idCarona);
		
		return caronas.get(idCarona).getVagas();
	}
	
	/**
	 * Recebe o id da carona e retorna o seu trajeto, ou seja, a origem e o destino.
	 * 
	 * @param idCarona id da carona
	 * @return trajeto da carona
	 */
	public String trajetoCarona(String idCarona){
		logger.info("Retornando trajeto da carona - id da carona: " + idCarona);
		
		return caronas.get(idCarona).getOrigem() + " - " + caronas.get(idCarona).getDestino();
	}
	
	/**
	 * Recebe o id da carona e retorna informações como origem, destino, data e hora.
	 * 
	 * @param idCarona id da carona
	 * @return informações da carona
	 */
	public String informacoesCarona(String idCarona){
		logger.info("Retornando informações da carona - id da carona: " + idCarona);
		
		String origem = caronas.get(idCarona).getOrigem();
		String destino = caronas.get(idCarona).getDestino();
		String data = caronas.get(idCarona).getData();
		String hora = caronas.get(idCarona).getHora();
		return  origem + " para " + destino + ", no dia " + data + ", as " + hora;
	}
	
	/**
	 * Recebe o id da carona e verifica se ele é válida, ou seja, se o id da carona existe na base de dados.
	 * 
	 * @param idCarona id da carona
	 * @return true = id da carona válido, false = id da carona inválido
	 */
	public boolean verificaCarona(String idCarona){
		logger.info("Verificando se o id da carona é válido - id da carona: " + idCarona);
		
		return caronas.containsKey(idCarona);
	}
	
	/**
	 * Apaga todas as caronas que estão armazenadas na base de dados.
	 */
	public void apagarCaronas(){
		logger.warn("Apagando todas as caronas.");
		
		caronas.clear();
	}

}
