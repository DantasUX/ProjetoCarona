package testeUnitario.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.Before;
import org.junit.Test;

import model.Carona;

public class CaronaTest {

	private Carona carona;
	
	@Before
	public void setUp(){
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
		LocalDate data = LocalDate.parse("23/06/2013", formatoData);
		
		DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);
		LocalTime hora = LocalTime.parse("16:00", formatoHora);
		
		carona = new Carona("Campina Grande", "João Pessoa", data, hora, 3);
	}
	
	@Test
	public void data(){
		assertEquals(carona.getData(), "23/06/2013");
	}
	
	@Test
	public void hora(){
		assertEquals(carona.getHora(), "16:00");
	}
	
	@Test
	public void metodosRestantes(){
		assertEquals(carona.getOrigem(), "Campina Grande");
		assertEquals(carona.getDestino(), "João Pessoa");
		assertEquals(carona.getVagas(), 3);
	}

}
