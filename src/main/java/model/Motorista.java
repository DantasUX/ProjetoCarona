package model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import dao.CaronaDAO;
import dao.PresencaDAO;
import dao.SolicitacaoDAO;
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
			Exception e = new Exception("Sessão inválida");
			logger.error("Sessão inválida - sessão: " + idSessao, e);
			throw e;
		}		
		if(origem == null || origem.equals("")){
			Exception e = new Exception("Origem inválida");
			logger.error("Origem inválida - origem: " + origem, e);
			throw e;
		}
		if(destino == null || destino.equals("")){
			Exception e = new Exception("Destino inválido");
			logger.error("Destino inválido - destino: " + destino, e);
			throw e;
		}
		if(data == null || data.equals("")){
			Exception e = new Exception("Data inválida");
			logger.error("Data inválida - data: " + data, e);
			throw e;
		}
		if(hora == null || hora.equals("")){
			Exception e = new Exception("Hora inválida");
			logger.error("Hora inválida - hora: " + hora, e);
			throw e;
		}
		if(vagas == null){
			Exception e = new Exception("Vaga inválida");
			logger.error("Vaga inválida - vaga: " + vagas, e);
			throw e;
		}
		UsuarioDAO u = UsuarioDAO.getInstance();
		if(!u.verificaSessao(idSessao)){
			Exception e = new Exception("Sessão inexistente");
			logger.error("Sessão inexistente - sessão: " + idSessao, e);
			throw e;
		}		
		
		Carona carona = new Carona(origem, destino, validaData(data), validaHora(hora), validaVagas(vagas));
		carona.setIdUsuario(idSessao);
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
	    	logger.error("Data inválida - data: " + data, new Exception("Data inválida"));
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
			logger.error("Hora inválida - hora: " + hora, new Exception("Hora inválida"));
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
			logger.error("Vaga inválida - vaga: " + vagas, new Exception("Vaga inválida"));
			throw new Exception("Vaga inválida");
		}
	}
	
	/**
	 * Recebe o id da sessão do usuário, o id da carona, o id da sugestão e os pontos de encontro de resposta
	 * do motorista. Após o caroneiro sugerir pontos de encontro, o motorista pode responder dizendo os pontos
	 * de encontro dele.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @param idSugestao id da sugestão ou solicitação
	 * @param pontos pontos de encontro da resposta do motorista
	 * @throws Exception
	 */
	public void responderSugestaoPontoEncontro(String idSessao, String idCarona, String idSugestao, String pontos) throws Exception{
		if(pontos.equals("")){
			Exception e = new Exception("Ponto Inválido");
			logger.error("Ponto Inválido - pontos: " + pontos, e);
			throw e;
		}
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		s.responderSugestaoPontoEncontro(idSessao, idCarona, idSugestao, pontos);
	}
	
	/**
	 * Recebe o id da sessão do usuário e o id da solicitação e aceita a solicitação do caroneiro para uma vaga
	 * na carona.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idSolicitacao id da solicitação
	 * @throws Exception
	 */
	public void aceitarSolicitacaoPontoEncontro(String idSessao, String idSolicitacao) throws Exception{
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		if(s.verificaSolicitacaoAceita(idSolicitacao)){
			Exception e = new Exception("Solicitação inexistente");
			logger.error("Solicitação inexistente - id solicitação: " + idSolicitacao, e);
			throw e;
		}
		s.aceitarSolicitacaoPontoEncontro(idSessao, idSolicitacao);
	}
	
	/**
	 * Recebe o id da sessão do usuário e o id da solicitação e rejeita a solicitação do caroneiro para uma vaga
	 * na carona.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idSolicitacao id da solicitação
	 * @throws Exception
	 */
	public void rejeitarSolicitacao(String idSessao, String idSolicitacao) throws Exception{
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		if(s.verificaSolicitacaoRejeitada(idSolicitacao)){
			Exception e = new Exception("Solicitação inexistente");
			logger.error("Solicitação inexistente - id solicitação: " + idSolicitacao, e);
			throw e;
		}
		s.rejeitarSolicitacao(idSessao, idSolicitacao);
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
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		List<String> a = s.getSolicitacoesConfirmadas(idSessao, idCarona);
		return a;
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
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		return s.getSolicitacoesPendentes(idSessao, idCarona);
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
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		return s.getPontosEncontro(idSessao, idCarona);
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
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		return s.getPontosSugeridos(idSessao, idCarona);
	}
	
	/**
	 * Recebe o id da sessão do usuário, o id da carona, o login do caroneiro, e a falta ou presença do caroneiro,
	 * faz a verificação necessária, e em seguida atribui uma falta ou presença ao caroneiro.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @param loginCaroneiro login do caroneiro
	 * @param review falta ou presença do caroneiro. Entradas possíveis: faltou, não faltou
	 * @throws Exception
	 */
	public void reviewVagaEmCarona(String idSessao, String idCarona, String loginCaroneiro, String review) throws Exception{
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		PresencaDAO dao = PresencaDAO.getInstance();
		if(!s.vagaUsuarioCarona(idCarona, loginCaroneiro)){
			Exception e = new Exception("Usuário não possui vaga na carona.");
			logger.error("Usuário não possui vaga na carona - id carona: " + idCarona, e);
			throw e;
		}
		if(review.equals("faltou")){
			dao.reviewVagaEmCarona(idSessao, idCarona, loginCaroneiro, true);
		}
		else if(review.equals("não faltou")){
			dao.reviewVagaEmCarona(idSessao, idCarona, loginCaroneiro, false);
		}
		else{
			Exception e = new Exception("Opção inválida.");
			logger.error("Opção inválida - review: " + review, e);
			throw e;
		}
	}
}
