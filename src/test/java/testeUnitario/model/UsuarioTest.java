package testeUnitario.model;

import static org.junit.Assert.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import model.Usuario;

public class UsuarioTest {
	
	private static final Logger logger = LogManager.getLogger(UsuarioTest.class);
	
	private Usuario usuario;
	
	@Before
	public void setUp() throws Exception{
		usuario = new Usuario();
	}
	
	@Test
	public void criarUsuario(){
		try{
			usuario = new Usuario(null, "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			usuario = new Usuario("", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			usuario = new Usuario("mark", "m@rk", null, "Palo Alto, California", "mark@facebook.com");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Nome inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			usuario = new Usuario("mark", "m@rk", "", "Palo Alto, California", "mark@facebook.com");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Nome inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			usuario = new Usuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", null);
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Email inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			usuario = new Usuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Email inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			usuario = new Usuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
			usuario = new Usuario("mark2", "m@rk2", "Mark Zuckerberg2", "Palo Alto, California2", "mark@facebook.com");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Já existe um usuário com este email");
			logger.error(e.getMessage(), e);
		}
		try{			
			usuario = new Usuario("mark", "m@rk2", "Mark Zuckerberg2", "Palo Alto, California2", "mark2@facebook.com");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Já existe um usuário com este login");
			logger.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void abrirSessao(){
		try{
			usuario.abrirSessao(null, "m@rk");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			usuario.abrirSessao("", "m@rk");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			usuario = new Usuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
			assertEquals(usuario.abrirSessao("mark", "m@rk"), "1");
			usuario = new Usuario("steve", "5t3v3", "Steven Paul Jobs", "Palo Alto, California", "jobs@apple.com");
			assertEquals(usuario.abrirSessao("steve", "5t3v3"), "2");
			usuario.abrirSessao("mark", "m@rk1");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			usuario.abrirSessao("mark1", "m@rk");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Usuário inexistente");
			logger.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void getAtributoUsuario(){
		try{
			usuario.getAtributoUsuario(null, "nome");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			usuario.getAtributoUsuario("", "nome");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			usuario.getAtributoUsuario("mark", null);
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Atributo inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			usuario.getAtributoUsuario("mark", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Atributo inválido");
			logger.error(e.getMessage(), e);
		}
		try{			
			usuario.getAtributoUsuario("mark1", "nome");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Usuário inexistente");
			logger.error(e.getMessage(), e);
		}
		try{
			assertEquals(usuario.getAtributoUsuario("mark", "nome"), "Mark Zuckerberg");
			assertEquals(usuario.getAtributoUsuario("mark", "endereco"), "Palo Alto, California");
			usuario.getAtributoUsuario("mark", "xpto");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Atributo inexistente");
			logger.error(e.getMessage(), e);
		}
	}

}
