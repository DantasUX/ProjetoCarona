package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe responsável por representar uma carona.
 * 
 * @author Yorras Gomes, Fábio Dantas
 *
 */
public class Carona {

	private String origem;
	private String destino;
	private String cidade;
	private LocalDate data;
	private LocalTime hora;
	private int vagas;
	private boolean municipal;
	private String idUsuario;
	
	/**
	 * Construtor padrão.
	 */
	public Carona(){
		
	}
	
	/**
	 * Construtor que recebe a origem, o destino, a data, a saida e a quantidade de vagas da carona.
	 * Cria uma carona.
	 * 
	 * @param origem origem da carona
	 * @param destino destino da carona
	 * @param data data da carona
	 * @param saida saida da carona
	 * @param vagas quantidade de vagas da carona
	 */
	public Carona(String origem, String destino, LocalDate data, LocalTime saida, int vagas) {
		this.origem = origem;
		this.destino = destino;
		this.cidade = null;
		this.data = data;
		this.hora = saida;
		this.vagas = vagas;
		this.municipal = false;
	}
	
	/**
	 * Construtor que recebe a origem, o destino, a cidade, a data, a saida e a quantidade de vagas da carona.
	 * Cria uma carona municipal.
	 * 
	 * @param origem
	 * @param destino
	 * @param cidade
	 * @param data
	 * @param saida
	 * @param vagas
	 */
	public Carona(String origem, String destino, String cidade, LocalDate data, LocalTime saida, int vagas) {
		this.origem = origem;
		this.destino = destino;
		this.cidade = cidade;
		this.data = data;
		this.hora = saida;
		this.vagas = vagas;
		this.municipal = true;
	}	

	/**
	 * 
	 * @return origem da carona
	 */
	public String getOrigem() {
		return origem;
	}

	/**
	 * 
	 * @return destino da carona
	 */
	public String getDestino() {
		return destino;
	}	

	public String getCidade() {
		return cidade;
	}

	/**
	 * 
	 * @return data da carona
	 */
	public String getData() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return formato.format(data);
	}	

	/**
	 * 
	 * @return hora da carona
	 */
	public String getHora() {
		return hora.toString();
	}
	
	/**
	 * 
	 * @return vagas da carona
	 */
	public int getVagas() {
		return vagas;
	}	

	/**
	 * 
	 * @return id do usuário
	 */
	public String getIdUsuario() {
		return idUsuario;
	}

	/**
	 * 
	 * @param idUsuario id do usuário
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * 
	 * @return true = carona municipal
	 */
	public boolean getMunicipal() {
		return municipal;
	}		
}
