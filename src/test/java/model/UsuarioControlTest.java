package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.Sistema;
import model.UsuarioControl;

public class UsuarioControlTest {	
	
	private UsuarioControl usuario;
	private Sistema zerarSistema;
	
	@Before
	public void setUp() throws Exception{
		usuario = new UsuarioControl();
		zerarSistema = new Sistema();
		zerarSistema.zerarSistema();
	}
	
	@Test
	public void criarUsuario() {
		try{			
			usuario.criarUsuario(null, "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
		}
		try{
			usuario.criarUsuario("", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
		}
		try{
			usuario.criarUsuario("mark", "m@rk", null, "Palo Alto, California", "mark@facebook.com");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Nome inválido");
		}
		try{
			usuario.criarUsuario("mark", "m@rk", "", "Palo Alto, California", "mark@facebook.com");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Nome inválido");
		}
		try{
			usuario.criarUsuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", null);
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Email inválido");
		}
		try{
			usuario.criarUsuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Email inválido");
		}
		try{
			usuario.criarUsuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
			usuario.criarUsuario("mark2", "m@rk2", "Mark Zuckerberg2", "Palo Alto, California2", "mark@facebook.com");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Já existe um usuário com este email");
		}
		try{			
			usuario.criarUsuario("mark", "m@rk2", "Mark Zuckerberg2", "Palo Alto, California2", "mark2@facebook.com");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Já existe um usuário com este login");
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
		}
		try{
			usuario.abrirSessao("", "m@rk");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
		}
		try{
			usuario.criarUsuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
			assertEquals(usuario.abrirSessao("mark", "m@rk"), "1");
			usuario.criarUsuario("steve", "5t3v3", "Steven Paul Jobs", "Palo Alto, California", "jobs@apple.com");
			assertEquals(usuario.abrirSessao("steve", "5t3v3"), "2");
			usuario.abrirSessao("mark", "m@rk1");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
		}
		try{
			usuario.abrirSessao("mark1", "m@rk");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Usuário inexistente");
		}
	}
	
	@Test
	public void getAtributoUsuario(){
		try{
			usuario.criarUsuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
			usuario.getAtributoUsuario(null, "nome");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
		}
		try{
			usuario.getAtributoUsuario("", "nome");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Login inválido");
		}
		try{
			usuario.getAtributoUsuario("mark", null);
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Atributo inválido");
		}
		try{
			usuario.getAtributoUsuario("mark", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Atributo inválido");
		}
		try{			
			usuario.getAtributoUsuario("mark1", "nome");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Usuário inexistente");
		}
		try{
			assertEquals(usuario.getAtributoUsuario("mark", "nome"), "Mark Zuckerberg");
			assertEquals(usuario.getAtributoUsuario("mark", "endereco"), "Palo Alto, California");
			usuario.getAtributoUsuario("mark", "xpto");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Atributo inexistente");
		}
	}
}
