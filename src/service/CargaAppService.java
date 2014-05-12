package service;

import java.util.ArrayList;

import service.exception.AplicacaoException;
import carga.*;

/**
 * Esse service � responsavel por chamar as cargas do sistema.<br /><br />
 * 
 * Para isso � preciso:<br />
 * ---- Instanciar a lista de cargas como uma nova lista vazia;<br />
 * ---- Instanciar uma Carga;<br />
 * ---- Adiciona-la na lista cargas;<br />
 * ---- Retornar o m�todo executarCargas;<br /><br />
 *  
 * Lembrando que � preciso jogar uma ApplicationExeption para o CargaAction.<br />
 * Isso por que diferentemente do TestNG, essa carga � executada pelo browser, logo tem uma
 * interface pr�pria para o retorno de erros e deve ser usada!
 * 
 * @author bruno.oliveira
 * 
 */
public class CargaAppService {

	// Lista das cargas que devem ser executadas.
	private ArrayList<CargaBase> cargas;
	
	/**
	 * Construtor que n�o cria nenhuma fabrica de service, ja que as mesmas s�o chamadas dentro das Cargas (Filhos de CargaBase).
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public CargaAppService() {}
			
	/**
	 * Executa apenas a carga b�sica do sistema:<br />
	 * ---- CargaUsuario;<br />
	 * ---- CargaParametros.
	 * 
	 * @return Retorna o resultado da chamada do m�todo executarCargas().
	 * @throws AplicacaoException
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public boolean executarCargaBasica() throws AplicacaoException{
		// Instancia a lista de cargas novamente, para ter certeza que apenas as cargas a seguir ser�o executadas.
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
	 * @return Retorna o resultado da chamada do m�todo executarCargas().
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

		//Inclui as indica��es
		cargas.add(new CargaIndicacao());
		//Inclui os especialistas
		cargas.add(new CargaEspecialista());
		//Inclui as avalia��es dos especialistas para as indica��es cadastradas
		cargas.add(new CargaAvalIndicacaoEspec());

		//Inclui os pacientes
		cargas.add(new CargaPaciente());
		//Inclui os atendimentos e anamneses
		cargas.add(new CargaAtendimento());
		
		return executarCargas();
	}


	/**
	 * Esse m�todo executa genericamente a lista de cargas
	 * percorrendo cada uma e chamando os respectivos m�todos 'executar'.<br />
	 * Se esse m�todo retornar false a execu��o das demais cargas ser�o interrompidas e falso ser� retornado.<br />
	 * Caso contr�rio, todas as cargas ter�o sido executadas e o retorno ser� true.
	 * 
	 * @return False - Caso ocorra algum erro durante a execu��o da(s) carga(s).<br /> True - Caso a execu��o termine sem nenhum erro.
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