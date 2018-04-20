package testeUnitario.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.Caroneiro;
import model.Motorista;
import model.Usuario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaroneiroTest {
	
	private static final Logger logger = LogManager.getLogger(CaroneiroTest.class);

	private Caroneiro caroneiro;
	private Motorista motorista;
	private Usuario usuario;
	
	@Before
	public void setUp() throws Exception{
		caroneiro = new Caroneiro();
		motorista = new Motorista();		
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
			logger.error(e.getMessage(), e);
		}
		try{
			caroneiro.localizarCarona("()", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Origem inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			caroneiro.localizarCarona("!", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Origem inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			caroneiro.localizarCarona("!?", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Origem inválida");
			logger.error(e.getMessage(), e);
		}
		try{
			caroneiro.localizarCarona("", ".");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Destino inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			caroneiro.localizarCarona("", "()");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Destino inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			caroneiro.localizarCarona("", "!?");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Destino inválido");
			logger.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void getAtributoCarona(){
		try{
			usuario = new Usuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
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
			logger.error(e.getMessage(), e);
		}
		try{
			caroneiro.getAtributoCarona("", "origem");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Identificador do carona é inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			caroneiro.getAtributoCarona("1", null);
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Atributo inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			caroneiro.getAtributoCarona("1", "");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Atributo inválido");
			logger.error(e.getMessage(), e);
		}
		try{
			caroneiro.getAtributoCarona("2", "origem");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Item inexistente");
			logger.error(e.getMessage(), e);
		}
		try{
			caroneiro.getAtributoCarona("1", "xpto");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Atributo inexistente");
			logger.error(e.getMessage(), e);
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
			logger.error(e.getMessage(), e);
		}
		try{						
			caroneiro.getTrajeto("");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Trajeto Inexistente");
			logger.error(e.getMessage(), e);
		}
		try{						
			caroneiro.getTrajeto("2");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Trajeto Inexistente");
			logger.error(e.getMessage(), e);
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
			logger.error(e.getMessage(), e);
		}
		try{			
			caroneiro.getCarona("");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Carona Inexistente");
			logger.error(e.getMessage(), e);
		}
		try{			
			caroneiro.getCarona("2");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Carona Inexistente");
			logger.error(e.getMessage(), e);
		}
	}

}
