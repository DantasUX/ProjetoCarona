package testeUnitario.dao;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.CaronaDAO;
import model.Carona;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaronaDAOTest {

	private CaronaDAO dao;
	private Carona carona;
	
	@Before
	public void setUp(){
		dao = CaronaDAO.getInstance();
	}
	
	@Test
	public void cadastrarCarona(){
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
		LocalDate data1 = LocalDate.parse("23/06/2013", formatoData);
		LocalDate data2 = LocalDate.parse("31/05/2013", formatoData);
		LocalDate data3 = LocalDate.parse("25/11/2026", formatoData);
		LocalDate data4 = LocalDate.parse("25/11/2016", formatoData);
		LocalDate data5 = LocalDate.parse("25/11/2017", formatoData);
		
		DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);
		LocalTime hora1 = LocalTime.parse("16:00", formatoHora);
		LocalTime hora2 = LocalTime.parse("08:00", formatoHora);
		LocalTime hora3 = LocalTime.parse("06:59", formatoHora);
		LocalTime hora4 = LocalTime.parse("05:00", formatoHora);
		LocalTime hora5 = LocalTime.parse("05:00", formatoHora);
		
		carona = new Carona("Campina Grande", "João Pessoa", data1, hora1, 3);		
		assertEquals(dao.cadastrarCarona(carona), "1");
		
		carona = new Carona("Rio de Janeiro", "São Paulo", data2, hora2, 2);
		assertEquals(dao.cadastrarCarona(carona), "2");
		
		carona = new Carona("João Pessoa", "Campina Grande", data3, hora3, 4);
		assertEquals(dao.cadastrarCarona(carona), "3");
		
		carona = new Carona("João Pessoa", "Lagoa Seca", data4, hora4, 4);
		assertEquals(dao.cadastrarCarona(carona), "4");
		
		carona = new Carona("João Pessoa", "Lagoa Seca", data5, hora5, 4);
		assertEquals(dao.cadastrarCarona(carona), "5");
	}
	
	@Test
	public void localizarCarona(){
		assertEquals(dao.localizarCarona("", "").size(), 5);
		assertEquals(dao.localizarCarona("João Pessoa", "").size(), 3);
		assertEquals(dao.localizarCarona("", "Lagoa Seca").size(), 2);
		assertEquals(dao.localizarCarona("Campina Grande", "João Pessoa").size(), 1);
		assertEquals(dao.localizarCarona("Campina Grande", "São Paulo").size(), 0);
	}
	
	@Test
	public void verificaCarona(){
		assertTrue(dao.verificaCarona("1"));
		assertFalse(dao.verificaCarona("10"));
	}
	
	@Test
	public void metodosRestantes(){
		assertEquals(dao.origemCarona("1"), "Campina Grande");
		assertEquals(dao.destinoCarona("1"), "João Pessoa");
		assertEquals(dao.dataCarona("1"), "23/06/2013");
		assertEquals(dao.vagasCarona("1"), 3);
		assertEquals(dao.trajetoCarona("1"), "Campina Grande - João Pessoa");
		assertEquals(dao.informacoesCarona("3"), "João Pessoa para Campina Grande, no dia 25/11/2026, as 06:59");
	}

}
