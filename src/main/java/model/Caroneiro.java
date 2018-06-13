package model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import dao.CaronaDAO;
import dao.InteresseDAO;
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
			logger.error("Origem inválida - origem: " + origem, e);
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
	 * Recebe a cidade, a origem e o destino da carona municipal, faz a verificação necessária de erros e localiza as caronas municipais.
	 * 
	 * @param cidade cidade da carona
	 * @param origem origem da carona
	 * @param destino destino da carona
	 * @return um map contendo o id e a carona
	 * @throws Exception
	 */
	public Map<String, Carona> localizarCaronaMunicipal(String cidade, String origem, String destino) throws Exception{
		logger.info("Executando método localizarCaronaMunicipal");
		
		CaronaDAO dao = CaronaDAO.getInstance();		
		if(cidade == null || cidade.equals("")){
			Exception e = new Exception("Cidade inexistente");
			logger.error("Cidade inexistente - cidade: " + cidade, e);
			throw e;
		}		
		return dao.localizarCaronaMunicipal(cidade, origem, destino);
	}
	
	/**
	 * Recebe a cidade da carona municipal, faz a verificação necessária de erros e localiza as caronas municipais.
	 * 
	 * @param cidade cidade da carona
	 * @return um map contendo o id e a carona
	 * @throws Exception
	 */
	public Map<String, Carona> localizarCaronaMunicipal(String cidade) throws Exception{
		logger.info("Executando método localizarCaronaMunicipal");
		
		CaronaDAO dao = CaronaDAO.getInstance();		
		if(cidade == null || cidade.equals("")){
			Exception e = new Exception("Cidade inexistente");
			logger.error("Cidade inexistente - cidade: " + cidade, e);
			throw e;
		}		
		return dao.localizarCaronaMunicipal(cidade);
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
		else if(atributo.equals("data") || atributo.equals("dataIda")){
			return dao.dataCarona(idCarona);
		}
		else if(atributo.equals("vagas")){
			return dao.vagasCarona(idCarona)+"";
		}
		else if(atributo.equals("ehMunicipal")){
			return dao.caronaMunicipal(idCarona)+"";
		}
		else if(atributo.equals("minimoCaroneiros")){
			return dao.minimoCaroneiro(idCarona)+"";
		}
		else if(atributo.equals("expired")){
			return caronaExpirada(idCarona)+"";
		}
		else{
			Exception e = new Exception("Atributo inexistente");
			logger.error("Atributo inexistente - atributo: " + atributo, e);
			throw e;
		}
	}
	
	/**
	 * Recebe o id da carona e verifica se a carona está expirada ou não.
	 * 
	 * @param idCarona id da carona
	 * @return true = carona expirada, false = carona não expirada
	 * @throws SQLException
	 */
	private boolean caronaExpirada(String idCarona) throws SQLException{
		logger.info("Executando método caronaExpirada");
		
		CaronaDAO c = CaronaDAO.getInstance();		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		String dataAgora = LocalDate.parse(LocalDate.now().toString()).format(formato);
		LocalDate data = LocalDate.parse(dataAgora, formato);
		LocalDate dataCarona = LocalDate.parse(c.dataCarona(idCarona), formato);
		
		if(data.isAfter(dataCarona)){
			return true;
		}
		return false;
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
		logger.info("Executando método sugerirPontoEncontro");
		
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
		logger.info("Executando método solicitarVagaPontoEncontro");
		
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		return s.solicitarVagaPontoEncontro(idSessao, idCarona, ponto);
	}
	
	/**
	 * Recebe o id da sessão do usuário e o id da carona, solicita uma vaga na carona e retorna o id da solicitação.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @return id da solicitação
	 * @throws Exception 
	 */
	public String solicitarVaga(String idSessao, String idCarona) throws Exception{
		logger.info("Executando método solicitarVaga");
		
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		if(isCaronaPreferencial(idCarona)){
			List<String> usuarios = usuariosPreferenciais();
			if(!usuarios.contains(idSessao)){
				Exception e = new Exception("Usuário não está na lista preferencial da carona");
				logger.error("Usuário não está na lista preferencial da carona - idSessao: " + idSessao, e);
				throw e;
			}
			else{
				return s.solicitarVaga(idSessao, idCarona);
			}
		}
		return s.solicitarVaga(idSessao, idCarona);
	}
	
	/**
	 * Recebe o id da sessão do usuário, o id da carona, e o review falando se a carona é segura e tranquila ou se ela
	 * não funcionou, faz a verificação necessária e em seguida marca a carona como segura e tranquila ou como não funcionou.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @param review carona segura e traquila ou carona não funcionou. Entradas possíveis: segura e traquila, não funcionou.
	 * @throws Exception
	 */
	public void reviewCarona(String idSessao, String idCarona, String review) throws Exception{
		logger.info("Executando método reviewCarona");
		
		CaronaDAO c = CaronaDAO.getInstance();
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		if(!s.vagaUsuarioCaronaPorSessao(idCarona, idSessao)){
			Exception e = new Exception("Usuário não possui vaga na carona.");
			logger.error("Usuário não possui vaga na carona - id carona: " + idCarona, e);
			throw e;
		}
		if(review.equals("segura e tranquila")){
			s.marcarComoSeguraTranquila(idSessao, idCarona);
		}
		else if(review.equals("não funcionou")){
			s.marcarNaoFuncionou(idSessao, idCarona);
		}
		else{
			Exception e = new Exception("Opção inválida.");
			logger.error("Opção inválida - Opção: " + review, e);
			throw e;
		}
	}
	
	/**
	 * Recebe o id da sessão do usuário, a origem, o destino e a data da carona, e a hora final e inicial,
	 * faz a verificação necessária de erros e retorna o id do interesse.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param origem origem da carona
	 * @param destino destino da carona
	 * @param data data da carona
	 * @param horaInicio hora inicial para encontrar a carona
	 * @param horaFim hora final para encontrar a carona
	 * @return id do interesse
	 * @throws Exception
	 */
	public String cadastrarInteresse(String idSessao, String origem, String destino, String data, String horaInicio, String horaFim) throws Exception{
		logger.info("Executando método cadastrarInteresse");
		
		InteresseDAO i = InteresseDAO.getInstance();		
		if(data == null){
			Exception e = new Exception("Data inválida");
			logger.error("Data inválida - data: " + data, e);
			throw e;
		}		
		if(origem.equals("-") || origem.equals("!")){
			Exception e = new Exception("Origem inválida");
			logger.error("Origem inválida - origem: " + origem, e);
			throw e;
		}
		if(destino.equals("-") || destino.equals("!")){
			Exception e = new Exception("Destino inválido");
			logger.error("Destino inválido - destino: " + destino, e);
			throw e;
		}		
		return i.cadastrarInteresse(idSessao, origem, destino, data, horaInicio, horaFim);
	}
	
	/**
	 * Recebe o id da carona e retorna o mínimo de caroneiros da carona relâmpago.
	 * 
	 * @param idCarona id da carona
	 * @return mínimo de caroneiro da carona relâmpago
	 * @throws SQLException
	 */
	public String getMinimoCaroneiros(String idCarona) throws SQLException{
		logger.info("Executando método getMinimoCaroneiros");
		
		CaronaDAO c = CaronaDAO.getInstance();
		return c.minimoCaroneiro(idCarona)+"";
	}
	
	/**
	 * Recebe o id da carona relâmpago e retorna informações como origem, destino, data e hora.
	 * 
	 * @param idCarona id da carona
	 * @return informações como origem, destino, data e hora
	 * @throws Exception
	 */
	public String getCaronaRelampago(String idCarona) throws Exception{
		logger.info("Executando método getCaronaRelampago");
		
		CaronaDAO c = CaronaDAO.getInstance();
		if(idCarona == null){
			Exception e = new Exception("Carona Inválida");
			logger.error("Carona Inválida - idCarona: " + idCarona, e);
			throw e;
		}
		if(!c.verificaCarona(idCarona)){
			Exception e = new Exception("Carona Inexistente");
			logger.error("Carona Inexistente - idCarona: " + idCarona, e);
			throw e;
		}
		return c.informacoesCarona(idCarona);
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
		
		CaronaDAO c = CaronaDAO.getInstance();
		return c.isCaronaPreferencial(idCarona);
	}
	
	/**
	 * Retorna a lista de usuários que tem preferência em uma carona.
	 * 
	 * @return lista contendo os usuários que tem preferência em uma carona
	 * @throws SQLException
	 */
	private List<String> usuariosPreferenciais() throws SQLException{
		logger.info("Executando método usuariosPreferenciais");
		
		SolicitacaoDAO dao = SolicitacaoDAO.getInstance();
		return dao.getUsuariosPreferenciaisCarona();
	}
}
