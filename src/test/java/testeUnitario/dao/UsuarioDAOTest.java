package testeUnitario.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.UsuarioDAO;
import model.Usuario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioDAOTest {
	
	private Usuario usuario;
	private UsuarioDAO dao;

	@Before
	public void setUp() throws Exception{		
		dao = UsuarioDAO.getInstance();
	}
	
	@Test
	public void abrirSessao() throws Exception{
		usuario = new Usuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
		assertEquals(dao.abrirSessao("mark", "m@rk"), "1");
		assertEquals(dao.abrirSessao("mark", "m@rk1"), "Senha Incorreta");
		assertEquals(dao.abrirSessao("mark1", "m@rk"), "Usuario Inexistente");
	}
	
	@Test
	public void nomeUsuario(){
		assertEquals(dao.nomeUsuario("mark"), "Mark Zuckerberg");
		assertEquals(dao.nomeUsuario("mark1"), "Login não existe.");
	}
	
	@Test
	public void enderecoUsuario(){
		assertEquals(dao.enderecoUsuario("mark"), "Palo Alto, California");
		assertEquals(dao.enderecoUsuario("mark1"), "Login não existe.");
	}
	
	@Test
	public void emailEstaCadastrado(){
		assertFalse(dao.emailEstaCadastrado("jobs@apple.com"));
		assertTrue(dao.emailEstaCadastrado("mark@facebook.com"));
	}
	
	@Test
	public void loginExiste(){
		assertFalse(dao.loginExiste("steve"));
		assertTrue(dao.loginExiste("mark"));
	}
	
	@Test
	public void verificaSessao(){
		assertTrue(dao.verificaSessao("1"));
		assertFalse(dao.verificaSessao("10"));
	}

}
