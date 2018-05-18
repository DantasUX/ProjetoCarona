package dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dao.SolicitacaoDAO;
import model.Motorista;
import model.Sistema;
import model.UsuarioControl;

public class SolicitacaoDAOTest {
	
	private SolicitacaoDAO dao;
	private UsuarioControl usuario;
	private Sistema zerarSistema;
	private Motorista motorista;

	@Before
	public void setUP() throws Exception{
		dao = SolicitacaoDAO.getInstance();
		usuario = new UsuarioControl();
		zerarSistema = new Sistema();
		motorista = new Motorista();
		zerarSistema.zerarSistema();
		usuario.criarUsuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
		usuario.criarUsuario("steve", "5t3v3", "Steven Paul Jobs", "Palo Alto, California", "jobs@apple.com");
		motorista.cadastrarCarona("1", "João Pessoa", "Campina Grande", "23/06/2013", "16:00", "3");
		motorista.cadastrarCarona("1", "Rio de Janeiro", "São Paulo", "31/05/2013", "08:00", "2");
	}
	
	@Test
	public void pontoEncontro() throws Exception{
		assertEquals(dao.sugerirPontoEncontro("2", "1", "Acude Velho; Hiper Bompreco"), "1");
		dao.responderSugestaoPontoEncontro("1", "1", "1", "Acude Velho;Parque da Crianca");
		assertEquals(dao.solicitarVagaPontoEncontro("2", "1", "Acude Velho"), "1");
		assertEquals(dao.origemSolicitacao("1"), "João Pessoa");
		assertEquals(dao.destinoSolicitacao("1"), "Campina Grande");
		assertEquals(dao.donoCarona("1"), "Mark Zuckerberg");
		assertEquals(dao.donoSolicitacao("1"), "Steven Paul Jobs");
		assertEquals(dao.pontoEncontro("1"), "Acude Velho");
		assertFalse(dao.verificaSolicitacaoAceita("1"));
		dao.aceitarSolicitacaoPontoEncontro("1", "1");
		assertTrue(dao.verificaSolicitacaoAceita("1"));
		dao.desistirRequisicao("2", "1", "1");
	}
	
	@Test
	public void solicitacao() throws Exception{
		assertEquals(dao.solicitarVaga("2", "1"), "1");
		dao.aceitarSolicitacaoPontoEncontro("1", "1");
		assertEquals(dao.solicitarVaga("2", "2"), "2");
		dao.rejeitarSolicitacao("1", "2");
		dao.verificaSolicitacaoRejeitada("2");
	}
}
