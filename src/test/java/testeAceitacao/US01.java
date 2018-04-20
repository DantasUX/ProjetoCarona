
package testeAceitacao;

import easyaccept.EasyAccept;
import model.ModelFacade;

public class US01 {
	
	private ModelFacade acesso;
	
	public US01(){
		acesso = new ModelFacade();
	}
	
	public void zerarSistema(){
		acesso.zerarSistema();
	}
	
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws Exception{
		acesso.criarUsuario(login, senha, nome, endereco, email);
	}
	
	public void encerrarSistema(){
		acesso.encerrarSistema();
	}
	
	public String abrirSessao(String login, String senha) throws Exception{
		return acesso.abrirSessao(login, senha);
	}
	
	public String getAtributoUsuario(String login, String atributo) throws Exception{
		return acesso.getAtributoUsuario(login, atributo);
	}

	public static void main(String[] args){
		args = new String[] {"testeAceitacao.US01", "src/test/resources/US01.txt"};
		EasyAccept.main(args);
	}
}
