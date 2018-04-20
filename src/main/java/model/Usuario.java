package model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import dao.UsuarioDAO;

/**
 * Classe responável por representar um usuário.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class Usuario {
	
	private static final Logger logger = LogManager.getLogger(Usuario.class);

	private String login;
	private String senha;
	private String nome;
	private String email;
	private String endereco;
	
	/**
	 * Construtor padrão.
	 */
	public Usuario(){
		
	}
	
	/**
	 * Construtor que recebe o login, a senha, o nome, o endereço e o email do usuário.
	 * Cria um usuário.
	 * 
	 * @param login login do usuário
	 * @param senha senha do usuário
	 * @param nome nome do usuário
	 * @param endereco endereço do usuário
	 * @param email email do usuário
	 * @throws Exception
	 */
	public Usuario(String login, String senha, String nome, String endereco, String email) throws Exception{
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.endereco = endereco;
		this.email = email;		
		criarUsuario();
	}
	
	/**
	 * Faz a verificação necessária dos atributos e cadastra um novo usuário.
	 * 
	 * @throws Exception
	 */
	public void criarUsuario() throws Exception{
		logger.info("Executando método criarUsuario");
		
		UsuarioDAO dao = UsuarioDAO.getInstance();
		
		if(login == null || login.equals("")){
			logger.error("Login inválido - login: " + login);
			throw new Exception("Login inválido");
		}
		if(nome == null || nome.equals("")){
			logger.error("Nome inválido - nome: " + nome);
			throw new Exception("Nome inválido");
		}
		if(email == null || email.equals("")){
			logger.error("Email inválido - email: " + email);
			throw new Exception("Email inválido");
		}
		if(dao.emailEstaCadastrado(email)){
			logger.error("Já existe um usuário com este email - email: " + email);
			throw new Exception("Já existe um usuário com este email");
		}
		if(dao.loginExiste(login)){
			logger.error("Já existe um usuário com este login - login: " + login);
			throw new Exception("Já existe um usuário com este login");
		}
		dao.cadastrarUsuario(this);
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
			logger.error("Login inválido - login: " + login);
			throw new Exception("Login inválido");
		}
		String idSessao = dao.abrirSessao(login, senha);
		if(idSessao.equals("Senha Incorreta")){
			logger.error("Login inválido - senha incorreta: " + senha);
			throw new Exception("Login inválido");
		}
		if(idSessao.equals("Usuario Inexistente")){
			logger.error("Usuário inexistente - login: " + login);
			throw new Exception("Usuário inexistente");
		}
		return idSessao;
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
			logger.error("Login inválido - login: " + login);
			throw new Exception("Login inválido");
		}
		else if(atributo == null || atributo.equals("")){
			logger.error("Atributo inválido - atributo: " + atributo);
			throw new Exception("Atributo inválido");
		}
		else if(!dao.loginExiste(login)){
			logger.error("Usuário inexistente - login: " + login);
			throw new Exception("Usuário inexistente");
		}				
		else if(atributo.equals("nome")){
			return dao.nomeUsuario(login);
		}
		else if(atributo.equals("endereco")){
			return dao.enderecoUsuario(login);
		}
		else{
			logger.error("Atributo inexistente - atributo: " + atributo);
			throw new Exception("Atributo inexistente");
		}
	}	

	/**
	 * 
	 * @return login do usuário
	 */
	public String getNomeLogin() {
		return login;
	}

	/**
	 * 
	 * @return senha do usuário
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * 
	 * @return nome do usuário
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * 
	 * @return endereço do usuário
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * 
	 * @return email do usuário
	 */
	public String getEmail() {
		return email;
	}	
}
