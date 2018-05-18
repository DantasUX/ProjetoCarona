package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.Caroneiro;
import model.Motorista;
import model.Sistema;
import model.UsuarioControl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaroneiroTest {	

	private Caroneiro caroneiro;
	private Motorista motorista;
	private UsuarioControl usuario;
	private Sistema zerarSistema;
	
	@Before
	public void setUp() throws Exception{
		caroneiro = new Caroneiro();
		motorista = new Motorista();
		usuario = new UsuarioControl();
		zerarSistema = new Sistema();
	}
	
	@Test
	public void localizarCarona(){
		try{			
			assertEquals(caroneiro.localizarCarona("", "").size(), 2);
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
		try{			
			assertEquals(caroneiro.localizarCaronaMunicipal("Campina Grande", "Açude Velho", "Shopping Boulevard").size(), 1);
			caroneiro.localizarCaronaMunicipal(null, "Açude Velho", "Shopping Boulevard");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Cidade inexistente");
		}
		try{			
			caroneiro.localizarCaronaMunicipal("", "Açude Velho", "Shopping Boulevard");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Cidade inexistente");
		}
		try{			
			assertEquals(caroneiro.localizarCaronaMunicipal("Campina Grande").size(), 1);
			caroneiro.localizarCaronaMunicipal(null);
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Cidade inexistente");
		}
		try{			
			caroneiro.localizarCaronaMunicipal("");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Cidade inexistente");
		}
	}
	
	@Test
	public void getAtributoCarona(){
		try{
			zerarSistema.zerarSistema();
			usuario.criarUsuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
			usuario.criarUsuario("steve", "5t3v3", "Steven Paul Jobs", "Palo Alto, California", "jobs@apple.com");
			usuario.criarUsuario("vader", "d4rth", "Anakin Skywalker", "Death Star I", "darthvader@empire.com");
			caroneiro.cadastrarInteresse("2", "João Pessoa", "Campina Grande", "23/06/2013", "06:00", "16:00");
			motorista.cadastrarCarona("1", "João Pessoa", "Campina Grande", "23/06/2013", "16:00", "3");
			motorista.cadastrarCaronaMunicipal("1", "Açude Velho", "Shopping Boulevard", "Campina Grande", "04/06/2013", "20:00", "2");
			
			assertEquals(caroneiro.getAtributoCarona("1", "origem"), "João Pessoa");
			assertEquals(caroneiro.getAtributoCarona("1", "destino"), "Campina Grande");
			assertEquals(caroneiro.getAtributoCarona("1", "data"), "23/06/2013");
			assertEquals(caroneiro.getAtributoCarona("1", "vagas"), "3");
			assertEquals(caroneiro.getAtributoCarona("1", "ehMunicipal"), "false");
			assertEquals(caroneiro.getAtributoCarona("2", "ehMunicipal"), "true");
			
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
			caroneiro.getAtributoCarona("3", "origem");
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
			caroneiro.getTrajeto("3");
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
			caroneiro.getCarona("3");
			fail("Falha! Não aconteceu a exceção.");
		}
		catch(Exception e){
			assertEquals(e.getMessage(), "Carona Inexistente");
		}
	}
	
	@Test
	public void reviewCarona(){		
		try {
			caroneiro.solicitarVaga("2", "1");
			motorista.aceitarSolicitacaoPontoEncontro("1", "1");
			caroneiro.reviewCarona("2", "1", "segura e tranquila");
			caroneiro.reviewCarona("2", "1", "não funcionou");
			caroneiro.reviewCarona("2", "1", "bacana");
			fail("Falha! Não aconteceu a exceção.");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Opção inválida.");
		}
		
		try {
			caroneiro.reviewCarona("3", "1", "segura e tranquila");			
			fail("Falha! Não aconteceu a exceção.");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Usuário não possui vaga na carona.");
		}
	}
	
	@Test
	public void interesse(){
		try {			
			caroneiro.cadastrarInteresse("2", "João Pessoa", "Campina Grande", null, "06:00", "16:00");
			fail("Falha! Não aconteceu a exceção.");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Data inválida");
		}
		try {			
			caroneiro.cadastrarInteresse("2", "-", "Campina Grande", "23/06/2013", "06:00", "16:00");
			fail("Falha! Não aconteceu a exceção.");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Origem inválida");
		}
		try {			
			caroneiro.cadastrarInteresse("2", "!", "Campina Grande", "23/06/2013", "06:00", "16:00");
			fail("Falha! Não aconteceu a exceção.");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Origem inválida");
		}
		try {			
			caroneiro.cadastrarInteresse("2", "João Pessoa", "-", "23/06/2013", "06:00", "16:00");
			fail("Falha! Não aconteceu a exceção.");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Destino inválido");
		}
		try {			
			caroneiro.cadastrarInteresse("2", "João Pessoa", "!", "23/06/2013", "06:00", "16:00");
			fail("Falha! Não aconteceu a exceção.");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Destino inválido");
		}
	}
}
