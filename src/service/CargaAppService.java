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
	 * Executa apenas a carga básica do sistema:<br />
	 * ---- CargaUsuario;<br />
	 * ---- CargaParametros.
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
	//	cargas.add(new CargaLimparUploads());
		cargas.add(new CargaUsuario());
		return executarCargas();
	}
	

	/**
	 * Executa a carga com os dados de estudo:<br />
	 * ---- executarCargaBasica;<br />
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

		//Executa a carga basica, se ela nao rodar, retorna falso.
		if(!this.executarCargaBasica())
			return false;

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