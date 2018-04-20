package testeAceitacao;

import java.util.Map;

import easyaccept.EasyAccept;
import model.Carona;
import model.ModelFacade;

public class US02 {
	
	ModelFacade acesso = new ModelFacade();
	
	public void zerarSistema(){
		acesso.zerarSistema();
	}
	
	public void encerrarSistema(){
		acesso.encerrarSistema();
	}
	
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws Exception{
		acesso.criarUsuario(login, senha, nome, endereco, email);
	}
	
	public String abrirSessao(String login, String senha) throws Exception{
		return acesso.abrirSessao(login, senha);
	}
	
	public String localizarCarona(int idSessão, String origem, String destino) throws Exception{		
		Map<String, Carona> caronas = acesso.localizarCarona(origem, destino);
		String keys = caronas.keySet().toString();
		keys = keys.replaceAll(" ", "");
		keys = "{" + keys.substring(1, keys.length()-1) + "}";
		return keys;
	}
	
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, String vagasDisponiveis) throws Exception{		
		return acesso.cadastrarCarona(idSessao, origem, destino, data, hora, vagasDisponiveis);
	}
	
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{		
		return acesso.getAtributoCarona(idCarona, atributo);
	}
	
	public String getTrajeto(String idCarona) throws Exception{		
		return acesso.getTrajeto(idCarona);
	}
	
	public String getCarona(String idCarona) throws Exception{		
		return acesso.getCarona(idCarona);
	}

	public static void main(String[] args){
		args = new String[] {"testeAceitacao.US02", "src/test/resources/US02.txt"};
		EasyAccept.main(args);
	}
}
