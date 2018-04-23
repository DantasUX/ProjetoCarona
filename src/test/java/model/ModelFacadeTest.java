package model;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.ModelFacade;
import model.ZerarSistema;
import model.Usuario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModelFacadeTest {

	private ModelFacade acesso;
	private ZerarSistema sessao;
	
	@Before
	public void setUp() throws SQLException{
		acesso = new ModelFacade();
		sessao = new ZerarSistema();		
	}
	
	@Test
	public void metodos() throws Exception{
		sessao.zerarSistema();
		acesso.criarUsuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
		assertEquals(acesso.abrirSessao("mark", "m@rk"), "1");
		assertEquals(acesso.getAtributoUsuario("mark", "nome"), "Mark Zuckerberg");
		assertEquals(acesso.cadastrarCarona("1", "João Pessoa", "Campina Grande", "23/06/2013", "16:00", "3"), "1");
		assertEquals(acesso.cadastrarCarona("1", "Rio de Janeiro", "São Paulo", "31/05/2013", "08:00", "2"), "2");
		assertEquals(acesso.cadastrarCarona("1", "João Pessoa", "Campina Grande", "25/11/2026", "06:59", "4"), "3");
		assertEquals(acesso.getAtributoCarona("1", "origem"), "João Pessoa");
		assertEquals(acesso.getTrajeto("1"), "João Pessoa - Campina Grande");
		assertEquals(acesso.getCarona("1"), "João Pessoa para Campina Grande, no dia 23/06/2013, as 16:00");
		assertEquals(acesso.localizarCarona("1", "", ""), "{1,2,3}");
		
		acesso.criarUsuario("steve", "5t3v3", "Steven Paul Jobs", "Palo Alto, California", "jobs@apple.com");
		acesso.criarUsuario("bill", "severino", "William Henry Gates III", "Medina, Washington", "billzin@msn.com");
		assertEquals(acesso.getCaronaUsuario("1", 1), "1");
		assertEquals(acesso.getCaronaUsuario("1", 2), "2");
		assertEquals(acesso.getTodasCaronasUsuario("1"), "{1,2,3}");
	}

	@Test
	public void solicitacoes() throws Exception{
		try {
			acesso.sugerirPontoEncontro("2", "1", "");
		}
		catch (Exception e) {			
			assertEquals(e.getMessage(), "Ponto Inválido");
		}
		acesso.sugerirPontoEncontro("2", "1", "Acude Velho; Hiper Bompreco");
		
		try {
			acesso.responderSugestaoPontoEncontro("1", "1", "1", "");
		}
		catch (Exception e) {			
			assertEquals(e.getMessage(), "Ponto Inválido");
		}
		acesso.responderSugestaoPontoEncontro("1", "1", "1", "Acude Velho;Parque da Crianca");
		
		acesso.solicitarVagaPontoEncontro("2", "1", "Acude Velho");
		acesso.aceitarSolicitacaoPontoEncontro("1", "1");
		
		try {
			acesso.aceitarSolicitacaoPontoEncontro("1", "1");
		}
		catch (Exception e) {			
			assertEquals(e.getMessage(), "Solicitação inexistente");
		}
		
		acesso.solicitarVaga("2", "2");
		acesso.rejeitarSolicitacao("1", "2");
		
		try {
			acesso.rejeitarSolicitacao("1", "2");
		}
		catch (Exception e) {			
			assertEquals(e.getMessage(), "Solicitação inexistente");
		}
		
		acesso.solicitarVaga("2", "3");
		acesso.aceitarSolicitacaoPontoEncontro("1", "3");
		try {
			acesso.reviewVagaEmCarona("1", "1", "bill", "faltou");
		}
		catch (Exception e) {			
			assertEquals(e.getMessage(), "Usuário não possui vaga na carona.");
		}
		try {
			acesso.reviewVagaEmCarona("1", "1", "steve", "falta");
		}
		catch (Exception e) {			
			assertEquals(e.getMessage(), "Opção inválida.");
		}
		acesso.reviewVagaEmCarona("1", "1", "steve", "faltou");
		acesso.reviewVagaEmCarona("1", "3", "steve", "não faltou");
		
		assertEquals(acesso.getAtributoSolicitacao("1", "origem"), "João Pessoa");
		assertEquals(acesso.getAtributoSolicitacao("1", "destino"), "Campina Grande");
		assertEquals(acesso.getAtributoSolicitacao("1", "Dono da carona"), "Mark Zuckerberg");
		assertEquals(acesso.getAtributoSolicitacao("1", "Dono da solicitacao"), "Steven Paul Jobs");
		assertEquals(acesso.getAtributoSolicitacao("1", "Ponto de Encontro"), "Acude Velho");
		
		acesso.desistirRequisicao("2", "1", "1");
		try {
			acesso.desistirRequisicao("2", "2", "2");
		}
		catch (Exception e) {			
			assertEquals(e.getMessage(), "Solicitação inexistente");
		}
		
	}
	
	@Test
	public void perfilUsuario() throws Exception{
		try {
			acesso.visualizarPerfil("1", "mark1");
		}
		catch (Exception e) {			
			assertEquals(e.getMessage(), "Login inválido");
		}
		Usuario usuario = acesso.visualizarPerfil("1", "mark");
		assertEquals(usuario.getNomeLogin(), "mark");
		assertEquals(usuario.getSenha(), "m@rk");
		assertEquals(usuario.getNome(), "Mark Zuckerberg");
		assertEquals(usuario.getEndereco(), "Palo Alto, California");
		assertEquals(usuario.getEmail(), "mark@facebook.com");
		
		assertEquals(acesso.getAtributoPerfil("mark", "nome"), "Mark Zuckerberg");
		assertEquals(acesso.getAtributoPerfil("mark", "endereco"), "Palo Alto, California");
		assertEquals(acesso.getAtributoPerfil("mark", "email"), "mark@facebook.com");
		assertEquals(acesso.getAtributoPerfil("mark", "historico de caronas"), "[1,2,3]");
		assertEquals(acesso.getAtributoPerfil("mark", "historico de vagas em caronas"), "[]");
		assertEquals(acesso.getAtributoPerfil("mark", "caronas seguras e tranquilas"), "0");
		assertEquals(acesso.getAtributoPerfil("mark", "caronas que não funcionaram"), "0");
		assertEquals(acesso.getAtributoPerfil("mark", "faltas em vagas de caronas"), "0");
		assertEquals(acesso.getAtributoPerfil("mark", "presencas em vagas de caronas"), "0");
	}
}
