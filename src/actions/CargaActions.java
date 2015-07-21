package actions;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import service.CargaAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.Constantes;
import util.SelectOneDataModel;

/**
 * 
 * Action responsável por realizar a carga do sistema.<br />
 * Atenção, ao fato de que esse action não requer autenticão para seus
 * métodos! Ou seja, pode ser acessada sem passar pelo filtro de login.
 * 
 * @author bruno.oliveira
 * 
 * */
public class CargaActions extends BaseActions {

	// Services
	private static CargaAppService cargaService;

	// Páginas
	private static String PAGINA_CARGA = "cargabd";

	// Componentes de Controle
	private static String SENHA_CARGA = Constantes.SENHA_CARGABD;
	private static String OPCAO_BASICA = "Carga Básica";
	private static String OPCAO_EXEMPLO1 = "Exemplo Inicial";
	private static String OPCAO_EXEMPLO2 = "Exemplo 2";
	private static String OPCAO_EXEMPLO3 = "100 Indicações";
	public List<String> tiposDeCarga = new ArrayList<String>();
	private SelectOneDataModel<String> comboTiposDeCarga;

	// Variáveis de Tela
	private String senha;
	private String descCarga;
	/**
	 * 
	 * Construtor da classe, onde a fábrica de service Carga é instanciada.
	 * 
	 * @throws Exception
	 *             Retorna uma opção caso ocorra um erro ao se instanciar o
	 *             service.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public CargaActions() throws Exception {
		try {
			cargaService = FabricaDeAppService
					.getAppService(CargaAppService.class);
		} catch (Exception e) {
			throw e;
		}
		tiposDeCarga.add(OPCAO_BASICA);
		tiposDeCarga.add(OPCAO_EXEMPLO1);
		tiposDeCarga.add(OPCAO_EXEMPLO2);
		tiposDeCarga.add(OPCAO_EXEMPLO3);
		descCarga = null;
		comboTiposDeCarga = null;
	}

	/**
	 * 
	 * Método chamado através de ajax para alterar a descrição sempre que o
	 * usuário mudar a combobox.
	 * 
	 * @param event
	 *            - Evento que representa a mudança de valor.
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public void alteraDescAjax(ValueChangeEvent event) {
		String opcao = event.getNewValue().toString();
		alteraDesc(opcao);
	}

	/**
	 * 
	 * Método chamado para alterar a descrição do tipo de carga sempre que o
	 * usuário mudar a combobox.
	 * 
	 * @param opcao
	 *            - String com o nome da opção de tipo de carga selecionada.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public void alteraDesc(String opcao) {
		if (opcao == null || opcao.equals(OPCAO_BASICA)) {
            descCarga = ("É a carga de dados mínima que deve ser usada na instalação do sistema para o usuário." +
                    "Possuí apenas os dados imprescindíveis ao funcionamento do sistema.Insere tipos de usuarios,"+
                    "usuario administrador, parametros e algoritmos.");
        }
        else if (opcao.equals(OPCAO_EXEMPLO1)) {
            descCarga = String.format("Carga relativa ao 1º exemplo compatível com as planilhas.");
           
        }
        else if (opcao.equals(OPCAO_EXEMPLO2)){
            descCarga = "Carga com novo estudo de casos complementando parâmetros (27) , pacientes (12) e mais atendimentos.";
        }
        else if (opcao.equals(OPCAO_EXEMPLO3)){
            descCarga = "Exemplo construído para testar um volume de dado maior para indicações, totalizando (122) antibióticos.";
        }
	}

	/**
	 * 
	 * Carrega a carga do sistema se a senha for igual ao valor definido na
	 * variável SENHA_CARGA.
	 * 
	 * @return Recarrega a página de carga exibindo a mensagem de resultado.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String executarCarga() {
		// Caso a senha não bata com a SENHA_CARGA, uma mensagem de erro é
		// gerada e a carga não é realizada.
		if (!senha.equals(SENHA_CARGA)) {
			error("usuario.ERRO_LOGIN_SENHA_INEXISTENTES");
			return PAGINA_CARGA;
		}
		String opcao = comboTiposDeCarga.getObjetoSelecionado();
		System.out.println(">>>" + opcao);
		try {
			if (opcao.equals(OPCAO_BASICA)) {
				cargaService.executarCargaBasica();
			}
			if (opcao.equals(OPCAO_EXEMPLO1)) {
				cargaService.executarCargaExemplo1();
			}
			if (opcao.equals(OPCAO_EXEMPLO2)){
				cargaService.executarCargaExemplo2();
			}
			if (opcao.equals(OPCAO_EXEMPLO3)){
				cargaService.executarCargaExemplo3();
			}
			info("carga.SUCESSO_CARGA");
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
		}
		descCarga = null;
		return PAGINA_CARGA;
	}

	// ================================== Métodos get() e set()
	// ================================== //

	public String getSenha() {
		return senha;
	}

	public String getDescCarga() {
		if (descCarga == null) {
			if (comboTiposDeCarga == null) {
				// Só passa aqui na primeira vez que roda o sistema.
				descCarga = "Carga básica do sistema.";
			} else {
				String opcao = comboTiposDeCarga.getObjetoSelecionado();
				alteraDesc(opcao);
			}

		}
		return descCarga;
	}

	public void setDescCarga(String descCarga) {
		this.descCarga = descCarga;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public static String getSENHA_CARGA() {
		return SENHA_CARGA;
	}

	public static void setSENHA_CARGA(String sENHACARGA) {
		SENHA_CARGA = sENHACARGA;
	}

	/**
	 * 
	 * Método que cria a Combobox que lista os tipos de carga do sistema.
	 * 
	 * @retun Lista dos tipos de carga que apareceram na combobox.
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public SelectOneDataModel<String> getComboTiposDeCarga() {

		if (comboTiposDeCarga == null) {

			comboTiposDeCarga = SelectOneDataModel
					.criaSemTextoInicial(tiposDeCarga);
		}
		return comboTiposDeCarga;
	}

	public void setComboTiposDeCarga(
			SelectOneDataModel<String> comboTiposDeCarga) {
		this.comboTiposDeCarga = comboTiposDeCarga;
	}

}