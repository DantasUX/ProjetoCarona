package model;

/**
 * Classe responsável por representar um usuário.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class Usuario {	

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
	public Usuario(String login, String senha, String nome, String endereco, String email){
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.endereco = endereco;
		this.email = email;
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
