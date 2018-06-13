package model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import dao.CaronaDAO;
import dao.MensagemDAO;
import dao.PresencaDAO;
import dao.SolicitacaoDAO;
import dao.UsuarioDAO;

/**
 * Faz o controle de usuario.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class UsuarioControl {
	
	private static final Logger logger = LogManager.getLogger(UsuarioControl.class);
	
	/**
	 * Faz a verificação necessária dos atributos e cadastra um novo usuário.
	 * 
	 * @throws Exception
	 */
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws Exception{
		logger.info("Executando método criarUsuario");
		
		UsuarioDAO dao = UsuarioDAO.getInstance();
		
		if(login == null || login.equals("")){
			Exception e = new Exception("Login inválido");
			logger.error("Login inválido - login: " + login, e);
			throw e;
		}
		if(nome == null || nome.equals("")){
			Exception e = new Exception("Nome inválido");
			logger.error("Nome inválido - nome: " + nome, e);
			throw e;
		}
		if(email == null || email.equals("")){
			Exception e = new Exception("Email inválido");
			logger.error("Email inválido - email: " + email, e);
			throw e;
		}
		if(dao.emailEstaCadastrado(email)){
			Exception e = new Exception("Já existe um usuário com este email");
			logger.error("Já existe um usuário com este email - email: " + email, e);
			throw e;
		}
		if(dao.loginExiste(login)){
			Exception e = new Exception("Já existe um usuário com este login");
			logger.error("Já existe um usuário com este login - login: " + login, e);
			throw e;
		}
		Usuario usuario = new Usuario(login, senha, nome, endereco, email);
		dao.cadastrarUsuario(usuario);
	}
	
	/**
	 * Recebe as informações do login e da senha do usuário, faz a verificação necessária de erros, se o login
	 * e a senha estiverem corretos, retorna o id da sessão do usuário.
	 * 
	 * @param login login do usuário
	 * @param senha senha do usuário
	 * @return id da sessão do usuário
	 * @throws Exception
	 */
	public String abrirSessao(String login, String senha) throws Exception{
		logger.info("Executando método abrirSessao");
		
		UsuarioDAO dao = UsuarioDAO.getInstance();
		
		if(login == null || login.equals("")){
			Exception e = new Exception("Login inválido");
			logger.error("Login inválido - login: " + login, e);
			throw e;
		}
		if(!dao.loginExiste(login)){
			Exception e = new Exception("Usuário inexistente");
			logger.error("Usuário inexistente - login: " + login, e);
			throw e;
		}
		if(!dao.senhaExiste(login, senha)){
			Exception e = new Exception("Login inválido");
			logger.error("Login inválido - senha incorreta: " + senha, e);
			throw e;
		}		
		return dao.abrirSessao(login, senha);
	}
	
	/**
	 * Recebe as informações do login e do atributo, faz a verificação necessária de erros, se o login existir
	 * e o atributo não for inválido retorna o nome ou endereço do usuário.
	 * 
	 * @param login login do usuário
	 * @param atributo nome ou endereco
	 * @return nome ou endereço do usuário
	 * @throws Exception
	 */
	public String getAtributoUsuario(String login, String atributo) throws Exception{
		logger.info("Executando método getAtributoUsuario");
		
		UsuarioDAO dao = UsuarioDAO.getInstance();
		if(login == null || login.equals("")){
			Exception e = new Exception("Login inválido");
			logger.error("Login inválido - login: " + login, e);
			throw e;
		}
		else if(atributo == null || atributo.equals("")){
			Exception e = new Exception("Atributo inválido");
			logger.error("Atributo inválido - atributo: " + atributo, e);
			throw e;
		}
		else if(!dao.loginExiste(login)){
			Exception e = new Exception("Usuário inexistente");
			logger.error("Usuário inexistente - login: " + login, e);
			throw e;
		}				
		else if(atributo.equals("nome")){
			return dao.nomeUsuario(login);
		}
		else if(atributo.equals("endereco")){
			return dao.enderecoUsuario(login);
		}
		else{
			Exception e = new Exception("Atributo inexistente");
			logger.error("Atributo inexistente - atributo: " + atributo, e);
			throw e;
		}
	}
	
	/**
	 * Recebe o id da solicitação e o atributo e retorna a origem, o destino, o dono da carona, o dono da
	 * solicitação ou o ponto de encontro da solicitação.
	 * 
	 * @param idSolicitacao id da solicitação
	 * @param atributo origem, destino, Dono da carona, Dono da solicitacao ou Ponto de encontro da solicitação
	 * @return origem, destino, Dono da carona, Dono da solicitacao ou Ponto de encontro da solicitação
	 * @throws SQLException
	 */
	public String getAtributoSolicitacao(String idSolicitacao, String atributo) throws SQLException{
		logger.info("Executando método getAtributoSolicitacao");
		
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();		
		if(atributo.equals("origem")){
			return s.origemSolicitacao(idSolicitacao);
		}
		if(atributo.equals("destino")){
			return s.destinoSolicitacao(idSolicitacao);
		}
		if(atributo.equals("Dono da carona")){
			return s.donoCarona(idSolicitacao);
		}
		if(atributo.equals("Dono da solicitacao")){
			return s.donoSolicitacao(idSolicitacao);
		}
		else{
			return s.pontoEncontro(idSolicitacao);
		}
	}
	
	/**
	 * Recebe o id da sessão e o login do usuário e retorna toda a informação do perfil do usuário.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param login login do usuário
	 * @return objeto usuário contendo as informações do perfil
	 * @throws Exception
	 */
	public Usuario visualizarPerfil(String idSessao, String login) throws Exception{
		logger.info("Executando método visualizarPerfil");
		
		UsuarioDAO u = UsuarioDAO.getInstance();
		if(!u.loginExiste(login)){
			Exception e = new Exception("Login inválido");
			logger.error("Login inválido - login: " + login, e);
			throw e;
		}
		return u.perfil(idSessao, login);
	}
	
	/**
	 * Recebe o login do usuário e atributo e retorna o nome, o endereço, o email, o históricos de caronas,
	 * o histórico de vagas em caronas, as caronas seguras e tranquilas, as caronas que não funcionaram,
	 * as faltas em vagas de caronas ou as presenças em vagas de caronas do usuário.
	 * 
	 * @param login do usuário
	 * @param atributo nome, endereco, email, historico de caronas, historico de vagas em caronas, caronas seguras
	 * e traquilas, caronas que não funcionaram, faltas em vagas de caronas ou presenca em vagas de caronas
	 * @return nome, endereço, email, históricos de caronas, histórico de vagas em caronas, caronas seguras e
	 * tranquilas, caronas que não funcionaram, faltas em vagas de caronas ou presenças em vagas de caronas do usuário
	 * @throws SQLException
	 */
	public String getAtributoPerfil(String login, String atributo) throws SQLException{
		logger.info("Executando método getAtributoPerfil");
		
		UsuarioDAO u = UsuarioDAO.getInstance();
		CaronaDAO c = CaronaDAO.getInstance();
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		PresencaDAO p = PresencaDAO.getInstance();
		if(atributo.equals("nome")){
			return u.nomeUsuario(login);
		}
		if(atributo.equals("endereco")){
			return u.enderecoUsuario(login);
		}
		if(atributo.equals("email")){
			return u.emailUsuario(login);
		}
		if(atributo.equals("historico de caronas")){
			return c.historicoCaronas(login).toString().replaceAll(" ", "");
		}
		if(atributo.equals("historico de vagas em caronas")){
			return s.historicoVagasCaronas(login).toString().replaceAll(" ", "");
		}
		if(atributo.equals("caronas seguras e tranquilas")){
			return s.caronasSeguras(login)+"";
		}
		if(atributo.equals("caronas que não funcionaram")){
			return s.caronasQueNaoFuncionou(login)+"";
		}
		if(atributo.equals("faltas em vagas de caronas")){
			return p.faltasEmCaronas(login)+"";
		}
		else{
			return p.presencasEmCaronas(login)+"";
		}
	}
	
	/**
	 * Recebe o id da sessão do usuário, o id da carona, o id da solicitação e marca a solicitação como desistida.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param idCarona id da carona
	 * @param idSolicitacao id da solicitação
	 * @throws Exception
	 */
	public void desistirRequisicao(String idSessao, String idCarona, String idSolicitacao) throws Exception{
		logger.info("Executando método desistirRequisicao");
		
		SolicitacaoDAO s = SolicitacaoDAO.getInstance();
		if(!s.verificaSolicitacaoAceita(idSolicitacao)){
			Exception e = new Exception("Solicitação inexistente");
			logger.error("Solicitação inexistente - id solicitação: " + idSolicitacao, e);
			throw e;
		}
		s.desistirRequisicao(idSessao, idCarona, idSolicitacao);
	}	
	
	/**
	 * Recebe o id da sessão do usuário e o índice da posição da carona que define a ordem com que a carona foi
	 * cadastrada e retorna o id da carona.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param indexCarona índice da posição da carona que define a ordem com que a carona foi cadastrada
	 * @return id da carona
	 * @throws SQLException
	 */
	public String getCaronaUsuario(String idSessao, int indexCarona) throws SQLException{
		logger.info("Executando método getCaronaUsuario");
		
		CaronaDAO dao = CaronaDAO.getInstance();
		return dao.getCaronaUsuario(idSessao, indexCarona);
	}
	
	/**
	 * Recebe o id da sessão do usuário e retorna todas as caronas do usuário.
	 * 
	 * @param idSessao id da sessão do usuário.
	 * @return todas as caronas do usuário
	 * @throws SQLException
	 */
	public Map<String, Carona> getTodasCaronasUsuario(String idSessao) throws SQLException{
		logger.info("Executando método getTodasCaronasUsuario");
		
		CaronaDAO dao = CaronaDAO.getInstance();
		return dao.getTodasCaronasUsuario(idSessao);
	}
	
	/**
	 * Recebe o id da sessão do usuário e retorna uma lista das mensagens que não foram lidas.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @return lista das mensagens que não foram lidas
	 * @throws SQLException
	 */
	public List<String> verificarMensagensPerfil(String idSessao) throws SQLException{
		logger.info("Executando método verificarMensagensPerfil");
		
		MensagemDAO m = MensagemDAO.getInstance();
		return m.mensagensPerfil(idSessao);
	}
}
