package dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import model.Usuario;

/**
 * Classe responsável por representar um banco de dados. Ela possui um map, onde a chave vai ser o id do usuário
 * e o objeto usuario estará armazenado no valor. Todos os valores são armazenados apenas em tempo de
 * execução.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class UsuarioDAO {
	
	private static final Logger logger = LogManager.getLogger(UsuarioDAO.class);
		
	private static UsuarioDAO instanciaUnica = null;

	private Map<String, Usuario> usuarios = new HashMap<String, Usuario>();
	
	private UsuarioDAO(){
		
	}
	
	/**
	 * Abre uma instância única da base de dados.
	 * 
	 * @return UsuarioDAO
	 */
	public static UsuarioDAO getInstance(){
		logger.info("Abrindo conexão com a base de dados de usuarios");
		
		if(instanciaUnica == null){
			instanciaUnica = new UsuarioDAO();			
		}		
		return instanciaUnica;
	}
	
	/**
	 * Recebe um usuário e o armazena na base de dados do sistema.
	 * 
	 * @param usuario objeto usuario
	 */
	public void cadastrarUsuario(Usuario usuario){
		logger.info("Cadastrando usuario: " + usuario.getNome());
		
		usuarios.put((usuarios.size()+1)+"", usuario);
	}
	
	/**
	 * Recebe o login e a senha do usuário, abre uma nova sessão e retorna o id da sessão.
	 * 
	 * @param login login do usuário
	 * @param senha senha do usuário
	 * @return id da sessão do usuário
	 */
	public String abrirSessao(String login, String senha){
		logger.info("Abrindo sessão login: " + login);
		
		for(String key: usuarios.keySet()){
			Usuario usuario = usuarios.get(key);
			if(usuario.getNomeLogin().equals(login)){
				if(usuario.getSenha().equals(senha)){
					return key;
				}
				else{
					logger.error("Não foi possível abrir sessão. Senha incorreta. " + senha);
					return "Senha Incorreta";
				}
			}
		}
		logger.error("Não foi possível abrir sessão. Login não existe. " + login);
		return "Usuario Inexistente";
	}
	
	/**
	 * Recebe o login do usuário e retorna o nome dele.
	 * 
	 * @param login login do usuário
	 * @return nome do usuário
	 */
	public String nomeUsuario(String login){
		logger.info("Retornando nome do usuário - login: " + login);
		
		for(String key: usuarios.keySet()){
			Usuario usuario = usuarios.get(key);
			if(usuario.getNomeLogin().equals(login)){
				return usuario.getNome();
			}
		}
		logger.error("Não foi possível retornar o nome do usuário. Login não existe. " + login);
		return "Login não existe.";
	}
	
	/**
	 * Recebe o login do usuário e retorna o endereço dele.
	 * 
	 * @param login login do usuário
	 * @return endereço do usuário
	 */
	public String enderecoUsuario(String login){
		logger.info("Retornando endereço do usuário - login: " + login);
		
		for(String key: usuarios.keySet()){
			Usuario usuario = usuarios.get(key);
			if(usuario.getNomeLogin().equals(login)){
				return usuario.getEndereco();
			}
		}
		logger.error("Não foi possível retornar o endereço do usuário. Login não existe. " + login);
		return "Login não existe.";
	}
	
	/**
	 * Recebe o email e verifica se ele já está cadastrado.
	 * 
	 * @param email email do usuário
	 * @return true = cadastrado, false = não cadastrado
	 */
	public boolean emailEstaCadastrado(String email){
		logger.info("Verificando se email já está cadastrado - email: " + email);
		
		for(String key: usuarios.keySet()){
			Usuario usuario = usuarios.get(key);
			if(usuario.getEmail().equals(email)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Recebe o login e verifica se ele existe.
	 * 
	 * @param login login do usuário
	 * @return true = login existe, false = login não existe
	 */
	public boolean loginExiste(String login){
		logger.info("Verificando se o login já existe - login: " + login);
		
		for(String key: usuarios.keySet()){
			Usuario usuario = usuarios.get(key);
			if(usuario.getNomeLogin().equals(login)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Recebe o id da sessão e verifica se ela é válida, ou seja, se o id da sessão existe na base de dados.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @return true =  sessão válida, false = sessão inválida
	 */
	public boolean verificaSessao(String idSessao){
		logger.info("Verificando se a sessão é válida - id da sessão: " + idSessao);
		return usuarios.containsKey(idSessao);
	}
}
