package controller;

public class Sessao {
	
	private static Sessao instanciaUnica = null;
	
	private String idSessao;
	private String idCarona;
	
	private Sessao(){
		
	}
	
	public static Sessao getInstance(){		
		if(instanciaUnica == null){
			instanciaUnica = new Sessao();			
		}		
		return instanciaUnica;
	}

	public String getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(String idSessao) {
		this.idSessao = idSessao;
	}

	public String getIdCarona() {
		return idCarona;
	}

	public void setIdCarona(String idCarona) {
		this.idCarona = idCarona;
	}
}
