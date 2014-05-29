package service;

import java.util.List;

import DAO.ParametroDAO;
import DAO.Impl.ParametroDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;

import service.anotacao.Transacional;
import service.exception.AplicacaoException;

import modelo.Parametro;

public class ParametroAppService {
	
	private static ParametroDAO parametroDAO;
	
	public ParametroAppService() throws Exception{
		try{
			parametroDAO = FabricaDeDao.getDao(ParametroDAOImpl.class);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);	
		}
	}
	
	@Transacional
	public void inclui(Parametro parametro) throws AplicacaoException{
		try{
			parametroDAO.recuperaParametroPorCodigo(parametro.getCodParametro());
			throw new AplicacaoException("parametro.CODIGO_EXISTENTE");
		}catch(ObjetoNaoEncontradoException ob){
		}
		try{
			parametroDAO.recuperaParametroPorNome(parametro.getNome());
			throw new AplicacaoException("parametro.NOME_EXISTENTE");
		}catch(ObjetoNaoEncontradoException ob){
		}
		parametroDAO.inclui(parametro);
	}

	public List<Parametro> recuperaListaDeParametrosPaginada(){
		return parametroDAO.recuperaListaDeParametrosPaginada();
	}
	
	public Parametro recuperaParametroPorCodigio(String codigo) throws AplicacaoException{
		Parametro parametro = null;
		try {
			parametro = parametroDAO.recuperaParametroPorCodigo(codigo);
		} catch (ObjetoNaoEncontradoException ob) {
			throw new AplicacaoException("parametro.CODIGO_NAO_ENCONTRADO");
		}
		return parametro;
	}
}
