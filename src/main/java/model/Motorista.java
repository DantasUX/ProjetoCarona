package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import dao.CaronaDAO;
import dao.UsuarioDAO;

/**
 * Classe responsável por cuidar das funções que são de um motorista.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class Motorista {
	
	private static final Logger logger = LogManager.getLogger(Motorista.class);

	/**
	 * Recebe a id da sessão do usuário, a origem, o destino, a data, a hora, e a quantidade de vagas da carona,
	 * faz a verificação necessária de erros, cadastra uma carona e retorna o id da carona cadastrada.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param origem origem da carona
	 * @param destino destino da carona
	 * @param data data da carona
	 * @param hora hora da carona
	 * @param vagas vagas da carona
	 * @return id da carona
	 * @throws Exception
	 */
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, String vagas) throws Exception{
		logger.info("Executando método cadastrarCarona");
		
		CaronaDAO c = CaronaDAO.getInstance();
		if(idSessao == null || idSessao.equals("")){
			logger.error("Sessão inválida - sessão: " + idSessao);
			throw new Exception("Sessão inválida");
		}		
		if(origem == null || origem.equals("")){
			logger.error("Origem inválida - origem: " + origem);
			throw new Exception("Origem inválida");
		}
		if(destino == null || destino.equals("")){
			logger.error("Destino inválido - destino: " + destino);
			throw new Exception("Destino inválido");
		}
		if(data == null || data.equals("")){
			logger.error("Data inválida - data: " + data);
			throw new Exception("Data inválida");
		}
		if(hora == null || hora.equals("")){
			logger.error("Hora inválida - hora: " + hora);
			throw new Exception("Hora inválida");
		}
		if(vagas == null){
			logger.error("Vaga inválida - vaga: " + vagas);
			throw new Exception("Vaga inválida");
		}
		UsuarioDAO u = UsuarioDAO.getInstance();
		if(!u.verificaSessao(idSessao)){
			logger.error("Sessão inexistente - sessão: " + idSessao);
			throw new Exception("Sessão inexistente");
		}		
		
		Carona carona = new Carona(origem, destino, validaData(data), validaHora(hora), validaVagas(vagas));
		return c.cadastrarCarona(carona);
	}
	
	/**
	 * Recebe uma string data, valida essa data e retorna a data representada por um objeto LocalDate.
	 * 
	 * @param data data da carona
	 * @return LocalDate representando a data
	 * @throws Exception
	 */
	private LocalDate validaData(String data) throws Exception{
		logger.info("Executando método validaData");
		
	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
	    try {
	        return LocalDate.parse(data, formato);
	    } catch (Exception e) {
	    	logger.error("Data inválida - data: " + data);
	    	throw new Exception("Data inválida");
	    } 
	}
	
	/**
	 * Recebe uma string hora, valida essa hora e retorna a hora representada por um objeto LocalTime.
	 * 
	 * @param hora hora da carona
	 * @return LocalTime representando a hora
	 * @throws Exception
	 */
	private LocalTime validaHora(String hora) throws Exception{
		logger.info("Executando método validaHora");
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);
				
		try{
			return LocalTime.parse(hora, formato);
		}catch(Exception e){
			logger.error("Hora inválida - hora: " + hora);
			throw new Exception("Hora inválida");
		}	
	}
	
	/**
	 * Recebe uma string vagas, a valida e a converte para int retornando a quantidade de vagas em int.
	 * 
	 * @param vagas vagas da carona no formato string
	 * @return vagas da carona no formato int
	 * @throws Exception
	 */
	private int validaVagas(String vagas) throws Exception{
		logger.info("Executando método validaVagas");
		
		try{
			return Integer.parseInt(vagas);
		}
		catch(Exception e){
			logger.error("Vaga inválida - vaga: " + vagas);
			throw new Exception("Vaga inválida");
		}
	}	
}
