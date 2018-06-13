package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import model.Usuario;

/**
 * Classe responsável por gerenciar as operações da tabela usuario do banco de dados.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class UsuarioDAO {
	
	private static final Logger logger = LogManager.getLogger(UsuarioDAO.class);
		
	private static UsuarioDAO instanciaUnica = null;
	
	/**
	 * Construtor padrão.
	 */
	private UsuarioDAO(){
		
	}
	
	/**
	 * Abre uma instância única da base de dados.
	 * 
	 * @return UsuarioDAO
	 */
	public static UsuarioDAO getInstance(){
		logger.info("Abrindo conexão com a base de dados de usuario");
		
		if(instanciaUnica == null){
			instanciaUnica = new UsuarioDAO();			
		}		
		return instanciaUnica;
	}
	
	/**
	 * Recebe um usuário e o armazena no banco de dados do sistema.
	 * 
	 * @param usuario objeto usuario
	 * @throws SQLException 
	 */
	public void cadastrarUsuario(Usuario usuario) throws SQLException{
		logger.info("Executando método cadastrarUsuario");
		
		Connection conexao = new ConnectionFactory().getConnection();		
		String sql = "INSERT INTO usuario " + "(login,senha,nome,endereco,email) "
				+ "values (?,?,?,?,?)";		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		stmt.setString(1, usuario.getNomeLogin());
		stmt.setString(2, usuario.getSenha());
		stmt.setString(3, usuario.getNome());
		stmt.setString(4, usuario.getEndereco());
		stmt.setString(5, usuario.getEmail());
		stmt.execute();
		stmt.close();		
		conexao.close();		
	}
	
	/**
	 * Recebe o login e a senha do usuário, abre uma nova sessão e retorna o id da sessão.
	 * 
	 * @param login login do usuário
	 * @param senha senha do usuário
	 * @return id da sessão do usuário
	 * @throws SQLException 
	 */
	public String abrirSessao(String login, String senha) throws SQLException{
		logger.info("Executando método abrirSessao");		
		
		String sessao = "";		
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT id FROM usuario WHERE login = '" + login + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while(rs.next()){
			sessao = rs.getString("id");
		}		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return sessao;
	}	
	
	/**
	 * Recebe o login do usuário e retorna o nome dele.
	 * 
	 * @param login login do usuário
	 * @return nome do usuário
	 * @throws SQLException 
	 */
	public String nomeUsuario(String login) throws SQLException{
		logger.info("Executando método nomeUsuario");		
		
		return retornaInformacaoUsuario(login, "nome");
	}
	
	/**
	 * Recebe o login do usuário e retorna o endereço dele.
	 * 
	 * @param login login do usuário
	 * @return endereço do usuário
	 * @throws SQLException 
	 */
	public String enderecoUsuario(String login) throws SQLException{
		logger.info("Executando método enderecoUsuario");	
		
		return retornaInformacaoUsuario(login, "endereco");
	}
	
	/**
	 * Recebe o login do usuário e retorna o email dele.
	 * 
	 * @param login login do usuario
	 * @return email do usuário
	 * @throws SQLException
	 */
	public String emailUsuario(String login) throws SQLException{
		logger.info("Executando método emailUsuario");	
		
		return retornaInformacaoUsuario(login, "email");
	}
	
	/**
	 * Recebe o id da sessão do usuário e retorna o email dele.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @return email do usuário
	 * @throws SQLException
	 */
	public String emailUsuarioPorSessao(String idSessao) throws SQLException{
		logger.info("Executando método emailUsuarioPorSessao");
		
		String informacaoRetornada = "";		
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT email FROM usuario WHERE id = '" + idSessao + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while(rs.next()){
			informacaoRetornada = rs.getString("email");
		}		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return informacaoRetornada;
	}
	
	/**
	 * Recebe o login do usuário e o nome da coluna da tabela usuario onde a informação está armazenada
	 * no banco de dados. Por fim, retorna a informação dessa coluna.
	 * 
	 * @param login login do usuário
	 * @param coluna uma coluna equivalente da tabela usuario no banco de dados. Exemplo: nome, endereco, email.
	 * @return a informação armazenada da coluna.
	 * @throws SQLException
	 */
	private String retornaInformacaoUsuario(String login, String coluna) throws SQLException{
		logger.info("Executando método retornaInformacaoUsuario");
		
		String informacaoRetornada = "";		
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT " + coluna + " FROM usuario WHERE login = '" + login + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while(rs.next()){
			informacaoRetornada = rs.getString(coluna);
		}		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return informacaoRetornada;
	}
	
	/**
	 * Recebe o email e verifica se ele já está cadastrado.
	 * 
	 * @param email email do usuário
	 * @return true = cadastrado, false = não cadastrado
	 * @throws SQLException 
	 */
	public boolean emailEstaCadastrado(String email) throws SQLException{
		logger.info("Executando método emailEstaCadastrado");		

		return verificaInformacaoUsuario(email, "email");
	}
	
	/**
	 * Recebe o login e verifica se ele existe.
	 * 
	 * @param login login do usuário
	 * @return true = login existe, false = login não existe
	 * @throws SQLException 
	 */
	public boolean loginExiste(String login) throws SQLException{
		logger.info("Executando método loginExiste");
		
		return verificaInformacaoUsuario(login, "login");
	}
	
	/**
	 * Recebe a senha e verifica se ela confere com a senha do login equivalente.
	 * 
	 * @param login login do usuário
	 * @param senha senha do usuário
	 * @return true = senha está correta, false = senha está incorreta
	 * @throws SQLException
	 */
	public boolean senhaExiste(String login, String senha) throws SQLException{
		logger.info("Executando método senhaExiste");
		
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT login FROM usuario WHERE login = '" + login + "' AND senha = '" + senha + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		boolean resultado = rs.next();		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return resultado;
	}
	
	/**
	 * Recebe o id da sessão e verifica se ela é válida, ou seja, se o id da sessão existe na base de dados.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @return true = sessão válida, false = sessão inválida
	 * @throws SQLException 
	 */
	public boolean verificaSessao(String idSessao) throws SQLException{
		logger.info("Executando método verificaSessao");
		
		return verificaInformacaoUsuario(idSessao, "id");
	}
	
	/**
	 * Recebe uma informação, que pode ser o email, login ou id, e a coluna onde a informação está armazenada
	 * no banco de dados. Por fim, retorna se a informação existe ou não.
	 * 
	 * @param informacao informação do email, login ou id.
	 * @param coluna uma coluna equivalente da tabela usuário no banco de daos. Exemplo: email, login, id.
	 * @return true = informação existe, false = informação não existe
	 * @throws SQLException
	 */
	private boolean verificaInformacaoUsuario(String informacao, String coluna) throws SQLException{
		logger.info("Executando método verificaInformacaoUsuario");
		
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT " + coluna + " FROM usuario WHERE " + coluna + " = '" + informacao + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		boolean resultado = rs.next();		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return resultado;
	}
	
	/**
	 * Recebe o id da sessão e o login do usuário e retorna toda a informação do perfil do usuário.
	 * 
	 * @param idSessao id da sessão do usuário
	 * @param login login do usuário
	 * @return objeto usuario contendo as informações do perfil.
	 * @throws Exception
	 */
	public Usuario perfil(String idSessao, String login) throws Exception{
		logger.info("Executando método perfil");
		
		Usuario usuario = null;		
		Connection conexao = new ConnectionFactory().getConnection();
		String sql = "SELECT * FROM usuario WHERE id = '" + idSessao + "' OR login = '" + login + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
		while(rs.next()){
			String loginUsuario = rs.getString("login");
			String senha = rs.getString("senha");
			String nome = rs.getString("nome");
			String endereco = rs.getString("endereco");
			String email = rs.getString("email");
			usuario = new Usuario(loginUsuario, senha, nome, endereco, email);
		}		
		stmt.execute();
		stmt.close();
		conexao.close();		
		return usuario;
	}
	
	/**
	 * Apaga todos os usuários do banco de dados.
	 * 
	 * @throws SQLException
	 */
	public void apagarUsuarios() throws SQLException{
		logger.info("Executando método apagarUsuarios");
		
		Connection conexao1 = new ConnectionFactory().getConnection();
		String sql1 = "DELETE FROM usuario WHERE id > 0";
		PreparedStatement stmt1 = conexao1.prepareStatement(sql1);
		stmt1.execute();
		stmt1.close();		
		conexao1.close();
		
		Connection conexao2 = new ConnectionFactory().getConnection();
		String sql2 = "ALTER TABLE usuario AUTO_INCREMENT = 1";
		PreparedStatement stmt2 = conexao2.prepareStatement(sql2);
		stmt2.execute();
		stmt2.close();		
		conexao2.close();
	}
}
