package service;

import java.util.ArrayList;

import service.exception.AplicacaoException;
import carga.*;

/**
 * Esse service é responsavel por chamar as cargas do sistema.<br /><br />
 * 
 * Para isso é preciso:<br />
 * ---- Instanciar a lista de cargas como uma nova lista vazia;<br />
 * ---- Instanciar uma Carga;<br />
 * ---- Adiciona-la na lista cargas;<br />
 * ---- Retornar o método executarCargas;<br /><br />
 *  
 * Lembrando que é preciso jogar uma ApplicationExeption para o CargaAction.<br />
 * Isso por que diferentemente do TestNG, essa carga é executada pelo browser, logo tem uma
 * interface própria para o retorno de erros e deve ser usada!
 * 
 * @author bruno.oliveira
 * 
 */
public class CargaAppService {

	// Lista das cargas que devem ser executadas.
	private ArrayList<CargaBase> cargas;
	
	/**
	 * Construtor que não cria nenhuma fabrica de service, ja que as mesmas são chamadas dentro das Cargas (Filhos de CargaBase).
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public CargaAppService() {}
			
	/**
	 * Executa apenas a carga básica do sistema.
	 * Exatamente como aconteceria na implantação do software numa empresa:<br />
	 * ---- CargaImplantacao;<br />
	 * ---- CargaParametros;<br />
	 * ---- CargaParametrosInterdemediario.
	 * 
	 * @return Retorna o resultado da chamada do método executarCargas().
	 * @throws AplicacaoException
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public boolean executarCargaBasica() throws AplicacaoException{
		// Instancia a lista de cargas novamente, para ter certeza que apenas as cargas a seguir serão executadas.
		cargas = new ArrayList<CargaBase>();
		cargas.add(new CargaImplantacao());
		cargas.add(new CargaParametros());
		cargas.add(new CargaParametroIntermediario());
		cargas.add(new CargaAlgoritmo());
		return executarCargas();
	}
	

	/**
	 * Executa a carga com os dados de estudo inicias.
	 * Estes dados estão compatíveis com os slides (transparências)
	 * passadas pelo Pedro Peloso no início do projeto e também
	 * estão compatíveis com as planilhas de testes de algoritmos que geramos:<br />
	 * ---- CargaUsuario;<br />
	 * ---- CargaParametros;<br />
	 * ---- CargaIndicacao;<br />
	 * ---- CargaEspecialista;<br />
	 * ---- CargaAvalIndicacaoEspec;<br />
	 * ---- CargaPaciente;<br />
	 * ---- CargaAtendimento;<br />
	 * 
	 * @return Retorna o resultado da chamada do método executarCargas().
	 * @throws AplicacaoException
	 * 
	 * @author felipe.pontes
	 * 
	 */
	public boolean executarCargaExemplo1() throws AplicacaoException{
		//Instancia a lista de cargas novamente, para ter certeza que
		//apenas as cargas a seguir serao executadas.
		cargas = new ArrayList<CargaBase>();

		// Inclui usuários
		cargas.add(new CargaUsuario());
		
		//Inclui algoritmos
		cargas.add(new CargaAlgoritmo());
		
		// Inclui parametros necessarios
		cargas.add(new CargaParametros());

		//Inclui as indicações
		cargas.add(new CargaIndicacao());
		//Inclui os especialistas
		cargas.add(new CargaEspecialista());
		//Inclui as avaliações dos especialistas para as indicações cadastradas
		cargas.add(new CargaAvalIndicacaoEspec());

		//Inclui os pacientes
		cargas.add(new CargaPaciente());
		//Inclui os atendimentos e anamneses
		cargas.add(new CargaAtendimento());
		
		return executarCargas();
	}

	/**
	 * Executa a carga com os dados de estudo passados pelo Pedro Peloso.
	 * Esse estudo de caso ainda está em desenvolvimento e visa simular uma situação
	 * de uso real utilizando toda nossa base de parametros e indicações.<br />
	 * ---- executarCargaExemplo1;<br />
	 * ---- CargaPacienteIntermediaria;<br />
	 * ---- CargaIndicacaoIntermediaria;<br />
	 * ---- CargaParametroIntermediario;<br />
	 * ---- CargaAtendimentoIntermediario;<br />
	 * ---- CargaAvalIndicacaoEspecIntermediaria;<br />
	 * 
	 * @return Retorna o resultado da chamada do método executarCargas().
	 * @throws AplicacaoException
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public boolean executarCargaExemplo2() throws AplicacaoException{
		//Instancia a lista de cargas novamente, para ter certeza que
		//apenas as cargas a seguir serao executadas.
		cargas = new ArrayList<CargaBase>();
		
		//Executa a carga do exemplo1, que significa todas
		// as informações passadas pelo Pedro Peloso no inicio do projeto
		//, se ela nao rodar, retorna falso.
		if(!this.executarCargaExemplo1())
			return false;		
		
		// Inclui novos pacientes que fora fornecidos pelo Pedro Peloso
		cargas.add(new CargaPacienteIntermediaria());
		
		// Inclui novas indicações que foram fornecidas pelo Pedro Peloso
		cargas.add(new CargaIndicacaoIntermediaria());
		
		// Inclui novos parametros que foram fornecidos pelo Pedro Peloso
		cargas.add(new CargaParametroIntermediario());
		
		// Inclui novas anamneses
		cargas.add(new CargaAtendimentoIntermediario());
		
		// Inclui novas avaliações para os parametros/indicacoes previamente inseridos
		cargas.add(new CargaAvalIndicacaoEspecIntermediaria());
		
		return executarCargas();
	}

	/**
	 * Carga de teste com exemplos de 100 indicações.
	 * Os dados não são oficiais, e foram postados apenas para testar
	 * o comportamento do sistema com um volume de dados maior.<br />
	 * ---- executarCargaExemplo1;<br />
	 * ---- CargaEstudoMultiplasIndicacao;<br />
	 * ---- CargaParametroIntermediario;<br />
	 * 
	 * @return Retorna o resultado da chamada do método executarCargas().
	 * @throws AplicacaoException
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public boolean executarCargaExemplo3() throws AplicacaoException{
		//Instancia a lista de cargas novamente, para ter certeza que
		//apenas as cargas a seguir serao executadas.
		cargas = new ArrayList<CargaBase>();

		//Executa a carga do exemplo1, que significa todas
		// as informações passadas pelo Pedro Peloso no inicio do projeto
		//, se ela nao rodar, retorna falso.
		if(!this.executarCargaExemplo1())
			return false;
		
		// Inclui mais de 100 indicações
		cargas.add(new CargaEstudoMultiplasIndicacao());

		// Inclui novos parametros que foram fornecidos pelo Pedro Peloso
		cargas.add(new CargaParametroIntermediario());
		
		return executarCargas();
	}


	/**
	 * Esse método executa genericamente a lista de cargas
	 * percorrendo cada uma e chamando os respectivos métodos 'executar'.<br />
	 * Se esse método retornar false a execução das demais cargas serão interrompidas e falso será retornado.<br />
	 * Caso contrário, todas as cargas terão sido executadas e o retorno será true.
	 * 
	 * @return False - Caso ocorra algum erro durante a execução da(s) carga(s).<br /> True - Caso a execução termine sem nenhum erro.
	 * @throws AplicacaoException
	 * 
	 * @author felipe.arruda
	 * 
	 */
	private boolean executarCargas()  throws AplicacaoException{
		for(CargaBase carga : cargas){
			System.out.println(">>>executando carga:"+carga.getClass());
			if(!carga.executar()){
				return false;
			}
			System.out.println(">>>Sucesso:"+carga.getClass());
		}
		cargas = new ArrayList<CargaBase>();
		return true;
	}	
}