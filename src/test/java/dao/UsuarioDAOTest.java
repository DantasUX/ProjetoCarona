package dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.UsuarioDAO;
import model.Sistema;
import model.Usuario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioDAOTest {
	
	private Usuario usuario;
	private UsuarioDAO dao;
	private Sistema zerarSistema;

	@Before
	public void setUp() throws Exception{		
		dao = UsuarioDAO.getInstance();
		zerarSistema = new Sistema();
	}	
	
	@Test
	public void abrirSessao() throws Exception{
		zerarSistema.zerarSistema();
		usuario = new Usuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
		dao.cadastrarUsuario(usuario);
		assertEquals(dao.abrirSessao("mark", "m@rk"), "1");
	}
	
	@Test
	public void nomeUsuario() throws SQLException{
		assertEquals(dao.nomeUsuario("mark"), "Mark Zuckerberg");
	}
	
	@Test
	public void enderecoUsuario() throws SQLException{
		assertEquals(dao.enderecoUsuario("mark"), "Palo Alto, California");
	}
	
	@Test
	public void emailUsuario() throws SQLException{
		assertEquals(dao.emailUsuario("mark"), "mark@facebook.com");
	}
	
	@Test
	public void emailEstaCadastrado() throws SQLException{
		assertFalse(dao.emailEstaCadastrado("jobs@apple.com"));
		assertTrue(dao.emailEstaCadastrado("mark@facebook.com"));
	}
	
	@Test
	public void loginExiste() throws SQLException{
		assertFalse(dao.loginExiste("steve"));
		assertTrue(dao.loginExiste("mark"));
	}
	
	@Test
	public void senhaExiste() throws SQLException{
		assertFalse(dao.senhaExiste("mark1", "m@rk"));
		assertFalse(dao.senhaExiste("mark", "m@rk1"));
		assertTrue(dao.senhaExiste("mark", "m@rk"));
	}
	
	@Test
	public void verificaSessao() throws SQLException{
		assertTrue(dao.verificaSessao("1"));
		assertFalse(dao.verificaSessao("10"));
	}
	
	@Test
	public void perfil() throws Exception{
		Usuario usuario = dao.perfil("1", "mark");
		assertEquals(usuario.getNomeLogin(), "mark");
		assertEquals(usuario.getSenha(), "m@rk");
		assertEquals(usuario.getNome(), "Mark Zuckerberg");
		assertEquals(usuario.getEndereco(), "Palo Alto, California");
		assertEquals(usuario.getEmail(), "mark@facebook.com");
	}

}
