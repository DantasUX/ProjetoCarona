package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.Caroneiro;
import model.Motorista;
import model.ZerarSistema;
import model.UsuarioControl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaroneiroTest {	

	private Caroneiro caroneiro;
	private Motorista motorista;
	private UsuarioControl usuario;
	private ZerarSistema zerarSistema;
	
	@Before
	public void setUp() throws Exception{
		caroneiro = new Caroneiro();
		motorista = new Motorista();
		usuario = new UsuarioControl();
		zerarSistema = new ZerarSistema();
	}
	
	@Test
	public void localizarCarona(){
		try{			
			assertEquals(caroneiro.localizarCarona("", "").size(), 1);
			caroneiro.localizarCarona("-", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Origem inválida");
		}
		try{
			caroneiro.localizarCarona("()", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Origem inválida");
		}
		try{
			caroneiro.localizarCarona("!", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Origem inválida");
		}
		try{
			caroneiro.localizarCarona("!?", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Origem inválida");
		}
		try{
			caroneiro.localizarCarona("", ".");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Destino inválido");
		}
		try{
			caroneiro.localizarCarona("", "()");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Destino inválido");
		}
		try{
			caroneiro.localizarCarona("", "!?");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Destino inválido");
		}
	}
	
	@Test
	public void getAtributoCarona(){
		try{
			zerarSistema.zerarSistema();
			usuario.criarUsuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
			usuario.criarUsuario("steve", "5t3v3", "Steven Paul Jobs", "Palo Alto, California", "jobs@apple.com");
			motorista.cadastrarCarona("1", "João Pessoa", "Campina Grande", "23/06/2013", "16:00", "3");
			
			assertEquals(caroneiro.getAtributoCarona("1", "origem"), "João Pessoa");
			assertEquals(caroneiro.getAtributoCarona("1", "destino"), "Campina Grande");
			assertEquals(caroneiro.getAtributoCarona("1", "data"), "23/06/2013");
			assertEquals(caroneiro.getAtributoCarona("1", "vagas"), "3");
			
			caroneiro.getAtributoCarona(null, "origem");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Identificador do carona é inválido");
		}
		try{
			caroneiro.getAtributoCarona("", "origem");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Identificador do carona é inválido");
		}
		try{
			caroneiro.getAtributoCarona("1", null);
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Atributo inválido");
		}
		try{
			caroneiro.getAtributoCarona("1", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Atributo inválido");
		}
		try{
			caroneiro.getAtributoCarona("2", "origem");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Item inexistente");
		}
		try{
			caroneiro.getAtributoCarona("1", "xpto");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Atributo inexistente");
		}
	}
	
	@Test
	public void getTrajeto(){
		try{
			assertEquals(caroneiro.getTrajeto("1"), "João Pessoa - Campina Grande");
			
			caroneiro.getTrajeto(null);
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Trajeto Inválida");
		}
		try{						
			caroneiro.getTrajeto("");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Trajeto Inexistente");
		}
		try{						
			caroneiro.getTrajeto("2");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Trajeto Inexistente");
		}
	}
	
	@Test
	public void getCarona(){
		try{
			assertEquals(caroneiro.getCarona("1"), "João Pessoa para Campina Grande, no dia 23/06/2013, as 16:00");
			
			caroneiro.getCarona(null);
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Carona Inválida");
		}
		try{			
			caroneiro.getCarona("");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Carona Inexistente");
		}
		try{			
			caroneiro.getCarona("2");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Carona Inexistente");
		}
	}
}
