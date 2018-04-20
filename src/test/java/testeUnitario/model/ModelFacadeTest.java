package testeUnitario.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.ModelFacade;

public class ModelFacadeTest {

	private ModelFacade acesso;
	
	@Before
	public void setUp(){
		acesso = new ModelFacade();
	}
	
	@Test
	public void metodos() throws Exception{
		acesso.criarUsuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
		assertEquals(acesso.abrirSessao("mark", "m@rk"), "1");
		assertEquals(acesso.getAtributoUsuario("mark", "nome"), "Mark Zuckerberg");
		assertEquals(acesso.cadastrarCarona("1", "João Pessoa", "Campina Grande", "23/06/2013", "16:00", "3"), "1");
		assertEquals(acesso.getAtributoCarona("1", "origem"), "João Pessoa");
		assertEquals(acesso.getTrajeto("1"), "João Pessoa - Campina Grande");
		assertEquals(acesso.getCarona("1"), "João Pessoa para Campina Grande, no dia 23/06/2013, as 16:00");
		assertEquals(acesso.localizarCarona("", "").size(), 1);
	}

}
