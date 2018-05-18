package dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.CaronaDAO;
import dao.UsuarioDAO;
import model.Carona;
import model.Sistema;
import model.Usuario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaronaDAOTest {

	private CaronaDAO dao;
	private Carona carona;
	private Sistema sessao;
	private Usuario usuario;
	private UsuarioDAO u;
	
	@Before
	public void setUp(){
		dao = CaronaDAO.getInstance();
		sessao = new Sistema();
		u = UsuarioDAO.getInstance();
	}
	
	@Test
	public void cadastrarCarona() throws SQLException{
		sessao.zerarSistema();
		usuario = new Usuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
		u.cadastrarUsuario(usuario);
		
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
		carona.setIdUsuario("1");
		assertEquals(dao.cadastrarCarona(carona), "1");
		
		carona = new Carona("Rio de Janeiro", "São Paulo", data2, hora2, 2);
		carona.setIdUsuario("1");
		assertEquals(dao.cadastrarCarona(carona), "2");
		
		carona = new Carona("João Pessoa", "Campina Grande", data3, hora3, 4);
		carona.setIdUsuario("1");
		assertEquals(dao.cadastrarCarona(carona), "3");
		
		carona = new Carona("João Pessoa", "Lagoa Seca", data4, hora4, 4);
		carona.setIdUsuario("1");
		assertEquals(dao.cadastrarCarona(carona), "4");
		
		carona = new Carona("João Pessoa", "Lagoa Seca", data5, hora5, 4);
		carona.setIdUsuario("1");
		assertEquals(dao.cadastrarCarona(carona), "5");
	}
	
	@Test
	public void localizarCarona() throws SQLException{
		assertEquals(dao.localizarCarona("", "").size(), 5);
		assertEquals(dao.localizarCarona("João Pessoa", "").size(), 3);
		assertEquals(dao.localizarCarona("", "Lagoa Seca").size(), 2);
		assertEquals(dao.localizarCarona("Campina Grande", "João Pessoa").size(), 1);
		assertEquals(dao.localizarCarona("Campina Grande", "São Paulo").size(), 0);
	}
	
	@Test
	public void verificaCarona() throws SQLException{
		assertTrue(dao.verificaCarona("1"));
		assertFalse(dao.verificaCarona("10"));
	}
	
	@Test
	public void metodosRestantes() throws SQLException{
		assertEquals(dao.origemCarona("1"), "Campina Grande");
		assertEquals(dao.destinoCarona("1"), "João Pessoa");
		assertEquals(dao.dataCarona("1"), "23/06/2013");
		assertEquals(dao.horaCarona("1"), "16:00");
		assertEquals(dao.vagasCarona("1"), 3);
		assertEquals(dao.trajetoCarona("1"), "Campina Grande - João Pessoa");
		assertEquals(dao.informacoesCarona("3"), "João Pessoa para Campina Grande, no dia 25/11/2026, as 06:59");
		assertEquals(dao.historicoCaronas("mark").size(), 5);
		assertEquals(dao.getCaronaUsuario("1", 1), "1");
		assertEquals(dao.getCaronaUsuario("1", 2), "2");
		assertEquals(dao.caronasSeguras("mark"), 0);
		assertEquals(dao.caronasQueNaoFuncionou("mark"), 0);
	}

}
