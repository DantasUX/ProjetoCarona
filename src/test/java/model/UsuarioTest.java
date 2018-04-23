package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Usuario;

public class UsuarioTest {	
	
	private Usuario usuario;
	
	@Before
	public void setUp() throws Exception{
		usuario = new Usuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
	}
	
	@Test
	public void getUsuario(){
		assertEquals(usuario.getNomeLogin(), "mark");
		assertEquals(usuario.getSenha(), "m@rk");
		assertEquals(usuario.getNome(), "Mark Zuckerberg");
		assertEquals(usuario.getEndereco(), "Palo Alto, California");
		assertEquals(usuario.getEmail(), "mark@facebook.com");
	}
	
}
