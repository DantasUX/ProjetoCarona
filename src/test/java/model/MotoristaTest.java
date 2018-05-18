package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Motorista;
import model.Sistema;
import model.UsuarioControl;

public class MotoristaTest {	

	private Motorista motorista;
	private UsuarioControl usuario;
	private Sistema zerarSistema;
	
	@Before
	public void setUp() throws Exception{
		motorista = new Motorista();
		usuario = new UsuarioControl();
		zerarSistema = new Sistema();
		zerarSistema.zerarSistema();
		usuario.criarUsuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
	}
	
	@Test
	public void cadastrarCarona(){
		try{
			motorista.cadastrarCarona("1", "João Pessoa", "Campina Grande", "23/06/2013", "16:00", "3");
			motorista.cadastrarCaronaMunicipal("1", "Açude Velho", "Shopping Boulevard", "Campina Grande", "04/06/2013", "20:00", "2");
			motorista.cadastrarCarona(null, "Campina Grande", "João Pessoa", "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Sessão inválida");
		}
		try{
			motorista.cadastrarCarona("", "Campina Grande", "João Pessoa", "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Sessão inválida");
		}
		try{
			motorista.cadastrarCarona("1", null, "João Pessoa", "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Origem inválida");
		}
		try{
			motorista.cadastrarCarona("1", "", "João Pessoa", "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Origem inválida");
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", null, "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Destino inválido");
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "", "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Destino inválido");
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", null, "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Data inválida");
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Data inválida");
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "23/06/2013", null, "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Hora inválida");
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "23/06/2013", "", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Hora inválida");
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "23/06/2013", "16:00", null);
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Vaga inválida");
		}
		try{
			motorista.cadastrarCarona("2", "Campina Grande", "João Pessoa", "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Sessão inexistente");
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "30/02/2014", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Data inválida");
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "23/06/2013", "uma", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Hora inválida");
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "23/06/2013", "16:00", "duas");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Vaga inválida");
		}
	}

}
