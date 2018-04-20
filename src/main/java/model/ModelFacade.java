package model;

import java.util.Map;

import dao.CaronaDAO;

/**
 * Classe que representa uma fachada para o sistema. As informações que vêm de fora do model ou saem, passam
 * por esssa fachada. Ela faz um controle do sistema.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class ModelFacade {

	private Usuario usuario;
	private Motorista motorista;
	private Caroneiro caroneiro;
	
	/**
	 * Construtor padrão. Inicializa um objeto usuario, motorista e caroneiro.
	 */
	public ModelFacade(){
		usuario = new Usuario();
		motorista = new Motorista();
		caroneiro = new Caroneiro();
	}
	
	/**
	 * Cria um novo usuario.
	 * 
	 * @param login login do usuário
	 * @param senha senha do usuário
	 * @param nome nome do usuário
	 * @param endereco endereço do usuário
	 * @param email email do usuário
	 * @throws Exception
	 */
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws Exception{
		usuario = new Usuario(login, senha, nome, endereco, email);
	}
	
	/**
	 * Abre uma sessão para o usuário.
	 * 
	 * @param login login do usuário
	 * @param senha senha do usuário
	 * @return id da sessão do usuário
	 * @throws Exception
	 */
	public String abrirSessao(String login, String senha) throws Exception{
		return usuario.abrirSessao(login, senha);
	}
	
	/**
	 * Retorna o nome ou o endereço do usuário.
	 * 
	 * @param login login do usuário
	 * @param atributo nome ou endereco
	 * @return nome ou endereço do usuário
	 * @throws Exception
	 */
	public String getAtributoUsuario(String login, String atributo) throws Exception{
		return usuario.getAtributoUsuario(login, atributo);
	}	
	
	/**
	 * Cadastra uma nova carona.
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
		return motorista.cadastrarCarona(idSessao, origem, destino, data, hora, vagas);
	}
	
	/**
	 * Retorna a origem, o destino, a data ou quantidade de vagas, dependendo do valor do atributo.
	 * 
	 * @param idCarona id da carona
	 * @param atributo origem, destino, data ou vagas
	 * @return origem, destino, data ou vagas, dependendo do valor do atributo
	 * @throws Exception
	 */
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{
		return caroneiro.getAtributoCarona(idCarona, atributo);
	}
	
	/**
	 * Retorna o trajeto, ou seja, a origem e o destino.
	 * 
	 * @param idCarona id da carona
	 * @return trajeto da carona
	 * @throws Exception
	 */
	public String getTrajeto(String idCarona) throws Exception{
		return caroneiro.getTrajeto(idCarona);
	}
	
	/**
	 * Retorna informações da carona como origem, destino, data e hora.
	 * 
	 * @param idCarona id da carona
	 * @return informações da carona
	 * @throws Exception
	 */
	public String getCarona(String idCarona) throws Exception{
		return caroneiro.getCarona(idCarona);
	}
	
	/**
	 * Retorna um map contendo o id e a carona.
	 * 
	 * @param origem origem da carona
	 * @param destino destino da carona
	 * @return um map contendo o id e a carona
	 * @throws Exception
	 */
	public Map<String, Carona> localizarCarona(String origem, String destino) throws Exception{
		return caroneiro.localizarCarona(origem, destino);
	}
	
	/**
	 * Apaga todas as caronas no sistema.
	 */
	public void zerarSistema(){
		CaronaDAO c = CaronaDAO.getInstance();
		c.apagarCaronas();
	}
	
	/**
	 * Encerra a sessão do usuário.
	 */
	public void encerrarSistema(){
		
	}
}
