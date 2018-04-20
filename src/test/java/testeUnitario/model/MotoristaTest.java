package testeUnitario.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import model.Motorista;
import model.Usuario;

public class MotoristaTest {
	
	private static final Logger logger = LogManager.getLogger(MotoristaTest.class);

	private Motorista motorista;
	private Usuario usuario;
	
	@Before
	public void setUp() throws Exception{
		motorista = new Motorista();
		usuario = new Usuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
	}
	
	@Test
	public void cadastrarCarona(){
		try{
			motorista.cadastrarCarona("1", "João Pessoa", "Campina Grande", "23/06/2013", "16:00", "3");
			motorista.cadastrarCarona(null, "Campina Grande", "João Pessoa", "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Sessão inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("", "Campina Grande", "João Pessoa", "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Sessão inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("1", null, "João Pessoa", "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Origem inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("1", "", "João Pessoa", "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Origem inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", null, "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Destino inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "", "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Destino inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", null, "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Data inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Data inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "23/06/2013", null, "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Hora inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "23/06/2013", "", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Hora inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "23/06/2013", "16:00", null);
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Vaga inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("2", "Campina Grande", "João Pessoa", "23/06/2013", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Sessão inexistente");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "30/02/2014", "16:00", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Data inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "23/06/2013", "uma", "3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Hora inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			motorista.cadastrarCarona("1", "Campina Grande", "João Pessoa", "23/06/2013", "16:00", "duas");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Vaga inválida");
			logger.error(e.getMessage(), e);
		}
	}

}
